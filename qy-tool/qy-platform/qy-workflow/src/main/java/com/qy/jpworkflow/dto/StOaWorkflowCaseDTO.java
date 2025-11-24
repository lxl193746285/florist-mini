package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发起工作流
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class StOaWorkflowCaseDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 审批单号
     */
    @ApiModelProperty(value = "审批单号")
    private String caseNo;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id")
    private Integer workflowId;

    /**
     * 表单id
     */
    @ApiModelProperty(value = "表单id")
    private Integer formId;

    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称")
    private String workflowName;

    /**
     * 申请名称
     */
    @ApiModelProperty(value = "申请名称")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Byte status;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Integer finishedTime;
    private Integer createTime;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Integer deptId;

    /**
     * 是否打印，0.未打印，1.已打印
     */
    @ApiModelProperty(value = "是否打印，0.未打印，1.已打印")
    private Integer isPrint;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Integer updateMark;
    private Integer creatorId;



}
