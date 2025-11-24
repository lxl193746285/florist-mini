package com.qy.rbac.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建角色命令
 *
 * @author legendjw
 */
@Data
public class CreateRoleCommand {
    /**
     * id
     */
    @NotBlank(message = "请输入角色id")
    private String id;
    /**
     * 描述
     */
    private String description;
}
