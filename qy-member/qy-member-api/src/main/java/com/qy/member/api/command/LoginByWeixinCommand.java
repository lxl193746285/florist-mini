package com.qy.member.api.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 微信登录命令
 *
 * @author legendjw
 */
@Data
public class LoginByWeixinCommand {
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
     * 会员系统id
     */
    private String systemId;

    /**
     * 微信应用id
     */
    @NotBlank(message = "请选择微信应用")
    private String appId;

    /**
     * 授权code
     */
    @NotBlank(message = "请输入授权code")
    private String code;
}