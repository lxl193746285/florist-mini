package com.qy.base.interfaces.organization.api;

import com.qy.organization.app.application.command.*;
import com.qy.organization.app.application.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.dto.SendMessageEmployeeInfoDTO;
import com.qy.organization.app.application.query.EmployeeQuery;
import com.qy.organization.app.application.service.EmployeeCommandService;
import com.qy.organization.app.application.service.EmployeeQueryService;
import com.qy.organization.app.domain.service.EmployeeService;
import com.qy.organization.app.domain.valueobject.EmployeeId;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织员工内部服务接口
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/employee/employees")
public class EmployeeApiController {
    private OrganizationSessionContext sessionContext;
    private EmployeeCommandService employeeCommandService;
    private EmployeeQueryService employeeQueryService;
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeApiController.class);

    public EmployeeApiController(OrganizationSessionContext sessionContext, EmployeeCommandService employeeCommandService,
                                 EmployeeQueryService employeeQueryService, EmployeeService employeeService) {
        this.sessionContext = sessionContext;
        this.employeeCommandService = employeeCommandService;
        this.employeeQueryService = employeeQueryService;
        this.employeeService = employeeService;
    }

    /**
     * 获取用户加入的指定组织的员工信息
     *
     * @param organizationId
     * @param userId
     * @return
     */
    @GetMapping("/basic-employees/by-organization-and-user")
    public ResponseEntity<EmployeeBasicDTO> getUserJoinOrganizationBasicEmployee(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "user_id") Long userId
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getUserJoinOrganizationBasicEmployee(organizationId, userId));
    }

    /**
     * 获取用户加入的所有组织的员工信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/basic-employees/by-user")
    public ResponseEntity<List<EmployeeBasicDTO>> getUserJoinOrganizationBasicEmployees(
            @RequestParam(value = "user_id") Long userId
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getUserJoinOrganizationBasicEmployees(userId));
    }

    /**
     * 根据员工id获取基本员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/basic-employees/{id}")
    public ResponseEntity<EmployeeBasicDTO> getBasicEmployee(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployeeById(id));
    }

    /**
     * 根据员工id+组织id获取基本员工信息（跨库使用）
     *
     * @param id
     * @param organizationId
     * @return
     */
    @GetMapping("/basic-employees/by-organization")
    public ResponseEntity<EmployeeBasicDTO> getBasicEmployeeByOrganizationId(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "organization_id") Long organizationId
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployeeByOrganizationId(id, organizationId));
    }

    /**
     * 根据会员id获取基本员工信息
     *
     * @param memberId
     * @return
     */
    @GetMapping("/basic-employees/by-member/{member_id}")
    public ResponseEntity<EmployeeBasicDTO> getBasicEmployeeByMemberId(
            @PathVariable(value = "member_id") Long memberId
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployeeByMemberId(memberId));
    }

    /**
     * 根据员工手机号获取基本员工信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/basic-employees/by-phone/{phone}")
    public ResponseEntity<EmployeeBasicDTO> getBasicEmployeeByPhone(
            @PathVariable(value = "phone") String phone
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployeeByPhone(phone));
    }

    /**
     * 根据查询条件获取员工基本信息
     *
     * @param query
     * @return
     */
    @GetMapping("/basic-employees")
    public ResponseEntity<List<EmployeeBasicDTO>> getBasicEmployees(EmployeeQuery query) {
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployees(query));
    }

    /**
     * 根据员工id集合获取多个基本员工信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/basic-employees/by-ids")
    public ResponseEntity<List<EmployeeBasicDTO>> getBasicEmployeesByIds(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployees(ids));
    }

    /**
     * 获取组织的创始人(超管)
     *
     * @param organizationId
     * @return
     */
    @GetMapping("/{organizationId}/creator")
    public ResponseEntity<EmployeeBasicDTO> getOrganizationCreator(
            @PathVariable(value = "organizationId") Long organizationId
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getOrganizationCreator(organizationId));
    }

    /**
     * 获取发送消息员工账号信息DTO
     *
     * @param employeeId
     * @return
     */
    @GetMapping("/{employeeId}/send-message-employee-info")
    public ResponseEntity<SendMessageEmployeeInfoDTO> getSendMessageEmployeeInfo(
            @PathVariable(value = "employeeId") Long employeeId
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getSendMessageEmployeeInfo(employeeId));
    }

    /**
     * 获取组织下有指定权限的用户
     *
     * @param organizationId 组织id，不传递则取用户的主组织id
     * @param permission     权限节点
     * @return
     */
    @GetMapping("/has-permission-basic-employees")
    public ResponseEntity<List<EmployeeBasicDTO>> getHasPermissionEmployees(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "permission") String permission
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getHasPermissionUsers(organizationId, permission));
    }

    /**
     * 设置员工身份权限
     *
     * @param command
     */
    @PostMapping("/set-identity-and-permission")
    public void setIdentityAndPermission(
            @RequestBody SetEmployeeIdentityCommand command
    ) {
        employeeCommandService.setIdentityAndPermission(command, sessionContext.getUser());
    }

    /**
     * 创建员工
     *
     * @param command
     */
    @PostMapping("/create-employee")
    public Long createIdentity(
            @RequestBody CreateEmployeeCommand command
    ) {
        command.setEmployee(sessionContext.getEmployee());
        return employeeCommandService.createEmployee(command, sessionContext.getUser()).getId();
    }

    /**
     * 修改员工
     *
     * @param command
     */
    @PostMapping("/update-employee")
    public void updateIdentity(
            @RequestBody UpdateEmployeeCommand command
    ) {
        command.setEmployee(sessionContext.getEmployee());
        employeeCommandService.updateEmployee(command);
    }

    /**
     * 删除员工
     *
     * @param command
     */
    @DeleteMapping("/delete-employee")
    public void deleteIdentity(
            @RequestBody DeleteEmployeeCommand command
    ) {
        command.setEmployee(sessionContext.getEmployee());
        employeeCommandService.deleteEmployee(command);
    }

    /**
     * 创建组织的创始人员工
     *
     * @param organizationId
     * @param userId
     * @param roleIds
     * @return employeeId
     */
    @PostMapping("/create-organization-creator-employee")
    public Long createOrganizationCreatorEmployee(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "department_id") Long departmentId,
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "role_ids") List<Long> roleIds
    ) {
        return employeeService.createOrganizationCreatorEmployee(new OrganizationId(organizationId), departmentId, userId, roleIds).getId();
    }

    /**
     * 更新组织创建人权限
     *
     * @param command
     */
    @PostMapping("/update-organization-creator-role")
    public void updateOrganizationCreatorRole(
            @RequestBody OrganizationCreatorCommand command
    ) {
        employeeService.updateOrganizationCreatorRole(new OrganizationId(command.getOrganizationId()), command.getRoleIds());
    }

    /**
     * 更换组织超管
     *
     * @param command
     */
    @PostMapping("/change-organization-creator")
    public void changeOrganizationCreator(
            @RequestBody OrganizationCreatorCommand command
    ) {
        employeeService.changeOrganizationCreator(new OrganizationId(command.getOrganizationId()), command.getUserId(), command.getRoleIds());
    }

}

