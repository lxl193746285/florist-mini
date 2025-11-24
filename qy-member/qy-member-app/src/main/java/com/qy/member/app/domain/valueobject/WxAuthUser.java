package com.qy.member.app.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

/**
 * 微信授权用户
 *
 * @author legendjw
 */
@Getter
@Builder
public class WxAuthUser {
    /**
     * openid
     */
    private String openId = "";

    /**
     * unionid
     */
    private String unionId = "";

    /**
     * 昵称
     */
    private String nickname = "";

    /**
     * 性别
     */
    private Integer sex = 0;

    /**
     * 国家
     */
    private String country = "";

    /**
     * 省份
     */
    private String province = "";

    /**
     * 城市
     */
    private String city = "";

    /**
     * 头像
     */
    private String headImgUrl = "";

    /**
     * 会话key
     */
    private String sessionKey = "";

    /**
     * 是否关注公众号
     */
    private Boolean subscribe;
}
