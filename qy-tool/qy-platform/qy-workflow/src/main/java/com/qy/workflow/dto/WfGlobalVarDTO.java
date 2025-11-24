package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 工作流_全局变量_支持外部变量传入主要用于变量替换
 *
 * @author iFeng
 * @since 2024-06-01
 */
@Data
public class WfGlobalVarDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
