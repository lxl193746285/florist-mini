package com.qy.identity.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建账号命令
 *
 * @author legendjw
 */
@Data
public class CreateAccountCommand {
    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 注册来源
     */
    private String source;
}