package com.qy.organization.app.application.service;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.organization.app.application.command.*;
import com.qy.organization.app.domain.valueobject.EmployeeId;
import com.qy.security.session.Identity;

/**
 * 员工命令服务
 *
 * @author legendjw
 */
public interface EmployeeCommandService {
    /**
     * 创建一个员工
     *
     * @param command
     * @return
     */
    EmployeeId createEmployee(CreateEmployeeCommand command, Identity identity);

    /**
     * 修改一个员工
     *
     * @param command
     */
    void updateEmployee(UpdateEmployeeCommand command);

    /**
     * 认证员工修改信息
     *
     * @param command
     */
    void updateIdentityEmployee(UpdateIdentityEmployeeCommand command);

    /**
     * 删除一个员工
     *
     * @param command
     */
    void deleteEmployee(DeleteEmployeeCommand command);

    /**
     * 员工在职
     *
     * @param command
     */
    void onJob(EmployeeOnJobCommand command);

    /**
     * 员工离职
     *
     * @param command
     */
    void leaveJob(EmployeeLeaveJobCommand command);

    /**
     * 设置身份以及权限
     *
     * @param command
     * @param identity
     */
    void setIdentityAndPermission(SetEmployeeIdentityCommand command, Identity identity);

    /**
     * 更改员工登录用户名
     *
     * @param command
     */
    void changeEmployeeUsername(ChangeEmployeeUsernameCommand command);

    /**
     * 更改员工登录手机号
     *
     * @param command
     */
    void changeEmployeeUserPhone(ChangeEmployeeUserPhoneCommand command);

    /**
     * 重置密码
     *
     * @param employeeId
     */
    void resetPassword(Long employeeId);

    /**
     * 切换组织
     *
     * @param command
     * @param identity
     * @return
     */
    AccessTokenDTO switchOrganization(SwitchOrganizationCommand command, Identity identity);

}