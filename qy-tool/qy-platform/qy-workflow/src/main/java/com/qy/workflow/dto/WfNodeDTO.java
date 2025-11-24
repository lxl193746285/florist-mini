package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
public class WfNodeDTO   implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *工作流Id
     */
    @ApiModelProperty(value = "节点名称")
    private String name;

    /**
     *当前节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long nodeId;


}
