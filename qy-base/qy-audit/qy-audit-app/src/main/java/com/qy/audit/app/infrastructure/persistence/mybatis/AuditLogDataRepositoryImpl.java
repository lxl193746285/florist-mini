package com.qy.audit.app.infrastructure.persistence.mybatis;

import com.qy.audit.app.application.query.AuditLogQuery;
import com.qy.audit.app.application.query.BatchAuditLogQuery;
import com.qy.audit.app.application.repository.AuditLogDataRepository;
import com.qy.audit.app.infrastructure.persistence.mybatis.dataobject.AuditLogDO;
import com.qy.audit.app.infrastructure.persistence.mybatis.mapper.AuditLogMapper;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 审核日志数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class AuditLogDataRepositoryImpl implements AuditLogDataRepository {
    private AuditLogMapper auditLogMapper;

    public AuditLogDataRepositoryImpl(AuditLogMapper auditLogMapper) {
        this.auditLogMapper = auditLogMapper;
    }

    @Override
    public Page<AuditLogDO> findPageByQuery(AuditLogQuery query) {
        LambdaQueryWrapper<AuditLogDO> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getSortType().equals("desc")) {
            queryWrapper.orderByDesc(AuditLogDO::getAuditTime);
        }
        else {
            queryWrapper.orderByAsc(AuditLogDO::getAuditTime);
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(AuditLogDO::getOrganizationId, query.getOrganizationId());
        }
        if (StringUtils.isNotBlank(query.getModuleId())) {
            queryWrapper.eq(AuditLogDO::getModuleId, query.getModuleId());
        }
        if (query.getDataId() != null) {
            queryWrapper.eq(AuditLogDO::getDataId, query.getDataId());
        }

        IPage<AuditLogDO> iPage = auditLogMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<AuditLogDO> findByQuery(AuditLogQuery query) {
        LambdaQueryWrapper<AuditLogDO> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getSortType().equals("desc")) {
            queryWrapper.orderByDesc(AuditLogDO::getAuditTime);
        }
        else {
            queryWrapper.orderByAsc(AuditLogDO::getAuditTime);
        }
        queryWrapper.eq(AuditLogDO::getOrganizationId, query.getOrganizationId());
        queryWrapper.eq(AuditLogDO::getModuleId, query.getModuleId());
        queryWrapper.eq(AuditLogDO::getDataId, query.getDataId());
        return auditLogMapper.selectList(queryWrapper);
    }

    @Override
    public List<AuditLogDO> findByQuery(BatchAuditLogQuery query) {
        if (query.getDataIds().isEmpty()) { return new ArrayList<>(); }

        LambdaQueryWrapper<AuditLogDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AuditLogDO::getAuditTime);

        if (query.getOrganizationId() != null) {
            queryWrapper.eq(AuditLogDO::getOrganizationId, query.getOrganizationId());
        }
        if (StringUtils.isNotBlank(query.getModuleId())) {
            queryWrapper.eq(AuditLogDO::getModuleId, query.getModuleId());
        }
        if (query.getDataIds() != null) {
            queryWrapper.in(AuditLogDO::getDataId, query.getDataIds());
        }

        return auditLogMapper.selectList(queryWrapper);
    }

    @Override
    public AuditLogDO findLatestAuditLog(AuditLogQuery query) {
        LambdaQueryWrapper<AuditLogDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AuditLogDO::getAuditTime).last("limit 1");
        queryWrapper.eq(AuditLogDO::getOrganizationId, query.getOrganizationId());
        queryWrapper.eq(AuditLogDO::getModuleId, query.getModuleId());
        queryWrapper.eq(AuditLogDO::getDataId, query.getDataId());
        return auditLogMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(AuditLogDO auditLogDO) {
        auditLogDO.setAuditTime(LocalDateTime.now());
        auditLogMapper.insert(auditLogDO);
        return auditLogDO.getId();
    }
}
