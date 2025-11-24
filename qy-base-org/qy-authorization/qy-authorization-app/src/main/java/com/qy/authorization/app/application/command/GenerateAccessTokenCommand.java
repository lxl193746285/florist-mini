package com.qy.authorization.app.application.command;

import lombok.Data;

import java.util.Map;

/**
 * 生成令牌token
 *
 * @author legendjw
 */
@Data
public class GenerateAccessTokenCommand {
    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 额外数据
     */
    private Map<String, String> extraData;
}