package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.rbac.app.application.query.MenuQuery;
import com.qy.rbac.app.domain.menu.MenuType;
import com.qy.rbac.app.domain.menu.ShowHiddenStatus;
import com.qy.rbac.app.infrastructure.persistence.MenuDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthMenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuRuleDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthMenuMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.MenuMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.MenuRuleMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class MenuDataRepositoryImpl implements MenuDataRepository {
    private MenuMapper menuMapper;
    private MenuRuleMapper menuRuleMapper;
    private AuthMenuMapper authMenuMapper;

    public MenuDataRepositoryImpl(MenuMapper menuMapper, MenuRuleMapper menuRuleMapper, AuthMenuMapper authMenuMapper) {
        this.menuMapper = menuMapper;
        this.menuRuleMapper = menuRuleMapper;
        this.authMenuMapper = authMenuMapper;
    }

    @Override
    public List<MenuDO> findByQuery(MenuQuery query) {
        //普通菜单查询
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
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
        if (menuDOS == null) {
            menuDOS = new ArrayList<>();
        }
        //权限菜单查询
        LambdaQueryWrapper<AuthMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(AuthMenuDO::getSort)
                .orderByAsc(AuthMenuDO::getId);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            wrapper.and(i -> i.like(AuthMenuDO::getName, query.getKeywords()));
        }
        if (query.getModuleId() != null) {
            wrapper.eq(AuthMenuDO::getModuleId, query.getModuleId());
        }
        List<AuthMenuDO> authMenuDOS = authMenuMapper.selectList(wrapper);

        if (authMenuDOS != null) {
            menuDOS.addAll(authMenuDOS.stream().map(authMenuDO -> {
                MenuDO menuDO = new MenuDO();
                BeanUtils.copyProperties(authMenuDO, menuDO);
                menuDO.setParentId(authMenuDO.getMenuId());
                menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
                menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
                return menuDO;
            }).collect(Collectors.toList()));
        }

        //如果使用了关键字查询则需要把所有上级菜单查出来
        //if (StringUtils.isNotBlank(query.getKeywords()) && !menuDOS.isEmpty()) {
        //    List<MenuDO> parentMenus = new ArrayList<>();
        //    for (MenuDO menuDO : menuDOS) {
        //        recursionLoadParentMenus(menuDO.getId(), parentMenus);
        //    }
        //    menuDOS.addAll(parentMenus);
        //}

        return menuDOS;
    }

    @Override
    public List<MenuDO> findByIds(List<Long> ids) {
        List<MenuDO> menuDOS = menuMapper.selectBatchIds(ids);
        if (menuDOS == null) {
            menuDOS = new ArrayList<>();
        }
        List<AuthMenuDO> authMenuDOS = authMenuMapper.selectBatchIds(ids);
        if (authMenuDOS != null) {
            menuDOS.addAll(authMenuDOS.stream().map(authMenuDO -> {
                MenuDO menuDO = new MenuDO();
                BeanUtils.copyProperties(authMenuDO, menuDO);
                menuDO.setParentId(authMenuDO.getMenuId());
                menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
                menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
                return menuDO;
            }).collect(Collectors.toList()));
        }
        return menuDOS;
    }

    @Override
    public List<MenuDO> findGeneralByAuthItems(List<String> authItems, Integer statusId) {
        if (authItems.isEmpty()) {
            return new ArrayList<>();
        }
        QueryWrapper<AuthMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("DISTINCT menu_id")
                .eq("is_deleted", LogicDeleteConstant.NOT_DELETED)
                .eq("auth_item_category", "menu")
                .in("auth_item", authItems);

        List<Object> menuIds = authMenuMapper.selectObjs(queryWrapper);
        List<Long> allIds = new ArrayList<>();
        for (Object parentId : menuIds) {
            Long longParentId = ((BigInteger) parentId).longValue();
            recursionLoadParentMenuIds(longParentId, allIds);
        }
        if (allIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<MenuDO> allQueryWrapper = new LambdaQueryWrapper<>();
        allQueryWrapper
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .and(i -> i.eq(MenuDO::getTypeId, MenuType.GENERAL_MENU.getId())
                        .or().eq(MenuDO::getTypeId, MenuType.PATH_MENU.getId()))
                .in(MenuDO::getId, allIds)
                .orderByAsc(MenuDO::getSort)
                .orderByAsc(MenuDO::getId);
        List<MenuDO> menuDOS = menuMapper.selectList(allQueryWrapper);

        if (statusId != null && statusId.intValue() == ShowHiddenStatus.SHOW.getId()) {
            menuDOS = filterHiddenMenus(menuDOS);
        }
        return menuDOS;
    }

    @Override
    public List<MenuDO> findAllByAuthItems(List<String> authItems) {
        if (authItems.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AuthMenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(AuthMenuDO::getId, AuthMenuDO::getMenuId)
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(AuthMenuDO::getAuthItem, authItems);
        List<Map<String, Object>> menus = authMenuMapper.selectMaps(queryWrapper);

        List<Long> allIds = new ArrayList<>();
        List<Long> parentIds = new ArrayList<>();
        for (Map<String, Object> menu : menus) {
            Long id = ((BigInteger) menu.get("id")).longValue();
            Long parentId = ((BigInteger) menu.get("menu_id")).longValue();
            allIds.add(id);
            parentIds.add(parentId);
        }
        for (Long parentId : parentIds) {
            recursionLoadParentMenuIds(parentId, allIds);
        }
        if (allIds.isEmpty()) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<MenuDO> allQueryWrapper = new LambdaQueryWrapper<>();
        allQueryWrapper
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(MenuDO::getId, allIds)
                .orderByAsc(MenuDO::getSort)
                .orderByAsc(MenuDO::getId);
        List<MenuDO> menuDOS = menuMapper.selectList(allQueryWrapper);
        if (menuDOS == null) {
            menuDOS = new ArrayList<>();
        }

        LambdaQueryWrapper<AuthMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(AuthMenuDO::getId, allIds)
                .orderByAsc(AuthMenuDO::getSort)
                .orderByAsc(AuthMenuDO::getId);
        List<AuthMenuDO> authMenuDOS = authMenuMapper.selectList(wrapper);
        if (authMenuDOS != null) {
            menuDOS.addAll(authMenuDOS.stream().map(authMenuDO -> {
                MenuDO menuDO = new MenuDO();
                BeanUtils.copyProperties(authMenuDO, menuDO);
                menuDO.setParentId(authMenuDO.getMenuId());
                menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
                menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
                return menuDO;
            }).collect(Collectors.toList()));
        }
        return menuDOS;
    }

    @Override
    public List<MenuDO> findAllPermissionMenus() {
        LambdaQueryWrapper<AuthMenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        List<AuthMenuDO> authMenuDOS = authMenuMapper.selectList(queryWrapper);
        return authMenuDOS.stream().map(authMenuDO -> {
            MenuDO menuDO = new MenuDO();
            BeanUtils.copyProperties(authMenuDO, menuDO);
            menuDO.setParentId(authMenuDO.getMenuId());
            menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
            menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
            return menuDO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MenuDO> findPermissionMenusAndParent(List<String> authItems) {
        if (authItems.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AuthMenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(AuthMenuDO::getAuthItem, authItems);
        List<AuthMenuDO> menus = authMenuMapper.selectList(queryWrapper);

        List<Long> parentIds = new ArrayList<>();
        for (AuthMenuDO menu : menus) {
            parentIds.add(menu.getMenuId());
        }
        List<MenuDO> menuDOS = new ArrayList<>();

        if (!parentIds.isEmpty()) {
            LambdaQueryWrapper<MenuDO> allQueryWrapper = new LambdaQueryWrapper<>();
            allQueryWrapper
                    .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                    .in(MenuDO::getId, parentIds);
            menuDOS = menuMapper.selectList(allQueryWrapper);
        }
        if (menuDOS == null) {
            menuDOS = new ArrayList<>();
        }
        menuDOS.addAll(menus.stream().map(authMenuDO -> {
            MenuDO menuDO = new MenuDO();
            BeanUtils.copyProperties(authMenuDO, menuDO);
            menuDO.setParentId(authMenuDO.getMenuId());
            menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
            menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
            return menuDO;
        }).collect(Collectors.toList()));
        return menuDOS;
    }

    @Override
    public List<MenuDO> findChildPermissionMenusByFunction(List<String> functions) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(MenuDO::getCode, functions)
                .eq(MenuDO::getTypeId, MenuType.GENERAL_MENU.getId());
        List<MenuDO> menuDOS = menuMapper.selectList(queryWrapper);
        if (menuDOS.isEmpty()) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<AuthMenuDO> childQueryWrapper = new LambdaQueryWrapper<>();
        childQueryWrapper
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(AuthMenuDO::getMenuId, menuDOS.stream().map(MenuDO::getId).collect(Collectors.toList()));

        List<AuthMenuDO> authMenuDOS = authMenuMapper.selectList(childQueryWrapper);
        return authMenuDOS.stream().map(authMenuDO -> {
            MenuDO menuDO = new MenuDO();
            BeanUtils.copyProperties(authMenuDO, menuDO);
            menuDO.setParentId(authMenuDO.getMenuId());
            menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
            menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
            return menuDO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MenuDO> findChildPermissionMenus(Long parentMenuId, Integer authTypeId) {
        LambdaQueryWrapper<AuthMenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(parentMenuId != null, AuthMenuDO::getMenuId, parentMenuId)
                .eq(authTypeId != null, AuthMenuDO::getAuthTypeId, authTypeId)
                .orderByAsc(AuthMenuDO::getSort);
        List<AuthMenuDO> authMenuDOS = authMenuMapper.selectList(queryWrapper);
        return authMenuDOS.stream().map(authMenuDO -> {
            MenuDO menuDO = new MenuDO();
            BeanUtils.copyProperties(authMenuDO, menuDO);
            menuDO.setParentId(authMenuDO.getMenuId());
            menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
            menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
            return menuDO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MenuRuleDO> findMenuRulesByMenuId(Long id) {
        LambdaQueryWrapper<MenuRuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuRuleDO::getMenuId, id)
                .orderByAsc(MenuRuleDO::getSort)
                .orderByAsc(MenuRuleDO::getId);
        return menuRuleMapper.selectList(queryWrapper);
    }

    @Override
    public MenuDO findById(Long id) {
        MenuDO menuDO = menuMapper.selectById(id);
        if (menuDO == null) {
            AuthMenuDO authMenuDO = authMenuMapper.selectById(id);
            if (authMenuDO != null) {
                menuDO = new MenuDO();
                BeanUtils.copyProperties(authMenuDO, menuDO);
                menuDO.setParentId(authMenuDO.getMenuId());
                menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
                menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
            }
        }
        return menuDO;
    }

    @Override
    public int countByName(String name) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getName, name)
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        LambdaQueryWrapper<AuthMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(AuthMenuDO::getName, name)
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);

        return menuMapper.selectCount(queryWrapper) + authMenuMapper.selectCount(wrapper);
    }

    @Override
    public MenuDO findByCode(String code) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getCode, code)
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return menuMapper.selectOne(queryWrapper);
    }

    private void recursionLoadParentMenus(Long id, List<MenuDO> menuDOS) {
        MenuDO menu = findMenu(id);
        if (menu != null && menuDOS.stream().allMatch(m -> !m.getId().equals(menu.getId()))) {
            menuDOS.add(menu);
            recursionLoadParentMenus(menu.getParentId(), menuDOS);
        }
    }

    private void recursionLoadParentMenuIds(Long id, List<Long> menuDOS) {
        MenuDO menu = findMenu(id);
        if (menu != null && !menuDOS.contains(menu.getId())) {
            menuDOS.add(menu.getId());
            recursionLoadParentMenuIds(menu.getParentId(), menuDOS);
        }
    }

    private MenuDO findMenu(Long id) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MenuDO::getId, id)
                .last("limit 1");
        return menuMapper.selectOne(queryWrapper);
    }

    /**
     * 过滤掉隐藏的菜单
     *
     * @param menuDOS
     * @return
     */
    private List<MenuDO> filterHiddenMenus(List<MenuDO> menuDOS) {
        List<MenuDO> showMenus = new ArrayList<>();
        for (MenuDO menuDO : menuDOS) {
            if (menuDO.getStatusId().intValue() == ShowHiddenStatus.HIDDEN.getId() || isParentMenuHidden(menuDO, menuDOS)) {
                continue;
            }
            showMenus.add(menuDO);
        }
        return showMenus;
    }

    /**
     * 父级菜单是否隐藏
     *
     * @param menuDO
     * @param menuDOS
     * @return
     */
    private boolean isParentMenuHidden(MenuDO menuDO, List<MenuDO> menuDOS) {
        if (menuDO.getParentId().equals(0L)) {
            return menuDO.getStatusId().intValue() == ShowHiddenStatus.HIDDEN.getId();
        }
        MenuDO parentMenu = menuDOS.stream().filter(m -> m.getId().equals(menuDO.getParentId())).findFirst().orElse(null);
        if (parentMenu == null){
            return false;
        }
        if (parentMenu.getStatusId().intValue() == ShowHiddenStatus.HIDDEN.getId()) {
            return true;
        }
        return isParentMenuHidden(parentMenu, menuDOS);
    }
}