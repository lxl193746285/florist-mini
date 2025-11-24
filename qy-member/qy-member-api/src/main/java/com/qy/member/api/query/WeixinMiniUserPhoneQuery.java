package com.qy.member.api.query;

import lombok.Data;

/**
 * @author di
 * @date 2023-10-20 11:44
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
