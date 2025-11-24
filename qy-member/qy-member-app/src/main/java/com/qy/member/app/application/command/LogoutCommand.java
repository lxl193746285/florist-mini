package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wwd
 * @date 2023-05-06 10:30
 */
@Data
public class LogoutCommand {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 客户端
     */
    @ApiModelProperty(value = "客户端")
    private String clientId;

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

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;
}
