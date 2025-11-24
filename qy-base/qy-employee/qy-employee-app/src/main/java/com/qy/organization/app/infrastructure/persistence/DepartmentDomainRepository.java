package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.domain.entity.Department;
import com.qy.organization.app.domain.valueobject.DepartmentId;

/**
 * 部门领域资源库
 *
 * @author legendjw
 */
public interface DepartmentDomainRepository {
    /**
     * 根据ID查询部门
     *
     * @param id
     * @return
     */
    Department findById(DepartmentId id);

    /**
     * 根据组织id查询顶级部门id
     *
     * @param organizationId
     * @return
     */
    Long findTopByOrganizationId(Long organizationId);
}