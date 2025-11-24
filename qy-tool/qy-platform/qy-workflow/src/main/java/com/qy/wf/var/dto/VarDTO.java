package com.qy.wf.var.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_变量
 *
 * @author syf
 * @since 2022-11-21
 */
@Data
public class VarDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 变量类型1系统变量，2运行时变量
     */
    @ApiModelProperty(value = "变量类型1系统变量，2运行时变量")
    private Integer varType;

    @ApiModelProperty(value = "变量类型名称")
    private String varTypeName;

    /**
     * 变量标识#系统变量，?运行时变量
     */
    @ApiModelProperty(value = "变量标识#系统变量，?运行时变量")
    private String varTag;

    /**
     * 变量ID
     */
    @ApiModelProperty(value = "变量ID")
    private String varId;

    /**
     * 变量名称
     */
    @ApiModelProperty(value = "变量名称")
    private String varName;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer sort;

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

    @ApiModelProperty(value = "状态名称：1启用0禁用")
    private String statusName;

}
