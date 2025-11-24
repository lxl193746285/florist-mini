package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.application.query.JobQuery;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 岗位数据资源库
 *
 * @author legendjw
 */
public interface JobDataRepository {
    /**
     * 分页查询岗位
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<JobDO> findByQuery(JobQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 查询岗位
     *
     * @param query
     * @return
     */
    List<JobDO> findByQuery(JobQuery query);

    /**
     * 根据id集合查询岗位
     *
     * @param ids
     * @return
     */
    List<JobDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询岗位
     *
     * @param id
     * @return
     */
    JobDO findById(Long id);

    /**
     * 查找岗位下员工id集合
     *
     * @param id
     * @return
     */
    List<Long> findJobEmployees(Long id);

    /**
     * 查找多个岗位下员工id集合
     *
     * @param ids
     * @return
     */
    List<Long> findJobEmployees(List<Long> ids);

    /**
     * 查询指定员工的岗位Id集合
     *
     * @param id
     * @return
     */
    List<Long> findEmployeeJobs(Long id);

    /**
     * 保存一个岗位
     *
     * @param jobDO
     * @return
     */
    Long save(JobDO jobDO);

    /**
     * 移除一个岗位
     *
     * @param id
     * @param employee
     */
    void remove(Long id, EmployeeIdentity employee);

    /**
     * 查找指定组织指定名称的数量
     *
     * @param organizationId
     * @param name
     * @param excludeId
     * @return
     */
    int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId);

    /**
     * 保存岗位员工
     *
     * @param jobDO
     * @param employeeIds
     */
    void saveJobEmployees(JobDO jobDO, List<Long> employeeIds);
}
