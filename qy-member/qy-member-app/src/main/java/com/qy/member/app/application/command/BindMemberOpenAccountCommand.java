package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 绑定会员开户组织命令
 *
 * @author legendjw
 */
@Data
public class BindMemberOpenAccountCommand {
    /**
     * 会员id
     */
    @NotNull(message = "请选择会员")
    private Long memberId;

    /**
     * 开户组织id
     */
    @NotNull(message = "请选择开户组织")
    private Long openAccountId;
}