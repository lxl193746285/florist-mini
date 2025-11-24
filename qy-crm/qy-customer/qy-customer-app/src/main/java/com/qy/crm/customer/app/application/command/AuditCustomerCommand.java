package com.qy.crm.customer.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 审核客户命令
 *
 * @author legendjw
 */
@Data
public class AuditCustomerCommand {
    /**
     * 客户id
     */
    @JsonIgnore
    private Long id;

    /**
     * 审核类型id: 1: 通过 2：拒绝
     */
    @NotNull(message = "请选择审核类型")
    private Integer status;

    /**
     * 拒绝原因
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;
}
