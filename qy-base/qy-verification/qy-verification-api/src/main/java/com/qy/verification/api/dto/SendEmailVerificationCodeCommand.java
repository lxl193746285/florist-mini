package com.qy.verification.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 发送邮箱验证码命令
 *
 * @author legendjw
 */
@Data
public class SendEmailVerificationCodeCommand {
    /**
     * 邮箱地址
     */
    @NotBlank(message = "请输入邮箱地址")
    private String address;

    /**
     * 场景
     */
    @NotBlank(message = "请输入场景")
    private String scene;
}