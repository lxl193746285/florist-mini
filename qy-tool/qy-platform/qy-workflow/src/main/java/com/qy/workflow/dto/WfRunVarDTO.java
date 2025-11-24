package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-23
 */
@Data
public class WfRunVarDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ApiModelProperty(value = "")
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
     * 变量ID
     */
    @ApiModelProperty(value = "变量ID")
    private String varId;

    /**
     * 变量值
     */
    @ApiModelProperty(value = "变量值")
    private String varValue;

}
