package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
public class WfDealDTO   implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *主键Id，办理id
     */
    @NotNull(message = "wf_run_node表主键Id")
    @ApiModelProperty(value = "wf_run_node表主键Id")
    private Long id;


    /**
     * 办理意见
     */
    @NotNull(message = "办理类型必填")
    @ApiModelProperty(value = "办理类型 1-同意，2拒绝，3-退回，4-作废，5撤回 6打回")
    private Integer result;

//    @NotNull(message = "处理方式")
//    @ApiModelProperty(value = "处理方式 1-继续流转，2退回")
//    private Integer dealMode;

    /**
     * 办理意见
     */
    @ApiModelProperty(value = "办理意见 包含 同意，拒绝，退回，作废，撤回原因")
    private String comment;

    /**
     * 下一节点
     */
    @ApiModelProperty(value = "下一节点")
    private Long nextNodeId;

    /**
     * 下一节点人
     */
    @ApiModelProperty(value = "下一节点人")
    private Long nextUserId;

    /**
     * 退回节点
     */
    @ApiModelProperty(value = "退回节点")
    private Long returnNodeId;

    /**
     * 退回人
     */
    @ApiModelProperty(value = "退回人")
    private Long returnUserId;

    /**
     * 任意变量传入，支持IDE变量
     */
    @ApiModelProperty(value = "任意变量传入，支持IDE变量")
    private List<WfGlobalVarDTO> outVars;

    /**
     * IDE变量
     */
    @ApiModelProperty(value = "IDE变量")
    private List<IdeCtrlValueDTO> ideVars;

    /**
     * 平台默认参数为0
     */
    private Integer platform=1;

}
