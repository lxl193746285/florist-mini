package com.qy.wf.defNode.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点
 *
 * @author syf
 * @since 2022-11-14
 */
@Data
public class DefNodeQueryDTO implements Serializable {
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
    * 节点名称 显示在设计图上
    */
    @ApiModelProperty(value = "节点名称 显示在设计图上")
    private String name;

    /**
    * 1启用0禁用
    */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

}
