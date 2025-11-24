package com.qy.member.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

/**
 * 指定手机号是否注册账号
 *
 * @author legendjw
 */
@Value
public class IsPhoneRegisteredDTO {
    /**
     * 结果 1：已经注册 0：未注册
     */
    @ApiModelProperty("结果 1：已经注册 0：未注册")
    private boolean result;
}
