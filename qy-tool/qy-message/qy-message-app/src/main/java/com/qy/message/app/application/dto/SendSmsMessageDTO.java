package com.qy.message.app.application.dto;

import lombok.Data;

import java.util.Map;

/**
 * 短信消息DTO
 *
 * @author legendjw
 */
@Data
public class SendSmsMessageDTO {
    /**
     * 平台id
     */
    private Long platformId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信模版id
     */
    private String smsTemplateId;

    /**
     * 消息数据
     */
    Map<String, Object> data;
}