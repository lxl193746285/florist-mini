package com.qy.message.app.application.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 消息平台查询
 *
 * @author legendjw
 */
@Data
public class PlatformQuery extends PageQuery {
    /**
     * 关键字
     */
    private String keywords;
}
