package com.qy.member.api.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author wwd
 * @since 2023-10-20 18:05
 */
@Getter
@Builder
public class WeixinMiniUserDTO {

    private String sessionKey;

    private String openid;

    private String unionid;
}
