package com.tencent.wxcloudrun.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 登录请求 DTO
 */
@Data
public class LoginRequest {
    /**
     * 登录账号（用户名/手机号/邮箱）
     */
    @NotBlank(message = "登录账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 用户类型：1-管理员，2-会员（可选，不传则自动识别）
     */
    private Integer userType;

    /**
     * 验证码（可选，预留）
     */
    private String captcha;
}
