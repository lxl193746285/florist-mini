package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 微信注册会员命令
 *
 * @author legendjw
 */
@Data
public class WeixinRegisterMemberCommand {
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
    @NotBlank(message = "请选择会员系统")
    private String systemId;

    /**
     * 姓名
     */
    private String name;

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

    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    private String password;

    /**
     * 微信token
     */
    @NotBlank(message = "请输入微信token")
    private String weixinToken;
}