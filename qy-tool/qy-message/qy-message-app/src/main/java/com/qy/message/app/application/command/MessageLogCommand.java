package com.qy.message.app.application.command;

import lombok.Data;

/**
 * 发送模版消息命令
 *
 * @author legendjw
 */
@Data
public class MessageLogCommand {
    /**
     * 模版模块id
     */
    private Long templateId;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 接收人
     */
    private Long receiveUserId;

    /**
     * 消息标识
     */
    private String srcId;

    /**
     * 源表行id
     */
    private Long srcRowId;
}