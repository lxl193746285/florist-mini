package com.qy.member.app.application.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 会员系统查询
 *
 * @author legendjw
 */
@Data
public class MemberSystemQuery extends PageQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 状态
     */
    private Integer statusId;
}
