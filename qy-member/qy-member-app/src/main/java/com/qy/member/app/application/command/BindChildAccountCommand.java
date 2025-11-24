package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 绑定子账号命令
 *
 * @author legendjw
 */
@Data
public class BindChildAccountCommand {
    /**
     * 会员id
     */
    @NotNull(message = "请选择会员")
    private Long memberId;

    /**
     * 员工集合
     */
    private List<Long> employeeIds;
}