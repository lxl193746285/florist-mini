package com.qy.organization.api.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 权限组查询
 *
 * @author legendjw
 */
@Data
public class RoleQuery extends PageQuery {
    /**
     * 组织id
     */
    private Long organizationId;
    /**
     * 上下文，代码表：role_context 支持以下：
     * 组织内部使用：organization
     * 下级客户使用：subordinate
     * 会员系统：member
     */
    private String context;
    /**
     * 上下文id
     */
    private String contextId;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 状态
     */
    private Integer statusId;
}
