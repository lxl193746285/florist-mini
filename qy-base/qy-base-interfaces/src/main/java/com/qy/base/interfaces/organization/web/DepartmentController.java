package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.application.command.CreateDepartmentCommand;
import com.qy.organization.app.application.command.DeleteDepartmentCommand;
import com.qy.organization.app.application.command.UpdateDepartmentCommand;
import com.qy.organization.app.application.dto.DepartmentBasicDTO;
import com.qy.organization.app.application.dto.DepartmentChildInfoDTO;
import com.qy.organization.app.application.dto.DepartmentDTO;
import com.qy.organization.app.application.query.DepartmentQuery;
import com.qy.organization.app.application.service.DepartmentCommandService;
import com.qy.organization.app.application.service.DepartmentQueryService;
import com.qy.rest.exception.NotFoundException;
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
 * 组织部门
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Api(tags = "组织部门")
@RestController
@RequestMapping("/v4/employee/departments")
public class DepartmentController {
    private OrganizationSessionContext sessionContext;
    private DepartmentQueryService departmentQueryService;
    private DepartmentCommandService departmentCommandService;

    public DepartmentController(OrganizationSessionContext sessionContext, DepartmentQueryService departmentQueryService, DepartmentCommandService departmentCommandService) {
        this.sessionContext = sessionContext;
        this.departmentQueryService = departmentQueryService;
        this.departmentCommandService = departmentCommandService;
    }

    /**
     * 获取组织部门列表
     *
     * @param query
     * @return
     */
    @ApiOperation("获取组织部门列表")
    @GetMapping
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@departmentPermission.LIST)")
    public ResponseEntity<List<DepartmentDTO>> getDepartments(@Valid DepartmentQuery query) {
        if (query.getOrganizationId() == null){
            query.setOrganizationId(sessionContext.getEmployee().getOrganizationId());
        }
        return ResponseUtils.ok().body(departmentQueryService.getDepartments(query, sessionContext.getUser()));
    }

    /**
     * 获取组织基本部门信息列表
     *
     * @param query
     * @return
     */
    @ApiOperation("获取组织基本部门信息列表")
    @GetMapping("/basic-departments")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@departmentPermission.LIST)")
    public ResponseEntity<List<DepartmentBasicDTO>> getBasicDepartments(@Valid DepartmentQuery query) {
        return ResponseUtils.ok().body(departmentQueryService.getBasicDepartments(query, sessionContext.getUser()));
    }

    /**
     * 获取部门子部门以及人员信息
     *
     * @param organizationId 组织id
     * @param departmentId 部门id
     * @param keywords 关键字
     * @return
     */
    @ApiOperation("获取部门子部门以及人员信息")
    @GetMapping("/child-info")
    public ResponseEntity<DepartmentChildInfoDTO> getDepartmentChildInfo(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "department_id") Long departmentId,
            @RequestParam(value = "keywords", required = false) String keywords
    ) {
        return ResponseUtils.ok().body(departmentQueryService.getDepartmentChildInfo(organizationId, departmentId, keywords));
    }

    /**
     * 获取单个组织部门
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个组织部门")
    @GetMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@departmentPermission, @departmentPermission.VIEW, #id)")
    public ResponseEntity<DepartmentDTO> getDepartmentById(
        @PathVariable(value = "id") Long id
    ) {
        DepartmentDTO departmentDTO = departmentQueryService.getDepartmentById(id, sessionContext.getUser());
        if (departmentDTO == null) {
            throw new NotFoundException("未找到指定的部门");
        }
        return ResponseUtils.ok().body(departmentDTO);
    }

    /**
     * 创建单个组织部门
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个组织部门")
    @PostMapping
    @PreAuthorize("@organizationSessionContext.getEmployee(#command.organizationId).hasPermission(@departmentPermission.CREATE)")
    public ResponseEntity<Object> createDepartment(
        @Valid @RequestBody CreateDepartmentCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        departmentCommandService.createDepartment(command);

        return ResponseUtils.ok("部门创建成功").build();
    }

    /**
     * 修改单个组织部门
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个组织部门")
    @PatchMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@departmentPermission, @departmentPermission.UPDATE, #id)")
    public ResponseEntity<Object> updateDepartment(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateDepartmentCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        departmentCommandService.updateDepartment(command);

        return ResponseUtils.ok("部门修改成功").build();
    }

    /**
     * 删除单个组织部门
     *
     * @param id
     */
    @ApiOperation("删除单个组织部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@departmentPermission, @departmentPermission.DELETE, #id)")
    public ResponseEntity<Object> deleteDepartment(
        @PathVariable(value = "id") Long id
    ) {
        DepartmentDTO departmentDTO = departmentQueryService.getDepartmentById(id);
        if (departmentDTO == null) {
            throw new NotFoundException("未找到需要删除的部门");
        }
        DeleteDepartmentCommand command = new DeleteDepartmentCommand();
        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(departmentDTO.getOrganizationId()));
        departmentCommandService.deleteDepartment(command);

        return ResponseUtils.noContent("删除部门成功").build();
    }
}

