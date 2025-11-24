package com.qy.identity.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 账号短信登录命令
 *
 * @author legendjw
 */
@Data
public class LoginByPhoneCommand {
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
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    private String phone;
    /**
     * 短信验证码
     */
    @NotBlank(message = "请输入验证码")
    private String verificationCode;
}