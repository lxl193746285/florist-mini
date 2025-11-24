package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 账号密码登录命令
 *
 * @author legendjw
 */
@Data
public class LoginByPasswordCommand {
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
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "请输入用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入密码")
    private String password;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private String appId;

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