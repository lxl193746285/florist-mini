package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.dto.*;
import com.qy.rbac.app.application.query.MenuQuery;
import com.qy.security.session.Identity;

import java.util.List;
import java.util.Map;

/**
 * 菜单查询服务
 *
 * @author legendjw
 */
public interface MenuQueryService {
    /**
     * 查询菜单
     *
     * @param query
     * @param identity
     * @return
     */
    List<MenuDTO> getMenus(MenuQuery query, Identity identity);

    /**
     * 根据ID查询菜单
     *
     * @param id
     * @param identity
     * @return
     */
    MenuDTO getMenuById(Long id, Identity identity);

    /**
     * 获取按照父级菜单分组的权限菜单
     *
     * @param userId
     * @param context
     * @param contextId
     * @return
     */
    List<MenuDTO> getPermissionParentMenus(String userId, String context, String contextId);

    /**
     * 获取用户前端可以访问的菜单
     *
     * @param userId
     * @param clientId
     * @return
     */
    List<ModuleMenuBasicDTO> getUserFrontendMenus(String userId, String clientId);

    /**
     * 获取用户指定上下文前端可以访问的菜单
     *
     * @param userId
     * @param context
     * @param contextId
     * @param clientId
     * @return
     */
    List<ModuleMenuBasicDTO> getUserFrontendMenus(String userId, String context, String contextId, String clientId);

    /**
     * 获取用户指定应用模块可以访问的菜单
     *
     * @param userId
     * @param appCode
     * @param moduleCode
     * @return
     */
    List<MenuBasicDTO> getUserAppMenus(String userId, String appCode, String moduleCode);

    /**
     * 获取用户指定应用模块可以访问的菜单
     *
     * @param userId
     * @param context
     * @param contextId
     * @param appCode
     * @param moduleCode
     * @return
     */
    List<MenuBasicDTO> getUserAppMenus(String userId, String context, String contextId, String appCode, String moduleCode);

    /**
     * 获取用户指定上下文授权的菜单
     *
     * @param userId
     * @param memberSystemId
     * @return
     */
    List<ModuleMenuBasicDTO> getUserAuthorizedMenus(String userId, String context, String contextId, String memberSystemId);

    /**
     * 获取用户指定上下文授权的菜单下的权限节点
     *
     * @param userId
     * @param menuId
     * @return
     */
    List<CategoryPermissionMenuDTO> getUserAuthorizedMenuPermissions(String userId, String context, String contextId, Long menuId);
    /**
     * 获取用户指定上下文授权的菜单下
     *
     * @param userId
     * @return
     */
    List<MenuBasicDTO> getUserAuthorizedMenuList(String userId, String context, String contextId);

    /**
     * 获取按照授权项分组的菜单
     *
     * @return
     */
    Map<String, MenuWithAuthItemDTO> getMenuGroupByAuthItem();

    /**
     * 根据认证项获取菜单名称
     *
     * @param authItem
     * @return
     */
    String getMenuNameByAuthItem(String authItem);

    /**
     * 导出菜单
     *
     * @param query
     * @param identity
     * @return
     */
    void exportMenus(MenuQuery query, Identity identity);

    /**
     * 根据功能标识查询权限菜单
     *
     * @param code     功能标示
     * @param authType
     * @return
     */
    List<MenuBasicDTO> getAuthMenuByCode(String code, Integer authType);
}