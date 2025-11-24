package com.qy.wf.var.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工作流_变量
 *
 * @author syf
 * @since 2022-11-21
 */
@Data
public class VarFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 变量类型1系统变量，2运行时变量
     */
    @NotNull(message = "变量类型1系统变量，2运行时变量 不能为空")
    @ApiModelProperty(value = "变量类型1系统变量，2运行时变量")
    private Integer varType;

    /**
     * 变量标识#系统变量，?运行时变量
     */
    @NotBlank(message = "变量标识#系统变量，?运行时变量 不能为空")
    @ApiModelProperty(value = "变量标识#系统变量，?运行时变量")
    private String varTag;

    /**
     * 变量ID
     */
    @NotBlank(message = "变量ID 不能为空")
    @ApiModelProperty(value = "变量ID")
    private String varId;

    /**
     * 变量名称
     */
    @NotBlank(message = "变量名称 不能为空")
    @ApiModelProperty(value = "变量名称")
    private String varName;

    /**
     * 顺序
     */
    @NotNull(message = "顺序 不能为空")
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
    @NotBlank(message = "备注 不能为空")
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @NotNull(message = "1启用0禁用 不能为空")
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
