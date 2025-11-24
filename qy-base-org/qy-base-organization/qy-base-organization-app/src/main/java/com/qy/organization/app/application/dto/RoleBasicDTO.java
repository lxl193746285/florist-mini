package com.qy.organization.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基本权限组信息
 *
 * @author legendjw
 */
@Data
public class RoleBasicDTO implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 标识：是否需要置灰的权限（0：否，1：是）
     */
    @ApiModelProperty("标识：是否需要置灰的权限（0：否，1：是）")
    private Integer isGray;

    private String contextId;
}
