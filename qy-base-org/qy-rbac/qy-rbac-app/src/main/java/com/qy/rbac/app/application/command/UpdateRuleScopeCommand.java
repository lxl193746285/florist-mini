package com.qy.rbac.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新规则范围命令
 *
 * @author legendjw
 */
@Data
public class UpdateRuleScopeCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 更新id
     */
    @JsonIgnore
    private String updateId;

    /**
     * 新id
     */
    @ApiModelProperty("新id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 是否选择数据 1: 是 0: 否
     */
    @ApiModelProperty("是否选择数据 1: 是 0: 否")
    private Byte isSelectData;

    /**
     * 数据源类型 1: 自定义 2: sql取值
     */
    @ApiModelProperty("数据源类型 1: 自定义 2: sql取值")
    private Byte dataSource = 1;

    /**
     * 数据取值sql
     */
    @ApiModelProperty("数据取值sql")
    private String dataSourceSql = "";

    /**
     * 展示形式 1: 多选框 2: 树形多选框
     */
    @ApiModelProperty("展示形式 1: 多选框 2: 树形多选框")
    private Byte selectShowForm = 1;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
}