package com.qy.rbac.app.application.command;

import com.qy.rbac.app.application.dto.PermissionWithRuleDTO;
import lombok.Data;

import java.util.List;

/**
 * 创建一个角色并授权命令
 *
 * @author legendjw
 */
@Data
public class CreateRoleAndAuthorizeCommand {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色描述
     */
    private String roleDescription;
    /**
     * 授权的权限节点
     */
    private List<PermissionWithRuleDTO> permissions;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    private Long systemId;
}
