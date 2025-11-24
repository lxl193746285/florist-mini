package com.qy.organization.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.organization.api.command.CreateJobCommand;
import com.qy.organization.api.command.UpdateJobCommand;
import com.qy.organization.api.dto.JobBasicDTO;
import com.qy.organization.api.query.JobQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-employee-job", configuration = FeignTokenRequestInterceptor.class)
public interface JobClient {
    /**
     * 获取组织基本岗位信息列表
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/employee/jobs/basic-jobs")
    List<JobBasicDTO> getBasicJobs(@SpringQueryMap JobQuery query);

    /**
     * 获取单个组织岗位基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/employee/jobs/basic-jobs/{id}")
    JobBasicDTO getBasicJobById(
            @PathVariable(value = "id") Long id
    );

    /**
     * 获取多个组织岗位基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/v4/api/employee/jobs/basic-jobs/by-ids")
    List<JobBasicDTO> getBasicJobsByIds(
            @RequestParam(value = "ids") List<Long> ids
    );

    /**
     * 创建岗位
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/employee/jobs/create-job")
    Long createJob(@RequestBody CreateJobCommand command);

    /**
     * 创建岗位（盛昊）
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/employee/jobs/create-job/sh")
    Long createJobForSh(@RequestBody CreateJobCommand command);

    /**
     * 修改岗位
     *
     * @param id
     * @param command
     */
    @PatchMapping("/v4/api/employee/jobs/update-job/{id}")
    void updateJob(
            @PathVariable(value = "id") Long id,
            @RequestBody UpdateJobCommand command
    );

    /**
     * 修改岗位（盛昊）
     *
     * @param id
     * @param command
     */
    @PatchMapping("/v4/api/employee/jobs/update-job/sh/{id}")
    void updateJobForSh(
            @PathVariable(value = "id") Long id,
            @RequestBody UpdateJobCommand command
    );

    /**
     * 删除岗位
     *
     * @param id
     */
    @DeleteMapping("/v4/api/employee/jobs/update-job/delete-job/{id}")
    void deleteJob(
            @PathVariable(value = "id") Long id
    );

    /**
     * 删除岗位（盛昊）
     *
     * @param id
     */
    @DeleteMapping("/v4/api/employee/jobs/update-job/delete-job/sh/{id}")
    void deleteJobForSh(
            @PathVariable(value = "id") Long id
    );
}