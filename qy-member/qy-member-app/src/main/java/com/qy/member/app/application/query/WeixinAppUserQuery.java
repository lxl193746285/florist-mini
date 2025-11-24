package com.qy.member.app.application.query;

import lombok.Data;

/**
 * 微信应用用户查询
 *
 * @author legendjw
 */
@Data
public class WeixinAppUserQuery {
    /**
     * 会员系统id
     */
    private String systemId;

    /**
     * 微信应用id
     */
    private String appId;

    /**
     * 授权code
     */
    private String code;
}
