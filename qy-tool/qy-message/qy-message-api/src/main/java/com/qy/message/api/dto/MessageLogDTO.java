package com.qy.message.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageLogDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 模版id
     */
    private Long templateId;

    /**
     * 消息类型
     */
    private Byte messageType;
    private String messageTypeName;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    private String sendTimeName;

    /**
     * 接收人
     */
    private Long receiveUserId;
    private String receiveUserName;

    /**
     * 发送状态（0：失败，1：成功）
     */
    private Byte sendStatus;
    private String sendStatusName;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 消息标识
     */
    private String srcId;

    /**
     * 源表行id
     */
    private Long srcRowId;


}
