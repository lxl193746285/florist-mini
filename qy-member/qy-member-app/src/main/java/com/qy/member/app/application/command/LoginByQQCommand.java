package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * QQ登录命令
 *
 * @author di
 */
@Data
public class LoginByQQCommand {
    /**
     * 客户端id
     */
    @NotBlank(message = "请输入客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @NotNull(message = "请输入客户端密钥")
    private String clientSecret;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * qqOpenId
     */
    private String openId;

    /**
     * qq会话token
     */
    private String accessToken;
}