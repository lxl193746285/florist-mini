package com.qy.wf.runNode.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *流程详情
 */
@Data
public class RunNodeDetailDTO {

    /**
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private Long beginUserId;
    /**
     * 流程发起人
     */
    @ApiModelProperty(value = "流程发起人")
    private String beginUserName;

    /**
     * 流程开始时间
     */
    @ApiModelProperty(value = "流程开始时间")
    private String beginTime;

    /**
     * 流程
     */
    private List<com.qy.wf.runNode.dto.RunNodeDetail> runNodeDetailList;



}
