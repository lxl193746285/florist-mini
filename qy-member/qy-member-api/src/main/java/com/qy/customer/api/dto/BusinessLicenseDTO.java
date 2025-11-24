package com.qy.customer.api.dto;

import lombok.Data;

/**
 * 简化版营业执照信息.
 */
@Data
public class BusinessLicenseDTO {
    /**
     * 唯一主键
     */
    private Long id;

    /**
     * 营业执照编号
     */
    private String licenseNumber;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 法人名称
     */
    private String legalPersonName;

    /**
     * 法人ID
     */
    private Long legalPersonId;

    /**
     * 营业执照图片地址
     */
    private String licenseImageUrl;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 注册地址
     */
    private String address;
}
