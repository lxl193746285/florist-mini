package com.qy.authorization.api.command;

import lombok.Data;

/**
 * 刷新令牌token
 *
 * @author legendjw
 */
@Data
public class RefreshAccessTokenCommand {
    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 刷新token
     */
    private String refreshToken;
}