package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.domain.entity.EmployeeInfo;
import com.qy.organization.app.domain.entity.Employee;
import com.qy.organization.app.domain.valueobject.EmployeeId;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.security.session.EmployeeIdentity;

import java.util.List;

/**
 * 员工领域资源库
 *
 * @author legendjw
 */
public interface EmployeeDomainRepository {
    /**
     * 根据id查找员工
     *
     * @param id
     * @return
     */
    Employee findById(EmployeeId id);

    /**
     * 根据id查找员工
     *
     * @param id
     * @return
     */
    EmployeeInfo findInfoById(EmployeeId id);

    /**
     * 根据memberId查找员工
     *
     * @param memberId
     * @return
     */
    EmployeeInfo findInfoByMemberId(Long memberId);

    /**
     * 获取指定组织下对应用户id对应的员工
     *
     * @param organizationId
     * @param userId
     * @return
     */
    Employee findByUser(OrganizationId organizationId, Long userId);

    /**
     * 获取指定组织的创始人员工
     *
     * @param organizationId
     * @return
     */
    Employee findOrganizationCreator(OrganizationId organizationId);

    /**
     * 获取指定组织所有的管理员
     *
     * @param organizationId
     * @return
     */
    List<Employee> findOrganizationAdmins(OrganizationId organizationId);

    /**
     * 保存一个员工
     *
     * @param employee
     * @return
     */
    EmployeeId save(Employee employee);

    /**
     * 保存一个员工资料
     *
     * @param employeeInfo
     * @return
     */
    EmployeeId saveInfo(EmployeeInfo employeeInfo);

    /**
     * 删除一个员工
     *
     * @param id
     * @param identity
     */
    void remove(EmployeeId id, EmployeeIdentity identity);

    /**
     * 查询指定组织下指定员工手机号的数量
     *
     * @param organizationId
     * @param phone
     * @param excludeId
     * @return
     */
    int countByOrganizationAndEmployeePhone(OrganizationId organizationId, String phone, Long excludeId);

    /**
     * 查询指定组织下指定员工邮箱的数量
     *
     * @param organizationId
     * @param email
     * @param excludeId
     * @return
     */
    int countByOrganizationAndEmployeeEmail(OrganizationId organizationId, String email, Long excludeId);
}
