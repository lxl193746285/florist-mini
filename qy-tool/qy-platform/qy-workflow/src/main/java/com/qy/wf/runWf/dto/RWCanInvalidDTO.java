package com.qy.wf.runWf.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RWCanInvalidDTO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 执行工作流id
     */
    @ApiModelProperty(value = "执行工作流id")
    private Long runWfId;

}
