package com.qy.audit.app.application.service;

import com.qy.audit.app.application.command.CreateAuditLogCommand;
import com.qy.audit.app.application.dto.AuditLogDTO;
import com.qy.audit.app.application.dto.AuditLogId;
import com.qy.audit.app.application.query.AuditLogQuery;
import com.qy.audit.app.application.query.BatchAuditLogQuery;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 审核日志服务
 *
 * @author legendjw
 */
public interface AuditLogService {
    /**
     * 查询分页审核日志
     *
     * @param query
     * @return
     */
    Page<AuditLogDTO> getAuditLogs(AuditLogQuery query);

    /**
     * 获取所有的审核日志
     *
     * @param query
     * @return
     */
    List<AuditLogDTO> getAllAuditLogs(AuditLogQuery query);

    /**
     * 获取批量的最新的审核日志
     *
     * @param query
     * @return
     */
    List<AuditLogDTO> getBatchLatestAuditLogs(BatchAuditLogQuery query);

    /**
     * 获取最新的审核日志
     *
     * @param query
     * @return
     */
    AuditLogDTO getLatestAuditLog(AuditLogQuery query);

    /**
     * 创建一个审核日志
     *
     * @param command
     * @return
     */
    AuditLogId createAuditLog(CreateAuditLogCommand command);
}
