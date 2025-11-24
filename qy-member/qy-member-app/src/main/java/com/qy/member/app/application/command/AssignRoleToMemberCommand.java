package com.qy.member.app.application.command;

import lombok.Data;

import java.util.List;

/**
 * 分配角色给指定人员
 *
 * @author legendjw
 */
@Data
public class AssignRoleToMemberCommand {
    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds;
}