package com.qy.organization.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.organization.api.command.CreateDepartmentCommand;
import com.qy.organization.api.command.CreateTopDepartmentCommand;
import com.qy.organization.api.command.UpdateDepartmentCommand;
import com.qy.organization.api.dto.DepartmentBasicDTO;
import com.qy.organization.api.query.DepartmentQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-employee-department", configuration = FeignTokenRequestInterceptor.class)
public interface DepartmentClient {
    /**
     * 获取组织基本部门信息列表
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/employee/departments/basic-departments")
    List<DepartmentBasicDTO> getBasicDepartments(@SpringQueryMap DepartmentQuery query);

    /**
     * 获取单个组织部门基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/employee/departments/basic-departments/{id}")
    DepartmentBasicDTO getBasicDepartmentById(
            @PathVariable(value = "id") Long id
    );

    /**
     * 获取多个组织部门基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/v4/api/employee/departments/basic-departments/by-ids")
    List<DepartmentBasicDTO> getBasicDepartmentsByIds(
            @RequestParam(value = "ids") List<Long> ids
    );

    /**
     * 获取指定部门的所有子部门
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/employee/departments/{id}/child-departments")
    List<DepartmentBasicDTO> getAllChildDepartments(
            @PathVariable(value = "id") Long id
    );

    /**
     * 创建部门
     *
     * @return
     */
    @PostMapping("/v4/api/employee/departments")
    Long createDepartment(
            @Valid @RequestBody CreateDepartmentCommand command
    );

    /**
     * 修改单个组织部门
     *
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/employee/departments")
    void updateDepartmentById(
            @Valid @RequestBody UpdateDepartmentCommand command
    );

    /**
     * 创建初始组织部门
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/employee/departments/create-top-department")
    Long createOrganizationTopDepartment(
            @Valid @RequestBody CreateTopDepartmentCommand command
    );
}