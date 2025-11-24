package com.qy.system.app.config.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 配置类别
 *
 * @author hh
 * @since 2024-07-09
 */
@Data
public class SystemConfigCategoryFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
