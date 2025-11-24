package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.application.query.DepartmentQuery;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 部门数据资源库
 *
 * @author legendjw
 */
public interface DepartmentDataRepository {
    /**
     * 根据查询对象查询部门
     *
     * @param query
     * @param filterQuery
     * @return
     */
    List<DepartmentDO> findByQuery(DepartmentQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 根据查询对象查询部门
     *
     * @param query
     * @return
     */
    List<DepartmentDO> findByQuery(DepartmentQuery query);

    /**
     * 根据父级部门id查询所有子部门
     *
     * @param parentId
     * @return
     */
    List<DepartmentDO> findChildren(Long parentId);

    /**
     * 查找指定组织的顶级部门
     *
     * @param organizationId
     * @return
     */
    DepartmentDO findOrganizationTopDepartment(Long organizationId);

    /**
     * 根据ID查询部门
     *
     * @param id
     * @return
     */
    DepartmentDO findById(Long id);

    /**
     * 根据ID集合查询部门
     *
     * @param ids
     * @return
     */
    List<DepartmentDO> findByIds(List<Long> ids);

    /**
     * 保存一个部门
     *
     * @param departmentDO
     * @return
     */
    Long save(DepartmentDO departmentDO);

    /**
     * 移除一个部门
     *
     * @param id
     * @param employee
     */
    void remove(Long id, EmployeeIdentity employee);

    /**
     * 查找指定组织指定部门名称的数量
     *
     * @param organizationId
     * @param name
     * @param excludeId
     * @return
     */
    int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId);

    /**
     * 查询指定父级下面的部门数量
     *
     * @param parentId
     * @return
     */
    int countByParentId(Long parentId);
}
