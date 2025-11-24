package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 通过手机号找回密码命令
 *
 * @author legendjw
 */
@Data
public class RetrievePasswordByPhoneCommand {
    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    @NotBlank(message = "请输入客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    @NotNull(message = "请输入客户端密钥")
    private String clientSecret;

    /**
     * 会员系统id
     */
    @ApiModelProperty("会员系统id")
    private String systemId;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 短信验证码
     */
    @ApiModelProperty("短信验证码")
    @NotBlank(message = "请输入验证码")
    private String verificationCode;

    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
    @NotBlank(message = "请输入新密码")
    private String password;
}
