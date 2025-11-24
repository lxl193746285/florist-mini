package com.qy.crm.customer.app.application.command;

import com.qy.crm.customer.app.application.dto.RelatedModuleDataDTO;
import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量保存客户开户行命令
 *
 * @author legendjw
 */
@Data
public class BatchSaveOpenBankCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联信息
     */
    private RelatedModuleDataDTO relatedModuleData;

    /**
     * 法人id
     */
    private Long legalPersonId;

    /**
     * 法人姓名
     */
    private String legalPersonName;

    /**
     * 开户行
     */
    private List<OpenBankForm> openBanks = new ArrayList<>();
}