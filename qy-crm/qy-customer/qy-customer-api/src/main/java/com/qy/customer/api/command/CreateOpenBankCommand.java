package com.qy.customer.api.command;

import com.qy.customer.api.dto.RelatedModuleDataDTO;
import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 创建开户行命令
 *
 * @author legendjw
 */
@Data
public class CreateOpenBankCommand implements Serializable {
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
    private List<RelatedModuleDataDTO> relatedModuleData;

    /**
     * 核准号
     */
    private String approvalNumber;

    /**
     * 名称
     */
    @NotBlank(message = "请输入开户行名称")
    private String name;

    /**
     * 法人id
     */
    private Long legalPersonId;

    /**
     * 法人姓名
     */
    private String legalPersonName;

    /**
     * 电话
     */
    private String tel;

    /**
     * 开户银行
     */
    private String depositBank;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 备注
     */
    private String remark;
}