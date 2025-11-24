package com.qy.crm.customer.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量审核客户命令
 *
 * @author legendjw
 */
@Data
public class BatchAuditCustomerCommand {
    /**
     * 客户id集合
     */
    @NotNull(message = "请选择审核的客户")
    private List<Long> ids = new ArrayList<>();

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
