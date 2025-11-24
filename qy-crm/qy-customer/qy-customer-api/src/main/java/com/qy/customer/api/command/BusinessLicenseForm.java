package com.qy.customer.api.command;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 营业执照表单
 *
 * @author legendjw
 */
@Data
public class BusinessLicenseForm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 类型
     */
    private String type;

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
     * 注册资本
     */
    private String registeredCapital;

    /**
     * 成立日期
     */
    private LocalDate registerDate;

    /**
     * 业务范围
     */
    private String businessScope;

    /**
     * 住所
     */
    private String domicile;

    /**
     * 营业执照照片附件id
     */
    private Long imageId;

    /**
     * 备注
     */
    private String remark;
}