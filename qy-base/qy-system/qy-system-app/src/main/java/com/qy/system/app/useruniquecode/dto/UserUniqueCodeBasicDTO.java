package com.qy.system.app.useruniquecode.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户设备唯一码
 *
 * @author wwd
 * @since 2024-04-19
 */
@Data
public class UserUniqueCodeBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    private String phone;

    /**
     * 设备唯一码
     */
    @ApiModelProperty(value = "设备唯一码")
    private String uniqueCode;

    /**
     * 客户端
     */
    @ApiModelProperty(value = "客户端")
    private String clientId;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long organizationId;

    /**
     * 1.经销商，2.公司员工
     */
    @ApiModelProperty(value = "1.经销商，2.公司员工")
    private Integer groupId;

    /**
     * 是否忽略验证，1.忽略，0.不忽略
     */
    @ApiModelProperty(value = "是否忽略验证，1.忽略，0.不忽略")
    private Integer isIgnore;

    @ApiModelProperty(value = "极光推送唯一码")
    private String registrationId;

}
