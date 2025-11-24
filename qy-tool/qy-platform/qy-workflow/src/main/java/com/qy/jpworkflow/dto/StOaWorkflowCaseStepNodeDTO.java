package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发起工作流处理节点
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class StOaWorkflowCaseStepNodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 流程步骤id
     */
    @ApiModelProperty(value = "流程步骤id")
    private Integer caseStepId;

    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id")
    private Integer nodeId;

    /**
     * 动作(0:处理,1:提交,2:回退,3:撤回,4:作废)
     */
    @ApiModelProperty(value = "动作(0:处理,1:提交,2:回退,3:撤回,4:作废)")
    private Byte action;

    /**
     * 状态(0:未处理,1:已处理)
     */
    @ApiModelProperty(value = "状态(0:未处理,1:已处理)")
    private Byte status;

    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private Byte isRead;

    /**
     * 阅读时间
     */
    @ApiModelProperty(value = "阅读时间")
    private Integer readTime;

    /**
     * 是否同意（0：不同意，1：同意）
     */
    @ApiModelProperty(value = "是否同意（0：不同意，1：同意）")
    private Byte isAgree;

    /**
     * 意见
     */
    @ApiModelProperty(value = "意见")
    private String opinion;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private Integer handleTime;

    /**
     * 处理人
     */
    @ApiModelProperty(value = "处理人")
    private Integer handleUserId;

    private Integer creatorId;
    private Integer createTime;

}
