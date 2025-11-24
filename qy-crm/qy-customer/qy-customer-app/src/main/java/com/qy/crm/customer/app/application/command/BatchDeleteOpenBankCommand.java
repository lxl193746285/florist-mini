package com.qy.crm.customer.app.application.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 删除开户行命令
 *
 * @author legendjw
 */
@Data
public class BatchDeleteOpenBankCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * ids
     */
    private List<Long> ids;
}