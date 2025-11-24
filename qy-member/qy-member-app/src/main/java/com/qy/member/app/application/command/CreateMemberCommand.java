package com.qy.member.app.application.command;

import com.qy.customer.api.command.BusinessLicenseForm;
import com.qy.customer.api.command.ContactForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建会员命令
 *
 * @author legendjw
 */
@Data
public class CreateMemberCommand {
    /**
     * 会员系统id
     */
    private String systemId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像附件id
     */
    private Long avatarAttachmentId;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信token
     */
    private String weixinToken;

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

    /**
     * 备注
     */
    private String remark;

    /**
     * 会员类型（1员工2经销商3经销商员工）
     */
    private Integer memberType;
}