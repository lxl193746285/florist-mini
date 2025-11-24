package com.qy.member.app.application.command;

import lombok.Data;

/**
 * 设置账号状态命令
 *
 * @author legendjw
 */
@Data
public class ModifyAccountStatusCommand {
    /**
     * 账号id
     */
    private Long id;

    /**
     * 状态id
     */
    private Integer statusId;
}