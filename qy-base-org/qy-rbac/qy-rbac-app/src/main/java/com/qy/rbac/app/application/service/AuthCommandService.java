package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.command.*;
import com.qy.rbac.app.application.command.*;
import com.qy.rbac.app.domain.auth.PermissionWithRule;

/**
 * 授权命令服务
 *
 * @author legendjw
 */
public interface AuthCommandService {
    /**
     * 给角色授权
     *
     * @param command
     */
    void authorizeRole(AuthorizeRoleCommand command);

    void authorizeRoleSh(AuthorizeRoleCommand command);

    /**
     * 分配角色给指定用户
     *
     * @param command
     */
    void assignRoleToUser(AssignRoleToUserCommand command);

    /**
     * 撤销指定用户的角色
     *
     * @param command
     */
    void revokeUserRole(RevokeUserRoleCommand command);

    /**
     * 创建角色并授权
     *
     * @param command
     */
    void createRoleAndAuthorize(CreateRoleAndAuthorizeCommand command);

    /**
     * 复制用户权限
     *
     * @param command
     */
    void copyUserPermission(CopyUserPermissionCommand command);

    /**
     * 给ROOT账号授权指定权限节点
     *
     * @param permissionWithRule
     */
    void authorizePermissionToRoot(PermissionWithRule permissionWithRule);

    /**
     * 给ROOT账号授权所有的权限节点
     */
    void authorizeAllPermissionToRoot();

}