package com.qy.wf.runWf.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流_执行_工作流
 *
 * @author wch
 * @since 2022-11-17
 */
@Data
public class RunWfQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 创建日期-开始(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-开始")
    private String startCreateDate;

    /**
    * 创建日期-结束(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-结束")
    private String endCreateDate;

    /**
    * 1启用0禁用
    */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

    @ApiModelProperty(value = "工作流id")
    private Long wfId;
    /**
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    private String wfTitle;

    /**
     * 工作流编号
     */
    @ApiModelProperty(value = "工作流编号")
    private String wfCode;

    /**
     * 流程类型（wf_wf_type 1请假，2报销）
     */
    @ApiModelProperty(value = "流程类型（wf_wf_type 1请假，2报销）")
    private Integer wfType;

    /**
     * 1发起,2办理中3结束
     */
    @ApiModelProperty(value = "流程状态： 1发起,2办理中3结束")
    private Integer wfStatus;

    /**
     * 1-同意，2拒绝，3-退回，4-作废，5撤回
     */
    @ApiModelProperty(value = "审批结果：1-同意，2拒绝，3-退回，4-作废，5撤回")
    private Integer approveResult;

    /**
     * 流程开始时间
     */
    @ApiModelProperty(value = "流程开始时间起")
    private String beginTimeStart;

    /**
     * 流程开始时间
     */
    @ApiModelProperty(value = "流程开始时间止")
    private String beginTimeEnd;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTimeStart;

    /**
     * 当前节点到达时间
     */
    @ApiModelProperty(value = "当前节点到达时间")
    private String curArriveTimeEnd;

    /**
     * 流程结束时间
     */
    @ApiModelProperty(value = "流程结束时间")
    private String endTimeStrat;

    /**
     * 流程结束时间
     */
    @ApiModelProperty(value = "流程结束时间")
    private String endTimeEnd;

    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTimeStart;

    /**
     * 当前节点办理时间
     */
    @ApiModelProperty(value = "当前节点办理时间")
    private String curDealTimeEnd;
//    /**
//     * 流程发起时间
//     */
//    @ApiModelProperty(value = "流程发起时间")
//    private String curSendTimeStart;
//
//    /**
//     * 流程发起时间
//     */
//    @ApiModelProperty(value = "流程发起时间")
//    private String curSendTimeEnd;

    /**
     * 流程发起人ID
     */
    @ApiModelProperty(value = "流程发起人ID")
    private Long beginUserId;

    /**
     * 当前节点办理人ID
     */
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeUserId;
    /**
     * 当前节点办理人ID
     */
    @ApiModelProperty(value = "当前节点办理人ID")
    private Long curNodeId;

    /**
     * 节点状态（0.未办理 1.办理中 2.已办理,3已发起,4.撤回）
     */
    @ApiModelProperty(value = "节点状态（0.未办理 1.办理中 2.已办理,3已发起,4.撤回）")
    private Integer curStatus;

    @ApiModelProperty(value = "快捷查询：1：待处理，2：已处理 3：已发起 4：我收到的")
    private Integer quickSearch;

    private String keywords;

    private Long creatorId;
    @ApiModelProperty(value = "大区经理id")
    private Long areaManagerId;
    @ApiModelProperty(value = "是否查看本大区（0：否，1：是）")
    private Integer isSelfSaleRegion;

    /**
     * 权限-指定工作流
     */
    @ApiModelProperty(value = "权限-指定工作流")
    private List<Long> authWfIds;
    /**
     * 权限-指定自己的（只要办理人是自己就可以）
     */
    @ApiModelProperty(value = "权限-指定自己的")
    private Long authHandlerId;

    @ApiModelProperty(value = "办理状态：1：我办理的；2：我待办的")
    private Integer dealStatus;
    @ApiModelProperty(value = "删除标识（0：否，1：是）")
    private Integer isDeleted;
}
