package com.qy.wf.runWf.dto;

import com.qy.common.dto.ArkBaseDTO;
import com.qy.wf.runNode.dto.RunNodeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 工作流_执行_工作流
 *
 * @author wch
 * @since 2022-11-17
 */
@Data
public class RunWfDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 当前节点状态（wf_node_status  1发起,2办理中3已办理）
     */
    @ApiModelProperty(value = "当前节点状态")
    private Integer curStatus;

    /**
     * 当前执行节点ID
     */
    @ApiModelProperty(value = "当前执行节点ID")
    private Long curRunNodeId;

    /**
     * 当前节点
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;
    @ApiModelProperty(value = "当前节点名称")
    private String curNodeName;

    /**
     * 当前节点办理人发起人
     */
    @ApiModelProperty(value = "当前节点办理人发起人")
    private Long curNodeUserId;
    @ApiModelProperty(value = "当前节点办理人发起人")
    private String curNodeUserName;

    /**
     * 当前节点状态
     */
    @ApiModelProperty(value = "当前节点状态")
    private Long curNodeStatus;
    @ApiModelProperty(value = "当前节点状态名称")
    private String curNodeStatusName;

    /**
     * 当前节点办理人发起人的发起时间
     */
    @ApiModelProperty(value = "当前节点办理人发起人的发起时间")
    private LocalDateTime curSendTime;
    @ApiModelProperty(value = "当前节点办理人发起人的发起时间")
    private String curSendTimeName;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private LocalDateTime curArriveTime;
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTimeName;

    /**
     * 当前节点阅知时间
     */
    @ApiModelProperty(value = "当前节点阅知时间")
    private LocalDateTime curReadTime;
    @ApiModelProperty(value = "当前节点阅知时间")
    private String curReadTimeName;

    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private LocalDateTime curDealTime;
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTimeName;

    /**
     * 当前节点审批结果（1-同意，2拒绝，3-退回，4-作废，5撤回）
     */
    @ApiModelProperty(value = "当前节点审批结果")
    private Integer approveResult;
    @ApiModelProperty(value = "当前节点审批结果")
    private String approveResultName;

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
     * 状态（1启用0禁用）
     */
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "状态名称")
    private String statusName;

    /**
     * 上一个执行节点ID
     */
    @ApiModelProperty(value = "上一个执行节点ID")
    private Long preRunNodeId;
    /**
     * 上一节点id
     */
    @ApiModelProperty(value = "上一节点id")
    private Long preNodeId;
    /**
     * 上一节点办理人id
     */
    @ApiModelProperty(value = "上一节点办理人id")
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


    private Long wfRunNodeId;

    /**
     * 工作流执行节点数组
     */
    private List<RunNodeDTO> runNodeDTOList;

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

    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;
    @ApiModelProperty(value = "删除标识")
    private String deleteTimeName;


//    /**
//     * 创建人
//     */
//    @ApiModelProperty(value = "创建人")
//    private Long creatorId;
//    /**
//     * 创建人
//     */
//    @ApiModelProperty(value = "创建人")
//    private Long updatorId;

}
