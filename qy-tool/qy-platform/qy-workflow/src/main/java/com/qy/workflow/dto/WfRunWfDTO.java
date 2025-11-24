package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_工作流
 *
 * @author iFeng
 * @since 2022-11-16
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


    @ApiModelProperty(value = "流程发起节点")
    private Long beginNodeId;

    /**
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private Long beginUserId;


    @ApiModelProperty(value = "上一节点")
    private Long preNodeId;

    @ApiModelProperty(value = "上一节点办理人")
    private Long preNodeUserId;


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
     * 当前节点发起时间
     */
    @ApiModelProperty(value = "当前节点办理人 起人发起时间")
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
    private Long approveResult;

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

}
