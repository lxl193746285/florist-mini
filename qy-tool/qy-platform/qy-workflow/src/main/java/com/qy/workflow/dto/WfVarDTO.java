package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_变量表
 *
 * @author iFeng
 * @since 2022-11-21
 */
@Data
public class WfVarDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *变量ID
     */
    @ApiModelProperty(value = "变量ID")
    private String varId;

    /**
     * 变量名称
     */
    @ApiModelProperty(value = "变量名称")
    private String varName;

    /**
     * 变量码
     */
    @ApiModelProperty(value = "变量码")
    private String varCode;

    /**
     * 变量值
     */
    @ApiModelProperty(value = "变量值")
    private String varValue;

    /**
     * 变量类型
     */
    @ApiModelProperty(value = "变量类型")
    private Integer varType;

}
