package com.qy.organization.app.application.service;

import com.qy.organization.app.application.command.*;
import com.qy.security.session.Identity;
import com.qy.organization.app.application.command.*;

/**
 * 权限组命令服务
 *
 * @author legendjw
 */
public interface OrganizationRoleCommandService {
    /**
     * 创建权限组
     *
     * @param command
     * @return
     */
    Long createRole(CreateRoleCommand command);

    /**
     * 复制权限组
     *
     * @param command
     * @return
     */
    Long copyRole(CopyRoleCommand command);

    /**
     * 编辑权限组
     *
     * @param command
     */
    void updateRole(UpdateRoleCommand command);

    /**
     * 删除权限组
     *
     * @param command
     */
    void deleteRole(DeleteRoleCommand command);

    /**
     * 给指定权限组授权
     *
     * @param command
     * @param identity
     */
    void authorizeRole(AuthorizeRoleCommand command, Identity identity);

    /**
     * 分配权限组给指定员工
     *
     * @param command
     */
    void assignRoleToEmployee(AssignRoleToEmployeeCommand command);

    /**
     * 分配权限组给指定用户
     *
     * @param command
     */
    void assignRoleToUser(AssignRoleToUserCommand command);

    /**
     * 撤销员工权限组
     *
     * @param employeeId
     */
    void revokeEmployeeRole(Long employeeId);

    /**
     * 设置默认权限组
     *
     * @param command
     */
    void setDefaultRole(SetDefaultRoleCommand command);
//    /**
//     * 会员系统-设置默认权限组
//     *
//     * @param command
//     */
//    void setDefaultRoleForMemberSystem(SetDefaultRoleForMemberCommand command);
}
