package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 短信修改密码命令
 *
 * @author legendjw
 */
@Data
public class ModifyPasswordCommand {
    /**
     * 短信验证码
     */
    @ApiModelProperty("短信验证码")
//    @NotBlank(message = "请输入验证码")
    private String verificationCode;

    /**
     * 旧密码
     */
    @ApiModelProperty("旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
//    @NotBlank(message = "请输入新密码")
    private String password;
}