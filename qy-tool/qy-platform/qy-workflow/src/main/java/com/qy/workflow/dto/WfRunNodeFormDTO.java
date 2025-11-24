package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
public class WfRunNodeFormDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司
     */
    @NotNull(message = "公司 不能为空")
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 执行流ID
     */
    @NotNull(message = "执行流ID 不能为空")
    @ApiModelProperty(value = "执行流ID")
    private Long runWfId;

    /**
     * 工作流ID
     */
    @NotNull(message = "工作流ID 不能为空")
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 自增顺序
     */
    @NotNull(message = "自增顺序 不能为空")
    @ApiModelProperty(value = "自增顺序")
    private Integer sort;

    /**
     * 节点当前标记
     */
    @ApiModelProperty(value = "节点当前标记")
    private Integer isCurMark;

    /**
     * 当前节点ID
     */
    @NotNull(message = "当前节点ID 不能为空")
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

    /**
     * 当前节点办理人ID
     */
    @NotNull(message = "当前节点办理人ID 不能为空")
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeUserId;


    @NotNull(message = "当前节点发起时间 不能为空")
    @ApiModelProperty(value = "当前节点办理人 起人发起时间")
    private LocalDateTime curSendTime;

    /**
     * 当前节点到达时间
     */
    @NotNull(message = "当前节点到达时间 不能为空")
    @ApiModelProperty(value = "当前节点到达时间")
    private LocalDateTime curArriveTime;


    /**
     * 当前节点阅知时间
     */
    @NotNull(message = "当前节点阅知时间 不能为空")
    @ApiModelProperty(value = "当前节点阅知时间")
    private LocalDateTime curReadTime;

    /**
     * 当前节点办理时间
     */
    @NotNull(message = "当前节点办理时间 不能为空")
    @ApiModelProperty(value = "当前节点办理时间")
    private LocalDateTime curDealTime;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @NotNull(message = "1-同意，2拒绝，3-退回，4-作废，5撤回 不能为空")
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer approveResult;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;



    /**
     * 下一节点ID
     */
    @ApiModelProperty(value = "下一节点ID")
    private Long nextNodeId;

    /**
     * 下一节点办理人
     */
    @ApiModelProperty(value = "下一节点办理人")
    private Long nextNodeUserId;


    /**
     * 下一节点办理状态
     */
    @ApiModelProperty(value = "下一节点办理状态")
    private Integer nextNodeStatus;

    /**
     * 备注
     */
    @NotBlank(message = "备注 不能为空")
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @NotNull(message = "1启用0禁用 不能为空")
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

    /**
     * 当前节点状态
     */
    @ApiModelProperty(value = "（0.未办理 1.办理中 2.已办理,3已发起）")
    private Integer curStatus;
    /**
     * 删除标记
     */
    @ApiModelProperty(value = "0未删除1删除")
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
    private Long deletor_id;

    /**
     * 上一执行节点ID
     */
    @ApiModelProperty(value = "上一执行节点ID")
    private Long preRunNodeId;

    /**
     * 上一节点ID
     */
    @ApiModelProperty(value = "上一节点ID")
    private Long preNodeId;

    /**
     * 上一执行节点办理人ID
     */
    @ApiModelProperty(value = "上一执行节点办理人ID")
    private Long preNodeUserId;

    /**
     * 上一节点状态
     */
    @ApiModelProperty(value = "上一节点状态")
    private Integer preNodeStatus;

    /**
     * 发起执行节点id
     */
    @ApiModelProperty(value = "发起执行节点id")
    private Long beginRunNodeId;
}
