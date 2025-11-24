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
 * 配置类别
 *
 * @author hh
 * @since 2024-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_system_config_category")
public class SystemConfigCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配置类别ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
