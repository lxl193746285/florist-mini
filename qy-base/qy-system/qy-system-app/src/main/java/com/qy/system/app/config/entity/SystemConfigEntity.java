package com.qy.system.app.config.entity;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置
 *
 * @author hh
 * @since 2024-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_system_config")
public class SystemConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
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
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long creatorId;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatorId;
    /**
     * 删除标记(0:未删除；1:已删除)
     */
    @ApiModelProperty(value = "删除标记(0:未删除；1:已删除)")
    private Integer isDeleted;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deletorId;
}
