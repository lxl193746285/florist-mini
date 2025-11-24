package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更换手机号命令
 *
 * @author legendjw
 */
@Data
public class ChangePhoneCommand {
    /**
     * 登录密码
     */
    @ApiModelProperty("登录密码")
    @NotBlank(message = "请输入登录密码")
    private String password;

    /**
     * 新的手机号
     */
    @ApiModelProperty("新的手机号")
    @NotBlank(message = "请输入新的手机号")
    private String phone;

    /**
     * 短信验证码
     */
    @ApiModelProperty("短信验证码")
    @NotBlank(message = "请输入验证码")
    private String verificationCode;
}