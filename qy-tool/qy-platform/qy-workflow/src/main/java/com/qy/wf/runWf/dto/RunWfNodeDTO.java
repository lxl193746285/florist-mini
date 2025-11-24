package com.qy.wf.runWf.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作流_执行_工作流_节点信息
 *
 * @author lxl
 * @since 2024-06-25
 */
@Data
public class RunWfNodeDTO {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 执行工作流ID
     */
    @ApiModelProperty(value = "执行工作流ID")
    private Long runWfId;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     *  当前标记（当前人）
     */
    @ApiModelProperty(value = "")
    private Integer isCurMark;

    /**
     * 节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

    /**
     * 节点状态  wf_node_status  1发起,2办理中3已办理
     */
    @ApiModelProperty(value = "节点状态")
    private Integer curStatus;

    /**
     * 当前节点办理人ID
     */
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeUserId;

    /**
     * 当前节点办理人 发起人发起时间
     */
    @ApiModelProperty(value = "当前节点办理人 发起人发起时间")
    private String curSendTime;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTime;

    /**
     * 当前节点阅知时间
     */
    @ApiModelProperty(value = "当前节点阅知时间")
    private String curReadTime;

    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTime;

    /**
     * 审批结果（1-同意，2拒绝，3-退回，4-作废，5撤回）
     */
    @ApiModelProperty(value = "审批结果")
    private Integer approveResult;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;

    /**
     * 下一执行节点ID
     */
    @ApiModelProperty(value = "下一执行节点ID")
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
     * 下一节点状态
     */
    @ApiModelProperty(value = "下一节点状态")
    private Integer nextNodeStatus;

    /**
     * 上一节点办理人ID
     */
    @ApiModelProperty(value = "上一节点办理人ID")
    private Long preNodeUserId;

    /**
     * 上一执行节点id
     */
    @ApiModelProperty(value = "上一执行节点id")
    private Long preRunNodeId;

    /**
     * 上一节点ID
     */
    @ApiModelProperty(value = "上一节点ID")
    private Long preNodeId;

    /**
     * 上一节点状态
     */
    @ApiModelProperty(value = "上一节点状态")
    private Integer preNodeStatus;


    /**
     * 办理权限（0无1有）
     */
    @ApiModelProperty(value = "办理权限")
    private Integer isCanDeal;
    /**
     * 同意权限（0无1有）
     */
    @ApiModelProperty(value = "同意权限")
    private Integer isCanAgree;
    /**
     * 打回权限（0无1有）
     */
    @ApiModelProperty(value = "打回权限")
    private Integer isRepulse;
    /**
     * 拒绝权限（0无1有）
     */
    @ApiModelProperty(value = "拒绝权限")
    private Integer isCanRefuse;
    /**
     * 退回权限（0无1有）
     */
    @ApiModelProperty(value = "退回权限")
    private Integer isCanReturn;
    /**
     * 作废权限（0无1有）
     */
    @ApiModelProperty(value = "作废权限")
    private Integer isCanInvalid;
    /**
     * 撤回权限（0无1有）
     */
    @ApiModelProperty(value = "撤回权限")
    private Integer isCanWithdraw;
    /**
     * 撤销权限（0无1有）
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanRevoke;

    /**
     * 工作流申请人id
     */
    @ApiModelProperty(value = "工作流申请人id")
    private Long beginUserId;

    /**
     * 工作流状态（1发起,2办理中3结束）
     */
    @ApiModelProperty(value = "工作流状态id")
    private Integer wfStatus;

    /**
     * 当前执行节点ID
     */
    @ApiModelProperty(value = "当前执行节点ID")
    private Long curRunNodeId;

    /**
     * 工作流开始节点ID
     */
    @ApiModelProperty(value = "工作流开始节点ID")
    private Long beginNodeId;

    /**
     * 工作流执行开始节点ID
     */
    @ApiModelProperty(value = "工作流执行开始节点ID")
    private Long beginRunNodeId;


    /**
     * 办理别名
     */
    @ApiModelProperty(value = "办理别名,如果为空默认值为办理")
    private String dealAlias;
    /**
     * 同意别名
     */
    @ApiModelProperty(value = "同意别名,如果为空默认值为同意")
    private String agreeAlias;
    /**
     * 撤销别名
     */
    @ApiModelProperty(value = "撤销别名,如果为空默认值为撤销")
    private String revokeAlias;
    /**
     * 拒绝别名
     */
    @ApiModelProperty(value = "拒绝别名,如果为空默认值为拒绝")
    private String refuseAlias;
    /**
     * 退回别名
     */
    @ApiModelProperty(value = "退回别名,如果为空默认值为退回")
    private String returnAlias;
    /**
     * 作废别名
     */
    @ApiModelProperty(value = "作废别名,如果为空默认值为作废")
    private String invalidAlias;
    /**
     * 撤回别名
     */
    @ApiModelProperty(value = "撤回别名,如果为空默认值为撤回")
    private String withdrawAlias;
    /**
     * 打回别名
     */
    @ApiModelProperty(value = "打回别名,如果为空默认值为打回")
    private String repulseAlias;

    /**
     * 节点办理人（办理人+待办人）
     */
    @ApiModelProperty(value = "节点办理人")
    private String combinedUserIds;

}
