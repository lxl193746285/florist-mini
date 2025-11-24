package com.qy.identity.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * 用户id
     */
    @JsonIgnore
    private Long userId;

    /**
     * 新的手机号
     */
    @NotBlank(message = "请输入新的手机号")
    private String phone;

    /**
     * 短信验证码
     */
    @NotBlank(message = "请输入验证码")
    private String verificationCode;
}