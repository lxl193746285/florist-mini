package com.qy.rbac.api.client;

import com.qy.rbac.api.dto.ContextAndRuleDTO;
import com.qy.rbac.api.dto.ContextDTO;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-rbac-auth")
public interface AuthClient {
    /**
     * 获取指定用户的权限
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/user/permissions")
    ResponseEntity<List<PermissionWithRuleDTO>> getUserPermissions(
            @RequestParam(value = "user") String user
    );

    /**
     * 获取指定用户在指定上下文的权限
     *
     * @param user
     * @param context
     * @param contextId
     * @return
     */
    @GetMapping("/v4/api/rbac/user/context/permissions")
    List<PermissionWithRuleDTO> getUserPermissions(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id") String contextId
    );

    /**
     * 获取指定用户在指定上下文的指定功能模块的权限
     *
     * @param user
     * @param context
     * @param contextId
     * @param functions
     * @return
     */
    @GetMapping("/v4/api/rbac/user/context/function/permissions")
    List<PermissionWithRuleDTO> getUserFunctionPermissions(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id") String contextId,
            @RequestParam(value = "functions") List<String> functions
    );

    /**
     * 指定用户是否有指定的权限
     *
     * @param user
     * @param permission
     * @return
     */
    @GetMapping("/v4/api/rbac/user/has-permission")
    boolean isUserHasPermission(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "permission") String permission
    );

    /**
     * 指定用户在指定上下文是否有指定的权限
     *
     * @param user
     * @param context
     * @param contextId
     * @param permission
     * @return
     */
    @GetMapping("/v4/api/rbac/user/context/has-permission")
    boolean isUserHasPermission(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id") String contextId,
            @RequestParam(value = "permission") String permission,
            @RequestParam(value = "system_id") Long systemId
    );

    /**
     * 指定用户拥有指定权限的上下文
     *
     * @param user
     * @param context
     * @param permission
     * @return
     */
    @GetMapping("/v4/api/rbac/user/has-permission-contexts")
    List<ContextDTO> getHasPermissionContexts(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "permission") String permission,
            @RequestParam(value = "system_id") Long systemId
    );

    /**
     * 获取指定用户有指定权限的上下文以及规则数据
     *
     * @param user
     * @param context
     * @param permission
     * @return
     */
    @GetMapping("/v4/api/rbac/user/has-permission-contexts-with-rule")
    List<ContextAndRuleDTO> getHasPermissionContextsWithRule(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "permission") String permission
    );

    /**
     * 获取指定用户指定的上下文的规则数据
     *
     * @param user
     * @param context
     * @param permission
     * @return
     */
    @GetMapping("/v4/api/rbac/user/has-permission-context-with-rule")
    ContextAndRuleDTO getHasPermissionContextWithRule(
            @RequestParam(value = "user") String user,
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id") String contextId,
            @RequestParam(value = "permission") String permission,
            @RequestParam(value = "system_id") Long systemId
    );

    /**
     * 获取拥有指定权限的用户
     *
     * @param context
     * @param contextId
     * @param permission
     * @return
     */
    @GetMapping("/v4/api/rbac/has-permission-users")
    List<String> getHasPermissionUsers(
            @RequestParam(value = "context") String context,
            @RequestParam(value = "context_id") String contextId,
            @RequestParam(value = "permission") String permission
    );
}
