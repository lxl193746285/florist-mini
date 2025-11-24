package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-16
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
     * 节点当前标记
     */
    @ApiModelProperty(value = "节点当前标记")
    private Integer isCurMark;

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
     * 当前节点发起时间
     */
    @ApiModelProperty(value = "当前节点发起时间")
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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
