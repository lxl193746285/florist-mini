package com.qy.wf.runWf.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RunIsCanInvalidDTO {

    /**
     * 工作流主表创建人
     */
    @ApiModelProperty(value = "工作流主表创建人")
    private Long wfCreatorId;
    /**
     * 工作流子表创建人
     */
    @ApiModelProperty(value = "工作流子表创建人")
    private Long creatorId;
    /**
     * 工作流子表当前办理人
     */
    @ApiModelProperty(value = "工作流子表当前办理人")
    private Long curNodeUserId;

}
