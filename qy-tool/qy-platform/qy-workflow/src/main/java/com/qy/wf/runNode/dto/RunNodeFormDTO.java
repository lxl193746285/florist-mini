package com.qy.wf.runNode.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_节点
 *
 * @author wch
 * @since 2022-11-17
 */
@Data
public class RunNodeFormDTO implements Serializable {
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
     * 
     */
    @NotNull(message = " 不能为空")
    @ApiModelProperty(value = "")
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

    /**
     * 当前节点办理人 发起人发起时间
     */
    @NotNull(message = "当前节点办理人 发起人发起时间 不能为空")
    @ApiModelProperty(value = "当前节点办理人 发起人发起时间")
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
    @NotBlank(message = "当前节点审批意见 不能为空")
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;

    /**
     * 下一节点人ID
     */
    @ApiModelProperty(value = "下一节点人ID")
    private Long nextNodeUserId;

    /**
     * 下一节点ID
     */
    @NotNull(message = "下一节点ID 不能为空")
    @ApiModelProperty(value = "下一节点ID")
    private Long nextNodeId;

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

}
