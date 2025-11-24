package com.qy.organization.app.application.service;

import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.query.EmployeeQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 员工查询服务
 *
 * @author legendjw
 */
public interface EmployeeQueryService {
    /**
     * 查询员工
     *
     * @param query
     * @param identity
     * @return
     */
    Page<EmployeeDTO> getEmployees(EmployeeQuery query, Identity identity);

    /**
     * 查询基本员工信息
     *
     * @param query
     * @param identity
     * @return
     */
    Page<EmployeeBasicDTO> getBasicEmployees(EmployeeQuery query, Identity identity);

    /**
     * 根据查询条件获取员工基本信息
     *
     * @param query
     * @return
     */
    List<EmployeeBasicDTO> getBasicEmployees(EmployeeQuery query);

    /**
     * 根据id集合获取员工基本信息
     *
     * @param ids
     * @return
     */
    List<EmployeeBasicDTO> getBasicEmployees(List<Long> ids);

    /**
     * 获取组织下有指定权限的用户
     *
     * @param organizationId
     * @return
     */
    List<EmployeeBasicDTO> getHasPermissionUsers(Long organizationId, String permission);

    /**
     * 根据组织id以及用户id获取员工基本信息
     *
     * @param organizationId
     * @param userId
     * @return
     */
    EmployeeBasicDTO getUserJoinOrganizationBasicEmployee(Long organizationId, Long userId);

    /**
     * 获取指定用户加入的组织员工基本信息
     *
     * @param userId
     * @return
     */
    List<EmployeeBasicDTO> getUserJoinOrganizationBasicEmployees(Long userId);

    /**
     * 根据ID查询员工基本信息
     *
     * @param id
     * @return
     */
    EmployeeBasicDTO getBasicEmployeeById(Long id);

    /**
     * 根据ID和组织id查询员工基本信息（跨区处理）
     *
     * @param id
     * @return
     */
    EmployeeBasicDTO getBasicEmployeeByOrganizationId(Long id, Long organizationId);

    /**
     * 根据手机号查询员工
     *
     * @param phone
     * @return
     */
    EmployeeBasicDTO getBasicEmployeeByPhone(String phone);

    /**
     * 根据ID查询员工基本信息
     *
     * @param memberId
     * @return
     */
    EmployeeBasicDTO getBasicEmployeeByMemberId(Long memberId);

    /**
     * 根据ID查询员工
     *
     * @param id
     * @param identity
     * @return
     */
    EmployeeDTO getEmployeeById(Long id, Identity identity);

    /**
     * 根据ID查询员工
     *
     * @param id
     * @return
     */
    EmployeeDTO getEmployeeById(Long id);

    /**
     * 根据ID查询员工
     *
     * @param id
     * @param identity
     * @return
     */
    EmployeeDetailDTO getEmployeeDetailById(Long id, Identity identity);

    /**
     * 获取组织的创始人(超管)
     *
     * @param organizationId
     * @return
     */
    EmployeeBasicDTO getOrganizationCreator(Long organizationId);

    /**
     * 获取指定员工的菜单权限勾选信息
     *
     * @param employeeId
     * @param identity
     * @return
     */
    RoleMenuPermissionDTO getEmployeeMenuPermission(Long employeeId, Identity identity);

    /**
     * 获取发送消息员工账号信息DTO
     *
     * @param employeeId
     * @return
     */
    SendMessageEmployeeInfoDTO getSendMessageEmployeeInfo(Long employeeId);
}