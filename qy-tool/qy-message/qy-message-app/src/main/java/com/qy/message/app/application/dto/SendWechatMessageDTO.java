package com.qy.message.app.application.dto;

import lombok.Data;

import java.util.Map;

/**
 * 微信消息DTO
 *
 * @author legendjw
 */
@Data
public class SendWechatMessageDTO {
    /**
     * 平台id
     */
    private Long platformId;

    /**
     * 公众号openId
     */
    private String openId;

    /**
     * 微信模版id
     */
    private String wechatTemplateId;

    /**
     * 消息数据
     */
    Map<String, Object> data;
}