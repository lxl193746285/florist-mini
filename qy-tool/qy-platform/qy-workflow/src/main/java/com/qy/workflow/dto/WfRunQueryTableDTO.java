package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 工作流_查询表结构
 *
 * @author iFeng
 * @since 2022-11-24
 */
@Data
public class WfRunQueryTableDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *表单ID 主键
     */
    private Long tableId;

    /**
     *查询名称
     */
    private String name;


    /**
     * 布局方式
     */
    private Integer style;

    /**
     * 列数量
     */
    private Integer columnCount;

    /**
     *列名称集合
     */
    private List<String> columnNames;

    /**
     * 数据集合
     */
    private List<Map<String, Object>> dataList;

    /**
     * 表规则 1表示查询表达式
     */
    private Integer dataRule;

    /**
     *数据Sql
     */
    private String dataSql;

    /**
     * 显示方式 1.直接显示 2.显示按钮链接 wf_def_node_table.show_mode
     */
    private Integer showMode;

    /**
     * 表单类型 1查询表单取wf_def_query_table表配置，2.工作流表单 3.系统开发界面 调用菜单功能  4.IDE设计器功能，5.跳转链接 代码表 wf_node_table
     */
    private Integer tableType;


    /**
     * 表单路径
     */
    private String url;

    /**
     * 表单Body
     */
    private String urlBody;

    private String ideFunId;


    /**
     * 界面Id
     */
    private String ideFormId;

    @ApiModelProperty(value = "办理意见 0-发起, 1-同意, 2-拒绝, 3-退回, 4-作废, 5-撤回, 6-办理, 7-撤销, 8-打回")
    private String dealResult;

}
