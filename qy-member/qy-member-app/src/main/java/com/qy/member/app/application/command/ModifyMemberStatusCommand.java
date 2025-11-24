package com.qy.member.app.application.command;

import lombok.Data;

/**
 * 设置会员状态命令
 *
 * @author legendjw
 */
@Data
public class ModifyMemberStatusCommand {
    /**
     * 会员id
     */
    private Long id;

    /**
     * 状态id
     */
    private Integer statusId;
}