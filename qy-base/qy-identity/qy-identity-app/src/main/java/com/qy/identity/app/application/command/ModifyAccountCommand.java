package com.qy.identity.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改账号命令
 *
 * @author legendjw
 */
@Data
public class ModifyAccountCommand {
    /**
     * 用户id
     */
    private Long userId;

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
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}