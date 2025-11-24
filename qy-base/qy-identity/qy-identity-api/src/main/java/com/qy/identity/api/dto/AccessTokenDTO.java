package com.qy.identity.api.dto;

import lombok.Data;

/**
 * 访问令牌DTO
 *
 * @author legendjw
 */
@Data
public class AccessTokenDTO {
    /**
     * 令牌类型
     */
    private String tokenType;
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 失效时间（秒数）
     */
    private Long expiresIn;
    /**
     * 授权范围
     */
    private String scope;
}