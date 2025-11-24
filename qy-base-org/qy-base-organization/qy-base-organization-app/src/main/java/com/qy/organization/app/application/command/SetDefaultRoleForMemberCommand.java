package com.qy.organization.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员系统-设置默认权限组命令
 *
 * @author legendjw
 */
@Data
public class SetDefaultRoleForMemberCommand {
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

    /**
     * 会员系统标识 member_system
     */
    @ApiModelProperty("会员系统标识 member_system")
    private String context;

    /**
     * 会员系统id
     */
    @ApiModelProperty("会员系统id")
    private Long contextId;

}
