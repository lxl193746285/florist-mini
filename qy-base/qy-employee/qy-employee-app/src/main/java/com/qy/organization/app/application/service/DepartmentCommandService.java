package com.qy.organization.app.application.service;

import com.qy.organization.app.application.command.CreateDepartmentCommand;
import com.qy.organization.app.application.command.DeleteDepartmentCommand;
import com.qy.organization.app.application.command.UpdateDepartmentCommand;

/**
 * 部门命令服务
 *
 * @author legendjw
 */
public interface DepartmentCommandService {
    /**
     * 创建组织的顶级部门
     *
     * @param organizationId
     * @return
     */
    Long createOrganizationTopDepartment(Long organizationId);

    /**
     * 创建部门命令
     *
     * @param command
     * @return
     */
    Long createDepartment(CreateDepartmentCommand command);

    /**
     * 编辑部门命令
     *
     * @param command
     */
    void updateDepartment(UpdateDepartmentCommand command);

    /**
     * 删除部门命令
     *
     * @param command
     */
    void deleteDepartment(DeleteDepartmentCommand command);
}
