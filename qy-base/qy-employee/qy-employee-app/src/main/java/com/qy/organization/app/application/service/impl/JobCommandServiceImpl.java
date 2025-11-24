package com.qy.organization.app.application.service.impl;

import com.qy.organization.app.application.command.CreateJobCommand;
import com.qy.organization.app.application.command.DeleteJobCommand;
import com.qy.organization.app.application.command.UpdateJobCommand;
import com.qy.organization.app.application.service.JobCommandService;
import com.qy.organization.app.infrastructure.persistence.JobDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 岗位命令实现
 *
 * @author legendjw
 */
@Service
public class JobCommandServiceImpl implements JobCommandService {
    private JobDataRepository jobDataRepository;

    public JobCommandServiceImpl(JobDataRepository jobDataRepository) {
        this.jobDataRepository = jobDataRepository;
    }

    @Override
    @Transactional
    public Long createJob(CreateJobCommand command) {
        if (jobDataRepository.countByOrganizationIdAndName(command.getOrganizationId(), command.getName(), null) > 0) {
            throw new ValidationException("组织下已经存在同名的岗位，请更换新的岗位名称");
        }

        EmployeeIdentity employee = command.getEmployee();
        JobDO jobDO = new JobDO();
        BeanUtils.copyProperties(command, jobDO, "employee");
        jobDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        jobDO.setCreatorId(employee.getId());
        jobDO.setCreatorName(employee.getName());
        jobDO.setCreateTime(LocalDateTime.now());
        jobDataRepository.save(jobDO);
        jobDataRepository.saveJobEmployees(jobDO, command.getEmployeeIds());
        return jobDO.getId();
    }

    @Override
    @Transactional
    public Long createJobForSh(CreateJobCommand command) {
        if (jobDataRepository.countByOrganizationIdAndName(command.getOrganizationId(), command.getName(), null) > 0) {
            throw new ValidationException("组织下已经存在同名的岗位，请更换新的岗位名称");
        }

        EmployeeIdentity employee = command.getEmployee();
        JobDO jobDO = new JobDO();
        BeanUtils.copyProperties(command, jobDO, "employee");
        jobDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        jobDO.setCreatorId(employee.getId());
        jobDO.setCreatorName(employee.getName());
        jobDO.setCreateTime(LocalDateTime.now());
        jobDataRepository.save(jobDO);
        return jobDO.getId();
    }

    @Override
    @Transactional
    public void updateJob(UpdateJobCommand command) {
        JobDO jobDO = jobDataRepository.findById(command.getId());
        if (jobDO == null) {
            throw new NotFoundException("未找到指定的岗位");
        }
        if (jobDataRepository.countByOrganizationIdAndName(jobDO.getOrganizationId(), command.getName(), jobDO.getId()) > 0) {
            throw new ValidationException("组织下已经存在同名的岗位，请更换新的岗位名称");
        }
        EmployeeIdentity employee = command.getEmployee();

        BeanUtils.copyProperties(command, jobDO, "employee");
        jobDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        jobDO.setUpdatorId(employee.getId());
        jobDO.setUpdatorName(employee.getName());
        jobDO.setUpdateTime(LocalDateTime.now());
        jobDataRepository.save(jobDO);
        jobDataRepository.saveJobEmployees(jobDO, command.getEmployeeIds());
    }

    @Override
    @Transactional
    public void updateJobForSh(UpdateJobCommand command) {
        JobDO jobDO = jobDataRepository.findById(command.getId());
        if (jobDO == null) {
            throw new NotFoundException("未找到指定的岗位");
        }
        if (jobDataRepository.countByOrganizationIdAndName(jobDO.getOrganizationId(), command.getName(), jobDO.getId()) > 0) {
            throw new ValidationException("组织下已经存在同名的岗位，请更换新的岗位名称");
        }
        EmployeeIdentity employee = command.getEmployee();

        BeanUtils.copyProperties(command, jobDO, "employee");
        jobDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        jobDO.setUpdatorId(employee.getId());
        jobDO.setUpdatorName(employee.getName());
        jobDO.setUpdateTime(LocalDateTime.now());
        jobDataRepository.save(jobDO);
    }

    @Override
    public void deleteJob(DeleteJobCommand command) {
        EmployeeIdentity employee = command.getEmployee();

        jobDataRepository.remove(command.getId(), employee);
    }

    @Override
    public void deleteJobForSh(DeleteJobCommand command) {
        EmployeeIdentity employee = command.getEmployee();

        jobDataRepository.remove(command.getId(), employee);
    }
}