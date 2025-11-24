package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流节点扭转
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class StOaWorkflowNodeReverseDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id")
    private Integer nodeId;

    /**
     * 下一节点id
     */
    @ApiModelProperty(value = "下一节点id")
    private Integer nextNodeId;

    /**
     * 条件类型 1:直接流转，2: sql
     */
    @ApiModelProperty(value = "条件类型 1:直接流转，2: sql")
    private Byte conditionType;

    /**
     * 转换条件
     */
    @ApiModelProperty(value = "转换条件")
    private String reverseCondition;

}
