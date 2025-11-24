package com.qy.member.app.application.command;

import com.qy.customer.api.command.BusinessLicenseForm;
import com.qy.customer.api.command.ContactForm;
import lombok.Data;

/**
 * 提交会员信息审核命令
 *
 * @author legendjw
 */
@Data
public class SubmitMemberInfoAuditCommand {
    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 性别id
     */
    private Integer genderId;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 等级id
     */
    private Integer levelId;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 省份代码
     */
    private Integer provinceId;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市代码
     */
    private Integer cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 地区代码
     */
    private Integer areaId;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 街道代码
     */
    private Integer streetId;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 法人
     */
    private ContactForm legalPerson;

    /**
     * 营业执照
     */
    private BusinessLicenseForm businessLicense;
}