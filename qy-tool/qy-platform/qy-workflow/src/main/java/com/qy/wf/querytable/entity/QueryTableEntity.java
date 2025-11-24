package com.qy.wf.querytable.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_设计_查询表单
 *
 * @author hh
 * @since 2022-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_def_query_table")
public class QueryTableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * 网格布局，标签布局显示列数
     */
    @ApiModelProperty(value = "网格布局，标签布局显示列数")
    private Integer columnCount;
    /**
     * 1SQL表达式
     */
    @ApiModelProperty(value = "1SQL表达式")
    private Integer dataRule;
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
     * 是否删除0.未删除1.已删除
     */
    @ApiModelProperty(value = "是否删除0.未删除1.已删除")
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
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String creatorName;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updatorName;
    /**
     * 删除者
     */
    @ApiModelProperty(value = "删除者")
    private String deletorName;


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
