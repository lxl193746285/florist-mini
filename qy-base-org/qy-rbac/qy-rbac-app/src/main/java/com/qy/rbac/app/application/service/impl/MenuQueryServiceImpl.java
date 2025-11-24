package com.qy.rbac.app.application.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.member.api.client.MemberSystemClient;
import com.qy.rbac.app.application.assembler.MenuAssembler;
import com.qy.rbac.app.application.assembler.MenuRuleAssembler;
import com.qy.rbac.app.application.dto.*;
import com.qy.rbac.app.application.query.MenuQuery;
import com.qy.rbac.app.application.service.AppQueryService;
import com.qy.rbac.app.application.service.AuthQueryService;
import com.qy.rbac.app.application.service.MenuQueryService;
import com.qy.rbac.app.application.service.ModuleQueryService;
import com.qy.rbac.app.application.utils.ExportUtils;
import com.qy.rbac.app.config.CacheConfig;
import com.qy.rbac.app.domain.menu.MenuType;
import com.qy.rbac.app.infrastructure.persistence.MenuDataRepository;
import com.qy.rbac.app.infrastructure.persistence.RuleScopeDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.MenuMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.security.session.Identity;
import com.qy.util.TreeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单查询服务实现
 *
 * @author legendjw
 */
@Service
public class MenuQueryServiceImpl implements MenuQueryService {
    public static String authItemCategoryCodeTable = "auth_item_category";
    public static String authTypeCategoryCodeTable = "rbac_auth_type";
    private MenuAssembler menuAssembler;
    private MenuDataRepository menuDataRepository;
    private AuthQueryService authQueryService;
    private ModuleQueryService moduleQueryService;
    private AppQueryService appQueryService;
    private RuleScopeDataRepository ruleScopeDataRepository;
    private CodeTableClient codeTableClient;
    private HttpServletResponse response;
    private MenuMapper menuMapper;
    private RedisTemplate redisTemplate;

    public MenuQueryServiceImpl(MenuAssembler menuAssembler, MenuDataRepository menuDataRepository,
                                AuthQueryService authQueryService, ModuleQueryService moduleQueryService,
                                AppQueryService appQueryService, RuleScopeDataRepository ruleScopeDataRepository,
                                CodeTableClient codeTableClient, HttpServletResponse response,
                                MenuMapper menuMapper, RedisTemplate redisTemplate) {
        this.menuAssembler = menuAssembler;
        this.menuDataRepository = menuDataRepository;
        this.authQueryService = authQueryService;
        this.moduleQueryService = moduleQueryService;
        this.appQueryService = appQueryService;
        this.ruleScopeDataRepository = ruleScopeDataRepository;
        this.codeTableClient = codeTableClient;
        this.response = response;
        this.menuMapper = menuMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<MenuDTO> getMenus(MenuQuery query, Identity identity) {
        List<MenuDO> menuDOS = menuDataRepository.findByQuery(query);
        List<ModuleBasicDTO> moduleBasicDTOS = menuDOS.isEmpty() ? new ArrayList<>() :
                moduleQueryService.getBasicModulesByIds(menuDOS.stream().map(MenuDO::getModuleId).collect(Collectors.toList()));
        List<MenuDO> parentMenus = menuDOS.isEmpty() ? new ArrayList<>() :
                menuDataRepository.findByIds(menuDOS.stream().map(MenuDO::getParentId).collect(Collectors.toList()));
        List<CodeTableItemBasicDTO> authItemCategories = codeTableClient.getSystemBasicCodeTableItems(authItemCategoryCodeTable);
        List<MenuDTO> menuDTOS = menuDOS.stream().map(m -> menuAssembler.toDTO(m,
                moduleBasicDTOS, parentMenus, authItemCategories, identity)).collect(Collectors.toList());
        return menuDTOS;
    }

    @Override
    public MenuDTO getMenuById(Long id, Identity identity) {
        MenuDO menuDO = menuDataRepository.findById(id);
        List<Long> moduleIds = new ArrayList<>();
        moduleIds.add(menuDO.getModuleId());
        List<ModuleBasicDTO> moduleBasicDTO = moduleQueryService.getBasicModulesByIds(moduleIds);
        List<Long> parentIds = new ArrayList<>();
        parentIds.add(menuDO.getParentId());
        return menuAssembler.toDTO(menuDO, moduleBasicDTO, menuDataRepository.findByIds(parentIds),
                codeTableClient.getSystemBasicCodeTableItems(authItemCategoryCodeTable), identity);
    }

    @Override
    public List<MenuDTO> getPermissionParentMenus(String userId, String context, String contextId) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId, context, contextId);
        List<String> authItems = permissions.stream().map(PermissionWithRuleDTO::getPermission).collect(Collectors.toList());
        List<MenuDO> menuDOS = menuDataRepository.findPermissionMenusAndParent(authItems);
        List<MenuDTO> menuDTOS = menuAssembler.toDTOs(menuDOS, null, null, null, null);
        List<CodeTableItemBasicDTO> authType = codeTableClient.getSystemBasicCodeTableItems(authTypeCategoryCodeTable);
        for (MenuDTO menuDTO : menuDTOS) {
            if (menuDTO.getTypeId() == MenuType.GENERAL_MENU.getId()) {
                menuDTO.setParentId(0L);
            } else {
                for (CodeTableItemBasicDTO authTypeDTO : authType){
                    if (authTypeDTO.getId().equals(menuDTO.getAuthTypeId().toString())){
                        menuDTO.setAuthTypeName(authTypeDTO.getName());
                    }
                }
            }

        }
        return TreeUtils.toTree(menuDTOS);
    }

    @Override
    public List<ModuleMenuBasicDTO> getUserFrontendMenus(String userId, String clientId) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId);
        AppBasicDTO app = appQueryService.getBasicAppByClientId(clientId);
        return getAppMenus(app, permissions, EnableDisableStatus.ENABLE);
    }

    @Override
    public List<ModuleMenuBasicDTO> getUserFrontendMenus(String userId, String context, String contextId, String clientId) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId, context, contextId);
        AppBasicDTO app = appQueryService.getBasicAppByClientId(clientId);
        return getAppMenus(app, permissions, EnableDisableStatus.ENABLE);
    }

    @Override
    public List<MenuBasicDTO> getUserAppMenus(String userId, String appCode, String moduleCode) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId);
        AppBasicDTO app = appQueryService.getBasicAppByCode(appCode);
        if (app == null) { return new ArrayList<>(); }
        ModuleBasicDTO module = moduleQueryService.getBasicModuleByAppAndCode(app.getId(), moduleCode);
        return getAppMenus(app, module, permissions, EnableDisableStatus.ENABLE);
    }

    @Override
    public List<MenuBasicDTO> getUserAppMenus(String userId, String context, String contextId, String appCode, String moduleCode) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId, context, contextId);
        AppBasicDTO app = appQueryService.getBasicAppByCode(appCode);
        if (app == null) { return new ArrayList<>(); }
        ModuleBasicDTO module = moduleQueryService.getBasicModuleByAppAndCode(app.getId(), moduleCode);
        if (module == null) { return new ArrayList<>(); }
        return getAppMenus(app, module, permissions, EnableDisableStatus.ENABLE);
    }

    @Override
    public List<ModuleMenuBasicDTO> getUserAuthorizedMenus(String userId, String context, String contextId, String memberSystemId) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId, context, contextId);
        return getAppMenus(permissions, null, memberSystemId);
    }

    @Override
    public List<CategoryPermissionMenuDTO> getUserAuthorizedMenuPermissions(String userId, String context, String contextId, Long menuId) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId, context, contextId);
        List<MenuDO> menuDOS = menuDataRepository.findChildPermissionMenus(menuId, null);
        //过滤没有权限的
        menuDOS = menuDOS.stream().filter(m -> permissions.stream().anyMatch(p -> p.getPermission().equals(m.getAuthItem()))).collect(Collectors.toList());
        if (menuDOS.isEmpty()) { return new ArrayList<>(); }

        //获取按照权限分类分组的权限菜单
        List<CategoryPermissionMenuDTO> permissionMenuDTOS = new ArrayList<>();
        List<RuleScopeDO> ruleScopeDOS = ruleScopeDataRepository.findAll();
        List<CodeTableItemBasicDTO> authItemCategories = codeTableClient.getSystemBasicCodeTableItems(authItemCategoryCodeTable);
        Map<String, List<MenuDO>> menuGroup = menuDOS.stream().collect(Collectors.groupingBy(MenuDO::getAuthItemCategory));
        for (CodeTableItemBasicDTO authItemCategory : authItemCategories) {
            if (!menuGroup.containsKey(authItemCategory.getId())) { continue; }

            CategoryPermissionMenuDTO permissionMenuDTO = new CategoryPermissionMenuDTO();
            permissionMenuDTO.setId(authItemCategory.getId());
            permissionMenuDTO.setName(authItemCategory.getName());

            //返回的菜单权限范围需要根据用户的范围进行过滤
            List<PermissionMenuDTO> permissionMenus = menuAssembler.toPermissionMenu(menuGroup.get(authItemCategory.getId()), ruleScopeDOS);
            for (PermissionMenuDTO permissionMenu : permissionMenus) {
                if (permissionMenu.getRules().isEmpty()) { continue; }
                PermissionWithRuleDTO permission = permissions.stream().filter(p -> p.getPermission().equals(permissionMenu.getAuthItem())).findFirst().orElse(null);
                List<RuleScopeBasicDTO> rules = new ArrayList<>();
                boolean isFind = false;
                for (RuleScopeBasicDTO rule : permissionMenu.getRules()) {
                    if (!isFind && rule.getId().equals(permission.getRuleScopeId())) {
                        isFind = true;
                    }
                    if (isFind) {
                        rules.add(rule);
                    }
                }
                permissionMenu.setRules(rules);
            }
            permissionMenuDTO.setPermissions(permissionMenus);

            permissionMenuDTOS.add(permissionMenuDTO);
        }

        return permissionMenuDTOS;
    }

    @Override
    public List<MenuBasicDTO> getUserAuthorizedMenuList(String userId, String context, String contextId) {
        List<PermissionWithRuleDTO> permissions = authQueryService.getUserPermissions(userId, context, contextId);
        List<MenuDO> menuDOS = menuDataRepository.findChildPermissionMenus(null, null);
        //过滤没有权限的
        menuDOS = menuDOS.stream().filter(m -> permissions.stream().anyMatch(p -> p.getPermission().equals(m.getAuthItem()))).collect(Collectors.toList());
        if (menuDOS.isEmpty()) { return new ArrayList<>(); }
        List<MenuBasicDTO> dtos = new ArrayList<>();
        menuDOS.stream().map(menuDO -> {
            dtos.add(menuAssembler.toBasicDTO(menuDO));
            return menuDO;
        }).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public Map<String, MenuWithAuthItemDTO> getMenuGroupByAuthItem() {
        Object cache = redisTemplate.opsForValue().get(CacheConfig.rbacMenuAuthMenuCacheKey);
//        Cache cache = cacheManager.getCache(CacheConfig.rbacMenuCacheName);
//        String cacheKey = CacheConfig.rbacMenuAuthMenuCacheKey;
        if (cache!= null) { return (Map<String, MenuWithAuthItemDTO>) cache; }

        List<MenuDO> permissionMenus = menuDataRepository.findAllPermissionMenus();
        List<MenuWithAuthItemDTO> menus = menuAssembler.toMenuWithAuthItem(permissionMenus);
        Map<String, MenuWithAuthItemDTO> map = new HashMap<>();
        for (MenuWithAuthItemDTO menu : menus) {
            map.put(menu.getAuthItem(), menu);
        }
        redisTemplate.opsForValue().set(CacheConfig.rbacMenuAuthMenuCacheKey, map);
//        cache.put(cacheKey, map);
        return map;
    }

    @Override
    public String getMenuNameByAuthItem(String authItem) {
        Map<String, MenuWithAuthItemDTO> map = getMenuGroupByAuthItem();
        if (map.containsKey(authItem)) {
            MenuWithAuthItemDTO menuWithAuthItemDTO = map.get(authItem);
            return StringUtils.isNotBlank(menuWithAuthItemDTO.getAliasName()) ? menuWithAuthItemDTO.getAliasName() : menuWithAuthItemDTO.getName();
        }
        else {
            return "";
        }
    }

    @Override
    public void exportMenus(MenuQuery query, Identity identity){
        List<MenuDO> menuDOS = getByQuery(query);
        List<ModuleBasicDTO> moduleBasicDTOS = menuDOS.isEmpty() ? new ArrayList<>() : moduleQueryService.getBasicModulesByIds(menuDOS.stream().map(MenuDO::getModuleId).collect(Collectors.toList()));
        List<MenuDO> parentMenus = menuDOS.isEmpty() ? new ArrayList<>() : menuDataRepository.findByIds(menuDOS.stream().map(MenuDO::getParentId).collect(Collectors.toList()));
        List<CodeTableItemBasicDTO> authItemCategories = codeTableClient.getSystemBasicCodeTableItems(authItemCategoryCodeTable);
        List<MenuDTO> menuDTOS = TreeUtils.toTree(menuDOS.stream().map(m -> menuAssembler.toDTO(m, moduleBasicDTOS, parentMenus, authItemCategories, identity)).collect(Collectors.toList()));
        List<ModuleBasicDTO> modules = moduleQueryService.getBasicModulesByIds(menuDTOS.stream().map(MenuDTO::getModuleId).collect(Collectors.toList()));
        List<AppBasicDTO> apps = appQueryService.getBasicAppsByIds(modules.stream().map(ModuleBasicDTO::getAppId).collect(Collectors.toList()));
        for (AppBasicDTO app : apps) {
            List<MenuDTO> menus = new ArrayList<>();
            for (ModuleBasicDTO moduleBasicDTO : modules) {
                for (MenuDTO menuDTO : menuDTOS) {
                    if(app.getId().equals(moduleBasicDTO.getAppId()) && menuDTO.getModuleId().equals(moduleBasicDTO.getId())){
                        menus.add(menuDTO);
                    }
                }
            }
            app.setMenuDTOs(menus);
        }

        String title = "菜单列表";
        String[] rowsName = new String[]{"应用","模块","一级菜单", "二级菜单", "外链", "功能标示", "权限菜单"
        };
        //创建工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建表格
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFCellStyle style = ExportUtils.getColumnTopStyle(workbook);
        HSSFRow rowTitle = sheet.createRow(0);
        for (int i = 0; i < rowsName.length; i++) {
            HSSFCell cell = rowTitle.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(rowsName[i]);
        }
        HSSFCellStyle cellStyle = ExportUtils.getStyle(workbook);

        int rowNum = 0;
        for (int l = 0; l < apps.size(); l++) {
            for (int i = 0; i < apps.get(l).getMenuDTOs().size(); i++) {
                List<Object[]> dataList = new ArrayList<Object[]>();
                Object[] objs;
                boolean flag = false;
                if(CollectionUtils.isEmpty(apps.get(l).getMenuDTOs().get(i).getChildren())){
                    objs = new Object[rowsName.length];
                    objs[0] = apps.get(l).getName();
                    objs[1] = apps.get(l).getMenuDTOs().get(i).getModuleName();
                    objs[2] = apps.get(l).getMenuDTOs().get(i).getName();
                    objs[3] = "";
                    objs[4] = apps.get(l).getMenuDTOs().get(i).getExternalLink();
                    objs[5] = apps.get(l).getMenuDTOs().get(i).getCode();
                    objs[6] = "";
                    dataList.add(objs);
                }else{
                    String typeName= "";
                    for (int j = 0; j < apps.get(l).getMenuDTOs().get(i).getChildren().size(); j++) {
                        objs = new Object[rowsName.length];
                        objs[0] = apps.get(l).getName();
                        objs[1] = apps.get(l).getMenuDTOs().get(i).getModuleName();
                        objs[2] = apps.get(l).getMenuDTOs().get(i).getName();
                        objs[4] = apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getExternalLink();
                        objs[5] = apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getCode();
                        //判断是普通还是权限菜单
                        if(apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getTypeId().intValue() == 0){
                            objs[3] = apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getName();
                        }else{
                            objs[3] = "";
                            flag = true;
                        }
                        if(CollectionUtils.isEmpty(apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getChildren())){
                            //判断是普通还是权限菜单
                            if(apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getTypeId().intValue() == 1) {
                                if (StringUtils.isEmpty(typeName.trim())) {
                                    typeName = apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getName();
                                } else {
                                    typeName = typeName + "、" + apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getName();
                                }
                                objs[6] = typeName;
                            }else{
                                objs[6] = "";
                            }
                        }else{
                            String threeName= "";
                            for (MenuDTO threeMenuDTO : apps.get(l).getMenuDTOs().get(i).getChildren().get(j).getChildren()) {
                                if(StringUtils.isEmpty(threeName.trim())){
                                    threeName = threeMenuDTO.getName();
                                }else{
                                    threeName = threeName + "、" + threeMenuDTO.getName();
                                }
                            }
                            objs[6] = threeName;
                        }
                        if(!flag) {
                            dataList.add(objs);
                        }
                    }
                    if(flag){
                        objs = new Object[rowsName.length];
                        objs[0] = apps.get(l).getName();
                        objs[1] = apps.get(l).getMenuDTOs().get(i).getModuleName();
                        objs[2] = apps.get(l).getMenuDTOs().get(i).getName();
                        objs[3] = "";
                        objs[4] = apps.get(l).getMenuDTOs().get(i).getExternalLink();
                        objs[5] = apps.get(l).getMenuDTOs().get(i).getCode();
                        objs[6] = typeName;
                        dataList.add(objs);
                    }
                }
                for (int k = 0;k < dataList.size(); k++) {
                    rowNum += 1;
                    HSSFRow dataRow = sheet.createRow(rowNum);
                    Object[] data = dataList.get(k);
                    for (int j =0; j<data.length ;j++) {
                        HSSFCell cell = dataRow.createCell(j);
                        cell.setCellValue(data[j] == null ? "" : data[j].toString());
                        cell.setCellStyle(cellStyle);
                    }
                }
                if(!flag) {
                    if (apps.get(l).getMenuDTOs().get(i).getChildren() != null && apps.get(l).getMenuDTOs().get(i).getChildren().size() > 1) {
                        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1 - apps.get(l).getMenuDTOs().get(i).getChildren().size(), rowNum, 2, 2));
                    }
                }
            }
        }
        for (int i = 1; i <= rowNum; i++) {
            Map<String, Integer> mapAppName = new HashMap<>();
            Map<String, Integer> mapModuleName = new HashMap<>();
            List<Map<String, Integer>> listModuleName = new ArrayList<>();
            for (int j = i+1; j <= rowNum; j++) {
                if(sheet.getRow(i).getCell(0).toString().equals(sheet.getRow(j).getCell(0).toString())){
                    mapAppName.put("start", i);
                    mapAppName.put("end", j);
                    if(j == rowNum){
                        i = j-1;
                    }
                }else{
                    i = j-1;
                    break;
                }
            }
            if(mapAppName.size() > 1) {
                for (int j = mapAppName.get("start"); j <= mapAppName.get("end"); j++) {
                    for (int k = j + 1; k <= mapAppName.get("end"); k++) {
                        if (sheet.getRow(j).getCell(1).toString().equals(sheet.getRow(k).getCell(1).toString())) {
                            mapModuleName.put("start", j);
                            mapModuleName.put("end", k);
                            if(k == mapAppName.get("end")){
                                j = k;
                            }
                        } else {
                            if (mapModuleName.size() > 1) {
                                listModuleName.add(mapModuleName);
                                mapModuleName = new HashMap<>();
                            }
                            j = k-1;
                            break;
                        }
                    }
                    if (mapModuleName.size() > 1) {
                        listModuleName.add(mapModuleName);
                        mapModuleName = new HashMap<>();
                    }
                }
            }
            if(mapAppName.size() > 1){
                sheet.addMergedRegion(new CellRangeAddress(mapAppName.get("start"), mapAppName.get("end"), 0, 0));
            }
            for (Map<String, Integer> map : listModuleName) {
                sheet.addMergedRegion(new CellRangeAddress(map.get("start"), map.get("end"), 1, 1));
            }
        }

        try {
            if (workbook != null) {
                Format format = new SimpleDateFormat("yyyyMMdd");
                String fileName = "Excel-" + format.format(new Date()) + title+".xls";
                try {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";" + "filename*=utf-8''" + fileName);
                response.setContentType("application/x-msdownload");
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MenuBasicDTO> getAuthMenuByCode(String code, Integer authType) {
        MenuDO menuDO = menuDataRepository.findByCode(code);
        if (menuDO == null){
            return null;
        }

        //TODO 权限菜单类型设置
        List<MenuDO> menuDOS = menuDataRepository.findChildPermissionMenus(menuDO.getId(), authType);
        if (menuDOS == null){
            return null;
        }
        return menuDOS.stream().map(m -> menuAssembler.toBasicDTO(m)).collect(Collectors.toList());
    }

    private List<ModuleMenuBasicDTO> getAppMenus(AppBasicDTO app, List<PermissionWithRuleDTO> permissions, EnableDisableStatus menuStatus) {
        List<String> authItems = permissions.stream().map(PermissionWithRuleDTO::getPermission).collect(Collectors.toList());
        List<MenuDO> menuDOS = menuDataRepository.findGeneralByAuthItems(authItems, menuStatus != null ? menuStatus.getId() : null);
        List<MenuBasicDTO> menuBasicDTOS = menuDOS.stream().map(m -> menuAssembler.toBasicDTO(m)).collect(Collectors.toList());
        List<MenuBasicDTO> menuBasicTrees = TreeUtils.toTree(menuBasicDTOS);
        List<ModuleBasicDTO> modules = moduleQueryService.getBasicModulesByIds(menuBasicTrees.stream().map(MenuBasicDTO::getModuleId).collect(Collectors.toList()));
        List<ModuleMenuBasicDTO> moduleMenus = new ArrayList<>();
        for (ModuleBasicDTO module : modules) {
            if (module.getStatusId().intValue() == EnableDisableStatus.DISABLE.getId() || !module.getAppId().equals(app.getId())) {
                continue;
            }
            ModuleMenuBasicDTO moduleMenuBasicDTO = new ModuleMenuBasicDTO();
            moduleMenuBasicDTO.setId(module.getId());
            moduleMenuBasicDTO.setParentId(0L);
            moduleMenuBasicDTO.setName(module.getName());
            moduleMenuBasicDTO.setCode(module.getCode());
            for (MenuBasicDTO menuBasicTree : menuBasicTrees) {
                if (menuBasicTree.getModuleId().equals(module.getId())) {
                    menuBasicTree.setParentId(module.getId());
                    moduleMenuBasicDTO.getChildren().add(menuBasicTree);
                }
            }
            moduleMenus.add(moduleMenuBasicDTO);
        }
        return moduleMenus;
    }

    private List<ModuleMenuBasicDTO> getAppMenus(List<PermissionWithRuleDTO> permissions, EnableDisableStatus menuStatus, String memberSystemId) {
        List<String> authItems = permissions.stream().map(PermissionWithRuleDTO::getPermission).collect(Collectors.toList());
        List<MenuDO> menuDOS = menuDataRepository.findGeneralByAuthItems(authItems, menuStatus != null ? menuStatus.getId() : null);
        List<MenuBasicDTO> menuBasicDTOS = menuDOS.stream().map(m -> menuAssembler.toBasicDTO(m)).collect(Collectors.toList());
        List<MenuBasicDTO> menuBasicTrees = TreeUtils.toTree(menuBasicDTOS);
        List<ModuleBasicDTO> modules = moduleQueryService.
                getBasicModulesByIds(menuBasicTrees.stream().map(MenuBasicDTO::getModuleId).collect(Collectors.toList()));
        List<AppBasicDTO> apps = appQueryService.getBasicAppsBySystemId(modules.stream().map(ModuleBasicDTO::getAppId).collect(Collectors.toList()),
               memberSystemId);
        List<ModuleMenuBasicDTO> appMenus = new ArrayList<>();
        for (AppBasicDTO app : apps) {
            ModuleMenuBasicDTO appMenu = new ModuleMenuBasicDTO();
            appMenu.setId(app.getId());
            appMenu.setParentId(0L);
            appMenu.setName(app.getName());
            appMenu.setCode(app.getCode());
            List<MenuBasicDTO> appModuleMenus = new ArrayList<>();
            for (ModuleBasicDTO module : modules) {
                if (!module.getAppId().equals(app.getId())) {
                    continue;
                }
                MenuBasicDTO moduleMenu = new MenuBasicDTO();
                moduleMenu.setId(module.getId());
                moduleMenu.setParentId(appMenu.getId());
                moduleMenu.setName(module.getName());
                moduleMenu.setCode(module.getCode());
                for (MenuBasicDTO menuBasicTree : menuBasicTrees) {
                    if (!menuBasicTree.getModuleId().equals(module.getId())) {
                        continue;
                    }
                    menuBasicTree.setParentId(module.getId());
                    moduleMenu.getChildren().add(menuBasicTree);
                }
                appModuleMenus.add(moduleMenu);
            }
            appMenu.setChildren(appModuleMenus);
            appMenus.add(appMenu);
        }
        return appMenus;
    }

    private List<MenuBasicDTO> getAppMenus(AppBasicDTO app, ModuleBasicDTO module, List<PermissionWithRuleDTO> permissions, EnableDisableStatus menuStatus) {
        List<String> authItems = permissions.stream().map(PermissionWithRuleDTO::getPermission).collect(Collectors.toList());
        List<MenuDO> menuDOS = menuDataRepository.findGeneralByAuthItems(authItems, menuStatus != null ? menuStatus.getId() : null);
        List<MenuBasicDTO> menuBasicDTOS = menuDOS.stream().map(m -> menuAssembler.toBasicDTO(m)).collect(Collectors.toList());
        List<MenuBasicDTO> menuBasicTrees = TreeUtils.toTree(menuBasicDTOS);
        List<MenuBasicDTO> menus = new ArrayList<>();
        for (MenuBasicDTO menuBasicTree : menuBasicTrees) {
            if (menuBasicTree.getModuleId().equals(module.getId())) {
                menus.add(menuBasicTree);
            }
        }
        return menus;
    }

    private List<MenuDO> getByQuery(MenuQuery query) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(MenuDO::getModuleId)
                .orderByAsc(MenuDO::getSort)
                .orderByAsc(MenuDO::getId);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(MenuDO::getName, query.getKeywords()));
        }
        if (query.getModuleId() != null) {
            queryWrapper.eq(MenuDO::getModuleId, query.getModuleId());
        }
        if (query.getTypeId() != null) {
            queryWrapper.eq(MenuDO::getTypeId, query.getTypeId());
        }
        List<MenuDO> menuDOS = menuMapper.selectList(queryWrapper);

        return menuDOS;
    }
}
