package com.qy.base.interfaces.organization.api;

import com.qy.organization.app.application.command.CreateJobCommand;
import com.qy.organization.app.application.command.DeleteJobCommand;
import com.qy.organization.app.application.command.UpdateJobCommand;
import com.qy.organization.app.application.dto.JobBasicDTO;
import com.qy.organization.app.application.dto.JobDTO;
import com.qy.organization.app.application.query.JobQuery;
import com.qy.organization.app.application.service.JobCommandService;
import com.qy.organization.app.application.service.JobQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织岗位内部服务接口
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/employee/jobs")
public class JobApiController {
    private OrganizationSessionContext sessionContext;
    private JobQueryService jobQueryService;
    private JobCommandService jobCommandService;

    public JobApiController(OrganizationSessionContext sessionContext, JobQueryService jobQueryService, JobCommandService jobCommandService) {
        this.sessionContext = sessionContext;
        this.jobQueryService = jobQueryService;
        this.jobCommandService = jobCommandService;
    }

    /**
     * 获取组织基本岗位信息列表
     *
     * @param query
     * @return
     */
    @GetMapping("/basic-jobs")
    public ResponseEntity<List<JobBasicDTO>> getBasicJobs(JobQuery query) {
        return ResponseUtils.ok().body(jobQueryService.getBasicJobs(query));
    }

    /**
     * 获取单个组织岗位基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/basic-jobs/{id}")
    public ResponseEntity<JobBasicDTO> getBasicJobById(
        @PathVariable(value = "id") Long id
    ) {
        JobBasicDTO jobDTO = jobQueryService.getBasicJobById(id);
        return ResponseUtils.ok().body(jobDTO);
    }

    /**
     * 获取多个组织岗位基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/basic-jobs/by-ids")
    public ResponseEntity<List<JobBasicDTO>> getBasicJobsByIds(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        List<JobBasicDTO> jobDTOs = jobQueryService.getBasicJobsByIds(ids);
        return ResponseUtils.ok().body(jobDTOs);
    }

    /**
     * 创建单个组织岗位
     *
     * @param command
     * @return
     */
    @PostMapping("/create-job")
    public Long createJob(
            @RequestBody CreateJobCommand command
    ) {
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        return jobCommandService.createJob(command);
    }

    /**
     * 创建单个组织岗位(盛昊用)
     *
     * @param command
     * @return
     */
    @PostMapping("/create-job/sh")
    public Long createJobForSh(
            @RequestBody CreateJobCommand command
    ) {
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        return jobCommandService.createJobForSh(command);
    }

    /**
     * 修改单个组织岗位
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/update-job/{id}")
    public void updateJob(
            @PathVariable(value = "id") Long id,
            @RequestBody UpdateJobCommand command
    ) {
        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        jobCommandService.updateJob(command);

    }

    /**
     * 修改单个组织岗位(盛昊用)
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/update-job/sh/{id}")
    public void updateJobForSh(
            @PathVariable(value = "id") Long id,
            @RequestBody UpdateJobCommand command
    ) {
        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(command.getOrganizationId()));
        jobCommandService.updateJobForSh(command);

    }

    /**
     * 删除单个组织岗位
     *
     * @param id
     */
    @DeleteMapping("/delete-job/{id}")
    public void deleteJob(
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

    }

    /**
     * 删除单个组织岗位(盛昊用)
     *
     * @param id
     */
    @DeleteMapping("/delete-job/sh/{id}")
    public void deleteJobForSh(
            @PathVariable(value = "id") Long id
    ) {
        JobDTO jobDTO = jobQueryService.getJobById(id);
        if (jobDTO == null) {
            throw new NotFoundException("未找到需要删除的岗位");
        }

        DeleteJobCommand command = new DeleteJobCommand();
        command.setId(id);
        command.setEmployee(sessionContext.getEmployee(jobDTO.getOrganizationId()));
        jobCommandService.deleteJobForSh(command);

    }
}