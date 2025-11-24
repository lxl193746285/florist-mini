package com.qy.organization.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.organization.api.command.*;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.api.dto.SendMessageEmployeeInfoDTO;
import com.qy.organization.api.query.EmployeeQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-employee", configuration = FeignTokenRequestInterceptor.class)
public interface EmployeeClient {
    /**
     * 获取用户加入的指定组织的员工信息
     *
     * @param organizationId
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/by-organization-and-user")
    EmployeeBasicDTO getUserJoinOrganizationBasicEmployee(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "user_id") Long userId
    );

    /**
     * 获取用户加入的所有组织的员工信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/by-user")
    List<EmployeeBasicDTO> getUserJoinOrganizationBasicEmployees(
            @RequestParam(value = "user_id") Long userId
    );

    /**
     * 根据员工id获取基本员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/{id}")
    EmployeeBasicDTO getBasicEmployeeById(
            @PathVariable(value = "id") Long id
    );

    /**
     * 根据员工id+组织id获取基本员工信息（跨库使用）
     *
     * @param id
     * @param organizationId
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/by-organization")
    EmployeeBasicDTO getBasicEmployeeByOrganizationId(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "organization_id") Long organizationId
    );

    /**
     * 根据会员id获取基本员工信息
     *
     * @param memberId
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/by-member/{member_id}")
    EmployeeBasicDTO getBasicEmployeeByMemberId(
            @PathVariable(value = "member_id") Long memberId
    );

    /**
     * 根据员工手机号获取基本员工信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/by-phone/{phone}")
    EmployeeBasicDTO getBasicEmployeeByPhone(
            @PathVariable(value = "phone") String phone
    );

    /**
     * 根据查询条件获取员工基本信息
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees")
    List<EmployeeBasicDTO> getBasicEmployees(@SpringQueryMap EmployeeQuery query);

    /**
     * 根据员工id集合获取多个基本员工信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/by-ids")
    List<EmployeeBasicDTO> getBasicEmployeesByIds(
            @RequestParam(value = "ids") List<Long> ids
    );

    /**
     * 获取组织的创始人(超管)
     *
     * @param organizationId
     * @return
     */
    @GetMapping("/v4/api/employee/employees/{organizationId}/creator")
    EmployeeBasicDTO getOrganizationCreator(
            @PathVariable(value = "organizationId") Long organizationId
    );

    /**
     * 获取发送消息员工账号信息DTO
     *
     * @param employeeId
     * @return
     */
    @GetMapping("/v4/api/employee/employees/{employeeId}/send-message-employee-info")
    SendMessageEmployeeInfoDTO getSendMessageEmployeeInfo(
            @PathVariable(value = "employeeId") Long employeeId
    );

    /**
     * 获取组织下有指定权限的用户
     *
     * @param organizationId 组织id，不传递则取用户的主组织id
     * @param permission     权限节点
     * @return
     */
    @GetMapping("/v4/api/employee/employees/has-permission-basic-employees")
    List<EmployeeBasicDTO> getHasPermissionEmployees(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "permission") String permission
    );

    /**
     * 设置员工身份权限
     *
     * @param command
     */
    @PostMapping("/v4/api/employee/employees/set-identity-and-permission")
    void setIdentityAndPermission(
            @RequestBody SetEmployeeIdentityCommand command
    );

    /**
     * 创建员工
     *
     * @param command
     */
    @PostMapping("/v4/api/employee/employees/create-employee")
    public Long createIdentity(
            @RequestBody CreateEmployeeCommand command
    );

    /**
     * 修改员工
     *
     * @param command
     */
    @PostMapping("/v4/api/employee/employees/update-employee")
    public void updateIdentity(
            @RequestBody UpdateEmployeeCommand command
    );

    /**
     * 删除员工
     *
     * @param command
     */
    @DeleteMapping("/v4/api/employee/employees/delete-employee")
    public void deleteIdentity(
            @RequestBody DeleteEmployeeCommand command
    );

    /**
     * 创建组织的创始人员工
     *
     * @param organizationId
     * @param userId
     * @param roleIds
     * @return employeeId
     */
    @PostMapping("/v4/api/employee/employees/create-organization-creator-employee")
    Long createOrganizationCreatorEmployee(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "department_id") Long departmentId,
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "role_ids") List<Long> roleIds
    );

    /**
     * 更新组织创建人权限
     *
     * @param command
     */
    @PostMapping("/v4/api/employee/employees/update-organization-creator-role")
    void updateOrganizationCreatorRole(
            @RequestBody OrganizationCreatorCommand command
    );

    /**
     * 更换组织超管
     *
     * @param command
     */
    @PostMapping("/v4/api/employee/employees/change-organization-creator")
    void changeOrganizationCreator(
            @RequestBody OrganizationCreatorCommand command
    );
}
