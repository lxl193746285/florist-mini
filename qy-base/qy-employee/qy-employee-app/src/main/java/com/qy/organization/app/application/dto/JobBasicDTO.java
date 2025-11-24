package com.qy.organization.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基本岗位信息
 *
 * @author legendjw
 */
@Data
public class JobBasicDTO implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
}
