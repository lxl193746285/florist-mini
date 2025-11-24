package com.qy.member.app.application.query;

import lombok.Data;

/**
 * @author wwd
 * @since 2023-10-20 11:44
 */
@Data
public class WeixinMiniUserPhoneQuery {

    /**
     * 微信应用id
     */
    private String wxAppId;

    /**
     * 微信授权获取手机号code
     */
    private String code;
}
