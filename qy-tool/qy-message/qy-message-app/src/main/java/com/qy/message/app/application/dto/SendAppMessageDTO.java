package com.qy.message.app.application.dto;

import lombok.Data;

import java.util.Map;

/**
 * APP消息DTO
 *
 * @author legendjw
 */
@Data
public class SendAppMessageDTO {
    /**
     * 平台id
     */
    private Long platformId;

    /**
     * 手机唯一id
     */
    private String mobileId;

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
    Map<String, String> data;
}