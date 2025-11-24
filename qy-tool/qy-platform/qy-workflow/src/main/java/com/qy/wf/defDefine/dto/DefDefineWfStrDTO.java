package com.qy.wf.defDefine.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DefDefineWfStrDTO {

    /**
     * 流程串
     */
    @ApiModelProperty(value = "流程串")
    private String wfStr;

}
