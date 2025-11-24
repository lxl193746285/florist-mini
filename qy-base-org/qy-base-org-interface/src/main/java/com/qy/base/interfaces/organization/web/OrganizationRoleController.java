package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.application.command.*;
import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.query.RoleQuery;
import com.qy.organization.app.application.service.OrganizationRoleCommandService;
import com.qy.organization.app.application.service.RoleQueryService;
import com.qy.organization.app.domain.enums.DefaultRole;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织权限组
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@Api(tags = "组织权限组")
@RequestMapping("/v4/organization/roles")
public class OrganizationRoleController {
    private OrganizationSessionContext sessionContext;
    private OrganizationRoleCommandService roleCommandService;
    private RoleQueryService roleQueryService;

    public OrganizationRoleController(OrganizationSessionContext sessionContext, OrganizationRoleCommandService roleCommandService, RoleQueryService roleQueryService) {
        this.sessionContext = sessionContext;
        this.roleCommandService = roleCommandService;
        this.roleQueryService = roleQueryService;
    }

    /**
     * 获取组织权限组列表
     *
     * @apiNote 模块id：role
     *
     *          操作以及权限节点
     *          首页（index） organization/role/index
     *          查看 (view) organization/role/view
     *          创建（create） organization/role/create
     *          编辑（update） organization/role/update
     *          删除（delete） organization/role/delete
     *          授权（authorize） organization/role/authorize
     *          设置默认权限组（set-default-role） organization/role/set-default-role
     *
     *          代码表：
     *          权限组上下文：role_context（系统）
     *          状态：common_status（系统）
     *          默认权限组：default_role（系统）
     *
     * @return
     */
    @ApiOperation("获取组织权限组列表")
    @GetMapping
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@rolePermission.LIST)")
    public ResponseEntity<List<RoleDTO>> getRoles(RoleQuery query) {
        Page<RoleDTO> page = roleQueryService.getRoles(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个组织权限组
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个组织权限组")
    @GetMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@rolePermission, @rolePermission.VIEW, #id)")
    public ResponseEntity<RoleDTO> getRole(
        @PathVariable(value = "id") Long id
    ) {
        RoleDTO roleDTO = roleQueryService.getRoleById(id, sessionContext.getUser());
        if (roleDTO == null) {
            throw new NotFoundException("未找到指定的权限组");
        }
        return ResponseUtils.ok().body(roleDTO);
    }

    /**
     * 创建单个组织权限组
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个组织权限组")
    @PostMapping
    @PreAuthorize("@organizationSessionContext.getEmployee(#command.organizationId).hasPermission(@rolePermission.CREATE)")
    public ResponseEntity<RoleDTO> createRole(
            @Valid @RequestBody CreateRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        Long id = roleCommandService.createRole(command);

        return ResponseUtils.ok("权限组创建成功").body(roleQueryService.getRoleById(id, sessionContext.getUser()));
    }

    /**
     * 修改单个组织权限组
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个组织权限组")
    @PatchMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@rolePermission, @rolePermission.UPDATE, #id)")
    public ResponseEntity<RoleDTO> updateRole(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateRoleCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        roleCommandService.updateRole(command);

        return ResponseUtils.ok("权限组修改成功").body(roleQueryService.getRoleById(id, sessionContext.getUser()));
    }

    /**
     * 删除单个组织权限组
     *
     * @param id
     */
    @ApiOperation("删除单个组织权限组")
    @DeleteMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@rolePermission, @rolePermission.DELETE, #id)")
    public ResponseEntity<Object> deleteRole(
        @PathVariable(value = "id") Long id
    ) {
        RoleDTO roleDTO = roleQueryService.getRoleById(id);
        if (roleDTO == null) {
            throw new NotFoundException("未找到需要删除的权限组");
        }

        DeleteRoleCommand command = new DeleteRoleCommand();
        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(roleDTO.getOrganizationId()));
        roleCommandService.deleteRole(command);

        return ResponseUtils.noContent("删除权限组成功").build();
    }

    /**
     * 复制一个权限组
     *
     * @param id 复制的权限组的id
     * @param command
     * @param result
     * @return
     */
    @ApiOperation("复制一个权限组")
    @PostMapping("/{id}/copy")
    public ResponseEntity<RoleDTO> copyRole(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CopyRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        RoleDTO roleDTO = roleQueryService.getRoleById(id);
        if (roleDTO == null) {
            throw new NotFoundException("未找到需要复制的权限组");
        }

        command.setEmployee(sessionContext.getEmployee(roleDTO.getOrganizationId()));
        command.setCopyRoleId(id);
        Long newId = roleCommandService.copyRole(command);

        return ResponseUtils.ok("权限组复制成功").body(roleQueryService.getRoleById(newId, sessionContext.getUser()));
    }

    /**
     * 权限组授权
     *
     * @param command
     * @param result
     * @return
     */
    @ApiOperation("权限组授权")
    @PostMapping("/{id}/authorize")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@rolePermission, @rolePermission.AUTHORIZE, #id)")
    public ResponseEntity<Object> authorizeRole(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AuthorizeRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setId(id);
        roleCommandService.authorizeRole(command, sessionContext.getUser());
        return ResponseUtils.ok("权限组授权成功").build();
    }

    /**
     * 获取指定权限组的权限
     *
     * @param id 权限组id
     * @return
     */
    @ApiOperation("获取指定权限组的权限")
    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<PermissionWithRuleDTO>> getRolePermissions(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseEntity.ok().body(roleQueryService.getRolePermissions(id));
    }

    /**
     * 获取指定权限组的菜单权限勾选信息
     *
     * @param id 权限组id
     * @return
     */
    @ApiOperation("获取指定权限组的菜单权限勾选信息")
    @GetMapping("/{id}/menu-and-permissions")
    public ResponseEntity<RoleMenuPermissionDTO> getRoleMenuPermission(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseEntity.ok().body(roleQueryService.getRoleMenuPermission(id, sessionContext.getUser()));
    }

    /**
     * 设置默认权限组
     *
     * @param command
     * @param result
     * @return
     */
    @ApiOperation("设置默认权限组")
    @PostMapping("/{id}/set-default-role")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@rolePermission, @rolePermission.SET_DEFAULT_ROLE, #id)")
    public ResponseEntity<Object> setDefaultRole(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody SetDefaultRoleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setId(id);
        command.setDefaultRole(DefaultRole.MEMBER_SYSTEM.name());
        roleCommandService.setDefaultRole(command);
        return ResponseUtils.ok("设置默认权限组成功").build();
    }

    /**
     * 权限组人员查询
     */
    @ApiOperation("权限组人员查询")
    @GetMapping("/getAuthUsers/{id}")
    public ResponseEntity<List<AuthRoleUserDTO>> getAuthUsers(
            @PathVariable(value = "id") Long id,
            AuthRoleUserQueryDTO query
    ) {
        query.setId(id);
        Page<AuthRoleUserDTO> page = roleQueryService.getAuthUsers(id,query);
        return ResponseUtils.ok(page).body(page.getRecords());
    }

//    /**
//     * 会员系统-设置默认权限组
//     *
//     * @param command
//     * @param result
//     * @return
//     */
//    @ApiOperation("会员系统-设置默认权限组")
//    @PostMapping("/{id}/set-default-role/for-member-system")
//    @PreAuthorize("@organizationSessionContext.user.hasPermission(@rolePermission, @rolePermission.MEMBER_SET_DEFAULT_ROLE, #id)")
//    public ResponseEntity<Object> setDefaultRoleForMemberSystem(
//            @PathVariable(value = "id") Long id,
//            @Valid @RequestBody SetDefaultRoleForMemberCommand command,
//            BindingResult result
//    ) {
//        ValidationUtils.handleValidationResult(result);
//        command.setId(id);
//        roleCommandService.setDefaultRoleForMemberSystem(command);
//        return ResponseUtils.ok("设置默认权限组成功").build();
//    }

    /**
     * 获取指定的默认权限组
     *
     * @param systemId
     * @param organizationId
     * @return
     */
    @ApiOperation("获取指定的默认权限组")
    @GetMapping("/default-role")
    public ResponseEntity<RoleBasicDTO> getDefaultRole(
            @RequestParam(value = "system_id") Long systemId,
            @RequestParam(value = "organization_id") Long organizationId
            ) {
        RoleBasicDTO roleDTO = roleQueryService.getDefaultRole(DefaultRole.MEMBER_SYSTEM, systemId, organizationId);
        if (roleDTO == null) {
            throw new NotFoundException("未找到指定的默认权限组");
        }
        return ResponseUtils.ok().body(roleDTO);
    }

}

