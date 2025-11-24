package com.qy.member.app.application.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 会员系统微信应用查询
 *
 * @author legendjw
 */
@Data
public class MemberSystemWeixinAppQuery extends PageQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 系统id
     */
    private Long systemId;

    /**
     * 关键字
     */
    private String keywords;
}
