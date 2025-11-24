package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发起工作流流转顺序
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class StOaWorkflowCaseStepDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id")
    private Integer workflowCaseId;

    /**
     * 当前步骤
     */
    @ApiModelProperty(value = "当前步骤")
    private Integer step;

    /**
     * 状态(0:未处理,1.已处理,)
     */
    @ApiModelProperty(value = "状态(0:未处理,1.已处理,)")
    private Byte status;

    private Integer creatorId;
    private Integer createTime;
}
