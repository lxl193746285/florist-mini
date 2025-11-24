package com.qy.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_run_node")
public class WfRunNodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "当前节点发送时间")
    private LocalDateTime curSendTime;

    /**
     * 节点当前标记
     */
    @ApiModelProperty(value = "节点当前标记")
    private Integer isCurMark;

    /**
     * 当前节点状态
     */
    @ApiModelProperty(value = "（0.未办理 1.办理中 2.已办理,3已发起）")
    private Integer curStatus;

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
    @ApiModelProperty(value = "备注")
    private String note;
    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long creatorId;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatorId;
    /**
     * 是否删除0.未删除1.已删除
     */
    @ApiModelProperty(value = "是否删除0.未删除1.已删除")
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
    private Long deletorId;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String creatorName;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updatorName;
    /**
     * 删除者
     */
    @ApiModelProperty(value = "删除者")
    private String deletorName;

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

}
