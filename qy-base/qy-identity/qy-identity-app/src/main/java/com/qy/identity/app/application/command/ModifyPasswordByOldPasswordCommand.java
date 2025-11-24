package com.qy.identity.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 根据旧密码修改密码命令
 *
 * @author legendjw
 */
@Data
public class ModifyPasswordByOldPasswordCommand {
    /**
     * 用户id
     */
    @JsonIgnore
    private Long userId;

    /**
     * 旧密码
     */
    @NotBlank(message = "请输入旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码")
    private String password;
}