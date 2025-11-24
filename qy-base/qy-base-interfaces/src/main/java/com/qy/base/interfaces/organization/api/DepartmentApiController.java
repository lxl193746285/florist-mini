package com.qy.base.interfaces.organization.api;

import com.qy.organization.app.application.command.CreateDepartmentCommand;
import com.qy.organization.app.application.command.CreateTopDepartmentCommand;
import com.qy.organization.app.application.command.UpdateDepartmentCommand;
import com.qy.organization.app.application.dto.DepartmentBasicDTO;
import com.qy.organization.app.application.query.DepartmentQuery;
import com.qy.organization.app.application.service.DepartmentCommandService;
import com.qy.organization.app.application.service.DepartmentQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织部门内部服务接口
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/employee/departments")
public class DepartmentApiController {
    private OrganizationSessionContext sessionContext;
    private DepartmentQueryService departmentQueryService;
    private DepartmentCommandService departmentCommandService;

    public DepartmentApiController(OrganizationSessionContext sessionContext, DepartmentQueryService departmentQueryService, DepartmentCommandService departmentCommandService) {
        this.sessionContext = sessionContext;
        this.departmentQueryService = departmentQueryService;
        this.departmentCommandService = departmentCommandService;
    }

    /**
     * 获取组织基本部门信息列表
     *
     * @param query
     * @return
     */
    @GetMapping("/basic-departments")
    public ResponseEntity<List<DepartmentBasicDTO>> getBasicDepartments(@Valid DepartmentQuery query) {
        return ResponseUtils.ok().body(departmentQueryService.getBasicDepartments(query));
    }

    /**
     * 获取单个组织部门基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/basic-departments/{id}")
    public ResponseEntity<DepartmentBasicDTO> getBasicDepartmentById(
        @PathVariable(value = "id") Long id
    ) {
        DepartmentBasicDTO departmentDTO = departmentQueryService.getBasicDepartmentById(id);
        return ResponseUtils.ok().body(departmentDTO);
    }

    /**
     * 获取多个组织部门基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/basic-departments/by-ids")
    public ResponseEntity<List<DepartmentBasicDTO>> getBasicDepartmentsByIds(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        List<DepartmentBasicDTO> departmentDTOs = departmentQueryService.getBasicDepartmentsByIds(ids);
        return ResponseUtils.ok().body(departmentDTOs);
    }

    /**
     * 获取指定部门的所有子部门
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/child-departments")
    public ResponseEntity<List<DepartmentBasicDTO>> getAllChildDepartments(
            @PathVariable(value = "id") Long id
    ) {
        List<DepartmentBasicDTO> departmentDTOs = departmentQueryService.getAllChildDepartments(id);
        return ResponseUtils.ok().body(departmentDTOs);
    }

    /**
     * 创建单个组织部门
     *
     * @param command
     * @return
     */
    @PostMapping
    public Long createDepartment(
            @Valid @RequestBody CreateDepartmentCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setEmployee(sessionContext.getEmployee());
        return departmentCommandService.createDepartment(command);
    }

    /**
     * 修改单个组织部门
     *
     * @param command
     * @return
     */
    @PatchMapping
    public void updateDepartmentById(
            @Valid @RequestBody UpdateDepartmentCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setEmployee(sessionContext.getEmployee());
        departmentCommandService.updateDepartment(command);
    }

    /**
     * 创建初始组织部门
     *
     * @param command
     * @return
     */
    @PostMapping("/create-top-department")
    public Long createOrganizationTopDepartment(
            @Valid @RequestBody CreateTopDepartmentCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        return departmentCommandService.createOrganizationTopDepartment(command.getOrganizationId());
    }

}