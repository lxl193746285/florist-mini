package com.qy.wf.nodetable.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点表单
 *
 * @author hh
 * @since 2022-11-19
 */
@Data
public class NodeTableQueryDTO implements Serializable {
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
     * 节点ID
     */
    @ApiModelProperty(value = "节点ID")
    private Long nodeId;
    /**
     * 1查询表单
     */
    @ApiModelProperty(value = "1查询表单")
    private Integer tableType;
    /**
     * 查询表单ID
     */
    @ApiModelProperty(value = "查询表单ID")
    private Long tableId;
    /**
     * 显示时机1：详细使用,2:办理使用
     */
    @ApiModelProperty(value = "显示时机1：详细使用,2:办理使用")
    private Integer showType;
    /**
     * 表单路径
     */
    @ApiModelProperty(value = "表单路径")
    private String tablePath;





}
