package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.application.query.EmployeeQuery;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeInfoDO;
import com.qy.rest.pagination.Page;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 员工数据资源库
 *
 * @author legendjw
 */
public interface EmployeeDataRepository {
    /**
     * 分页查询员工
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<EmployeeDO> findByQuery(EmployeeQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 查询员工
     *
     * @param query
     * @return
     */
    List<EmployeeDO> findByQuery(EmployeeQuery query);

    /**
     * 根据id集合查找员工
     *
     * @param ids
     * @return
     */
    List<EmployeeDO> findByIds(List<Long> ids);

    /**
     * 根据用户集合查找员工
     *
     * @param userIds
     * @return
     */
    List<EmployeeDO> findByUserIds(List<Long> userIds);

    /**
     * 根据id集合查找员工信息
     *
     * @param ids
     * @return
     */
    List<EmployeeInfoDO> findInfoByIds(List<Long> ids);

    /**
     * 根据组织id以及用户id获取员工
     *
     * @param organizationId
     * @param userId
     * @return
     */
    EmployeeDO findByOrganizationIdAndUserId(Long organizationId, Long userId);

    /**
     * 获取组织创始人
     *
     * @param organizationId
     * @return
     */
    EmployeeDO findOrganizationCreator(Long organizationId);

    /**
     * 根据用户id获取员工信息
     *
     * @param userId
     * @return
     */
    List<EmployeeDO> findByUserId(Long userId);

    /**
     * 根据id查找员工
     *
     * @param id
     * @return
     */
    EmployeeDO findById(Long id);

    /**
     * 根据会员id查找员工
     *
     * @param memberId
     * @return
     */
    EmployeeDO findByMemberId(Long memberId);

    /**
     * 根据手机号查找员工
     *
     * @param phone
     * @return
     */
    EmployeeDO findByPhone(String phone);

    /**
     * 根据id查找员工信息
     *
     * @param id
     * @return
     */
    EmployeeInfoDO findInfoById(Long id);
}
