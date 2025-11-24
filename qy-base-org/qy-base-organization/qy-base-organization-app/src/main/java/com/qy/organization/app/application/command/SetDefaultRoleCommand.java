package com.qy.organization.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设置默认权限组命令
 *
 * @author legendjw
 */
@Data
public class SetDefaultRoleCommand implements Serializable {
    /**
     * 权限组id
     */
    @ApiModelProperty("权限组id")
    private Long id;

    /**
     * 默认权限组类型 系统代码表：default_role
     */
    @ApiModelProperty("默认权限组类型 系统代码表：default_role")
    private String defaultRole;
}