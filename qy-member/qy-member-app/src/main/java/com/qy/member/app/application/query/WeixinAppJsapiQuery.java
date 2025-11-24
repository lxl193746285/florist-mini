package com.qy.member.app.application.query;

import lombok.Data;

/**
 * 微信会员jsapi查询
 *
 * @author legendjw
 */
@Data
public class WeixinAppJsapiQuery {
    /**
     * 会员系统id
     */
    private String systemId;

    /**
     * 微信应用id
     */
    private String appId;

    /**
     * 回调url
     */
    private String url;
}
