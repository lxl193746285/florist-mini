package com.qy.member.api.dto;

import lombok.Data;

/**
 * 发送消息会员账号信息DTO
 *
 * @author legendjw
 */
@Data
public class SendMessageAccountInfoDTO {
    /**
     * 会员账号id
     */
    private Long accountId;

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 微信appid
     */
    private String weixinAppId;

    /**
     * 公众号openId
     */
    private String openId;

    /**
     * 手机唯一id
     */
    private String mobileId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}