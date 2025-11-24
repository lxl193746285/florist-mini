package com.qy.member.app.application.command;

import com.qy.customer.api.command.BusinessLicenseForm;
import com.qy.customer.api.command.ContactForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 注册会员命令
 *
 * @author legendjw
 */
@Data
public class RegisterMemberCommand {
    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    @NotBlank(message = "请输入客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    @NotNull(message = "请输入客户端密钥")
    private String clientSecret;

    /**
     * 会员系统id
     */
    @ApiModelProperty("会员系统id")
    private String systemId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 短信验证码
     */
    @ApiModelProperty("短信验证码")
    @NotBlank(message = "请输入验证码")
    private String verificationCode;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotBlank(message = "请输入密码")
    private String password;

    /**
     * 微信token
     */
    @ApiModelProperty("微信token")
    private String weixinToken;

    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String invitationCode;

    /**
     * 性别id
     */
    @ApiModelProperty("性别id")
    private Integer genderId;

    /**
     * 性别名称
     */
    @ApiModelProperty("性别名称")
    private String genderName;

    /**
     * 等级id
     */
    @ApiModelProperty("等级id")
    private Integer levelId;

    /**
     * 等级名称
     */
    @ApiModelProperty("等级名称")
    private String levelName;

    /**
     * 省份代码
     */
    @ApiModelProperty("省份代码")
    private Integer provinceId;

    /**
     * 省份名称
     */
    @ApiModelProperty("省份名称")
    private String provinceName;

    /**
     * 城市代码
     */
    @ApiModelProperty("城市代码")
    private Integer cityId;

    /**
     * 城市名称
     */
    @ApiModelProperty("城市名称")
    private String cityName;

    /**
     * 地区代码
     */
    @ApiModelProperty("地区代码")
    private Integer areaId;

    /**
     * 地区名称
     */
    @ApiModelProperty("地区名称")
    private String areaName;

    /**
     * 街道代码
     */
    @ApiModelProperty("街道代码")
    private Integer streetId;

    /**
     * 街道名称
     */
    @ApiModelProperty("街道名称")
    private String streetName;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String address;

    /**
     * 法人
     */
    @ApiModelProperty("法人")
    private ContactForm legalPerson;

    /**
     * 营业执照
     */
    @ApiModelProperty("营业执照")
    private BusinessLicenseForm businessLicense;

    /**
     * 会员类型
     */
    private Integer memberType;
}