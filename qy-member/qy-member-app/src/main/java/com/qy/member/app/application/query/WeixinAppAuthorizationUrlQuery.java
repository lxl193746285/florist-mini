package com.qy.member.app.application.query;

import lombok.Data;

/**
 * 微信会员授权地址查询
 *
 * @author legendjw
 */
@Data
public class WeixinAppAuthorizationUrlQuery {
//    /**
//     * 会员系统id
//     */
//    private String systemId;
//
//    /**
//     * 微信应用id
//     */
//    private String appId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 回调url
     */
    private String url;
}
