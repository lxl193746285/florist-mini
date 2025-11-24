package com.qy.rbac.api.command;

import lombok.Data;

/**
 * 复制指定用户的权限给其他用户
 *
 * @author legendjw
 */
@Data
public class RbacCopyUserPermissionCommand {
    /**
     * 指定用户id
     */
    private String sourceUserId;

    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 复制到的用户id
     */
    private String toUserId;
}
