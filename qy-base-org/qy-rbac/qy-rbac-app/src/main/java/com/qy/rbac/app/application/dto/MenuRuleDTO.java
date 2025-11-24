package com.qy.rbac.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单规则
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class MenuRuleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private Long menuId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 规则范围id
     */
    @ApiModelProperty("规则范围id")
    private String scopeId;

    /**
     * 是否选择数据 1: 是 0: 否
     */
    @ApiModelProperty("是否选择数据 1: 是 0: 否")
    private Byte isSelectData;

    /**
     * 数据源类型 1: 自定义 2: sql取值
     */
    @ApiModelProperty("数据源类型 1: 自定义 2: sql取值")
    private Byte dataSource;

    /**
     * 展示形式 1: 多选框 2: 树形多选框
     */
    @ApiModelProperty("展示形式 1: 多选框 2: 树形多选框")
    private Byte selectShowForm;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
}