package com.qy.organization.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 邀请加入组织命令
 *
 * @author legendjw
 */
@Data
public class InviteJoinOrganizationCommand {
    /**
     * 邀请用户
     */
    @JsonIgnore
    private Long userId;

    /**
     * 邀请码
     */
    private String code;
}
