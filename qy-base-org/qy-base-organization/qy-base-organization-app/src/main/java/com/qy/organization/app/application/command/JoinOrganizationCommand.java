package com.qy.organization.app.application.command;

import lombok.Data;

/**
 * 加入组织命令
 *
 * @author legendjw
 */
@Data
public class JoinOrganizationCommand {
    /**
     * 加入用户
     */
    private Long userId;

    /**
     * 加入组织
     */
    private Long organizationId;

    /**
     * 邀请人id
     */
    private Long inviterId;

    /**
     * 邀请人姓名
     */
    private String inviterName;
}
