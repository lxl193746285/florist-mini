package com.qy.organization.app.application.command;

import lombok.Data;

import java.util.List;

/**
 * 分配角色给指定人员
 *
 * @author legendjw
 */
@Data
public class AssignRoleToUserCommand {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 人员id
     */
    private Long userId;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds;

    private Long systemId;
}