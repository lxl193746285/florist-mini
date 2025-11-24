package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 账号短信登录命令
 *
 * @author legendjw
 */
@Data
public class LoginByPhoneCommand {
    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    @NotBlank(message = "请输入客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @ApiModelProperty(value = "客户端密钥")
    @NotNull(message = "请输入客户端密钥")
    private String clientSecret;

    /**
     * 会员系统id
     */
    @ApiModelProperty(value = "会员系统id")
    private String systemId;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long organizationId;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * appId
     */
    @ApiModelProperty(value = "appId")
    // @NotBlank(message = "appId")
    private String appId;

    /**
     * 短信验证码
     */
    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message = "请输入验证码")
    private String verificationCode;

    /**
     * 微信token
     */
    @ApiModelProperty(value = "微信token")
    private String weixinToken;

    /**
     * 设备唯一码
     */
    @ApiModelProperty(value = "设备唯一码")
    private String uniqueCode;

    /**
     * 手机型号
     */
    @ApiModelProperty(value = "手机型号")
    private String phoneModel;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;
}