package com.qy.system.app.config.dto;

import com.qy.system.app.comment.dto.ArkBaseDTO;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 配置类别
 *
 * @author hh
 * @since 2024-07-09
 */
@Data
public class SystemConfigCategoryDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配置类别ID
     */
    @ApiModelProperty(value = "配置类别ID")
    private Long id;

    /**
     * 配置类别名称
     */
    @ApiModelProperty(value = "配置类别名称")
    private String name;

    /**
     * 标识符
     */
    @ApiModelProperty(value = "标识符")
    private String identifier;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 状态：0 禁用 1开启 默认开启
     */
    @ApiModelProperty(value = "状态：0 禁用 1开启 默认开启")
    private Integer status;

    /**
     * 是否系统配置
     */
    @ApiModelProperty(value = "是否系统配置")
    private Byte isSystem;

    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String statusName;

}
