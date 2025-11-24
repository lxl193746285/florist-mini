package com.qy.member.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信登录DTO
 *
 * @author legendjw
 */
@Data
public class WeixinLoginDTO {
    /**
     * 登录状态：1: 成功登录 2：未绑定账号 3: 未关注公众号
     */
    private int status;

    /**
     * 访问令牌
     */
    private AccessTokenDTO accessToken;

    /**
     * 会员id
     */
    private Long memberId;


    /**
     * 微信会话
     */
    private WeixinSessionDTO weixinSession;

    /**
     * openid
     */
    @ApiModelProperty("openid")
    private String openId;

    private Long organizationId;
}
