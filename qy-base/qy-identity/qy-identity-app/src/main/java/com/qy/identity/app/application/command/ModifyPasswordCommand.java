package com.qy.identity.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码命令
 *
 * @author legendjw
 */
@Data
public class ModifyPasswordCommand {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码")
    private String password;
}