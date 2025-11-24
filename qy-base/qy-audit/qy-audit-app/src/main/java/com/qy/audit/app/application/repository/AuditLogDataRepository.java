package com.qy.audit.app.application.repository;

import com.qy.audit.app.application.query.AuditLogQuery;
import com.qy.audit.app.application.query.BatchAuditLogQuery;
import com.qy.audit.app.infrastructure.persistence.mybatis.dataobject.AuditLogDO;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 审核日志数据资源库
 *
 * @author legendjw
 */
public interface AuditLogDataRepository {
    /**
     * 根据查询查询审核日志
     *
     * @param query
     * @return
     */
    Page<AuditLogDO> findPageByQuery(AuditLogQuery query);

    /**
     * 根据查询查询审核日志
     *
     * @param query
     * @return
     */
    List<AuditLogDO> findByQuery(AuditLogQuery query);

    /**
     * 根据批量查询条查询审核日志
     *
     * @param query
     * @return
     */
    List<AuditLogDO> findByQuery(BatchAuditLogQuery query);

    /**
     * 查询最新的审核日志
     *
     * @param query
     * @return
     */
    AuditLogDO findLatestAuditLog(AuditLogQuery query);

    /**
     * 保存一个审核日志
     *
     * @param auditLogDO
     * @return
     */
    Long save(AuditLogDO auditLogDO);
}