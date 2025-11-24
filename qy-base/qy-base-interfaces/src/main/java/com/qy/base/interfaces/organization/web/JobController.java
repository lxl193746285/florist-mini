package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.application.command.CreateJobCommand;
import com.qy.organization.app.application.command.DeleteJobCommand;
import com.qy.organization.app.application.command.UpdateJobCommand;
import com.qy.organization.app.application.dto.JobDTO;
import com.qy.organization.app.application.query.JobQuery;
import com.qy.organization.app.application.service.JobCommandService;
import com.qy.organization.app.application.service.JobQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织岗位
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Api(tags = "组织岗位")
@RestController
@RequestMapping("/v4/employee/jobs")
public class JobController {
    private OrganizationSessionContext sessionContext;
    private JobCommandService jobCommandService;
    private JobQueryService jobQueryService;

    public JobController(OrganizationSessionContext sessionContext, JobCommandService jobCommandService, JobQueryService jobQueryService) {
        this.sessionContext = sessionContext;
        this.jobCommandService = jobCommandService;
        this.jobQueryService = jobQueryService;
    }

    /**
     * 获取组织岗位列表
     *
     * @return
     */
    @ApiOperation("获取组织岗位列表")
    @GetMapping
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@jobPermission.LIST)")
    public ResponseEntity<List<JobDTO>> getJobs(JobQuery query) {
        Page<JobDTO> page = jobQueryService.getJobs(query, sessionContext.getUser());
        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个组织岗位
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个组织岗位")
    @GetMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@jobPermission, @jobPermission.VIEW, #id)")
    public ResponseEntity<JobDTO> getJob(
        @PathVariable(value = "id") Long id
    ) {
        JobDTO jobDTO = jobQueryService.getJobById(id, sessionContext.getUser());
        if (jobDTO == null) {
            throw new NotFoundException("未找到指定的岗位");
        }
        return ResponseUtils.ok().body(jobDTO);
    }

    /**
     * 创建单个组织岗位
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个组织岗位")
    @PostMapping
    @PreAuthorize("@organizationSessionContext.getEmployee(#command.organizationId).hasPermission(@jobPermission.CREATE)")
    public ResponseEntity<Object> createJob(
            @Valid @RequestBody CreateJobCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        jobCommandService.createJob(command);

        return ResponseUtils.ok("岗位创建成功").build();
    }

    /**
     * 修改单个组织岗位
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个组织岗位")
    @PatchMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@jobPermission, @jobPermission.UPDATE, #id)")
    public ResponseEntity<Object> updateJob(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateJobCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        jobCommandService.updateJob(command);

        return ResponseUtils.ok("岗位修改成功").build();
    }

    /**
     * 删除单个组织岗位
     *
     * @param id
     */
    @ApiOperation("删除单个组织岗位")
    @DeleteMapping("/{id}")
    @PreAuthorize("@organizationSessionContext.user.hasPermission(@jobPermission, @jobPermission.DELETE, #id)")
    public ResponseEntity<Object> deleteJob(
        @PathVariable(value = "id") Long id
    ) {
        JobDTO jobDTO = jobQueryService.getJobById(id);
        if (jobDTO == null) {
            throw new NotFoundException("未找到需要删除的岗位");
        }

        DeleteJobCommand command = new DeleteJobCommand();
        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(jobDTO.getOrganizationId()));
        jobCommandService.deleteJob(command);

        return ResponseUtils.noContent("删除岗位成功").build();
    }
}

