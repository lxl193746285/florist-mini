package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
public class WfStartDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String funName;

    /**
     * 申请人ID
     */
    private Long applyUserId;

    /**
     * 业务表ID一般是主键ID
     */
    @ApiModelProperty(value = "外部表Id")
    private Long outId;

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


    @ApiModelProperty(value = "工作流执行ID")
    private Long wfRunId;

    /**
     * 任意变量传入，支持IDE变量
     */
    @ApiModelProperty(value = "任意变量传入")
    private List<WfGlobalVarDTO> outVars;

    /**
     * IDE变量
     */
    @ApiModelProperty(value = "IDE变量")
    private List<IdeCtrlValueDTO> ideVars;

    /**
     * 平台默认参数为0
     */
    private Integer platform=0;
}
