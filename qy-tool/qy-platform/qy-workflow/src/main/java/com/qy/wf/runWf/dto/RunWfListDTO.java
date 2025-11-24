package com.qy.wf.runWf.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作流_执行_工作流
 *
 * @author wch
 * @since 2022-11-17
 */
@Data
public class RunWfListDTO  {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司id")
    private Long companyId;
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 流程类型（wf_def_define_wf_type）
     */
    @ApiModelProperty(value = "流程类型")
    private Integer wfType;
    @ApiModelProperty(value = "流程类型")
    private String wfTypeName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

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
    @ApiModelProperty(value = "工作流名称")
    private String wfName;

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
     * 工作流状态（1发起,2办理中3结束）
     */
    @ApiModelProperty(value = "工作流状态id")
    private Integer wfStatus;
    @ApiModelProperty(value = "工作流状态名称")
    private String wfStatusName;

    /**
     * 流程发起人id
     */
    @ApiModelProperty(value = "流程发起人id")
    private Long beginUserId;
    @ApiModelProperty(value = "流程发起人名称")
    private String beginUserName;

    /**
     * 流程发起部门id
     */
    @ApiModelProperty(value = "流程发起部门id")
    private Long beginDeptId;
    @ApiModelProperty(value = "流程发起部门名称")
    private String beginDeptName;

    /**
     * 流程发起时间
     */
    @ApiModelProperty(value = "流程发起时间")
    private LocalDateTime beginTime;
    @ApiModelProperty(value = "流程发起时间")
    private String beginTimeName;

    /**
     * 流程结束时间
     */
    @ApiModelProperty(value = "流程结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "流程结束时间")
    private String endTimeName;



    /**
     * 上一节点办理人
     */
    @ApiModelProperty(value = "上一节点办理人")
    private Long perNodeUserId;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 状态（1启用0禁用）
     */
    @ApiModelProperty(value = "状态id")
    private Integer status;
    @ApiModelProperty(value = "状态名称")
    private String statusName;

    /**
     * 当前节点审批结果（1-同意，2拒绝，3-退回，4-作废，5撤回）
     */
    @ApiModelProperty(value = "当前节点审批结果")
    private Integer curApproveResult;
    @ApiModelProperty(value = "当前节点审批结果名称")
    private String curApproveResultName;

    /**
     * 当前节点审批意见
     */
    @ApiModelProperty(value = "当前节点审批意见")
    private String curApproveComments;



    /**
     * 上一个执行节点ID
     */
    @ApiModelProperty(value = "上一个执行节点ID")
    private Long wfPreRunNodeId;
    /**
     * 上一节点
     */
    @ApiModelProperty(value = "上一节点")
    private Long wfPreNodeId;
    /**
     * 上一节点办理人
     */
    @ApiModelProperty(value = "上一节点办理人")
    private Long wfPreNodeUserId;
    /**
     * 上一个节点状态
     */
    @ApiModelProperty(value = "上一个节点状态")
    private Long wfPreNodeStatus;
    /**
     * 下一个执行节点ID
     */
    @ApiModelProperty(value = "下一个执行节点ID")
    private Long wfNextRunNodeId;
    /**
     * 下一个节点
     */
    @ApiModelProperty(value = "下一个节点")
    private Long wfNextNodeId;
    /**
     * 下一个节点办理人
     */
    @ApiModelProperty(value = "下一个节点办理人")
    private Long wfNextNodeUserId;
    /**
     * 下一个节点状态
     */
    @ApiModelProperty(value = "下一个节点状态")
    private Long wfNextNodeStatus;

    /******************************************子表数据***************************************************/

    /**
     * 子表id
     */
    @ApiModelProperty(value = "子表id")
    private Long wfRunNodeId;

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
     * 当前节点办理人发起人
     */
    @ApiModelProperty(value = "当前节点办理人发起人")
    private Long curNodeUserId;

    /**
     * 当前节点办理人发起人
     */
    @ApiModelProperty(value = "当前节点办理人发起人")
    private String curNodeUserName;


    /**
     * 当前节点办理人发起人的发起时间
     */
    @ApiModelProperty(value = "当前节点办理人发起人的发起时间")
    private LocalDateTime curSendTime;
    /**
     * 当前节点办理人发起人的发起时间
     */
    @ApiModelProperty(value = "当前节点办理人发起人的发起时间")
    private String curSendTimeName;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private LocalDateTime curArriveTime;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTimeName;

    /**
     * 当前节点阅知时间
     */
    @ApiModelProperty(value = "当前节点阅知时间")
    private LocalDateTime curReadTime;

    /**
     * 当前节点阅知时间
     */
    @ApiModelProperty(value = "当前节点阅知时间")
    private String curReadTimeName;

    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private LocalDateTime curDealTime;

    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTimeName;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer approveResult;
    @ApiModelProperty(value = "1-同意，2拒绝，3-退回，4-作废，5撤回")
    private String approveResultName;

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
     * 下一节点ID
     */
    @ApiModelProperty(value = "下一节点名称")
    private String nextNodeName;

    /**
     * 当前标记（当前人）
     */
    @ApiModelProperty(value = "")
    private Integer isCurMark;

    /**
     * 下一节点状态 wf_node_status  1发起,2办理中3已办理
     */
    @ApiModelProperty(value = "下一节点状态")
    private Integer nextNodeStatus;
    @ApiModelProperty(value = "下一节点状态名称")
    private String nextNodeStatusName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTimeName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String updateTimeName;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String creatorName;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long creatorId;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String updatorName;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long updatorId;




    /**
     * 操作权限
     */
    private List<Action> actions = new ArrayList<>();

    /**
     * 办理权限  0无1有
     */
    private Integer isCanDeal;


    /**
     * 同意权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanAgree;
    /**
     * 打回权限,0无1有
     */
    @ApiModelProperty(value = "打回权限,0无1有")
    private Integer isRepulse;
    /**
     * 拒绝权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanRefuse;
    /**
     * 退回权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanReturn;
    /**
     * 作废权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanInvalid;
    /**
     * 撤回权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanWithdraw;

    /**
     * 撤销权限 0无1有
     */
    @ApiModelProperty(value = "0无1有")
    private Integer isCanRevoke;

}
