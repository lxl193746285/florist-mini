package com.qy.wf.runNode.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_节点
 *
 * @author lxl
 * @since 2024-09-12
 */
@Data
public class ReplaceApproverFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 执行流ID
     */
    @NotNull(message = "执行流ID 不能为空")
    @ApiModelProperty(value = "执行流ID")
    private Long runWfId;

    /**
     * 当前执行节点ID
     */
    @NotNull(message = "当前执行节点ID 不能为空")
    @ApiModelProperty(value = "当前执行节点ID")
    private Long curRunNodeId;

    /**
     * 当前节点办理人ID
     */
    @NotNull(message = "当前节点办理人ID 不能为空")
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeUserId;

}
