package com.qy.organization.app.domain.entity;

import lombok.Data;

@Data
public class BindWeixinFormDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * openid
     */
    private String openId;

    /**
     * unionId
     */
    private String unionId;

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;
}
