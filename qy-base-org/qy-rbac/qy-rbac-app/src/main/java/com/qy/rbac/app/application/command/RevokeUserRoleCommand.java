package com.qy.rbac.app.application.command;

import lombok.Data;

/**
 * 撤销指定用户角色命令
 *
 * @author legendjw
 */
@Data
public class RevokeUserRoleCommand {
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
}
