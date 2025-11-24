package com.qy.rbac.api.client;

import com.qy.rbac.api.command.*;
import com.qy.rbac.api.command.*;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-rbac-role")
public interface RoleClient {
    /**
     * 获取指定角色的权限
     *
     * @param id 角色id
     * @return
     */
    @GetMapping("/v4/api/rbac/roles/{id}/permissions")
    List<PermissionWithRuleDTO> getRolePermissions(
            @PathVariable(value = "id") String id
    );

    /**
     * 创建一个角色
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/rbac/roles")
    void createRole(
            @Valid @RequestBody RbacCreateRoleCommand command
    );

    /**
     * 修改角色
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/rbac/roles/{id}")
    void updateRole(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody RbacUpdateRoleCommand command
    );

    /**
     * 删除角色
     *
     * @param id
     */
    @DeleteMapping("/v4/api/rbac/roles/{id}")
    void deleteRole(
            @PathVariable(value = "id") String id
    );

    /**
     * 角色授权
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/rbac/roles/{id}/authorize")
    void authorizeRole(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody RbacAuthorizeRoleCommand command
    );

    /**
     * 角色授权-盛昊新增部门清除缓存用
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/rbac/roles/{id}/batch-authorize")
    void batchAuthorizeRole(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody RbacAuthorizeRoleCommand command
    );

    /**
     * 分配角色给指定用户
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/rbac/roles/assign-role-to-user")
    void assignRoleToUser(
            @Valid @RequestBody RbacAssignRoleToUserCommand command
    );

    /**
     * 撤销指定用户的角色
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/rbac/roles/revoke-user-role")
    void revokeUserRole(
            @Valid @RequestBody RbacRevokeUserRoleCommand command
    );

    /**
     * 创建角色并授权
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/rbac/roles/create-role-and-authorize")
    void createRoleAndAuthorize(
            @Valid @RequestBody RbacCreateRoleAndAuthorizeCommand command
    );

    /**
     * 复制用户权限
     *
     * @param command
     */
    @PostMapping("/v4/api/rbac/roles/copy-user-permission")
    void copyUserPermission(
            @Valid @RequestBody RbacCopyUserPermissionCommand command
    );
}
