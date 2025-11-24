package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 创建子账号命令
 *
 * @author legendjw
 */
@Data
public class CreateChildAccountCommand {
    /**
     * 会员id
     */
    @NotNull(message = "请选择会员")
    private Long memberId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 短信验证码 场景：MEMBER_CREATE_ACCOUNT
     */
    @NotBlank(message = "请输入验证码")
    private String verificationCode;
}