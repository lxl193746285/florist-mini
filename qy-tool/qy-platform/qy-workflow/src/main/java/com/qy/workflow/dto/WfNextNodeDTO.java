package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流_执行_办理节点和办理人
 *
 * @author iFeng
 * @since 2022-11-23
 */
@Data
public class WfNextNodeDTO   implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *工作流Id
     */
    @ApiModelProperty(value = "工作流Id")
    private Long wfId;


    /**
     *当前节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long nodeId;


    /**
     * 节点类型
     */
    @ApiModelProperty(value = "1开始，2办理，3结束")
    private  Integer nodeType;
    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    /**
     * 节点办理人
     */
    @ApiModelProperty(value = "节点办理人")
    private List<WfNodeUserDTO> users;

}
