package com.qy.uims.security.permission;

import com.qy.security.permission.rule.PermissionFilterQuery;

import java.util.List;

/**
 * 组织过滤查询对象
 *
 * @author legendjw
 */
public class OrganizationFilterQuery implements PermissionFilterQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 指定部门id
     */
    private Long departmentId;

    /**
     * 指定部门id集合
     */
    private List<Long> departmentIds;

    /**
     * 指定员工id
     */
    private Long employeeId;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<Long> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<Long> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}