package com.qy.rbac.api.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建角色命令
 *
 * @author legendjw
 */
@Data
public class RbacUpdateRoleCommand {
    /**
     * id
     */
    private String id;
    /**
     * 新的id
     */
    @NotBlank(message = "请输入新的角色id")
    private String newId;
    /**
     * 描述
     */
    private String description;
}
