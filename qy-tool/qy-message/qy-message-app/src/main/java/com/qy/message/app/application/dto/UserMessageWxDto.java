package com.qy.message.app.application.dto;

import lombok.Data;

@Data
public class UserMessageWxDto {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * openId
     */
    private String openid;

}
