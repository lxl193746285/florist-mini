package com.qy.organization.app.domain.service;

import com.qy.organization.app.application.dto.MenuCheckWithPermissionDTO;
import com.qy.organization.app.domain.enums.EmployeePermissionType;
import com.qy.organization.app.domain.valueobject.EmployeeId;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.organization.app.domain.valueobject.UserId;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 员工领域服务
 *
 * @author legendjw
 */
public interface EmployeeService {
    /**
     * 转为管理员
     *
     * @param employeeId
     */
    void changeToAdmin(EmployeeId employeeId);

    /**
     * 转为操作员
     *
     * @param employeeId
     * @param permissionType
     * @param roleIds
     * @param menuPermissions
     * @param identity
     */
    void changeToOperator(EmployeeId employeeId, EmployeePermissionType permissionType, List<Long> roleIds, List<MenuCheckWithPermissionDTO> menuPermissions, Identity identity);

    /**
     * 转为无权限员工
     *
     * @param employeeId
     */
    void changeToEmployee(EmployeeId employeeId);

    /**
     * 创建组织的创始人员工
     *
     * @param organizationId
     * @param departmentId
     * @param userId
     * @param roleIds
     * @return
     */
    EmployeeId createOrganizationCreatorEmployee(OrganizationId organizationId, Long departmentId, Long userId, List<Long> roleIds);

    /**
     * 更改组织创始人的权限组
     *
     * @param organizationId
     * @param roleIds
     */
    void updateOrganizationCreatorRole(OrganizationId organizationId, List<Long> roleIds);

    /**
     * 更换组织创始人
     *
     * @param organizationId
     * @param newCreatorId
     * @param roleIds
     */
    void changeOrganizationCreator(OrganizationId organizationId, Long newCreatorId, List<Long> roleIds);

    /**
     * 加入组织
     *
     * @param userId
     * @param organizationId
     * @param inviterId
     * @param inviterName
     * @return
     */
    EmployeeId joinOrganization(UserId userId, OrganizationId organizationId, Long inviterId, String inviterName);

}
