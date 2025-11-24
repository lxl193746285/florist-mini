package com.qy.customer.api.command;

import com.qy.customer.api.dto.RelatedModuleDataDTO;
import lombok.Data;

/**
 * 保存营业执照命令.
 */
@Data
public class SaveBusinessLicenseCommand {
    private Object identity;
    private Long organizationId;
    private RelatedModuleDataDTO relatedModuleData;
    private BusinessLicenseForm businessLicense;
    private Long legalPersonId;
    private String legalPersonName;
}
