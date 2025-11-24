package com.qy.system.app.version.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * APP版本
 *
 * @author syf
 * @since 2024-05-21
 */
@Data
public class AppVersionQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 名称
    */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Long typeId;

    /**
     * 项目类型
     */
    @ApiModelProperty(value = "项目类型")
    private Long itemTypeId;

}
