package com.qy.organization.app.application.command;

import com.qy.organization.app.application.dto.MenuCheckWithPermissionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 权限组授权命令
 *
 * @author legendjw
 */
@Data
public class AuthorizeRoleCommand {
    /**
     * 权限组id
     */
    @ApiModelProperty("权限组id")
    private Long id;

    /**
     * 授权的权限节点
     */
    @ApiModelProperty("授权的权限节点")
    private List<MenuCheckWithPermissionDTO> menuPermissions;
}
