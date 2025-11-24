package com.qy.base.interfaces.rbac.api;

import com.qy.rbac.app.application.dto.CategoryPermissionMenuDTO;
import com.qy.rbac.app.application.dto.MenuBasicDTO;
import com.qy.rbac.app.application.dto.MenuDTO;
import com.qy.rbac.app.application.dto.ModuleMenuBasicDTO;
import com.qy.rbac.app.application.service.MenuQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.SessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/rbac/menus")
public class MenuApiController {
    private SessionContext sessionContext;
    private MenuQueryService menuQueryService;

    public MenuApiController(SessionContext sessionContext, MenuQueryService menuQueryService) {
        this.sessionContext = sessionContext;
        this.menuQueryService = menuQueryService;
    }

    /**
     * 获取按照父级菜单分组的权限菜单
     *
     * @return
     */
    @GetMapping("/user/permission-parent-menus")
    public ResponseEntity<List<MenuDTO>> getPermissionParentMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId
    ) {
        return ResponseUtils.ok().body(menuQueryService.getPermissionParentMenus(user, context, contextId));
    }

    /**
     * 获取按照父级菜单分组的权限菜单
     *
     * @return
     */
    @GetMapping("/user/menus")
    public ResponseEntity<List<MenuBasicDTO>> getUserMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId
    ) {
        return ResponseUtils.ok().body(menuQueryService.getUserAuthorizedMenuList(user, context, contextId));
    }

    /**
     * 获取指定用户的权限
     *
     * @return
     */
    @GetMapping("/user/frontend-menus")
    public ResponseEntity<List<ModuleMenuBasicDTO>> getUserFrontendMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "client_id") String clientId
    ) {
        return ResponseUtils.ok().body(menuQueryService.getUserFrontendMenus(user, clientId));
    }

    /**
     * 获取用户指定上下文前端可以访问的菜单
     *
     * @return
     */
    @GetMapping("/user/context/frontend-menus")
    public ResponseEntity<List<ModuleMenuBasicDTO>> getUserFrontendMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "client_id") String clientId
    ) {
        return ResponseUtils.ok().body(menuQueryService.getUserFrontendMenus(user, context, contextId, clientId));
    }

    /**
     * 获取用户指定应用模块可以访问的菜单
     *
     * @return
     */
    @GetMapping("/user/app-menus")
    public ResponseEntity<List<MenuBasicDTO>> getUserAppMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "app_code") String appCode,
            @RequestParam(value = "module_code") String moduleCode
    ) {
        return ResponseUtils.ok().body(menuQueryService.getUserAppMenus(user, appCode, moduleCode));
    }

    /**
     * 获取用户指定上下文指定应用模块可以访问的菜单
     *
     * @return
     */
    @GetMapping("/user/context/app-menus")
    public ResponseEntity<List<MenuBasicDTO>> getUserAppMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "app_code") String appCode,
            @RequestParam(value = "module_code") String moduleCode
    ) {
        return ResponseUtils.ok().body(menuQueryService.getUserAppMenus(user, context, contextId, appCode, moduleCode));
    }

    /**
     * 获取用户指定上下文授权的菜单
     *
     * @return
     */
    @GetMapping("/user/context/authorized-menus")
    public ResponseEntity<List<ModuleMenuBasicDTO>> getUserAuthorizedMenus(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "member_system_id", required = false) String memberSystemId
    ) {
        return ResponseUtils.ok().body(menuQueryService.getUserAuthorizedMenus(user, context, contextId, memberSystemId));
    }

    /**
     * 获取用户指定上下文授权的菜单下的权限节点
     *
     * @return
     */
    @GetMapping("/user/context/authorized-menus/permissions")
    public ResponseEntity<List<CategoryPermissionMenuDTO>> getUserAuthorizedMenuPermissions(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "menu_id") Long menuId
    ) {
        return ResponseUtils.ok().body(menuQueryService.getUserAuthorizedMenuPermissions(user, context, contextId, menuId));
    }

    /**
     * 根据授权项获取菜单名称
     *
     * @return
     */
    @GetMapping("/name/by-auth-item")
    public ResponseEntity<String> getMenuNameByAuthItem(
            @RequestParam(value = "authItem") String authItem
    ) {
        return ResponseUtils.ok().body(menuQueryService.getMenuNameByAuthItem(authItem));
    }

    /**
     * 根据功能标识获取权限菜单
     *
     * @return
     */
    @GetMapping("/by-code")
    public ResponseEntity<List<MenuBasicDTO>> getMenuByCode(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "auth_type") Integer authType
    ) {
        return ResponseUtils.ok().body(menuQueryService.getAuthMenuByCode(code, authType));
    }

    /**
     * 根据功能标识获取权限菜单
     *
     * @return
     */
    @GetMapping("/all-by-code")
    public ResponseEntity<List<MenuBasicDTO>> getAllMenuByCode(
            @RequestParam(value = "code") String code
    ) {
        return ResponseUtils.ok().body(menuQueryService.getAuthMenuByCode(code, null));
    }
}