package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.app.application.query.JobQuery;
import com.qy.organization.app.infrastructure.persistence.JobDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeJobDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeJobMapper;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.JobMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class JobDataRepositoryImpl implements JobDataRepository {
    private JobMapper jobMapper;
    private EmployeeJobMapper employeeJobMapper;

    public JobDataRepositoryImpl(JobMapper jobMapper, EmployeeJobMapper employeeJobMapper) {
        this.jobMapper = jobMapper;
        this.employeeJobMapper = employeeJobMapper;
    }

    @Override
    public Page<JobDO> findByQuery(JobQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<JobDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(JobDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(JobDO::getSort)
                .orderByDesc(JobDO::getCreateTime);

        if (filterQuery == null) {
            queryWrapper.eq(JobDO::getId, 0);
        }
        else {
            queryWrapper.in(JobDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(JobDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getEmployeeId() != null, JobDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(JobDO::getName, query.getKeywords()).or().like(JobDO::getNamePinyin, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(JobDO::getOrganizationId, query.getOrganizationId());
        }
        IPage<JobDO> iPage = jobMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<JobDO> findByQuery(JobQuery query) {
        LambdaQueryWrapper<JobDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(JobDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(JobDO::getSort)
                .orderByDesc(JobDO::getCreateTime);

        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(JobDO::getName, query.getKeywords()).or().like(JobDO::getNamePinyin, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(JobDO::getOrganizationId, query.getOrganizationId());
        }
        return jobMapper.selectList(queryWrapper);
    }

    @Override
    public List<JobDO> findByIds(List<Long> ids) {
        return jobMapper.selectBatchIds(ids);
    }

    @Override
    public JobDO findById(Long id) {
        LambdaQueryWrapper<JobDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(JobDO::getId, id)
                .eq(JobDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return jobMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Long> findJobEmployees(Long id) {
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getJobId, id));
        if (employeeJobDOS.isEmpty()) {
            return new ArrayList<>();
        }
        return employeeJobDOS.stream().map(EmployeeJobDO::getEmployeeId).collect(Collectors.toList());
    }

    @Override
    public List<Long> findJobEmployees(List<Long> ids) {
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().in(EmployeeJobDO::getJobId, ids));
        if (employeeJobDOS.isEmpty()) {
            return new ArrayList<>();
        }
        return employeeJobDOS.stream().map(EmployeeJobDO::getEmployeeId).collect(Collectors.toList());
    }

    @Override
    public List<Long> findEmployeeJobs(Long id) {
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, id));
        if (employeeJobDOS.isEmpty()) {
            return new ArrayList<>();
        }
        return employeeJobDOS.stream().map(EmployeeJobDO::getJobId).collect(Collectors.toList());
    }

    @Override
    public Long save(JobDO jobDO) {
        if (jobDO.getId() == null) {
            jobMapper.insert(jobDO);
        }
        else {
            jobMapper.updateById(jobDO);
        }
        return jobDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity employee) {
        JobDO jobDO = findById(id);
        jobDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        jobDO.setDeletorId(employee.getId());
        jobDO.setDeletorName(employee.getName());
        jobDO.setDeleteTime(LocalDateTime.now());
        jobMapper.updateById(jobDO);
    }

    @Override
    public int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId) {
        LambdaQueryWrapper<JobDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(JobDO::getOrganizationId, organizationId)
                .eq(JobDO::getName, name)
                .eq(JobDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(JobDO::getId, excludeId);
        }
        return jobMapper.selectCount(queryWrapper);
    }

    @Override
    public void saveJobEmployees(JobDO jobDO, List<Long> employeeIds) {
        employeeJobMapper.delete(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getJobId, jobDO.getId()));
        LocalDateTime time = LocalDateTime.now();
        for (Long employeeId : employeeIds) {
            EmployeeJobDO employeeJobDO = new EmployeeJobDO();
            employeeJobDO.setOrganizationId(jobDO.getOrganizationId());
            employeeJobDO.setEmployeeId(employeeId);
            employeeJobDO.setJobId(jobDO.getId());
            employeeJobDO.setCreateTime(time);
            employeeJobMapper.insert(employeeJobDO);
        }
    }
}
