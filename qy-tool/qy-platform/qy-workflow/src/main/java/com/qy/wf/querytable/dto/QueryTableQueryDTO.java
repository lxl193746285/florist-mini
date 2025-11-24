package com.qy.wf.querytable.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_查询表单
 *
 * @author hh
 * @since 2022-11-19
 */
@Data
public class QueryTableQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 创建日期-开始(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-开始")
    private String startCreateDate;

    /**
    * 创建日期-结束(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-结束")
    private String endCreateDate;

    /**
    * 1启用0禁用
    */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;


    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;
    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;
    /**
     * 1表布局，2网格布局3标签布局
     */
    @ApiModelProperty(value = "1表布局，2网格布局3标签布局")
    private Integer style;
    /**
     * 网格布局，标签布局显示列数
     */
    @ApiModelProperty(value = "网格布局，标签布局显示列数")
    private Integer columnCount;

    /**
     * 表单类型
     */
    @ApiModelProperty(value = "表单类型 1查询表单 取wf_def_query_table表配置，2.工作流表单 3.系统开发界面 调用菜单功能  4.IDE设计器功能，5.跳转链接  6.调用菜单功能 代码表 wf_node_table ")
    private Integer tableType;

    /**
     * url
     */
    @ApiModelProperty(value = "url")
    private String url;

    /**
     * body体
     */
    @ApiModelProperty(value = "body体")
    private String urlBody;

    /**
     * IDE功能ID
     */
    @ApiModelProperty(value = "IDE功能ID")
    private String ideFunId;

    /**
     * IDE表单ID
     */
    @ApiModelProperty(value = "IDE表单ID")
    private String ideFormId;

}
