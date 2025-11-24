package com.qy.wf.runWf.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_工作流
 *
 * @author wch
 * @since 2022-11-17
 */
@Data
public class RunWfFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司
     */
//    @NotNull(message = "公司 不能为空")
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 流程类型（wf_wf_type 1请假，2报销）
     */
//    @NotNull(message = "流程类型（wf_wf_type 1请假，2报销） 不能为空")
    @ApiModelProperty(value = "流程类型（wf_wf_type 1请假，2报销）")
    private Integer wfType;

    /**
     * 工作流编号
     */
    @NotBlank(message = "工作流编号 不能为空")
    @ApiModelProperty(value = "工作流编号")
    private String wfCode;

    /**
     * 工作流ID
     */
    @NotNull(message = "工作流ID 不能为空")
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 流程标题
     */
    @NotBlank(message = "流程标题 不能为空")
    @ApiModelProperty(value = "流程标题")
    private String wfTitle;

    /**
     * 流程摘要
     */
    @NotBlank(message = "流程摘要 不能为空")
    @ApiModelProperty(value = "流程摘要")
    private String wfNote;

    /**
     * 1发起,2办理中3结束
     */
    @NotNull(message = "1发起,2办理中3结束 不能为空")
    @ApiModelProperty(value = "1发起,2办理中3结束")
    private Integer wfStatus;

    /**
     * 流程发起人
     */
    @NotNull(message = "流程发起人 不能为空")
    @ApiModelProperty(value = "流程发起人")
    private Long beginUserId;

    /**
     * 流程发起部门
     */
    @NotNull(message = "流程发起部门 不能为空")
    @ApiModelProperty(value = "流程发起部门")
    private Long beginDeptId;

    /**
     * 流程开始时间
     */
    @NotNull(message = "流程开始时间 不能为空")
    @ApiModelProperty(value = "流程开始时间")
    private LocalDateTime beginTime;

    /**
     * 流程结束时间
     */
//    @NotNull(message = "流程结束时间 不能为空")
    @ApiModelProperty(value = "流程结束时间")
    private LocalDateTime endTime;

    /**
     * 当前节点ID
     */
//    @NotNull(message = "当前节点ID 不能为空")
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

    /**
     * 当前节点办理人发起人
     */
//    @NotNull(message = "当前节点办理人发起人 不能为空")
    @ApiModelProperty(value = "当前节点办理人发起人")
    private Long curNodeUserId;

    /**
     * 当前节点办理人发起人的发起时间
     */
//    @NotNull(message = "当前节点办理人发起人的发起时间 不能为空")
    @ApiModelProperty(value = "当前节点办理人发起人的发起时间")
    private LocalDateTime curSendTime;

    /**
     * 当前节点到达时间
     */
//    @NotNull(message = "当前节点到达时间 不能为空")
    @ApiModelProperty(value = "当前节点到达时间")
    private LocalDateTime curArriveTime;

    /**
     * 当前节点阅知时间
     */
//    @NotNull(message = "当前节点阅知时间 不能为空")
    @ApiModelProperty(value = "当前节点阅知时间")
    private LocalDateTime curReadTime;

    /**
     * 当前节点办理时间
     */
//    @NotNull(message = "当前节点办理时间 不能为空")
    @ApiModelProperty(value = "当前节点办理时间")
    private LocalDateTime curDealTime;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
//    @NotNull(message = "1-同意，2拒绝，3-退回，4-作废，5撤回 不能为空")
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer approveResult;

    /**
     * 当前节点审批意见
     */
//    @NotBlank(message = "当前节点审批意见 不能为空")
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;

    /**
     * 当前状态（0.未办理 1.办理中 2.已办理）
     */
    @ApiModelProperty(value = "当前状态（0.未办理 1.办理中 2.已办理 3.已发起 4.撤回 5.作废）")
    private Integer curStatus;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer curApproveResult;
    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String curApproveComments;

    /**
     * 备注
     */
//    @NotBlank(message = "备注 不能为空")
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
//    @NotNull(message = "1启用0禁用 不能为空")
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
