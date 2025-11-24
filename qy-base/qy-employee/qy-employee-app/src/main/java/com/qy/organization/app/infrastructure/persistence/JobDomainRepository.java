package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.domain.entity.Job;
import com.qy.organization.app.domain.valueobject.JobId;

import java.util.List;

/**
 * 岗位领域资源库
 *
 * @author legendjw
 */
public interface JobDomainRepository {
    /**
     * 根据ID查询岗位
     *
     * @param id
     * @return
     */
    Job findById(JobId id);

    /**
     * 根据ID集合查询岗位
     *
     * @param ids
     * @return
     */
    List<Job> findByIds(List<Long> ids);
}