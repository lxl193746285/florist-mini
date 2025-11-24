package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.command.BatchDeleteMenuCommand;
import com.qy.rbac.app.application.command.CreateMenuCommand;
import com.qy.rbac.app.application.command.DeleteMenuCommand;
import com.qy.rbac.app.application.command.UpdateMenuCommand;
import com.qy.rbac.app.application.enums.CommonPermissionAction;
import com.qy.rbac.app.application.service.AuthCommandService;
import com.qy.rbac.app.application.service.MenuCommandService;
import com.qy.rbac.app.application.service.PermissionCommandService;
import com.qy.rbac.app.application.service.PermissionQueryService;
import com.qy.rbac.app.config.CacheConfig;
import com.qy.rbac.app.domain.auth.PermissionId;
import com.qy.rbac.app.domain.menu.*;
import com.qy.rbac.app.domain.share.User;
import com.qy.rbac.app.infrastructure.persistence.MenuDataRepository;
import com.qy.rbac.app.infrastructure.persistence.MenuDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单命令服务实现
 *
 * @author legendjw
 */
@Service
public class MenuCommandServiceImpl implements MenuCommandService {
    private MenuDomainRepository menuDomainRepository;
    private MenuDataRepository menuDataRepository;
    private PermissionQueryService permissionQueryService;
    private PermissionCommandService permissionCommandService;
    private AuthCommandService authCommandService;
    private RedisTemplate redisTemplate;

    public MenuCommandServiceImpl(MenuDomainRepository menuDomainRepository, MenuDataRepository menuDataRepository,
                                  PermissionQueryService permissionQueryService, PermissionCommandService permissionCommandService,
                                  AuthCommandService authCommandService, RedisTemplate redisTemplate) {
        this.menuDomainRepository = menuDomainRepository;
        this.menuDataRepository = menuDataRepository;
        this.permissionQueryService = permissionQueryService;
        this.permissionCommandService = permissionCommandService;
        this.authCommandService = authCommandService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public MenuId createGeneralMenu(CreateMenuCommand command) {
        if (StringUtils.isEmpty(command.getCode())){
            throw new ValidationException("请输入功能标识");
        }
        Identity identity = command.getIdentity();
        if (menuDomainRepository.countByModuleAndTypeAndName(command.getModuleId(), command.getTypeId(), command.getName(), null) > 0) {
            throw new ValidationException("指定的菜单名称已经存在");
        }
        if (command.getTypeId() == MenuType.GENERAL_MENU.getId() && menuDomainRepository.countByCode(command.getCode(), null) > 0){
            throw new ValidationException("功能标识已存在");
        }

        GeneralMenu generalMenu = GeneralMenu.builder()
                .moduleId(new ModuleId(command.getModuleId()))
                .parentId(new MenuId(command.getParentId()))
                .name(command.getName())
                .aliasName(command.getAliasName())
                .code(command.getCode())
                .externalLink(command.getExternalLink())
                .extraData(command.getExtraData())
                .icon(command.getIcon())
                .status(ShowHiddenStatus.getById(command.getStatusId()))
                .sort(command.getSort())
                .remark(command.getRemark())
                .creator(new User(identity.getId(), identity.getName()))
                .createTime(LocalDateTime.now())
                .build();
        MenuId menuId = menuDomainRepository.saveGeneralMenu(generalMenu);

        //创建子权限菜单
        if (!command.getChildPermissionMenus().isEmpty()) {
            for (String childPermissionMenu : command.getChildPermissionMenus()) {
                CommonPermissionAction permissionAction = CommonPermissionAction.getById(childPermissionMenu);
                if (permissionAction == null) { continue; }
                CreateMenuCommand createChildCommand = new CreateMenuCommand();
                createChildCommand.setIdentity(command.getIdentity());
                createChildCommand.setName(permissionAction.getName());
                createChildCommand.setModuleId(command.getModuleId());
                createChildCommand.setParentId(menuId.getId());
                createChildCommand.setStatusId(command.getStatusId());
                createChildCommand.setAuthItem(String.format("%s/%s", command.getAuthItemPrefix(), permissionAction.getId()));
                createChildCommand.setAuthItemCategory("basic");
                createChildCommand.setRefreshRootPermission(false);
                createChildCommand.setAuthTypeId(permissionAction.getType());
                createChildCommand.setAuthTypeName(permissionAction.getTypeName());
                createPermissionMenu(createChildCommand);
            }
        }
        // 增加页面权限
        CreateMenuCommand createMenuCommand = new CreateMenuCommand();
        createMenuCommand.setIdentity(command.getIdentity());
        createMenuCommand.setName(CommonPermissionAction.MENU.getName());
        createMenuCommand.setModuleId(command.getModuleId());
        createMenuCommand.setParentId(menuId.getId());
        createMenuCommand.setStatusId(command.getStatusId());
        createMenuCommand.setAuthItem(String.format("%s/%s", command.getAuthItemPrefix(), CommonPermissionAction.MENU.getId()));
        createMenuCommand.setAuthItemCategory(CommonPermissionAction.MENU.getId());// 菜单权限
        createMenuCommand.setRefreshRootPermission(false);
        createMenuCommand.setAuthTypeId(CommonPermissionAction.MENU.getType());
        createMenuCommand.setAuthTypeName(CommonPermissionAction.MENU.getTypeName());
        createPermissionMenu(createMenuCommand);

        //刷新超管权限
        if (command.isRefreshRootPermission()) {
            authCommandService.authorizeAllPermissionToRoot();
        }

        //清除权限菜单缓存
        clearAuthMenuCache();

        return menuId;
    }

    @Override
    public MenuId createPathMenu(CreateMenuCommand command) {
        Identity identity = command.getIdentity();
        if (menuDomainRepository.countByModuleAndTypeAndName(command.getModuleId(), command.getTypeId(), command.getName(), null) > 0) {
            throw new ValidationException("指定的菜单名称已经存在");
        }
        GeneralMenu generalMenu = GeneralMenu.builder()
                .moduleId(new ModuleId(command.getModuleId()))
                .parentId(new MenuId(command.getParentId()))
                .name(command.getName())
                .aliasName(command.getAliasName())
                .code(command.getCode())
                .externalLink(command.getExternalLink())
                .extraData(command.getExtraData())
                .icon(command.getIcon())
                .status(ShowHiddenStatus.getById(command.getStatusId()))
                .sort(command.getSort())
                .remark(command.getRemark())
                .creator(new User(identity.getId(), identity.getName()))
                .createTime(LocalDateTime.now())
                .build();
        MenuId menuId = menuDomainRepository.savePathMenu(generalMenu);

        //刷新超管权限
        if (command.isRefreshRootPermission()) {
            authCommandService.authorizeAllPermissionToRoot();
        }
        //清除权限菜单缓存
        clearAuthMenuCache();
        return menuId;
    }

    @Override
    @Transactional
    public MenuId createPermissionMenu(CreateMenuCommand command) {
        Identity identity = command.getIdentity();
        if (StringUtils.isBlank(command.getAuthItem())) {
            throw new ValidationException("权限菜单权限节点不能为空");
        }
        if (menuDomainRepository.countByAuthItem(command.getAuthItem(), null) > 0) {
            throw new ValidationException(String.format("%s权限节点已经存在", command.getAuthItem()));
        }
        GeneralMenu parentMenu = menuDomainRepository.findGeneralMenuById(new MenuId(command.getParentId()));
        if (parentMenu == null) {
            throw new ValidationException("未找到此菜单的父级菜单");
        }
        List<MenuRule> menuRules = command.getRules().stream().map(r -> {
            return MenuRule.builder().name(r.getName()).scopeId(r.getScopeId()).sort(r.getSort()).build();
        }).collect(Collectors.toList());

        //保存权限菜单
        PermissionMenu permissionMenu = PermissionMenu.builder()
                .moduleId(new ModuleId(command.getModuleId()))
                .parentId(parentMenu.getId())
                .name(command.getName())
                .aliasName(command.getAliasName())
                .code(command.getCode())
                .authItem(command.getAuthItem())
                .authItemCategory(command.getAuthItemCategory())
                .authItemExplain(command.getAuthItemExplain())
                .extraData(command.getExtraData())
                .icon(command.getIcon())
                .status(ShowHiddenStatus.getById(command.getStatusId()))
                .sort(command.getSort())
                .remark(command.getRemark())
                .rules(menuRules)
                .creator(new User(identity.getId(), identity.getName()))
                .createTime(LocalDateTime.now())
                .externalLink(command.getExternalLink())
                .authTypeId(command.getAuthTypeId())
                .authTypeName(command.getAuthTypeName())
                .build();
        MenuId menuId = menuDomainRepository.savePermissionMenu(permissionMenu);

        //保存权限节点
        PermissionId permissionId = permissionCommandService.createPermission(permissionMenu.getAuthItem(),
                String.format("%s-%s", parentMenu.getName(), permissionMenu.getName()));

        //刷新超管权限
        if (command.isRefreshRootPermission()) {
            authCommandService.authorizeAllPermissionToRoot();

            //清除权限菜单缓存
            clearAuthMenuCache();
        }

        return menuId;
    }

    @Override
    @Transactional
    public void updateGeneralMenu(UpdateMenuCommand command) {
        GeneralMenu generalMenu = menuDomainRepository.findGeneralMenuById(new MenuId(command.getId()));
        if (generalMenu == null) {
            throw new NotFoundException("未找到指定的菜单");
        }
        if (menuDomainRepository.countByModuleAndTypeAndName(command.getModuleId(), command.getTypeId(),
                command.getName(), generalMenu.getId().getId()) > 0) {
            throw new ValidationException("指定的菜单名称已经存在");
        }
        if (command.getTypeId() == MenuType.GENERAL_MENU.getId() &&
                menuDomainRepository.countByCode(command.getCode(), command.getId()) > 0){
            throw new ValidationException("功能标识已存在");
        }
        boolean isChangeModule = !command.getModuleId().equals(generalMenu.getModuleId().getId());
        generalMenu.modifyInfo(command);
        menuDomainRepository.saveGeneralMenu(generalMenu);
        if (isChangeModule) {
            menuDomainRepository.batchUpdateChildMenuModule(generalMenu.getId(), new ModuleId(command.getModuleId()));
        }

        //清除权限菜单缓存
        clearAuthMenuCache();
    }

    @Override
    public void updatePathMenu(UpdateMenuCommand command) {
        GeneralMenu generalMenu = menuDomainRepository.findGeneralMenuById(new MenuId(command.getId()));
        if (generalMenu == null) {
            throw new NotFoundException("未找到指定的菜单");
        }
        if (menuDomainRepository.countByModuleAndTypeAndName(command.getModuleId(), command.getTypeId(),
                command.getName(), generalMenu.getId().getId()) > 0) {
            throw new ValidationException("指定的菜单名称已经存在");
        }
        boolean isChangeModule = !command.getModuleId().equals(generalMenu.getModuleId().getId());
        generalMenu.modifyInfo(command);
        menuDomainRepository.saveGeneralMenu(generalMenu);
        if (isChangeModule) {
            menuDomainRepository.batchUpdateChildMenuModule(generalMenu.getId(), new ModuleId(command.getModuleId()));
        }

    }

    @Override
    @Transactional
    public void updatePermissionMenu(UpdateMenuCommand command) {
        PermissionMenu permissionMenu = menuDomainRepository.findPermissionMenuById(new MenuId(command.getId()));
        if (permissionMenu == null) {
            throw new NotFoundException("未找到指定的菜单");
        }
        if (menuDomainRepository.countByAuthItem(command.getAuthItem(), permissionMenu.getId().getId()) > 0) {
            throw new ValidationException(String.format("%s权限节点已经存在", command.getAuthItem()));
        }
        if (!command.getModuleId().equals(permissionMenu.getModuleId().getId())) {
            throw new NotFoundException("权限菜单不能更换模块，请调整顶级的普通菜单");
        }
        String oldPermissionAuthItem = permissionMenu.getAuthItem();
        GeneralMenu parentMenu = menuDomainRepository.findGeneralMenuById(permissionMenu.getParentId());
        List<MenuRule> menuRules = command.getRules().stream().map(r -> {
            return MenuRule.builder().id(new MenuRuleId(r.getId())).name(r.getName()).scopeId(r.getScopeId()).sort(r.getSort()).build();
        }).collect(Collectors.toList());

        //保存权限菜单
        permissionMenu.modifyInfo(command);
        permissionMenu.modifyRules(menuRules);
        menuDomainRepository.savePermissionMenu(permissionMenu);

        //修改权限节点
        if (!oldPermissionAuthItem.equals(permissionMenu.getAuthItem())) {
            permissionCommandService.updatePermission(oldPermissionAuthItem, permissionMenu.getAuthItem(), String.format("%s-%s",
                    parentMenu.getName(), permissionMenu.getName()));
        }

        //刷新超管权限
        authCommandService.authorizeAllPermissionToRoot();

        //清除权限菜单缓存
        clearAuthMenuCache();
    }

    @Override
    @Transactional
    public void deleteMenu(DeleteMenuCommand command) {
        List<Object> menus = menuDomainRepository.findAllChildMenus(new MenuId(command.getId()));
        for (Object menu : menus) {
            if (menu instanceof GeneralMenu) {
                menuDomainRepository.removeMenu(((GeneralMenu) menu).getId(), command.getIdentity());
            }
            else if (menu instanceof PermissionMenu) {
                menuDomainRepository.removeMenu(((PermissionMenu) menu).getId(), command.getIdentity());
                //删除对应权限节点
                permissionCommandService.deletePermission(((PermissionMenu) menu).getAuthItem());
            }
        }

        //清除权限菜单缓存
        clearAuthMenuCache();
    }

    @Override
    @Transactional
    public void batchDeleteMenu(BatchDeleteMenuCommand command) {
        if (command.getIds().isEmpty()) {
            throw new ValidationException("删除菜单不能为空");
        }
        for (Long id : command.getIds()) {
            DeleteMenuCommand deleteMenuCommand = new DeleteMenuCommand();
            deleteMenuCommand.setId(id);
            deleteMenuCommand.setIdentity(command.getIdentity());
            deleteMenu(deleteMenuCommand);
        }

        //清除权限菜单缓存
        clearAuthMenuCache();
    }

    @Override
    @Transactional
    public void batchRepairMenuPermission() {
        List<MenuDO> menuDOS = menuDataRepository.findAllPermissionMenus();
        for (MenuDO menuDO : menuDOS) {
            if (permissionQueryService.isPermissionExist(menuDO.getAuthItem())) {
                continue;
            }
            MenuDO parentMenu = menuDataRepository.findById(menuDO.getParentId());
            permissionCommandService.createPermission(menuDO.getAuthItem(), String.format("%s-%s",
                    parentMenu != null ? parentMenu.getName() : "", menuDO.getName()));
        }
    }

    /**
     * 清除权限权限菜单缓存
     */
    private void clearAuthMenuCache() {
//        Cache userPermissionCache = cacheManager.getCache(CacheConfig.rbacMenuCacheName);
//        userPermissionCache.evictIfPresent(CacheConfig.rbacMenuAuthMenuCacheKey);
        redisTemplate.delete(CacheConfig.rbacMenuAuthMenuCacheKey);
    }
}
