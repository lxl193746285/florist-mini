package com.qy.audit.api.client;

import com.qy.audit.api.command.CreateAuditLogCommand;
import com.qy.audit.api.dto.AuditLogDTO;
import com.qy.audit.api.dto.AuditLogId;
import com.qy.audit.api.query.AuditLogQuery;
import com.qy.audit.api.query.BatchAuditLogQuery;
import com.qy.rest.pagination.SimplePageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 审核日志接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-audit-log")
public interface AuditLogClient {
    /**
     * 获取审核日志列表
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/audit/audit-logs")
    SimplePageImpl<AuditLogDTO> getAuditLogs(@SpringQueryMap AuditLogQuery query);

    /**
     * 获取指定条件下所有的审核日志
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/audit/audit-logs/all-audit-logs")
    List<AuditLogDTO> getAllAuditLogs(@SpringQueryMap AuditLogQuery query);

    /**
     * 获取批量的最新的审核日志
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/audit/audit-logs/batch-latest-audit-logs")
    List<AuditLogDTO> getBatchLatestAuditLogs(@SpringQueryMap BatchAuditLogQuery query);

    /**
     * 获取最新的审核日志
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/audit/audit-logs/latest-audit-log")
    AuditLogDTO getLatestAuditLog(@SpringQueryMap AuditLogQuery query);

    /**
     * 创建审核日志
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/audit/audit-logs")
    AuditLogId createAuditLog(
            @Valid @RequestBody CreateAuditLogCommand command
    );
}
