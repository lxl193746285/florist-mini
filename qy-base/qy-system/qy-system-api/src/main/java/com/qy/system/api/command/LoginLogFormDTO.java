package com.qy.system.api.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统登录日志
 *
 * @author wwd
 * @since 2024-04-18
 */
@Data
public class LoginLogFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operateTime;

    /**
     * 操作ip
     */
    @ApiModelProperty(value = "操作ip")
    private String operateIp;

    /**
     * 设备唯一码
     */
    @ApiModelProperty(value = "设备唯一码")
    private String uniqueCode;

    /**
     * 类型，1.登录，2.退出
     */
    @ApiModelProperty(value = "类型，1.登录，2.退出")
    private Integer type;

    /**
     * 用户代理
     */
    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    private String extraData;

    private Integer isException;

}
