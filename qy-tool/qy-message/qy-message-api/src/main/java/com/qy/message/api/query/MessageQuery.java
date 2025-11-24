package com.qy.message.api.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 消息查询
 *
 * @author legendjw
 */
@Data
public class MessageQuery extends PageQuery {
    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否已读 1: 已读 0： 未读
     */
    private Integer isRead;
}
