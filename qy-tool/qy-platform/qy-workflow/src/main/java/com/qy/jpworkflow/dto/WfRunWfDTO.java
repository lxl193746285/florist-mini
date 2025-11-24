package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_工作流
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class WfRunWfDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 流程类型（wf_def_define_wf_type）
     */
    @ApiModelProperty(value = "流程类型（wf_def_define_wf_type）")
    private Integer wfType;

    /**
     * 工作流编号
     */
    @ApiModelProperty(value = "工作流编号")
    private String wfCode;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    private String wfTitle;

    /**
     * 流程摘要
     */
    @ApiModelProperty(value = "流程摘要")
    private String wfNote;

    /**
     * 1发起,2办理中3结束
     */
    @ApiModelProperty(value = "1发起,2办理中3结束")
    private Integer wfStatus;

    /**
     * 发起执行节点id
     */
    @ApiModelProperty(value = "发起执行节点id")
    private Long beginRunNodeId;

    /**
     * 发起节点id
     */
    @ApiModelProperty(value = "发起节点id")
    private Long beginNodeId;

    /**
     * 流程发起人id
     */
    @ApiModelProperty(value = "流程发起人id")
    private Long beginUserId;

    /**
     * 流程发起部门id
     */
    @ApiModelProperty(value = "流程发起部门id")
    private Long beginDeptId;

    /**
     * 流程开始时间
     */
    @ApiModelProperty(value = "流程开始时间")
    private LocalDateTime beginTime;

    /**
     * 流程结束时间
     */
    @ApiModelProperty(value = "流程结束时间")
    private LocalDateTime endTime;

    /**
     * 当前节点状态 wf_node_status  1发起,2办理中3已办理
     */
    @ApiModelProperty(value = "当前节点状态 wf_node_status  1发起,2办理中3已办理")
    private Integer curStatus;

    /**
     * 当前执行节点ID
     */
    @ApiModelProperty(value = "当前执行节点ID")
    private Long curRunNodeId;

    /**
     * 当前节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

    /**
     * 当前节点办理人发起人
     */
    @ApiModelProperty(value = "当前节点办理人发起人")
    private Long curNodeUserId;

    /**
     * 当前节点状态
     */
    @ApiModelProperty(value = "当前节点状态")
    private Integer curNodeStatus;

    /**
     * 当前节点办理人发起人的发起时间
     */
    @ApiModelProperty(value = "当前节点办理人发起人的发起时间")
    private LocalDateTime curSendTime;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private LocalDateTime curArriveTime;

    /**
     * 当前节点阅知时间
     */
    @ApiModelProperty(value = "当前节点阅知时间")
    private LocalDateTime curReadTime;

    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private LocalDateTime curDealTime;

    /**
     * 当前节点审批结果（1-同意，2拒绝，3-退回，4-作废，5撤回）
     */
    @ApiModelProperty(value = "当前节点审批结果（1-同意，2拒绝，3-退回，4-作废，5撤回）")
    private Integer curApproveResult;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String curApproveComments;

    /**
     * 当前节点审批结果（1-同意，2拒绝，3-退回，4-作废，5撤回）
     */
    @ApiModelProperty(value = "当前节点审批结果（1-同意，2拒绝，3-退回，4-作废，5撤回）")
    private Integer approveResult;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 上一个执行节点ID
     */
    @ApiModelProperty(value = "上一个执行节点ID")
    private Long preRunNodeId;

    /**
     * 上一节点
     */
    @ApiModelProperty(value = "上一节点")
    private Long preNodeId;

    /**
     * 上一节点办理人
     */
    @ApiModelProperty(value = "上一节点办理人")
    private Long preNodeUserId;

    /**
     * 上一个节点状态
     */
    @ApiModelProperty(value = "上一个节点状态")
    private Integer preNodeStatus;

    /**
     * 下一个执行节点ID
     */
    @ApiModelProperty(value = "下一个执行节点ID")
    private Long nextRunNodeId;

    /**
     * 下一个节点
     */
    @ApiModelProperty(value = "下一个节点")
    private Long nextNodeId;

    /**
     * 下一个节点办理人
     */
    @ApiModelProperty(value = "下一个节点办理人")
    private Long nextNodeUserId;

    /**
     * 下一个节点状态
     */
    @ApiModelProperty(value = "下一个节点状态")
    private Integer nextNodeStatus;

    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long beginUserId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long curNodeUserId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long preNodeUserId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long nextNodeUserId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long creatorId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long updatorId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long deletorId2;

}
