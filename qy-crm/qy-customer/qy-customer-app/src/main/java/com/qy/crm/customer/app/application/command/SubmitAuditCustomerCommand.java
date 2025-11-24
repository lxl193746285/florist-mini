package com.qy.crm.customer.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 提交审核客户命令
 *
 * @author legendjw
 */
@Data
public class SubmitAuditCustomerCommand {
    /**
     * 客户id
     */
    @JsonIgnore
    private Long id;
}
