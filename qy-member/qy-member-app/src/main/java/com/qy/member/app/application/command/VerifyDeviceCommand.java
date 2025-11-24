package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wwd
 * @date 2023-05-06 10:30
 */
@Data
public class VerifyDeviceCommand {


    /**
     * 设备唯一码
     */
    @ApiModelProperty(value = "设备唯一码")
    private String uniqueCode;


    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long organizationId;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    private String registrationId;

    private Long logId;

}
