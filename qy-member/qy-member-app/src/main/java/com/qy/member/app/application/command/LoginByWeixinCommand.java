package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 微信登录命令
 *
 * @author legendjw
 */
@Data
public class LoginByWeixinCommand {
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

//    /**
//     * 会员系统id
//     */
//    @ApiModelProperty(value = "会员系统id")
//    private String systemId;
//
//    /**
//     * 微信应用id
//     */
//    @ApiModelProperty(value = "微信应用id")
//    @NotBlank(message = "请选择微信应用")
//    private String appId;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long organizationId;

    /**
     * 授权code
     */
    @ApiModelProperty(value = "授权code")
    @NotBlank(message = "请输入授权code")
    private String code;

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