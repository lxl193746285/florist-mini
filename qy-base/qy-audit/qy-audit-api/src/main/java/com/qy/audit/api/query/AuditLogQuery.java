package com.qy.audit.api.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 审核日志查询
 *
 * @author legendjw
 */
@Data
public class AuditLogQuery extends PageQuery {
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
    private Long dataId;

    /**
     * 排序类型
     */
    private String sortType = "desc";
}
