package com.qy.rbac.api.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 规则范围DTO
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class RuleScopeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
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
    private Byte dataSource;

    /**
     * 数据取值sql
     */
    @ApiModelProperty("数据取值sql")
    private String dataSourceSql;

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

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTimeName;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}