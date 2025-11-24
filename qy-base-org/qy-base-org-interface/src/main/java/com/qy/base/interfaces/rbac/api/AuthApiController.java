package com.qy.base.interfaces.rbac.api;

import com.qy.rbac.app.application.dto.ContextAndRuleDTO;
import com.qy.rbac.app.application.dto.ContextDTO;
import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;
import com.qy.rbac.app.application.service.AuthCommandService;
import com.qy.rbac.app.application.service.AuthQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.SessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 授权内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/rbac")
public class AuthApiController {
    private SessionContext sessionContext;
    private AuthCommandService authCommandService;
    private AuthQueryService authQueryService;

    public AuthApiController(SessionContext sessionContext, AuthCommandService authCommandService, AuthQueryService authQueryService) {
        this.sessionContext = sessionContext;
        this.authCommandService = authCommandService;
        this.authQueryService = authQueryService;
    }

    /**
     * 获取指定用户的权限
     *
     * @return
     */
    @GetMapping("/user/permissions")
    public ResponseEntity<List<PermissionWithRuleDTO>> getUserPermissions(
            @RequestParam(value = "user") String user
    ) {
        return ResponseUtils.ok().body(authQueryService.getUserPermissions(user));
    }

    /**
     * 获取指定用户在指定上下文的权限
     *
     * @param user
     * @param context
     * @param contextId
     * @return
     */
    @GetMapping("/user/context/permissions")
    public ResponseEntity<List<PermissionWithRuleDTO>> getUserPermissions(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId
    ) {
        return ResponseUtils.ok().body(authQueryService.getUserPermissions(user, context, contextId));
    }

    /**
     * 获取指定用户在指定上下文的指定功能模块的权限
     *
     * @param user
     * @param context
     * @param contextId
     * @param functions
     * @return
     */
    @GetMapping("/user/context/function/permissions")
    public ResponseEntity<List<PermissionWithRuleDTO>> getUserPermissions(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "functions") List<String> functions
    ) {
        return ResponseUtils.ok().body(authQueryService.getUserFunctionPermissions(user, context, contextId, functions));
    }

    /**
     * 指定用户是否有指定的权限
     *
     * @param user
     * @param permission
     * @return
     */
    @GetMapping("/user/has-permission")
    public ResponseEntity<Boolean> isUserHasPermission(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "permission") String permission
    ) {
        return ResponseUtils.ok().body(authQueryService.isUserHasPermission(user, permission));
    }

    /**
     * 指定用户在指定上下文是否有指定的权限
     *
     * @param user
     * @param context
     * @param contextId
     * @param permission
     * @return
     */
    @GetMapping("/user/context/has-permission")
    public ResponseEntity<Boolean> isUserHasPermission(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "permission") String permission,
            @RequestParam(value = "system_id") Long systemId
    ) {
        return ResponseUtils.ok().body(authQueryService.isUserHasPermission(user, context, contextId, permission, systemId));
    }

    /**
     * 指定用户拥有指定权限的上下文
     *
     * @param user
     * @param context
     * @param permission
     * @return
     */
    @GetMapping("/user/has-permission-contexts")
    public ResponseEntity<List<ContextDTO>> getHasPermissionContexts(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "permission") String permission,
            @RequestParam(value = "system_id") Long systemId
    ) {
        return ResponseUtils.ok().body(authQueryService.getHasPermissionContexts(user, context, permission, systemId));
    }

    /**
     * 获取指定用户有指定权限的上下文以及规则数据
     *
     * @param user
     * @param context
     * @param permission
     * @return
     */
    @GetMapping("/user/has-permission-contexts-with-rule")
    public ResponseEntity<List<ContextAndRuleDTO>> getHasPermissionContextsWithRule(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "permission") String permission
    ) {
        return ResponseUtils.ok().body(authQueryService.getHasPermissionContextsWithRule(user, context, permission));
    }


    /**
     * 获取指定用户指定的上下文的规则数据
     *
     * @param user
     * @param context
     * @param permission
     * @return
     */
    @GetMapping("/user/has-permission-context-with-rule")
    public ResponseEntity<ContextAndRuleDTO> getHasPermissionContextWithRule(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id", required = false) String contextId,
            @RequestParam(value = "permission") String permission,
            @RequestParam(value = "system_id") Long systemId
    ) {
        return ResponseUtils.ok().body(authQueryService.getHasPermissionContextWithRule(user, context, contextId, permission, systemId));
    }

    /**
     * 获取拥有指定权限的用户
     *
     * @param context
     * @param contextId
     * @param permission
     * @return
     */
    @GetMapping("/has-permission-users")
    public ResponseEntity<List<String>> getHasPermissionUsers(
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id") String contextId,
            @RequestParam(value = "permission") String permission
    ) {
        return ResponseUtils.ok().body(authQueryService.getHasPermissionUsers(context, contextId, permission));
    }
}