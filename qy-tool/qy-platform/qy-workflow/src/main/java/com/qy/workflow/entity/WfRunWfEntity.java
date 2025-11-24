package com.qy.workflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_工作流
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_run_wf")
public class WfRunWfEntity implements Serializable {
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
     * 流程类型
     */
    @ApiModelProperty(value = "流程类型")
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
     * 
     */
    @ApiModelProperty(value = "")
    private Integer wfStatus;

    @ApiModelProperty(value = "当前执行节点ID")
    private Long curRunNodeId;

    @ApiModelProperty(value = "上一个执行节点ID")
    private Long preRunNodeId;

    @ApiModelProperty(value = "上一节点")
    private Long preNodeId;

    @ApiModelProperty(value = "上一节点办理人")
    private Long preNodeUserId;
    private Integer preNodeStatus;

    /**
     * 发起执行节点id
     */
    @ApiModelProperty(value = "发起执行节点id")
    private Long beginRunNodeId;

    /**
     * 流程发起节点
     */
    @ApiModelProperty(value = "流程发起节点")
    private Long beginNodeId;

    /**
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private Long beginUserId;
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
     * 当前节点状态
     */
    @ApiModelProperty(value = "当前节点状态")
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
     * 当前节点发送时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "当前节点发送时间")
    private LocalDateTime curSendTime;


    /**
     * 当前节点到达时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "当前节点到达时间")
    private LocalDateTime curArriveTime;
    /**
     * 当前节点阅知时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "当前节点阅知时间")
    private LocalDateTime curReadTime;
    /**
     * 当前节点办理时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "当前节点办理时间")
    private LocalDateTime curDealTime;
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
}
