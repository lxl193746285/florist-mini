package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 绑定微信命令
 *
 * @author legendjw
 */
@Data
public class BindWeixinCommand {
    /**
     * 微信应用id
     */
    @ApiModelProperty("微信应用id")
    private String appId;

    /**
     * 授权code
     */
    @ApiModelProperty("授权code")
    @NotBlank(message = "请输入授权code")
    private String code;

    private String clientId;
}