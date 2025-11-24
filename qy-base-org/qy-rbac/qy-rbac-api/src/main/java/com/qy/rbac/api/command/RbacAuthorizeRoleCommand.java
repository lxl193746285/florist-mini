package com.qy.rbac.api.command;

import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import lombok.Data;

import java.util.List;

/**
 * 角色授权命令
 *
 * @author legendjw
 */
@Data
public class RbacAuthorizeRoleCommand {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 授权的权限节点
     */
    private List<PermissionWithRuleDTO> permissions;
}
