package com.qy.member.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 微信会话DTO
 *
 * @author legendjw
 */
@Data
public class WeixinSessionDTO {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 微信token
     */
    private String weixinToken;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
