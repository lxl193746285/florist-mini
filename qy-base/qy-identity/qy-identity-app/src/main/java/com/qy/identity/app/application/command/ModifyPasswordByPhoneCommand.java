package com.qy.identity.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 短信修改密码命令
 *
 * @author legendjw
 */
@Data
public class ModifyPasswordByPhoneCommand {
    /**
     * 用户id
     */
    @JsonIgnore
    private Long userId;

    /**
     * 短信验证码
     */
    @NotBlank(message = "请输入验证码")
    private String verificationCode;

    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码")
    private String password;
}