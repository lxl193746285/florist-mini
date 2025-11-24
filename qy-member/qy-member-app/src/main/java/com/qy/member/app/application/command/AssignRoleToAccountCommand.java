package com.qy.member.app.application.command;

import lombok.Data;

import java.util.List;

/**
 * 分配角色给指定人员
 *
 * @author legendjw
 */
@Data
public class AssignRoleToAccountCommand {
    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds;
}