package com.qy.wf.runWf.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作流执行详情-基本信息
 */
@Data
public class RunWfDetail {
    /**
     * 流程类型（wf_wf_type 1请假，2报销）
     */
    @ApiModelProperty(value = "流程类型（wf_wf_type 1请假，2报销）")
    private Integer wfType;
    /**
     * 流程类型（wf_wf_type 1请假，2报销）
     */
    @ApiModelProperty(value = "流程类型（wf_wf_type 1请假，2报销）")
    private String wfTypeName;
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
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private Long wfRunId;
    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流名称")
    private String wfName;
    /**
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    private String wfTitle;
    /**
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private Long beginUserId;
    /**
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private String beginUserName;
    /**
     * 流程开始时间
     */
    @ApiModelProperty(value = "流程开始时间")
    private String beginTime;

    /**
     * 1发起,2办理中3结束
     */
    @ApiModelProperty(value = "1发起,2办理中3结束")
    private Integer wfStatus;

    /**
     * 1发起,2办理中3结束
     */
    @ApiModelProperty(value = "1发起,2办理中3结束")
    private String wfStatusName;

    /**
     * 流程摘要
     */
    @ApiModelProperty(value = "流程摘要")
    private String wfNote;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer approveResult;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private String approveResultName;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;
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
    private Long preNodeStatus;
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
    private Long nextNodeStatus;

    /**
     * 当前执行节点ID
     */
    @ApiModelProperty(value = "当前执行节点ID")
    private Long curRunNodeId;
    /**
     * 操作权限
     */
    private List<Action> actions = new ArrayList<>();
}
