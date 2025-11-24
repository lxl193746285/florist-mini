package com.qy.message.api.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 设置所有消息已读
 *
 * @author legendjw
 */
@Data
public class SetAllMessageReadCommand implements Serializable {
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
}