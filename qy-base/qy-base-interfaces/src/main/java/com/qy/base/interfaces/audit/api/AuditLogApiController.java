package com.qy.base.interfaces.audit.api;

import com.qy.audit.app.application.command.CreateAuditLogCommand;
import com.qy.audit.app.application.dto.AuditLogDTO;
import com.qy.audit.app.application.dto.AuditLogId;
import com.qy.audit.app.application.query.AuditLogQuery;
import com.qy.audit.app.application.query.BatchAuditLogQuery;
import com.qy.audit.app.application.service.AuditLogService;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 审核日志内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/audit/audit-logs")
public class AuditLogApiController {
    private AuditLogService auditLogService;

    public AuditLogApiController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    /**
     * 获取审核日志列表
     *
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<SimplePageImpl<AuditLogDTO>> getAuditLogs(AuditLogQuery query) {
        Page<AuditLogDTO> page = auditLogService.getAuditLogs(query);
        return ResponseUtils.ok().body(new SimplePageImpl(page));
    }

    /**
     * 获取指定条件下所有的审核日志
     *
     * @param query
     * @return
     */
    @GetMapping("/all-audit-logs")
    public ResponseEntity<List<AuditLogDTO>> getAllAuditLogs(AuditLogQuery query) {
        List<AuditLogDTO> auditLogDTOS = auditLogService.getAllAuditLogs(query);
        return ResponseUtils.ok().body(auditLogDTOS);
    }

    /**
     * 获取批量的最新的审核日志
     *
     * @param query
     * @return
     */
    @GetMapping("/batch-latest-audit-logs")
    public ResponseEntity<List<AuditLogDTO>> getBatchLatestAuditLogs(BatchAuditLogQuery query) {
        List<AuditLogDTO> auditLogDTOS = auditLogService.getBatchLatestAuditLogs(query);
        return ResponseUtils.ok().body(auditLogDTOS);
    }

    /**
     * 获取最新的审核日志
     *
     * @param query
     * @return
     */
    @GetMapping("/latest-audit-log")
    public ResponseEntity<AuditLogDTO> getLatestAuditLog(AuditLogQuery query) {
        AuditLogDTO auditLogDTO = auditLogService.getLatestAuditLog(query);
        return ResponseUtils.ok().body(auditLogDTO);
    }


    /**
     * 创建审核日志
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping
    public ResponseEntity<AuditLogId> createAuditLog(
            @Valid @RequestBody CreateAuditLogCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        auditLogService.createAuditLog(command);

        return ResponseUtils.ok("创建审核日志成功").build();
    }
}