package com.qy.rbac.api.command;

import lombok.Data;

import java.util.List;

/**
 * 分配角色给指定用户
 *
 * @author legendjw
 */
@Data
public class RbacAssignRoleToUserCommand {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id集合
     */
    private List<String> roleIds;

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
