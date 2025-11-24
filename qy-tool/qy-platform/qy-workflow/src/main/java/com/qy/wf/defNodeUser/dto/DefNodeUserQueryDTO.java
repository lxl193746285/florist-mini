package com.qy.wf.defNodeUser.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点人员
 *
 * @author syf
 * @since 2022-11-14
 */
@Data
public class DefNodeUserQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id")
    private Long nodeId;

    /**
     * 人员类型（1办理人，2抄送人，3代办人）
     */
    @ApiModelProperty(value = "人员类型")
    private Integer type;

}
