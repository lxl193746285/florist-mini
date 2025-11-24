package com.qy.wf.defDefine.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_发起
 *
 * @author lxl
 * @since 2022-11-12
 */
@Data
public class DefStartDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 工作流名称
     */
    @ApiModelProperty(value = "工作流名称")
    private String name;

    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String funName;

}
