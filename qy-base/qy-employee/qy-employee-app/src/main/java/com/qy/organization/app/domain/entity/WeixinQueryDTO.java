package com.qy.organization.app.domain.entity;

import lombok.Data;

@Data
public class WeixinQueryDTO {

    /**
     * 微信code
     */
    private String code;

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;
}
