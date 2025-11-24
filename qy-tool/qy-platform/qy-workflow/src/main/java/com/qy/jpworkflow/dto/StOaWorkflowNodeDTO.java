package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工作流节点
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class StOaWorkflowNodeDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id")
    private Integer workflowId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类型(1:开始节点,2:步骤节点,3:子流程4: 结束节点)
     */
    @ApiModelProperty(value = "类型(1:开始节点,2:步骤节点,3:子流程4: 结束节点)")
    private Integer type;

    /**
     * 是否并行(0:否,1:是)
     */
    @ApiModelProperty(value = "是否并行(0:否,1:是)")
    private Byte isParallel;

    /**
     * 办理人类型
     */
    @ApiModelProperty(value = "办理人类型")
    private Byte transactorType;

    /**
     * 范围类型
     */
    @ApiModelProperty(value = "范围类型")
    private Integer scopeType;

    /**
     * 是否多分支并行(0:否,1:是)
     */
    @ApiModelProperty(value = "是否多分支并行(0:否,1:是)")
    private Byte isMultipleParallel;

    /**
     * 是否必须闭合(0:否,1:是)
     */
    @ApiModelProperty(value = "是否必须闭合(0:否,1:是)")
    private Byte isMustOcclusion;

    /**
     * 闭合步骤
     */
    @ApiModelProperty(value = "闭合步骤")
    private Integer occlusionStep;

    /**
     * 是否允许退回(0:否,1:是)
     */
    @ApiModelProperty(value = "是否允许退回(0:否,1:是)")
    private Byte isSendBack;

    /**
     * 可退回节点
     */
    @ApiModelProperty(value = "可退回节点")
    private String sendBackNode;

    /**
     * 是否有时间限制
     */
    @ApiModelProperty(value = "是否有时间限制")
    private Byte isHaveTimeLimit;

    /**
     * 时限
     */
    @ApiModelProperty(value = "时限")
    private Integer timeLimit;

    /**
     * 时限单位(1:天,2:小时)
     */
    @ApiModelProperty(value = "时限单位(1:天,2:小时)")
    private Byte timeLimitUnit;

    /**
     * 超时处理方式
     */
    @ApiModelProperty(value = "超时处理方式")
    private Byte timeoutHandleWay;

    /**
     * 允许作废(0:否,1:是)
     */
    @ApiModelProperty(value = "允许作废(0:否,1:是)")
    private Byte isCancellation;

    /**
     * 超期提醒提前时间
     */
    @ApiModelProperty(value = "超期提醒提前时间")
    private BigDecimal timeoutAheadTime;

    /**
     * 办理人提醒类型
     */
    @ApiModelProperty(value = "办理人提醒类型")
    private String transactorCautionType;

    /**
     * 抄送人
     */
    @ApiModelProperty(value = "抄送人")
    private String ccIds;

    /**
     * 抄送人提醒类型
     */
    @ApiModelProperty(value = "抄送人提醒类型")
    private String ccCautionType;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Byte isUseForm;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Integer customFormId;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private String isAgree;

    private Integer creatorId;

}
