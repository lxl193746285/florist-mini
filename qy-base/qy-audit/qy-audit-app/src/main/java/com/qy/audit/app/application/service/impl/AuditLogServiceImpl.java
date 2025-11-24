package com.qy.audit.app.application.service.impl;

import com.qy.audit.app.application.assembler.AuditLogAssembler;
import com.qy.audit.app.application.command.CreateAuditLogCommand;
import com.qy.audit.app.application.dto.AuditLogDTO;
import com.qy.audit.app.application.dto.AuditLogId;
import com.qy.audit.app.application.query.AuditLogQuery;
import com.qy.audit.app.application.query.BatchAuditLogQuery;
import com.qy.audit.app.application.repository.AuditLogDataRepository;
import com.qy.audit.app.application.service.AuditLogService;
import com.qy.audit.app.infrastructure.persistence.mybatis.dataobject.AuditLogDO;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.rest.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 审核日志服务实现
 *
 * @author legendjw
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {
    private AuditLogAssembler auditLogAssembler;
    private AuditLogDataRepository auditLogDataRepository;
    private CodeTableClient codeTableClient;

    public AuditLogServiceImpl(AuditLogAssembler auditLogAssembler, AuditLogDataRepository auditLogDataRepository, CodeTableClient codeTableClient) {
        this.auditLogAssembler = auditLogAssembler;
        this.auditLogDataRepository = auditLogDataRepository;
        this.codeTableClient = codeTableClient;
    }

    @Override
    public Page<AuditLogDTO> getAuditLogs(AuditLogQuery query) {
        Page<AuditLogDO> auditLogDOPage = auditLogDataRepository.findPageByQuery(query);
        return auditLogDOPage.map(auditLogDO -> auditLogAssembler.toDTO(auditLogDO));
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs(AuditLogQuery query) {
        List<AuditLogDO> auditLogDOS = auditLogDataRepository.findByQuery(query);
        return auditLogDOS.stream().map(auditLogDO -> auditLogAssembler.toDTO(auditLogDO)).collect(Collectors.toList());
    }

    @Override
    public List<AuditLogDTO> getBatchLatestAuditLogs(BatchAuditLogQuery query) {
        List<AuditLogDO> auditLogDOS = auditLogDataRepository.findByQuery(query);
        List<AuditLogDTO> auditLogDTOS = new ArrayList<>();
        for (AuditLogDO auditLogDO : auditLogDOS) {
            if (!auditLogDTOS.stream().anyMatch(a -> a.getDataId().equals(auditLogDO.getDataId()))) {
                auditLogDTOS.add(auditLogAssembler.toDTO(auditLogDO));
            }
        }
        return auditLogDTOS;
    }

    @Override
    public AuditLogDTO getLatestAuditLog(AuditLogQuery query) {
        AuditLogDO auditLogDO = auditLogDataRepository.findLatestAuditLog(query);
        return auditLogAssembler.toDTO(auditLogDO);
    }

    @Override
    public AuditLogId createAuditLog(CreateAuditLogCommand command) {
        AuditLogDO auditLogDO = new AuditLogDO();
        BeanUtils.copyProperties(command, auditLogDO);
        if (StringUtils.isBlank(command.getTypeName())) {
            String typeName = codeTableClient.getSystemCodeTableItemName("common_audit_type", String.valueOf(command.getTypeId()));
            auditLogDO.setTypeName(typeName);
        }
        Long auditLogId = auditLogDataRepository.save(auditLogDO);
        return new AuditLogId(auditLogId);
    }
}
