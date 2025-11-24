package com.qy.wf.querytable.dto;

import com.qy.common.dto.ArkBaseDTO;
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
public class QueryTableDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    private String name;

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
     * 1表布局，2网格布局3标签布局
     */
    @ApiModelProperty(value = "1表布局，2网格布局3标签布局")
    private String styleName;

    /**
     * 网格布局，标签布局显示列数
     */
    @ApiModelProperty(value = "网格布局，标签布局显示列数")
    private Integer columnCount;

    /**
     * 1SQL表达式
     */
    @ApiModelProperty(value = "1SQL表达式")
    private Integer dataRule;

    @ApiModelProperty(value = "数据类型：1、SQL表达式")
    private String dataRuleName;

    /**
     * 执行SQL
     */
    @ApiModelProperty(value = "执行SQL")
    private String dataSql;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;
    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private String statusName;

    /**
     * 表单类型
     */
    @ApiModelProperty(value = "表单类型 1查询表单 取wf_def_query_table表配置，2.工作流表单 3.系统开发界面 调用菜单功能  4.IDE设计器功能，5.跳转链接  6.调用菜单功能 代码表 wf_node_table ")
    private Integer tableType;

    /**
     * 表单类型
     */
    @ApiModelProperty(value = "表单类型")
    private String tableTypeName;

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
