package com.qy.workflow.api.dto;

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
//    @NotNull(message = "执行节点id 不能为空")
    @ApiModelProperty(value = "执行节点id")
    private Long runNodeId;

    /**
     * 执行节点ids
     */
//    @NotNull(message = "执行节点id 不能为空")
    @ApiModelProperty(value = "执行节点ids")
    private List<Long> runNodeIds;

    /**
     * 已有的权限按钮（不传则内部取值）
     */
    @ApiModelProperty(value = "已有的权限按钮")
    private List<Action> actions;

}
