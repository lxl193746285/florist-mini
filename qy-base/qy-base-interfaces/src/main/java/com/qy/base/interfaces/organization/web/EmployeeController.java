package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.application.command.*;
import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.query.EmployeeQuery;
import com.qy.organization.app.application.security.EmployeePermission;
import com.qy.organization.app.application.service.EmployeeCommandService;
import com.qy.organization.app.application.service.EmployeeQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.security.session.OrganizationSessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织员工
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Api(tags = "组织员工")
@RestController
@RequestMapping("/v4/employee/employees")
public class EmployeeController {
    private OrganizationSessionContext sessionContext;
    private EmployeeCommandService employeeCommandService;
    private EmployeeQueryService employeeQueryService;
    private MemberSystemSessionContext memberSystemSessionContext;


    public EmployeeController(OrganizationSessionContext sessionContext, EmployeeCommandService employeeCommandService,
                              EmployeeQueryService employeeQueryService, MemberSystemSessionContext memberSystemSessionContext) {
        this.sessionContext = sessionContext;
        this.employeeCommandService = employeeCommandService;
        this.employeeQueryService = employeeQueryService;
        this.memberSystemSessionContext = memberSystemSessionContext;
    }

    /**
     * 获取组织员工列表
     *
     * @apiNote 员工模块ID：employee
     *          操作以及权限节点
     *          首页（list） organization/employee/index
     *          查看  (view) organization/employee/view
     *          创建（create） organization/employee/create
     *          编辑（update） organization/employee/update
     *          删除（delete） organization/employee/delete
     *          在职（on-job） organization/employee/on-job
     *          离职（leave-job） organization/employee/leave-job
     *          设置身份（set-identity） organization/employee/set-identity
     *
     *          代码表：
     *          用户性别：gender（系统）
     *          用户分类：user_category（组织）
     *          是否在通讯录显示: common_yes_or_no（系统）
     *          在职离职：job_status（系统）
     *          离职原因：employee_leave_reason（组织）
     *
     * @return
     */
    @ApiOperation("获取组织员工列表")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees(EmployeeQuery query) {
        //判断权限
        EmployeeIdentity employeeIdentity = query.getOrganizationId() != null ? sessionContext.getEmployee(query.getOrganizationId()) : sessionContext.getEmployee();
        if (!employeeIdentity.hasPermission(query.getPermission() == null ? EmployeePermission.LIST.getPermission() : query.getPermission())) {
            throw new AccessDeniedException("没有权限访问");
        }

        Page<EmployeeDTO> page = employeeQueryService.getEmployees(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取组织基本员工信息列表
     *
     * @return
     */
    @ApiOperation("获取组织基本员工信息列表")
    @GetMapping("/basic-employees")
    public ResponseEntity<List<EmployeeBasicDTO>> getBasicEmployees(EmployeeQuery query) {
        //判断权限
        EmployeeIdentity employeeIdentity = query.getOrganizationId() != null ? sessionContext.getEmployee(query.getOrganizationId()) : sessionContext.getEmployee();
        if (!employeeIdentity.hasPermission(query.getPermission() == null ? EmployeePermission.LIST.getPermission() : query.getPermission())) {
            throw new AccessDeniedException("没有权限访问");
        }

        Page<EmployeeBasicDTO> page = employeeQueryService.getBasicEmployees(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取组织下有指定权限的用户
     *
     * @param organizationId 组织id，不传递则取用户的主组织id
     * @param permission 权限节点
     * @return
     */
    @ApiOperation("获取组织下有指定权限的用户")
    @GetMapping("/has-permission-basic-employees")
    public ResponseEntity<List<EmployeeBasicDTO>> getHasPermissionEmployees(
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "permission") String permission
    ) {
        EmployeeIdentity identity = sessionContext.getEmployee();
        if (organizationId == null) {
            organizationId = identity.getOrganizationId();
        }
        return ResponseUtils.ok().body(employeeQueryService.getHasPermissionUsers(organizationId, permission));
    }

    /**
     * 获取单个组织员工
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个组织员工")
    @GetMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.VIEW, #id)")
    public ResponseEntity<EmployeeDetailDTO> getEmployee(
        @PathVariable(value = "id") Long id
    ) {
        EmployeeDetailDTO employeeDetailDTO = employeeQueryService.getEmployeeDetailById(id, sessionContext.getUser());
        if (employeeDetailDTO == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        return ResponseUtils.ok().body(employeeDetailDTO);
    }

    /**
     * 创建单个组织员工
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个组织员工")
    @PostMapping
    @PreAuthorize("@organizationSessionContext.getEmployee(#command.organizationId).hasPermission(@employeePermission.CREATE)")
    public ResponseEntity<Object> createEmployee(
            @Valid @RequestBody CreateEmployeeCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        if (command.getOrganizationId() == null){
            command.setEmployee(sessionContext.getEmployee(sessionContext.getEmployee().getOrganizationId()));
        } else {
            command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        }
        employeeCommandService.createEmployee(command, sessionContext.getUser());

        return ResponseUtils.ok("员工创建成功").build();
    }

    /**
     * 修改单个组织员工
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个组织员工")
    @PatchMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.UPDATE, #id)")
    public ResponseEntity<Object> updateEmployee(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateEmployeeCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        System.out.println("##########执行开始");
        employeeCommandService.updateEmployee(command);
        System.out.println("##########执行结束");
        return ResponseUtils.ok("员工修改成功").build();
    }

    /**
     * 删除单个组织员工
     *
     * @param id
     */
    @ApiOperation("删除单个组织员工")
    @DeleteMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.DELETE, #id)")
    public ResponseEntity<Object> deleteEmployee(
        @PathVariable(value = "id") Long id
    ) {
        EmployeeDTO employeeDTO = employeeQueryService.getEmployeeById(id);
        if (employeeDTO == null) {
            throw new NotFoundException("未找到需要删除的员工");
        }

        DeleteEmployeeCommand command = new DeleteEmployeeCommand();
        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(employeeDTO.getOrganizationId()));
        employeeCommandService.deleteEmployee(command);

        return ResponseUtils.noContent("删除员工成功").build();
    }

    /**
     * 设置员工在职
     *
     * @return
     */
    @ApiOperation("设置员工在职")
    @PostMapping("/{id}/on-job")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.ON_JOB, #id)")
    public ResponseEntity<Object> onJob(
            @PathVariable(value = "id") Long id
    ) {
        EmployeeOnJobCommand command = new EmployeeOnJobCommand();

        command.setId(id);
        employeeCommandService.onJob(command);

        return ResponseUtils.ok("设置员工在职成功").build();
    }

    /**
     * 设置员工离职
     *
     * @return
     */
    @ApiOperation("设置员工离职")
    @PostMapping("/{id}/leave-job")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.LEAVE_JOB, #id)")
    public ResponseEntity<Object> leaveJob(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody EmployeeLeaveJobCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        employeeCommandService.leaveJob(command);

        return ResponseUtils.ok("设置员工离职成功").build();
    }

    /**
     * 获取指定员工的菜单权限勾选信息
     *
     * @param id
     * @return
     */
    @ApiOperation("获取指定员工的菜单权限勾选信息")
    @GetMapping("/{id}/menu-and-permissions")
    public ResponseEntity<RoleMenuPermissionDTO> getEmployeeMenuPermission(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseEntity.ok().body(employeeQueryService.getEmployeeMenuPermission(id, sessionContext.getUser()));
    }

    /**
     * 设置员工身份权限
     *
     * @param command
     * @return
     */
    @ApiOperation("设置员工身份权限")
    @PostMapping("/{id}/set-identity-and-permission")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.SET_IDENTITY, #id)")
    public ResponseEntity<Object> setIdentityAndPermission(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody SetEmployeeIdentityCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        employeeCommandService.setIdentityAndPermission(command, sessionContext.getUser());

        return ResponseUtils.ok("设置员工身份权限成功").build();
    }

    /**
     * 批量设置员工身份权限
     *
     * @param command
     * @return
     */
    @ApiOperation("批量设置员工身份权限")
    @PostMapping("/batch-set-identity-and-permission")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission.BATCH_SET_IDENTITY)")
    public ResponseEntity<Object> batchSetIdentityAndPermission(
            @Valid @RequestBody SetEmployeeIdentityCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        if (command.getIds() != null && command.getIds().size() > 0) {
            for (Long id: command.getIds()) {
                command.setId(id);
                employeeCommandService.setIdentityAndPermission(command, sessionContext.getUser());
            }
        }
        return ResponseUtils.ok("设置员工身份权限成功").build();
    }

    /**
     * 更改员工登录用户名
     *
     * @param command
     * @return
     */
    @ApiOperation("更改员工登录用户名")
    @PostMapping("/{id}/change-username")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.CHANGE_USERNAME, #id)")
    public ResponseEntity<Object> changeEmployeeUsername(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ChangeEmployeeUsernameCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        employeeCommandService.changeEmployeeUsername(command);

        return ResponseUtils.ok("更换员工登录用户名成功").build();
    }

    /**
     * 更改员工登录手机号
     *
     * @param command
     * @return
     */
    @ApiOperation("更改员工登录手机号")
    @PostMapping("/{id}/change-user-phone")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.CHANGE_USER_PHONE, #id)")
    public ResponseEntity<Object> changeEmployeeUserPhone(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ChangeEmployeeUserPhoneCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        employeeCommandService.changeEmployeeUserPhone(command);

        return ResponseUtils.ok("更换员工登录手机号成功").build();
    }

    /**
     * 重置密码
     *
     * @param id 员工id
     * @return
     */
    @ApiOperation("重置密码")
    @PostMapping("/{id}/reset-password")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@employeePermission, @employeePermission.RESET_PASSWORD, #id)")
    public ResponseEntity<Object> resetPassword(
            @PathVariable(value = "id") Long id
    ) {
        employeeCommandService.resetPassword(id);

        return ResponseUtils.ok("重置密码成功").build();
    }

    /**
     * 根据token获取基本员工信息
     *
     * @return
     */
    @GetMapping("/by-member")
    public ResponseEntity<EmployeeBasicDTO> getBasicEmployeeByMemberId(
    ) {
        return ResponseUtils.ok().body(employeeQueryService.getBasicEmployeeByMemberId(memberSystemSessionContext.getMember().getId()));
    }

}

