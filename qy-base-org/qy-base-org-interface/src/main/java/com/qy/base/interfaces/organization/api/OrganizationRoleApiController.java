package com.qy.base.interfaces.organization.api;

import com.qy.organization.app.application.command.AssignRoleToEmployeeCommand;
import com.qy.organization.app.application.command.AssignRoleToUserCommand;
import com.qy.organization.app.application.dto.RoleBasicDTO;
import com.qy.organization.app.application.dto.RoleDTO;
import com.qy.organization.app.application.dto.RoleUserDTO;
import com.qy.organization.app.application.query.RoleQuery;
import com.qy.organization.app.application.service.OrganizationRoleCommandService;
import com.qy.organization.app.application.service.RoleQueryService;
import com.qy.organization.app.domain.enums.DefaultRole;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织权限组内部服务
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/organization/roles")
public class OrganizationRoleApiController {
    private OrganizationSessionContext sessionContext;
    private OrganizationRoleCommandService roleCommandService;
    private RoleQueryService roleQueryService;

    public OrganizationRoleApiController(OrganizationSessionContext sessionContext, OrganizationRoleCommandService roleCommandService, RoleQueryService roleQueryService) {
        this.sessionContext = sessionContext;
        this.roleCommandService = roleCommandService;
        this.roleQueryService = roleQueryService;
    }

    /**
     * 获取组织权限组列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<RoleDTO>> getRoles(RoleQuery query) {
        Page<RoleDTO> page = roleQueryService.getRoles(query, sessionContext.getUser());

        return ResponseUtils.ok().body(page);
    }

    /**
     * 获取指定用户的权限组
     *
     * @return
     */
    @GetMapping("/by-user")
    public ResponseEntity<List<RoleBasicDTO>> getRolesByOrganizationAndUser(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "system_id") Long systemId
    ) {
        return ResponseUtils.ok().body(roleQueryService.getRolesByOrganizationAndUser(organizationId, userId, systemId));
    }

    /**
     * 获取权限组下的用户
     * @param id
     * @return
     */
    @GetMapping("/{id}/users")
    public ResponseEntity<List<RoleUserDTO>> getRoleUsers(
            @PathVariable(value = "id") Long id
    ) {
        List<RoleUserDTO> roleUserDTOS = roleQueryService.getRoleUsers(id);
        return ResponseUtils.ok().body(roleUserDTOS);
    }

    /**
     * 分配权限组给指定用户
     *
     * @param command
     * @return
     */
    @PostMapping("/assign-role-to-user")
    public ResponseEntity<Object> assignRoleToUser(
            @Valid @RequestBody AssignRoleToUserCommand command
    ) {
        roleCommandService.assignRoleToUser(command);
        return ResponseUtils.ok("分配权限组成功").build();
    }

    /**
     * 移除指定员工权限组
     *
     * @return
     */
    @PostMapping("/revoke-employee-role")
    public ResponseEntity<Object> revokeEmployeeRole(
            @RequestParam(value = "employee_id") Long employeeId
    ) {
        roleCommandService.revokeEmployeeRole(employeeId);
        return ResponseUtils.ok("移除权限组成功").build();
    }

    /**
     * 分配权限组给指定员工
     *
     * @return
     */
    @PostMapping("/assign-role-to-employee")
    public ResponseEntity<Object> assignRoleToEmployee(
            @RequestBody AssignRoleToEmployeeCommand command
    ) {
        roleCommandService.assignRoleToEmployee(command);
        return ResponseUtils.ok("分配权限组成功").build();
    }

    /**
     * 获取指定的默认权限组
     *
     * @param systemId
     * @param organizationId
     * @return
     */
    @GetMapping("/default-role")
    public ResponseEntity<RoleBasicDTO> getDefaultRole(
            @RequestParam(value = "system_id") Long systemId,
            @RequestParam(value = "organization_id") Long organizationId
    ) {
        return ResponseUtils.ok().body(roleQueryService.getDefaultRole(DefaultRole.MEMBER_SYSTEM, systemId, organizationId));
    }

    /**
     * 获取指定的默认权限组
     *
     * @param authItem
     * @return
     */
    @GetMapping("/auth-item")
    public ResponseEntity<RoleBasicDTO> getByAuthItem(
            @RequestParam(value = "auth_item") String authItem
    ) {
        return ResponseUtils.ok().body(roleQueryService.getRoleByAuthItem(authItem));
    }
}