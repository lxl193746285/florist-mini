package com.qy.authorization.app.application.command;

import lombok.Data;

import java.util.Set;

/**
 * 创建授权客户端
 *
 * @author legendjw
 */
@Data
public class CreateAuthClientCommand {
    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 授权类型
     */
    private Set<String> authorizedGrantTypes;
}
