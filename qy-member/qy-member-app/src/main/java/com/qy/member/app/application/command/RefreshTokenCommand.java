package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 刷新令牌token
 *
 * @author legendjw
 */
@Data
public class RefreshTokenCommand {
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
     * 刷新token
     */
    @ApiModelProperty("刷新token")
    @NotNull(message = "请输入刷新token")
    private String refreshToken;
}