package com.qy.organization.app.application.service;

import com.qy.organization.app.application.dto.DepartmentBasicDTO;
import com.qy.organization.app.application.dto.DepartmentChildInfoDTO;
import com.qy.organization.app.application.dto.DepartmentDTO;
import com.qy.organization.app.application.query.DepartmentQuery;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 部门查询服务
 *
 * @author legendjw
 */
public interface DepartmentQueryService {
    /**
     * 查询部门
     *
     * @param query
     * @param identity
     * @return
     */
    List<DepartmentDTO> getDepartments(DepartmentQuery query, Identity identity);


    /**
     * 查询基本部门
     *
     * @param query
     * @param identity
     * @return
     */
    List<DepartmentBasicDTO> getBasicDepartments(DepartmentQuery query, Identity identity);

    /**
     * 查询基本部门
     *
     * @param query
     * @return
     */
    List<DepartmentBasicDTO> getBasicDepartments(DepartmentQuery query);

    /**
     * 获取部门子部门以及人员信息
     *
     * @param organizationId
     * @param departmentId
     * @param keywords
     * @return
     */
    DepartmentChildInfoDTO getDepartmentChildInfo(Long organizationId, Long departmentId, String keywords);

    /**
     * 根据ID查询部门
     *
     * @param id
     * @return
     */
    DepartmentDTO getDepartmentById(Long id);

    /**
     * 根据ID查询部门
     *
     * @param id
     * @return
     */
    DepartmentDTO getDepartmentById(Long id, Identity identity);

    /**
     * 根据ID查询基本部门信息
     *
     * @param id
     * @return
     */
    DepartmentBasicDTO getBasicDepartmentById(Long id);

    /**
     * 根据ID集合查询基本部门信息
     *
     * @param ids
     * @return
     */
    List<DepartmentBasicDTO> getBasicDepartmentsByIds(List<Long> ids);

    /**
     * 获取指定部门下所有的子部门
     *
     * @param id
     * @return
     */
    List<DepartmentBasicDTO> getAllChildDepartments(Long id);

    List<DepartmentDTO> getDepartmentsForShV2(MultiOrganizationFilterQuery filterQuery, DepartmentQuery departmentQuery, Identity user);
}