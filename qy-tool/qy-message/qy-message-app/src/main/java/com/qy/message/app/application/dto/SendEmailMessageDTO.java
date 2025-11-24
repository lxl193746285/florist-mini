package com.qy.message.app.application.dto;

import lombok.Data;

import java.util.Map;

/**
 * 邮箱消息DTO
 *
 * @author legendjw
 */
@Data
public class SendEmailMessageDTO {
    /**
     * 平台id
     */
    private Long platformId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 消息数据
     */
    Map<String, Object> data;
}