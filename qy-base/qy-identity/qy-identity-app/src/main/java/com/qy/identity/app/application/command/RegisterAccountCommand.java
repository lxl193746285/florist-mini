package com.qy.identity.app.application.command;

import com.qy.identity.app.domain.enums.UserSource;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 注册账号命令
 *
 * @author legendjw
 */
@Data
public class RegisterAccountCommand {
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
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

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
     * 注册来源
     */
    private String source = UserSource.REGISTER.getId();
}