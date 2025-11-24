package com.qy.wf.runWf.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流_执行_工作流
 *
 * @author lxl
 * @since 2024-10-16
 */
@Data
public class RWActionFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 执行节点id
     */
    @ApiModelProperty(value = "执行节点id")
    private Long runNodeId;

    /**
     * 执行节点id
     */
    @ApiModelProperty(value = "执行节点id")
    private List<Long> runNodeIds;

    /**
     * 已有的权限按钮
     */
    @ApiModelProperty(value = "已有的权限按钮")
    private List<Action> actions;

}
