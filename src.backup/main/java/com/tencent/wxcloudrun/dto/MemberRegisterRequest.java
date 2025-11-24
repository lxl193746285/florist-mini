package com.tencent.wxcloudrun.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 会员注册请求 DTO
 */
@Data
public class MemberRegisterRequest {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名必须是4-20位字母、数字或下划线")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,20}$",
             message = "密码必须是8-20位，包含大小写字母和数字")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱（可选）
     */
    private String email;

    /**
     * 昵称（可选）
     */
    private String nickname;

    /**
     * 验证码
     */
    private String smsCode;

    /**
     * 注册来源：wechat-微信，app-APP，web-网页
     */
    private String source;

    /**
     * 推荐人ID（可选）
     */
    private Long referrerId;
}
