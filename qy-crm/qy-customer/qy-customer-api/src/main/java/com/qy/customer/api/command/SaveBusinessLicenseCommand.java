package com.qy.customer.api.command;

import com.qy.customer.api.dto.RelatedModuleDataDTO;
import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存营业执照命令
 *
 * @author legendjw
 */
@Data
public class SaveBusinessLicenseCommand implements Serializable {
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
     * 营业执照
     */
    private BusinessLicenseForm businessLicense;
}