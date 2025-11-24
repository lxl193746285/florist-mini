package com.qy.rbac.api.client;

import com.qy.rbac.api.dto.CategoryPermissionMenuDTO;
import com.qy.rbac.api.dto.MenuBasicDTO;
import com.qy.rbac.api.dto.MenuDTO;
import com.qy.rbac.api.dto.ModuleMenuBasicDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 菜单客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-rbac-menu")
public interface MenuClient {
    /**
     * 获取按照父级菜单分组的权限菜单
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/user/permission-parent-menus")
    List<MenuDTO> getPermissionParentMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId
    );

    @GetMapping("/v4/api/rbac/menus/user/menus")
    public List<MenuBasicDTO> getUserMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId
    );

    /**
     * 获取指定用户的权限
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/user/frontend-menus")
    List<ModuleMenuBasicDTO> getUserFrontendMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "client_id") String clientId
    );

    /**
     * 获取用户指定上下文前端可以访问的菜单
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/user/context/frontend-menus")
    List<ModuleMenuBasicDTO> getUserFrontendMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "client_id") String clientId
    );

    /**
     * 获取用户指定应用模块可以访问的菜单
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/user/app-menus")
    List<MenuBasicDTO> getUserAppMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "app_code") String appCode,
            @RequestParam(value = "module_code") String moduleCode
    );

    /**
     * 获取用户指定上下文指定应用模块可以访问的菜单
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/user/context/app-menus")
    List<MenuBasicDTO> getUserAppMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "app_code") String appCode,
            @RequestParam(value = "module_code") String moduleCode
    );

    /**
     * 获取用户指定上下文授权的菜单
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/user/context/authorized-menus")
    List<ModuleMenuBasicDTO> getUserAuthorizedMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "member_system_id", required = false) String memberSystemId
    );

    /**
     * 获取用户指定上下文授权的菜单下的权限节点
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/user/context/authorized-menus/permissions")
    List<CategoryPermissionMenuDTO> getUserAuthorizedMenuPermissions(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "menu_id") Long menuId
    );

    /**
     * 根据授权项获取菜单名称
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/name/by-auth-item")
    String getMenuNameByAuthItem(
            @RequestParam(value = "authItem") String authItem
    );

    /**
     * 根据功能标识获取权限菜单
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/by-code")
    List<MenuBasicDTO> getAuthByCode(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "auth_type") Integer authType
    );

    /**
     * 根据功能标识获取权限菜单
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/menus/all-by-code")
    List<MenuBasicDTO> getAllAuthByCode(
            @RequestParam(value = "code") String code
    );
}
