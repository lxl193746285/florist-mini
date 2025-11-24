package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_节点
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class WfRunNodeDTO  extends ArkBaseDTO implements Serializable {
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
     * 执行流ID
     */
    @ApiModelProperty(value = "执行流ID")
    private Long runWfId;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 自增顺序
     */
    @ApiModelProperty(value = "自增顺序")
    private Integer sort;

    /**
     * 当前标记（当前人）
     */
    @ApiModelProperty(value = "当前标记（当前人）")
    private Integer isCurMark;

    /**
     * 当前状态  wf_node_status  1发起,2办理中3已办理
     */
    @ApiModelProperty(value = "当前状态  wf_node_status  1发起,2办理中3已办理")
    private Integer curStatus;

    /**
     * 当前节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

    /**
     * 当前节点办理人ID
     */
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeUserId;

    /**
     * 当前节点办理人 发起人发起时间
     */
    @ApiModelProperty(value = "当前节点办理人 发起人发起时间")
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
     *  代码表 wf_approve_result 1-同意，2拒绝，3-退回，4-作废，5撤回， 6办理 ,7撤销
     */
    @ApiModelProperty(value = " 代码表 wf_approve_result 1-同意，2拒绝，3-退回，4-作废，5撤回， 6办理 ,7撤销")
    private Integer approveResult;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;

    /**
     * 下一个执行节点ID
     */
    @ApiModelProperty(value = "下一个执行节点ID")
    private Long nextRunNodeId;

    /**
     * 下一节点ID
     */
    @ApiModelProperty(value = "下一节点ID")
    private Long nextNodeId;

    /**
     * 下一节点办理人ID
     */
    @ApiModelProperty(value = "下一节点办理人ID")
    private Long nextNodeUserId;

    /**
     * 下一节点状态 wf_node_status  1发起,2办理中3已办理
     */
    @ApiModelProperty(value = "下一节点状态 wf_node_status  1发起,2办理中3已办理")
    private Integer nextNodeStatus;

    /**
     * 上一个执行节点ID
     */
    @ApiModelProperty(value = "上一个执行节点ID")
    private Long preRunNodeId;

    /**
     * 上一个节点
     */
    @ApiModelProperty(value = "上一个节点")
    private Long preNodeId;

    /**
     * 上一个节点办理人
     */
    @ApiModelProperty(value = "上一个节点办理人")
    private Long preNodeUserId;

    /**
     * 上一个节点状态
     */
    @ApiModelProperty(value = "上一个节点状态")
    private Integer preNodeStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long curNodeUserId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long nextNodeUserId2;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long preNodeUserId2;

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
