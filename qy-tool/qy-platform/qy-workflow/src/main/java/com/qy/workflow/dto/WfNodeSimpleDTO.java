package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点
 *
 * @author iFeng
 * @since 2023-08-16
 */
@Data
public class WfNodeSimpleDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 节点名称 显示在设计图上
     */
    @ApiModelProperty(value = "节点名称 显示在设计图上")
    private String name;
}
