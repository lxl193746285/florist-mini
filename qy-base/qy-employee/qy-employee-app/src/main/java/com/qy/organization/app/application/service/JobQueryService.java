package com.qy.organization.app.application.service;

import com.qy.organization.app.application.dto.JobBasicDTO;
import com.qy.organization.app.application.dto.JobDTO;
import com.qy.organization.app.application.query.JobQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 岗位查询服务
 *
 * @author legendjw
 */
public interface JobQueryService {
    /**
     * 查询岗位
     *
     * @param query
     * @param identity
     * @return
     */
    Page<JobDTO> getJobs(JobQuery query, Identity identity);

    /**
     * 查询岗位
     *
     * @param query
     * @return
     */
    List<JobBasicDTO> getBasicJobs(JobQuery query);

    /**
     * 根据id集合获取基本的岗位信息
     *
     * @param ids
     * @return
     */
    List<JobBasicDTO> getBasicJobsByIds(List<Long> ids);

    /**
     * 根据id获取基本的岗位信息
     *
     * @param id
     * @return
     */
    JobBasicDTO getBasicJobById(Long id);

    /**
     * 获取员工的岗位信息
     *
     * @param id
     * @return
     */
    List<JobBasicDTO> getEmployeeJobs(Long id);

    /**
     * 根据ID查询岗位
     *
     * @param id
     * @return
     */
    JobDTO getJobById(Long id);

    /**
     * 根据ID查询岗位
     *
     * @param id
     * @return
     */
    JobDTO getJobById(Long id, Identity identity);
}