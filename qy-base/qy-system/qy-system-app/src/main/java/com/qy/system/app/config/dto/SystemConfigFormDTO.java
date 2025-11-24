package com.qy.system.app.config.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 配置
 *
 * @author hh
 * @since 2024-07-09
 */
@Data
public class SystemConfigFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 配置类别 关联表qc_config_category
     */
    @ApiModelProperty(value = "配置类别 关联表qc_config_category")
    private Long categoryId;

    /**
     * 分类标识
     */
    @ApiModelProperty(value = "分类标识")
    private String categoryIdentifier;

    /**
     * 配置名称
     */
    @ApiModelProperty(value = "配置名称")
    private String title;

    /**
     * 属性
     */
    @ApiModelProperty(value = "属性")
    private String attribute;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String attributeType;

    /**
     * 配置内容
     */
    @ApiModelProperty(value = "配置内容")
    private String attributeConfig;

    /**
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String value;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
