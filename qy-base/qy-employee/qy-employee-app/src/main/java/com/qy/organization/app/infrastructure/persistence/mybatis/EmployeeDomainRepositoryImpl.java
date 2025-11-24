package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.app.domain.entity.EmployeeInfo;
import com.qy.organization.app.domain.entity.Department;
import com.qy.organization.app.domain.entity.Employee;
import com.qy.organization.app.domain.entity.Job;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.valueobject.DepartmentId;
import com.qy.organization.app.domain.valueobject.EmployeeId;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.organization.app.infrastructure.persistence.DepartmentDomainRepository;
import com.qy.organization.app.infrastructure.persistence.EmployeeDomainRepository;
import com.qy.organization.app.infrastructure.persistence.JobDomainRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.converter.EmployeeConverter;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeInfoDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeJobDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeInfoMapper;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeJobMapper;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.sequence.Sequence;
import com.qy.security.session.EmployeeIdentity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class EmployeeDomainRepositoryImpl implements EmployeeDomainRepository {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDomainRepositoryImpl.class);

    private EmployeeConverter employeeConverter;
    private EmployeeMapper employeeMapper;
    private EmployeeInfoMapper employeeInfoMapper;
    private EmployeeJobMapper employeeJobMapper;
    private DepartmentDomainRepository departmentDomainRepository;
    private JobDomainRepository jobDomainRepository;
    private Sequence sequence;

    public EmployeeDomainRepositoryImpl(EmployeeConverter employeeConverter, EmployeeMapper employeeMapper, EmployeeInfoMapper employeeInfoMapper, EmployeeJobMapper employeeJobMapper, DepartmentDomainRepository departmentDomainRepository, JobDomainRepository jobDomainRepository, Sequence sequence) {
        this.employeeConverter = employeeConverter;
        this.employeeMapper = employeeMapper;
        this.employeeInfoMapper = employeeInfoMapper;
        this.employeeJobMapper = employeeJobMapper;
        this.departmentDomainRepository = departmentDomainRepository;
        this.jobDomainRepository = jobDomainRepository;
        this.sequence = sequence;
    }

    @Override
    public Employee findById(EmployeeId id) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getId, id.getId())
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        EmployeeDO employeeDO = employeeMapper.selectOne(employeeQueryWrapper);
        if (employeeDO == null) {
            return null;
        }
        Department department = departmentDomainRepository.findById(new DepartmentId(employeeDO.getDepartmentId()));
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, employeeDO.getId()));
        List<Long> jobIds = employeeJobDOS.stream().map(e -> e.getJobId()).collect(Collectors.toList());
        List<Job> jobs = jobIds.isEmpty() ? new ArrayList<>() : jobDomainRepository.findByIds(jobIds);

        return employeeConverter.toEntity(employeeDO, department, jobs);
    }

    @Override
    public EmployeeInfo findInfoById(EmployeeId id) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getId, id.getId())
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        EmployeeDO employeeDO = employeeMapper.selectOne(employeeQueryWrapper);
        if (employeeDO == null) {
            return null;
        }
        EmployeeInfoDO employeeInfoDO = employeeInfoMapper.selectById(employeeDO.getId());
        Department department = departmentDomainRepository.findById(new DepartmentId(employeeDO.getDepartmentId()));
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, employeeDO.getId()));
        List<Long> jobIds = employeeJobDOS.stream().map(e -> e.getJobId()).collect(Collectors.toList());
        List<Job> jobs = jobIds.isEmpty() ? new ArrayList<>() : jobDomainRepository.findByIds(jobIds);

        return employeeConverter.toEntity(employeeDO, employeeInfoDO, department, jobs);
    }

    @Override
    public EmployeeInfo findInfoByMemberId(Long memberId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getMemberId, memberId)
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        EmployeeDO employeeDO = employeeMapper.selectOne(employeeQueryWrapper);
        if (employeeDO == null) {
            return null;
        }
        EmployeeInfoDO employeeInfoDO = employeeInfoMapper.selectById(employeeDO.getId());
        Department department = departmentDomainRepository.findById(new DepartmentId(employeeDO.getDepartmentId()));
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, employeeDO.getId()));
        List<Long> jobIds = employeeJobDOS.stream().map(e -> e.getJobId()).collect(Collectors.toList());
        List<Job> jobs = jobIds.isEmpty() ? new ArrayList<>() : jobDomainRepository.findByIds(jobIds);

        return employeeConverter.toEntity(employeeDO, employeeInfoDO, department, jobs);
    }

    @Override
    public Employee findByUser(OrganizationId organizationId, Long userId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getOrganizationId, organizationId.getId())
                .eq(EmployeeDO::getUserId, userId)
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        EmployeeDO employeeDO = employeeMapper.selectOne(employeeQueryWrapper);
        if (employeeDO == null) {
            return null;
        }
        Department department = departmentDomainRepository.findById(new DepartmentId(employeeDO.getDepartmentId()));
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, employeeDO.getId()));
        List<Long> jobIds = employeeJobDOS.stream().map(e -> e.getJobId()).collect(Collectors.toList());
        List<Job> jobs = jobIds.isEmpty() ? new ArrayList<>() : jobDomainRepository.findByIds(jobIds);

        return employeeConverter.toEntity(employeeDO, department, jobs);
    }

    @Override
    public Employee findOrganizationCreator(OrganizationId organizationId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(EmployeeDO::getOrganizationId, organizationId.getId())
                .eq(EmployeeDO::getIdentityTypeId, EmployeeIdentityType.CREATOR.getId())
                .last("limit 1");
        EmployeeDO employeeDO = employeeMapper.selectOne(employeeQueryWrapper);
        if (employeeDO == null) {
            return null;
        }
        Department department = departmentDomainRepository.findById(new DepartmentId(employeeDO.getDepartmentId()));
        List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, employeeDO.getId()));
        List<Long> jobIds = employeeJobDOS.stream().map(e -> e.getJobId()).collect(Collectors.toList());
        List<Job> jobs = jobIds.isEmpty() ? new ArrayList<>() : jobDomainRepository.findByIds(jobIds);

        return employeeConverter.toEntity(employeeDO, department, jobs);
    }

    @Override
    public List<Employee> findOrganizationAdmins(OrganizationId organizationId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(EmployeeDO::getOrganizationId, organizationId.getId())
                .eq(EmployeeDO::getIdentityTypeId, EmployeeIdentityType.ADMIN.getId());
        List<EmployeeDO> employeeDOS = employeeMapper.selectList(employeeQueryWrapper);
        if (employeeDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<Employee> employees = new ArrayList<>();
        for (EmployeeDO employeeDO : employeeDOS) {
            Department department = departmentDomainRepository.findById(new DepartmentId(employeeDO.getDepartmentId()));
            List<EmployeeJobDO> employeeJobDOS = employeeJobMapper.selectList(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, employeeDO.getId()));
            List<Long> jobIds = employeeJobDOS.stream().map(e -> e.getJobId()).collect(Collectors.toList());
            List<Job> jobs = jobIds.isEmpty() ? new ArrayList<>() : jobDomainRepository.findByIds(jobIds);

            employees.add(employeeConverter.toEntity(employeeDO, department, jobs));
        }
        return employees;
    }

    @Override
    @Transactional
    public EmployeeId save(Employee employee) {
        EmployeeDO employeeDO = employeeConverter.toDO(employee);
        if (employeeDO.getId() == null) {
            employeeDO.setId(sequence.nextId());
        }
        if (employeeMapper.selectById(employee.getId()) == null) {
            employeeMapper.insert(employeeDO);
        }
        else {
            employeeMapper.updateById(employeeDO);
        }
        return new EmployeeId(employeeDO.getId());
    }

    @Override
    @Transactional
    public EmployeeId saveInfo(EmployeeInfo employeeInfo) {
        EmployeeDO employeeDO = employeeConverter.toDO(employeeInfo);
        EmployeeInfoDO employeeInfoDO = employeeConverter.toInfoDO(employeeInfo);
        if (employeeDO.getId() == null) {
            employeeDO.setId(sequence.nextId());
        }
        if (employeeMapper.selectById(employeeInfo.getId()) == null) {
            employeeMapper.insert(employeeDO);
            employeeInfoDO.setEmployeeId(employeeDO.getId());
            employeeInfoMapper.insert(employeeInfoDO);
        }
        else {
            //修复邮箱无法清空
            if(StringUtils.isEmpty(employeeDO.getEmail()))
            {
                employeeDO.setEmail("");
            }
            employeeMapper.updateById(employeeDO);
            employeeInfoMapper.updateById(employeeInfoDO);
        }
        EmployeeId employeeId = new EmployeeId(employeeDO.getId());
        saveEmployeeJobs(employeeId, employeeInfo.getOrganizationId(), employeeInfo.getJobs());

        return employeeId;
    }

    @Override
    public void remove(EmployeeId id, EmployeeIdentity identity) {
        EmployeeDO employeeDO = employeeMapper.selectById(id.getId());
        employeeDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        employeeDO.setDeletorId(identity.getId());
        employeeDO.setDeletorName(identity.getName());
        employeeDO.setDeleteTime(LocalDateTime.now());
        employeeMapper.updateById(employeeDO);
    }

    @Override
    public int countByOrganizationAndEmployeePhone(OrganizationId organizationId, String phone, Long excludeId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(organizationId.getId() != null, EmployeeDO::getOrganizationId, organizationId.getId())
                .eq(EmployeeDO::getPhone, phone)
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            employeeQueryWrapper.ne(EmployeeDO::getId, excludeId);
        }
        return employeeMapper.selectCount(employeeQueryWrapper);
    }

    @Override
    public int countByOrganizationAndEmployeeEmail(OrganizationId organizationId, String email, Long excludeId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getOrganizationId, organizationId.getId())
                .eq(EmployeeDO::getEmail, email)
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            employeeQueryWrapper.ne(EmployeeDO::getId, excludeId);
        }
        return employeeMapper.selectCount(employeeQueryWrapper);
    }

    /**
     * 保存员工岗位
     *
     * @param employeeId
     * @param jobs
     */
    private void saveEmployeeJobs(EmployeeId employeeId, OrganizationId organizationId, List<Job> jobs) {
        if (jobs == null) {
            return;
        }
        if (employeeId != null) {
            employeeJobMapper.delete(new LambdaQueryWrapper<EmployeeJobDO>().eq(EmployeeJobDO::getEmployeeId, employeeId.getId()));
        }
        LocalDateTime time = LocalDateTime.now();
        for (Job job : jobs) {
            EmployeeJobDO employeeJobDO = new EmployeeJobDO();
            employeeJobDO.setOrganizationId(organizationId.getId());
            employeeJobDO.setEmployeeId(employeeId.getId());
            employeeJobDO.setJobId(job.getId().getId());
            employeeJobDO.setCreateTime(time);
            employeeJobMapper.insert(employeeJobDO);
        }
    }
}
