package com.qy.message.api.dto;

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
