package com.qy.verification.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 发送短信验证码命令
 *
 * @author legendjw
 */
@Data
public class SendSmsVerificationCodeCommand {
    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 场景
     *
     * 目前存在以下可用：
     * REGISTER: 方舟云账号注册
     * LOGIN: 方舟云账号登录
     * RETRIEVE_PASSWORD: 忘记密码找回密码
     * CHANGE_PHONE: 更换手机号
     * MODIFY_USER_PASSWORD: 修改用户密码
     * OPEN_ACCOUNT: 开户
     *
     * OPEN_MEMBER: 开通会员
     * REGISTER_MEMBER: 注册会员
     * MEMBER_LOGIN: 会员的登录
     * MEMBER_RETRIEVE_PASSWORD: 会员忘记密码找回密码
     * MEMBER_CHANGE_PHONE: 会员更换手机号
     * MODIFY_MEMBER_PASSWORD: 修改会员密码
     * MEMBER_CREATE_ACCOUNT: 创建会员子账号
     */
    @NotBlank(message = "请输入场景")
    private String scene;

    /**
     * 临时会话token
     */
    private String tmpAccessToken;

    /**
     * 图形验证码
     */
    private String code;
}