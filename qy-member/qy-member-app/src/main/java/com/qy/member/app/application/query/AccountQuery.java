package com.qy.member.app.application.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 个人会员系统查询
 *
 * @author legendjw
 */
@Data
public class AccountQuery extends PageQuery {

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 关键字
     */
    private String keywords;
}
