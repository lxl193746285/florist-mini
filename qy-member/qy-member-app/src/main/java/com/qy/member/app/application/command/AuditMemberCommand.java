package com.qy.member.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 审核会员命令
 *
 * @author legendjw
 */
@Data
public class AuditMemberCommand {
    /**
     * 会员id
     */
    @JsonIgnore
    private Long id;

    /**
     * 审核层级
     */
    private Integer auditLevel = 2;

    /**
     * 审核类型id: 1: 通过 2：拒绝
     */
    @NotNull(message = "请选择审核类型")
    private Integer statusId;

    /**
     * 拒绝原因
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核人id
     */
    @JsonIgnore
    private Long auditorId;

    /**
     * 审核人姓名
     */
    @JsonIgnore
    private String auditorName;
}
