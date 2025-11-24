package com.qy.member.app.application.command;

import com.qy.customer.api.command.BusinessLicenseForm;
import com.qy.customer.api.command.ContactForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更新会员命令
 *
 * @author legendjw
 */
@Data
public class UpdateMemberCommand {
    /**
     * 会员id
     */
    private Long id;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名")
    private String name;

    /**
     * 头像附件id
     */
    private Long avatarAttachmentId;

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
    private Long levelId;

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
     * 法人
     */
    private ContactForm legalPerson;

    /**
     * 营业执照
     */
    private BusinessLicenseForm businessLicense;

    /**
     * 备注
     */
    private String remark;

    /**
     * 组织id
     */
    private Long organizationId;

    private Integer memberType;
}