package com.qy.wf.runWf.dto;

import com.qy.wf.runNode.dto.RunNodeDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工作流管理详情
 */
@Data
public class RunWfDetailDTO {
    /**
     * 基本信息
     */
    @ApiModelProperty(value = "基本信息")
    private com.qy.wf.runWf.dto.RunWfDetail runWfDetail;

    /**
     * 流程
     */
    @ApiModelProperty(value = "流程")
    private RunNodeDetailDTO runNodeDetailDTO;


}
