package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.application.query.MenuQuery;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuRuleDO;

import java.util.List;

/**
 * 菜单数据资源库
 *
 * @author legendjw
 */
public interface MenuDataRepository {
    /**
     * 分页查询菜单
     *
     * @param query
     * @return
     */
    List<MenuDO> findByQuery(MenuQuery query);

    /**
     * 根据id集合查询菜单
     *
     * @param ids
     * @return
     */
    List<MenuDO> findByIds(List<Long> ids);

    /**
     * 根据授权项获取普通菜单
     *
     * @param authItems
     * @param statusId
     * @return
     */
    List<MenuDO> findGeneralByAuthItems(List<String> authItems, Integer statusId);

    /**
     * 根据授权项获取所有菜单
     *
     * @param authItems
     * @return
     */
    List<MenuDO> findAllByAuthItems(List<String> authItems);

    /**
     * 查询所有的权限菜单
     *
     * @return
     */
    List<MenuDO> findAllPermissionMenus();

    /**
     * 查询指定权限菜单以及他们的父级菜单
     *
     * @return
     */
    List<MenuDO> findPermissionMenusAndParent(List<String> authItems);

    /**
     * 获取指定功能菜单下的权限菜单
     *
     * @param functions
     * @return
     */
    List<MenuDO> findChildPermissionMenusByFunction(List<String> functions);

    /**
     * 获取指定菜单下的权限菜单
     *
     * @param parentMenuId
     * @param authTypeId
     * @return
     */
    List<MenuDO> findChildPermissionMenus(Long parentMenuId, Integer authTypeId);

    /**
     * 根据菜单id获取其下的规则
     *
     * @param id
     * @return
     */
    List<MenuRuleDO> findMenuRulesByMenuId(Long id);

    /**
     * 根据ID查询菜单
     *
     * @param id
     * @return
     */
    MenuDO findById(Long id);

    /**
     * 查询指定菜单名称的数量
     *
     * @param name
     * @return
     */
    int countByName(String name);

    /**
     * 通过功能标识查询菜单
     *
     * @param code
     * @return
     */
    MenuDO findByCode(String code);
}
