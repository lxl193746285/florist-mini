package com.qy.wf.runWf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_工作流
 *
 * @author wch
 * @since 2022-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_run_wf")
public class RunWfEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;
    /**
     * 流程类型（wf_wf_type 1请假，2报销）
     */
    @ApiModelProperty(value = "流程类型（wf_wf_type 1请假，2报销）")
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
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private Long beginUserId;    /**
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private Long beginNodeId;
    /**
     * 流程发起部门
     */
    @ApiModelProperty(value = "流程发起部门")
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
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer approveResult;
    /**
     * 当前节点审批意见
     */
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
    @ApiModelProperty(value = "备注")
    private String note;
    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long creatorId;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatorId;
    /**
     * 是否删除0.未删除1.已删除
     */
    @ApiModelProperty(value = "是否删除0.未删除1.已删除")
    private Integer isDeleted;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deletorId;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String creatorName;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updatorName;
    /**
     * 删除者
     */
    @ApiModelProperty(value = "删除者")
    private String deletorName;
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


}
