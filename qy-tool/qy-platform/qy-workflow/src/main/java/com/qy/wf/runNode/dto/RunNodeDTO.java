package com.qy.wf.runNode.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_执行_节点
 *
 * @author wch
 * @since 2022-11-17
 */
@Data
public class RunNodeDTO  extends ArkBaseDTO implements Serializable {
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
     * 公司
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

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
     * 
     */
    @ApiModelProperty(value = "")
    private Integer isCurMark;

    /**
     * 当前节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

    /**
     * 当前节点ID
     */
    @ApiModelProperty(value = "当前节点名称")
    private String curNodeName;
    /**
     * 当前状态（0.未办理 1.办理中 2.已办理）
     */
    @ApiModelProperty(value = "当前状态（0.未办理 1.办理中 2.已办理）")
    private Integer curStatus;

    /**
     * 当前状态（0.未办理 1.办理中 2.已办理）
     */
    @ApiModelProperty(value = "当前状态（0.未办理 1.办理中 2.已办理）")
    private String curStatusName;
    /**
     * 当前节点办理人ID
     */
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeUserId;

    /**
     * 当前节点办理人ID
     */
    @ApiModelProperty(value = "当前节点办理人ID")
    private String curNodeUserName;

    /**
     * 当前节点办理人 发起人发起时间
     */
    @ApiModelProperty(value = "当前节点办理人 发起人发起时间")
    private String curSendTime;
    @ApiModelProperty(value = "当前节点办理人 发起人发起时间")
    private String curSendTimeName;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTime;
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTimeName;

    /**
     * 当前节点阅知时间
     */
    @ApiModelProperty(value = "当前节点阅知时间")
    private String curReadTime;
    @ApiModelProperty(value = "当前节点阅知时间")
    private String curReadTimeName;
    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTime;
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTimeName;

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
     * 下一节点人ID
     */
    @ApiModelProperty(value = "下一节点人ID")
    private Long nextNodeUserId;

    /**
     * 下一节点ID
     */
    @ApiModelProperty(value = "下一节点ID")
    private Long nextNodeId;

    /**
     * 下一节点ID
     */
    @ApiModelProperty(value = "下一节点名称")
    private String nextNodeName;

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
