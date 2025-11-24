package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
public class WfCurrentNodeDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *工作流Id
     */
    @NotNull(message = "工作流Id")
    @ApiModelProperty(value = "工作流Id")
    private Long wfId;


    /**
     *当前节点ID
     */
    @NotNull(message = "当前节点ID")
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

}
