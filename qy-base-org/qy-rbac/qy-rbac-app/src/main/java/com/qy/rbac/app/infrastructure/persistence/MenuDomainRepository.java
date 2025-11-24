package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.domain.menu.GeneralMenu;
import com.qy.rbac.app.domain.menu.MenuId;
import com.qy.rbac.app.domain.menu.ModuleId;
import com.qy.rbac.app.domain.menu.PermissionMenu;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 菜单领域资源库
 *
 * @author legendjw
 */
public interface MenuDomainRepository {
    /**
     * 根据id获取普通菜单
     *
     * @param id
     * @return
     */
    GeneralMenu findGeneralMenuById(MenuId id);

    /**
     * 根据id获取权限菜单
     *
     * @param id
     * @return
     */
    PermissionMenu findPermissionMenuById(MenuId id);

    /**
     * 查找所有的子菜单
     *
     * @param id
     * @return
     */
    List<Object> findAllChildMenus(MenuId id);

    /**
     * 保存普通菜单
     *
     * @param generalMenu
     * @return
     */
    MenuId saveGeneralMenu(GeneralMenu generalMenu);


    /**
     * 保存普通菜单
     *
     * @param generalMenu
     * @return
     */
    MenuId savePathMenu(GeneralMenu generalMenu);

    /**
     * 保存权限菜单
     *
     * @param permissionMenu
     * @return
     */
    MenuId savePermissionMenu(PermissionMenu permissionMenu);

    /**
     * 删除一个菜单
     *
     * @param menuId
     * @param identity
     */
    void removeMenu(MenuId menuId, Identity identity);

    /**
     * 批量更新子菜单的模块
     *
     * @param menuId
     * @param moduleId
     */
    void batchUpdateChildMenuModule(MenuId menuId, ModuleId moduleId);

    /**
     * 获取权限菜单父级菜单
     *
     * @param moduleId
     * @param typeId
     * @param name
     * @param excludeId
     * @return
     */
    int countByModuleAndTypeAndName(Long moduleId, Integer typeId, String name, Long excludeId);

    /**
     * 获取指定授权项的菜单数量
     *
     * @param authItem
     * @param excludeId
     * @return
     */
    int countByAuthItem(String authItem, Long excludeId);

    /**
     * 统计功能标识菜单数量
     *
     * @param code
     * @param excludeId
     * @return
     */
    int countByCode(String code, Long excludeId);
}
