package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.RoleUserDTO;
import com.qy.organization.app.application.query.EmployeeQuery;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.infrastructure.persistence.EmployeeDataRepository;
import com.qy.organization.app.infrastructure.persistence.JobDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeInfoDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeInfoMapper;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class EmployeeDataRepositoryImpl implements EmployeeDataRepository {
    private EmployeeMapper employeeMapper;
    private EmployeeInfoMapper employeeInfoMapper;
    private JobDataRepository jobDataRepository;
    private RoleManageClient roleManageClient;

    public EmployeeDataRepositoryImpl(EmployeeMapper employeeMapper, EmployeeInfoMapper employeeInfoMapper, JobDataRepository jobDataRepository, RoleManageClient roleManageClient) {
        this.employeeMapper = employeeMapper;
        this.employeeInfoMapper = employeeInfoMapper;
        this.jobDataRepository = jobDataRepository;
        this.roleManageClient = roleManageClient;
    }

    @Override
    public Page<EmployeeDO> findByQuery(EmployeeQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(query.getDepartmentId() != null, EmployeeDO::getDepartmentId, query.getDepartmentId())
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
//                .eq(query.getSourceType() != null, EmployeeDO::getSourceType, query.getSourceType())
                .orderByDesc(EmployeeDO::getJobStatusId)
                .orderByDesc(EmployeeDO::getCreateTime).orderByAsc(EmployeeDO::getId);


        if (filterQuery == null) {
            queryWrapper.eq(EmployeeDO::getId, 0);
        }
        else {
            queryWrapper.in(EmployeeDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(EmployeeDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getDepartmentId() != null, EmployeeDO::getDepartmentId, permissionFilterQuery.getDepartmentId())
                                    .in(permissionFilterQuery.getDepartmentIds() != null, EmployeeDO::getDepartmentId, permissionFilterQuery.getDepartmentIds())
                                    .eq(permissionFilterQuery.getEmployeeId() != null, EmployeeDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }

        if (query.getOrganizationId() != null) {
            queryWrapper.eq(EmployeeDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getDepartmentIds() != null) {
            queryWrapper.in(EmployeeDO::getDepartmentId, query.getDepartmentIds());
        }
        if (query.getJobStatus() != null) {
            queryWrapper.eq(EmployeeDO::getJobStatusId, query.getJobStatus());
        }
        if (query.getJobId() != null) {
            List<Long> employeeIds = jobDataRepository.findJobEmployees(query.getJobId());
            if (employeeIds.isEmpty()) {
                queryWrapper.eq(EmployeeDO::getId, 0);
            }
            else {
                queryWrapper.in(EmployeeDO::getId, employeeIds);
            }
        }
        if (query.getRoleId() != null) {
            List<Long> employeeIds = roleManageClient.getRoleUsers(query.getRoleId()).stream().map(RoleUserDTO::getId).collect(Collectors.toList());
            if (employeeIds.isEmpty()) {
                queryWrapper.eq(EmployeeDO::getId, 0);
            }
            else {
                queryWrapper.in(EmployeeDO::getId, employeeIds);
            }
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i ->
                    i.like(EmployeeDO::getName, query.getKeywords())
                            .or().like(EmployeeDO::getNamePinyin, query.getKeywords())
                            .or().like(EmployeeDO::getPhone, query.getKeywords())
                            .or().like(EmployeeDO::getEmail, query.getKeywords())
            );
        }
        if (query.getIds() != null) {
            queryWrapper.in(EmployeeDO::getId, query.getIds());
        }
        IPage<EmployeeDO> iPage = employeeMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDataRepositoryImpl.class);

    @Override
    public List<EmployeeDO> findByQuery(EmployeeQuery query) {
        LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(EmployeeDO::getJobStatusId)
                .orderByDesc(EmployeeDO::getCreateTime);

        if (query.getOrganizationId() != null) {
            queryWrapper.eq(EmployeeDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getDepartmentId() != null) {
            queryWrapper.eq(EmployeeDO::getDepartmentId, query.getDepartmentId());
        }
        if (query.getDepartmentIds() != null) {
            queryWrapper.in(EmployeeDO::getDepartmentId, query.getDepartmentIds());
        }
        if (query.getJobStatus() != null) {
            queryWrapper.eq(EmployeeDO::getJobStatusId, query.getJobStatus());
        }
        if (query.getJobId() != null) {
            List<Long> employeeIds = jobDataRepository.findJobEmployees(query.getJobId());
            if (employeeIds.isEmpty()) {
                queryWrapper.eq(EmployeeDO::getId, 0);
            }
            else {
                queryWrapper.in(EmployeeDO::getId, employeeIds);
            }
        }
        if (query.getJobIds() != null) {
            List<Long> employeeIds = jobDataRepository.findJobEmployees(query.getJobIds());
            if (employeeIds.isEmpty()) {
                queryWrapper.eq(EmployeeDO::getId, 0);
            }
            else {
                queryWrapper.in(EmployeeDO::getId, employeeIds);
            }
        }
        if (query.getRoleId() != null) {
            List<Long> employeeIds = roleManageClient.getRoleUsers(query.getRoleId()).stream().map(RoleUserDTO::getId).collect(Collectors.toList());
            if (employeeIds.isEmpty()) {
                queryWrapper.eq(EmployeeDO::getId, 0);
            }
            else {
                queryWrapper.in(EmployeeDO::getId, employeeIds);
            }
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i ->
                    i.like(EmployeeDO::getName, query.getKeywords())
                            .or().like(EmployeeDO::getNamePinyin, query.getKeywords())
                            .or().like(EmployeeDO::getPhone, query.getKeywords())
                            .or().like(EmployeeDO::getEmail, query.getKeywords())
            );
        }
        return employeeMapper.selectList(queryWrapper);
    }

    @Override
    public List<EmployeeDO> findByIds(List<Long> ids) {
        return ids != null && !ids.isEmpty() ? employeeMapper.selectBatchIds(ids) : new ArrayList<>();
    }

    @Override
    public List<EmployeeDO> findByUserIds(List<Long> userIds) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(EmployeeDO::getUserId, userIds)
                .eq(EmployeeDO::getJobStatusId, JobStatus.ON_JOB.getId());
        return employeeMapper.selectList(employeeQueryWrapper);
    }

    @Override
    public List<EmployeeInfoDO> findInfoByIds(List<Long> ids) {
        return ids != null && !ids.isEmpty() ? employeeInfoMapper.selectBatchIds(ids) : new ArrayList<>();
    }

    @Override
    public EmployeeDO findByOrganizationIdAndUserId(Long organizationId, Long userId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(EmployeeDO::getOrganizationId, organizationId)
                .eq(EmployeeDO::getUserId, userId)
                .last("limit 1");
        return employeeMapper.selectOne(employeeQueryWrapper);
    }

    @Override
    public EmployeeDO findOrganizationCreator(Long organizationId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(EmployeeDO::getOrganizationId, organizationId)
                .eq(EmployeeDO::getIdentityTypeId, EmployeeIdentityType.CREATOR.getId())
                .last("limit 1");
        return employeeMapper.selectOne(employeeQueryWrapper);
    }

    @Override
    public List<EmployeeDO> findByUserId(Long userId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(EmployeeDO::getJobStatusId, JobStatus.ON_JOB.getId())
                .eq(EmployeeDO::getUserId, userId);
        return employeeMapper.selectList(employeeQueryWrapper);
    }

    @Override
    public EmployeeDO findById(Long id) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
               .eq(EmployeeDO::getId, id)
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return employeeMapper.selectOne(employeeQueryWrapper);
    }

    @Override
    public EmployeeDO findByMemberId(Long memberId) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getMemberId, memberId)
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return employeeMapper.selectOne(employeeQueryWrapper);
    }

    @Override
    public EmployeeDO findByPhone(String phone) {
        LambdaQueryWrapper<EmployeeDO> employeeQueryWrapper = new LambdaQueryWrapper<>();
        employeeQueryWrapper
                .eq(EmployeeDO::getPhone, phone)
                .eq(EmployeeDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return employeeMapper.selectOne(employeeQueryWrapper);
    }

    @Override
    public EmployeeInfoDO findInfoById(Long id) {
        return employeeInfoMapper.selectById(id);
    }
}