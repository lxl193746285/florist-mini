package com.qy.customer.api.command;

import lombok.Data;

/**
 * 营业执照表单.
 */
@Data
public class BusinessLicenseForm {
    private Long id;
    private String licenseNumber;
    private String companyName;
    private String businessScope;
    private String registeredCapital;
    private String address;
    private String licenseImageUrl;
    private String issueDate;
    private String expireDate;
}
