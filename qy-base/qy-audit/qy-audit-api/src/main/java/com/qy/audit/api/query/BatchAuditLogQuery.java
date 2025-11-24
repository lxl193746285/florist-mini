package com.qy.audit.api.query;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量获取审核日志查询
 *
 * @author legendjw
 */
@Data
public class BatchAuditLogQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联模块id
     */
    private String moduleId;

    /**
     * 关联数据id
     */
    private List<Long> dataIds = new ArrayList<>();
}
