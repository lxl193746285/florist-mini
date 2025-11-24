package com.qy.workflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 变量是否存在
 *
 * @author iFeng
 * @since 2022-11-21
 */
@Data
public class WfVarFindDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *是否存在
     */
    @ApiModelProperty(value = "是否存在")
    private Boolean exist;

    /**
     * 变量值
     */
    @ApiModelProperty(value = "变量值")
    private String varValue;

}
