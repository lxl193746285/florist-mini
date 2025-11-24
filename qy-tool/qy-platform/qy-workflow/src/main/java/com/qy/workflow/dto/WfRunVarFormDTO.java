package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-23
 */
@Data
public class WfRunVarFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司
     */
    @NotNull(message = "公司 不能为空")
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 执行流ID
     */
    @NotNull(message = "执行流ID 不能为空")
    @ApiModelProperty(value = "执行流ID")
    private Long runWfId;

    /**
     * 工作流ID
     */
    @NotNull(message = "工作流ID 不能为空")
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 变量ID
     */
    @NotBlank(message = "变量ID 不能为空")
    @ApiModelProperty(value = "变量ID")
    private String varId;

    /**
     * 变量值
     */
    @NotBlank(message = "变量值 不能为空")
    @ApiModelProperty(value = "变量值")
    private String varValue;

}
