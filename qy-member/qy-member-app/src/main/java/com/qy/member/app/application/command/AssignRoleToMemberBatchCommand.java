package com.qy.member.app.application.command;

import lombok.Data;

import java.util.List;

/**
 * 批量分配角色给指定人员
 *
 * @author hh
 */
@Data
public class AssignRoleToMemberBatchCommand {

    /**
     * 会员id
     */
    private List<Long> memberIds;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds;
}
