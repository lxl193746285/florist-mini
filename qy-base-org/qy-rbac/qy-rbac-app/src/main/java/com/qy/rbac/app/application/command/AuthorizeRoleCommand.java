package com.qy.rbac.app.application.command;

import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;
import lombok.Data;

import java.util.List;

/**
 * 角色授权命令
 *
 * @author legendjw
 */
@Data
public class AuthorizeRoleCommand {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 授权的权限节点
     */
    private List<PermissionWithRuleDTO> permissions;
}
