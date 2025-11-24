package com.qy.member.app.application.dto;

import lombok.Data;

/**
 * 绑定微信信息
 *
 * @author legendjw
 */
@Data
public class BindWeixinInfoDTO {
    /**
     * 微信openid
     */
    private String openId;

    /**
     * 微信unionid
     */
    private String unionId;
}