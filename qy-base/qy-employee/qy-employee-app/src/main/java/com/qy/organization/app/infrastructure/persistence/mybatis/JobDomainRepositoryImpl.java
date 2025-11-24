package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.app.domain.entity.Job;
import com.qy.organization.app.domain.valueobject.JobId;
import com.qy.organization.app.infrastructure.persistence.JobDomainRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.converter.JobConverter;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.JobMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class JobDomainRepositoryImpl implements JobDomainRepository {
    private JobConverter jobConverter;
    private JobMapper jobMapper;

    public JobDomainRepositoryImpl(JobConverter jobConverter, JobMapper jobMapper) {
        this.jobConverter = jobConverter;
        this.jobMapper = jobMapper;
    }

    @Override
    public Job findById(JobId id) {
        JobDO jobDO = jobMapper.selectById(id.getId());
        if (jobDO == null) {
            return null;
        }
        return jobConverter.toEntity(jobDO);
    }

    @Override
    public List<Job> findByIds(List<Long> ids) {
        List<JobDO> jobDOS = jobMapper.selectBatchIds(ids);
        if (jobDOS.isEmpty()) {
            return new ArrayList<>();
        }
        return jobDOS.stream().map(j -> jobConverter.toEntity(j)).collect(Collectors.toList());
    }
}
