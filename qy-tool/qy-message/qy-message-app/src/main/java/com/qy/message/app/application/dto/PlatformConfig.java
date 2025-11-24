package com.qy.message.app.application.dto;

import lombok.Data;

/**
 * 消息平台配置
 *
 * @author legendjw
 */
@Data
public class PlatformConfig {
    /**
     * 微信appid
     */
    private String weixinAppId;

    /**
     * 微信app密钥
     */
    private String weixinAppSecret;

    /**
     * 发送邮箱域名
     */
    private String mailHost;

    /**
     * 发送邮箱用户名
     */
    private String mailUsername;

    /**
     * 发送邮箱密码
     */
    private String mailPassword;

    /**
     * 发送邮箱端口号
     */
    private String mailPort;

    /**
     * APP推送key
     */
    private String appPushKey;

    /**
     * APP推送密钥
     */
    private String appPushSecret;

    /**
     * 短信key
     */
    private String smsKey;

    /**
     * 短信密钥
     */
    private String smsSecret;

    /**
     * 短信签名
     */
    private String smsSign;
}
