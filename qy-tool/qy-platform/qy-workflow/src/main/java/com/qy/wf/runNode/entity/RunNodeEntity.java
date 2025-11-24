package com.qy.wf.runNode.entity;

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
 * @author wch
 * @since 2022-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_run_node")
public class RunNodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
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
     * 当前标记（当前人）
     */
    @ApiModelProperty(value = "")
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
     * 当前状态（0.未办理 1.办理中 2.已办理）
     */
    @ApiModelProperty(value = "当前状态（0.未办理 1.办理中 2.已办理 3.已发起 4.撤回 5.作废）")
    private Integer curStatus;
    /**
     * 当前节点办理人 发起人发起时间
     */
    @ApiModelProperty(value = "当前节点办理人 发起人发起时间")
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
     * 下一节点状态（0.未办理 1.办理中 2.已办理）
     */
    @ApiModelProperty(value = "下一节点状态（0.未办理 1.办理中 2.已办理）")
    private Integer nextNodeStatus;
    /**
     * 下一节点人ID
     */
    @ApiModelProperty(value = "下一节点人ID")
    private Long nextNodeUserId;
    /**
     * 删除者
     */
    @ApiModelProperty(value = "删除者")
    private String deletorName;


}
