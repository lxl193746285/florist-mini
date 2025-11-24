package com.qy.wf.runNode.dto;

import com.qy.message.api.dto.MessageLogDTO;
import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程
 */
@Data
public class RunNodeDetail {


    /**
     * 当前节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;
    /**
     * 当前id
     */
    @ApiModelProperty(value = "当前id")
    private Long wfRunNodeId;
    /**
     * 当前节点名称
     */
    @ApiModelProperty(value = "当前节点名称")
    private String curNodeName;

    /**
     * 当前节点办理人ID
     */
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeUserId;

    /**
     * 当前节点办理人
     */
    @ApiModelProperty(value = "当前节点办理人")
    private String curNodeUserName;

    /**
     * 当前标记（0 不是当前 1是当前）
     */
    @ApiModelProperty(value = "当前标记（0 不是 1是）")
    private Integer isCurMark;

    /**
     * 当前标记（）
     */
    @ApiModelProperty(value = "当前标记（0 不是 1是）")
    private String isCurMarkName;

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
     * 当前节点办理人 发起人发起时间
     */
    @ApiModelProperty(value = "当前节点办理人 发起人发起时间")
    private String curSendTime;

    /**
     * 审批结果：1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer approveResult;

    /**
     * 审批结果：1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private String approveResultName;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String approveComments;

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
     * 停留时长
     */
    @ApiModelProperty(value = "停留时长")
    private String stayTime;

    /**
     * 下一节点状态（0.未办理 1.办理中 2.已办理）
     */
    @ApiModelProperty(value = "下一节点状态（0.未办理 1.办理中 2.已办理）")
    private Integer nextNodeStatus;
    /**
     * 下一节点状态（0.未办理 1.办理中 2.已办理）
     */
    @ApiModelProperty(value = "下一节点状态（0.未办理 1.办理中 2.已办理）")
    private String nextNodeStatusName;
    /**
     * 操作权限
     */
    private List<Action> actions = new ArrayList<>();
    /**
     * 消息推送list
     */
    List<MessageLogDTO> messageSendList;
}
