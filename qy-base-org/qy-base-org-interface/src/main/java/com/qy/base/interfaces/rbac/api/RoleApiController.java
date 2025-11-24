package com.qy.base.interfaces.rbac.api;

import com.qy.rbac.app.application.command.*;
import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;
import com.qy.rbac.app.application.service.AuthCommandService;
import com.qy.rbac.app.application.service.AuthQueryService;
import com.qy.rbac.app.application.service.RoleCommandService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.SessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/rbac/roles")
public class RoleApiController {
    private SessionContext sessionContext;
    private AuthCommandService authCommandService;
    private RoleCommandService roleCommandService;
    private AuthQueryService authQueryService;

    public RoleApiController(SessionContext sessionContext, AuthCommandService authCommandService, RoleCommandService roleCommandService, AuthQueryService authQueryService) {
        this.sessionContext = sessionContext;
        this.authCommandService = authCommandService;
        this.roleCommandService = roleCommandService;
        this.authQueryService = authQueryService;
    }

    /**
     * 获取指定角色的权限
     *
     * @param id 角色id
     * @return
     */
    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<PermissionWithRuleDTO>> getRolePermissions(
            @PathVariable(value = "id") String id
    ) {
        return ResponseEntity.ok().body(authQueryService.getRolePermissions(id));
    }

    /**
     * 创建一个角色
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> createRole(
            @Valid @RequestBody CreateRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        roleCommandService.createRole(command.getId(), command.getDescription());
        return ResponseUtils.ok("角色创建成功").build();
    }

    /**
     * 修改角色`
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateRole(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody UpdateRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setId(id);
        roleCommandService.updateRole(command.getId(), command.getNewId(), command.getDescription());

        return ResponseUtils.ok("角色修改成功").build();
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(
            @PathVariable(value = "id") String id
    ) {
        roleCommandService.deleteRole(id);

        return ResponseUtils.noContent("删除角色成功").build();
    }

    /**
     * 角色授权
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/{id}/authorize")
    public ResponseEntity<Object> authorizeRole(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody AuthorizeRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setRoleId(id);
        authCommandService.authorizeRole(command);
        return ResponseUtils.ok("角色授权成功").build();
    }

    /**
     * 盛昊新增部门清理缓存
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/{id}/batch-authorize")
    public ResponseEntity<Object> batchAuthorizeRole(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody AuthorizeRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setRoleId(id);
        authCommandService.authorizeRoleSh(command);
        return ResponseUtils.ok("角色授权成功").build();
    }

    /**
     * 分配角色给指定用户
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/assign-role-to-user")
    public ResponseEntity<Object> assignRoleToUser(
            @Valid @RequestBody AssignRoleToUserCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        authCommandService.assignRoleToUser(command);
        return ResponseUtils.ok("分配角色成功").build();
    }

    /**
     * 撤销指定用户的角色
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/revoke-user-role")
    public ResponseEntity<Object> revokeUserRole(
            @Valid @RequestBody RevokeUserRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        authCommandService.revokeUserRole(command);
        return ResponseUtils.ok("撤销角色成功").build();
    }

    /**
     * 创建角色并授权
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/create-role-and-authorize")
    public ResponseEntity<Object> createRoleAndAuthorize(
            @Valid @RequestBody CreateRoleAndAuthorizeCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        authCommandService.createRoleAndAuthorize(command);
        return ResponseUtils.ok("创建角色并授权成功").build();
    }

    /**
     * 复制用户权限
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/copy-user-permission")
    public ResponseEntity<Object> copyUserPermission(
            @Valid @RequestBody CopyUserPermissionCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        authCommandService.copyUserPermission(command);
        return ResponseUtils.ok("复制用户权限成功").build();
    }
}