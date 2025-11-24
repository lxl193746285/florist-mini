package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.rbac.app.application.enums.AuthMenuType;
import com.qy.rbac.app.domain.menu.*;
import com.qy.rbac.app.domain.menu.*;
import com.qy.rbac.app.infrastructure.persistence.MenuDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.converter.MenuConverter;
import com.qy.rbac.app.infrastructure.persistence.mybatis.converter.MenuRuleConverter;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthMenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuRuleDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthMenuMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.MenuMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.MenuRuleMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class MenuDomainRepositoryImpl implements MenuDomainRepository {
    private MenuConverter menuConverter;
    private MenuRuleConverter menuRuleConverter;
    private MenuMapper menuMapper;
    private MenuRuleMapper menuRuleMapper;
    private AuthMenuMapper authMenuMapper;

    public MenuDomainRepositoryImpl(MenuConverter menuConverter, MenuRuleConverter menuRuleConverter, MenuMapper menuMapper,
                                    MenuRuleMapper menuRuleMapper, AuthMenuMapper authMenuMapper) {
        this.menuConverter = menuConverter;
        this.menuRuleConverter = menuRuleConverter;
        this.menuMapper = menuMapper;
        this.menuRuleMapper = menuRuleMapper;
        this.authMenuMapper = authMenuMapper;
    }

    @Override
    public GeneralMenu findGeneralMenuById(MenuId id) {
        MenuDO menuDO = findMenuById(id);
        if (menuDO == null) {
            return null;
        }
        return menuConverter.toGeneralMenu(menuDO);
    }

    @Override
    public PermissionMenu findPermissionMenuById(MenuId id) {
        MenuDO menuDO;
        AuthMenuDO authMenuDO = authMenuMapper.selectById(id);
        if (authMenuDO == null) {
            return null;
        } else {
            menuDO = new MenuDO();
            BeanUtils.copyProperties(authMenuDO, menuDO);
            menuDO.setParentId(authMenuDO.getMenuId());
            menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
            menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
        }
        List<MenuRuleDO> menuRuleDOS = getMenuRules(id);
        List<MenuRule> menuRules = menuRuleDOS.stream().map(m -> menuRuleConverter.toMenuRule(m)).collect(Collectors.toList());
        return menuConverter.toPermissionMenu(menuDO, menuRules);
    }

    @Override
    public List<Object> findAllChildMenus(MenuId id) {
        MenuDO menuDO = menuMapper.selectById(id);
        if (menuDO == null) {
            AuthMenuDO authMenuDO = authMenuMapper.selectById(id);
            if (authMenuDO == null){
                return new ArrayList<>();
            }
            menuDO = new MenuDO();
            BeanUtils.copyProperties(authMenuDO, menuDO);
            menuDO.setParentId(authMenuDO.getMenuId());
            menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
            menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
            List<Object> objects = new ArrayList<>();
            objects.add(menuConverter.toPermissionMenu(menuDO, null));
            return objects;
        }else {
            List<MenuDO> menuDOS = new ArrayList<>();
            menuDOS.add(menuDO);
            recursionLoadChildMenus(id.getId(), menuDOS);

            return menuDOS.stream().map(m -> m.getTypeId().intValue() == MenuType.GENERAL_MENU.getId() ?
                    menuConverter.toGeneralMenu(m) :
                    menuConverter.toPermissionMenu(m, null)).collect(Collectors.toList());
        }

    }

    @Override
    public MenuId saveGeneralMenu(GeneralMenu generalMenu) {
        MenuDO menuDO = menuConverter.generalMenuToDO(generalMenu);
        menuDO.setTypeId(MenuType.GENERAL_MENU.getId());
        menuDO.setTypeName(MenuType.GENERAL_MENU.getName());
        if (generalMenu.getId() == null) {
            menuMapper.insert(menuDO);
        }
        else {
            menuMapper.updateById(menuDO);
        }
        return new MenuId(menuDO.getId());
    }

    @Override
    public MenuId savePathMenu(GeneralMenu generalMenu) {
        MenuDO menuDO = menuConverter.generalMenuToDO(generalMenu);
        menuDO.setTypeId(MenuType.PATH_MENU.getId());
        menuDO.setTypeName(MenuType.PATH_MENU.getName());
        if (generalMenu.getId() == null) {
            menuMapper.insert(menuDO);
        }
        else {
            menuMapper.updateById(menuDO);
        }
        return new MenuId(menuDO.getId());
    }

    @Override
    public MenuId savePermissionMenu(PermissionMenu permissionMenu) {
        MenuDO menuDO = menuConverter.permissionMenuToDO(permissionMenu);
        AuthMenuDO authMenuDO = new AuthMenuDO();
        BeanUtils.copyProperties(menuDO, authMenuDO);
        authMenuDO.setMenuId(menuDO.getParentId());
        authMenuDO.setAuthTypeId(permissionMenu.getAuthTypeId());
        if (permissionMenu.getId() == null) {
            authMenuMapper.insert(authMenuDO);
            menuDO.setId(authMenuDO.getId());
        }
        else {
            authMenuMapper.updateById(authMenuDO);
        }
        saveMenuRule(menuDO, permissionMenu.getRules());
        return new MenuId(authMenuDO.getId());
    }

    @Override
    public void removeMenu(MenuId menuId, Identity identity) {
        MenuDO menuDO = menuMapper.selectById(menuId.getId());
        if (menuDO == null){
            AuthMenuDO authMenuDO = authMenuMapper.selectById(menuId.getId());
            if (authMenuDO == null){
                return;
            }
            authMenuDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
            authMenuDO.setDeletorId(identity.getId());
            authMenuDO.setDeletorName(identity.getName());
            authMenuDO.setDeleteTime(LocalDateTime.now());
            authMenuMapper.updateById(authMenuDO);
            return;
        }
        menuDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        menuDO.setDeletorId(identity.getId());
        menuDO.setDeletorName(identity.getName());
        menuDO.setDeleteTime(LocalDateTime.now());
        menuMapper.updateById(menuDO);

    }

    @Override
    public void batchUpdateChildMenuModule(MenuId menuId, ModuleId moduleId) {
        List<MenuDO> menuDOS = new ArrayList<>();
        recursionLoadChildMenus(menuId.getId(), menuDOS);
        if (menuDOS.isEmpty()) {
            return;
        }
        // 更新菜单
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MenuDO::getId, menuDOS.stream().map(MenuDO::getId).collect(Collectors.toList()));
        MenuDO updateMenu = new MenuDO();
        updateMenu.setModuleId(moduleId.getId());
        menuMapper.update(updateMenu, queryWrapper);

        // 更新权限菜单
        LambdaQueryWrapper<AuthMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(AuthMenuDO::getId, menuDOS.stream().map(MenuDO::getId).collect(Collectors.toList()));
        AuthMenuDO updateAuthMenu = new AuthMenuDO();
        updateAuthMenu.setModuleId(moduleId.getId());
        authMenuMapper.update(updateAuthMenu, wrapper);
    }

    @Override
    public int countByModuleAndTypeAndName(Long moduleId, Integer typeId, String name, Long excludeId) {
        if (typeId == MenuType.GENERAL_MENU.getId()){
            LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                    .eq(MenuDO::getModuleId, moduleId)
                    .eq(MenuDO::getTypeId, typeId)
                    .eq(MenuDO::getName, name);
            if (excludeId != null) {
                queryWrapper.ne(MenuDO::getId, excludeId);
            }
            return menuMapper.selectCount(queryWrapper);
        }

        LambdaQueryWrapper<AuthMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(AuthMenuDO::getModuleId, moduleId)
                .eq(AuthMenuDO::getName, name);
        if (excludeId != null) {
            wrapper.ne(AuthMenuDO::getId, excludeId);
        }
        return authMenuMapper.selectCount(wrapper);

    }

    @Override
    public int countByAuthItem(String authItem, Long excludeId) {
        LambdaQueryWrapper<AuthMenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AuthMenuDO::getAuthItem, authItem)
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(AuthMenuDO::getId, excludeId);
        }
        return authMenuMapper.selectCount(queryWrapper);
    }

    @Override
    public int countByCode(String code, Long excludeId) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getCode, code)
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(MenuDO::getId, excludeId);
        }

        LambdaQueryWrapper<AuthMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(AuthMenuDO::getCode, code)
                .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            wrapper.ne(AuthMenuDO::getId, excludeId);
        }
        return authMenuMapper.selectCount(wrapper);
    }

    private MenuDO findMenuById(MenuId id) {
        MenuDO menuDO = menuMapper.selectById(id);
        if (menuDO == null){
            AuthMenuDO  authMenuDO = authMenuMapper.selectById(id);
            if (authMenuDO != null){
                menuDO = new MenuDO();
                BeanUtils.copyProperties(authMenuDO, menuDO);
                menuDO.setParentId(authMenuDO.getMenuId());
                menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
                menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
            }
        }
        return menuDO;
    }

    private List<MenuDO> findByParentId(Long id) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MenuDO::getParentId, id);
        List<MenuDO> menuDOS = menuMapper.selectList(queryWrapper);
        if (menuDOS == null || menuDOS.isEmpty()){
            LambdaQueryWrapper<AuthMenuDO> wrapper = new LambdaQueryWrapper<>();
            wrapper
                    .eq(AuthMenuDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                    .eq(AuthMenuDO::getMenuId, id);
            List<AuthMenuDO> authMenuDOS = authMenuMapper.selectList(wrapper);
            menuDOS = authMenuDOS.stream().map(authMenuDO -> {
                MenuDO menuDO = new MenuDO();
                BeanUtils.copyProperties(authMenuDO, menuDO);
                menuDO.setParentId(authMenuDO.getMenuId());
                menuDO.setTypeId(MenuType.PERMISSION_MENU.getId());
                menuDO.setTypeName(MenuType.PERMISSION_MENU.getName());
                return menuDO;
            }).collect(Collectors.toList());
        }
        return menuDOS;
    }

    private void recursionLoadChildMenus(Long id, List<MenuDO> menuDOS) {
        List<MenuDO> childDepartments = findByParentId(id);
        menuDOS.addAll(childDepartments);
        for (MenuDO childMenuDO : childDepartments) {
            recursionLoadChildMenus(childMenuDO.getId(), menuDOS);
        }
    }

    private List<MenuRuleDO> getMenuRules(MenuId id) {
        LambdaQueryWrapper<MenuRuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MenuRuleDO::getMenuId, id.getId())
                .orderByAsc(MenuRuleDO::getSort);
        return menuRuleMapper.selectList(queryWrapper);
    }

    private void saveMenuRule(MenuDO menuDO, List<MenuRule> menuRules) {
        LambdaQueryWrapper<MenuRuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuRuleDO::getMenuId, menuDO.getId());
        menuRuleMapper.delete(queryWrapper);

        for (MenuRule rule : menuRules) {
            MenuRuleDO menuRuleDO = menuRuleConverter.toDO(rule);
            menuRuleDO.setMenuId(menuDO.getId());
            menuRuleMapper.insert(menuRuleDO);
        }
    }
}
