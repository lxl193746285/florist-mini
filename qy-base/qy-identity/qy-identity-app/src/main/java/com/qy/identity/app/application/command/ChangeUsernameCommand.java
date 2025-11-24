package com.qy.identity.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更换用户名命令
 *
 * @author legendjw
 */
@Data
public class ChangeUsernameCommand {
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
     * 新的用户名
     */
    @NotBlank(message = "请输入用户名")
    private String username;
}