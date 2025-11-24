package com.qy.organization.app.application.service.impl;

import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.assembler.JobAssembler;
import com.qy.organization.app.application.dto.JobBasicDTO;
import com.qy.organization.app.application.dto.JobDTO;
import com.qy.organization.app.application.query.JobQuery;
import com.qy.organization.app.application.security.JobPermission;
import com.qy.organization.app.application.service.JobQueryService;
import com.qy.organization.app.infrastructure.persistence.JobDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位查询服务实现
 *
 * @author legendjw
 */
@Service
public class JobQueryServiceImpl implements JobQueryService {
    private JobAssembler jobAssembler;
    private JobDataRepository jobDataRepository;
    private JobPermission jobPermission;
    private OrganizationClient organizationClient;

    public JobQueryServiceImpl(JobAssembler jobAssembler, JobDataRepository jobDataRepository, JobPermission jobPermission,
                               OrganizationClient organizationClient) {
        this.jobAssembler = jobAssembler;
        this.jobDataRepository = jobDataRepository;
        this.jobPermission = jobPermission;
        this.organizationClient = organizationClient;
    }

    @Override
    public Page<JobDTO> getJobs(JobQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = jobPermission.getFilterQuery(identity, JobPermission.LIST);
        Page<JobDO> jobDOPage = jobDataRepository.findByQuery(query, filterQuery);
        List<OrganizationBasicDTO> organizationBasicDTOS = jobDOPage.getRecords().isEmpty() ?
                new ArrayList<>() :
                organizationClient.getBasicOrganizationsByIds(jobDOPage.getRecords().stream().map(JobDO::getOrganizationId).collect(Collectors.toList()));
        return jobDOPage.map(job -> jobAssembler.toDTO(job, organizationBasicDTOS, identity));
    }

    @Override
    public List<JobBasicDTO> getBasicJobs(JobQuery query) {
        List<JobDO> jobDOS = jobDataRepository.findByQuery(query);
        return jobAssembler.toBasicDTOs(jobDOS);
    }

    @Override
    public List<JobBasicDTO> getBasicJobsByIds(List<Long> ids) {
        List<JobDO> jobDOS = jobDataRepository.findByIds(ids);
        return jobAssembler.toBasicDTOs(jobDOS);
    }

    @Override
    public JobBasicDTO getBasicJobById(Long id) {
        JobDO jobDO = jobDataRepository.findById(id);
        return jobAssembler.toBasicDTO(jobDO);
    }

    @Override
    public List<JobBasicDTO> getEmployeeJobs(Long id) {
        List<Long> jobIds = jobDataRepository.findEmployeeJobs(id);
        if (jobIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<JobDO> jobDOS = jobDataRepository.findByIds(jobIds);
        return jobAssembler.toBasicDTOs(jobDOS);
    }

    @Override
    public JobDTO getJobById(Long id) {
        JobDO jobDO = jobDataRepository.findById(id);
        return jobAssembler.toDTO(jobDO, null, null);
    }

    @Override
    public JobDTO getJobById(Long id, Identity identity) {
        JobDO jobDO = jobDataRepository.findById(id);
        List<OrganizationBasicDTO> organizationBasicDTOS = jobDO == null ? new ArrayList<>() : organizationClient.getBasicOrganizationsByIds(Arrays.asList(jobDO.getOrganizationId()));
        return jobAssembler.toDTO(jobDO, organizationBasicDTOS, identity);
    }
}
