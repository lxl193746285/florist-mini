package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_执行_下一节点的办理状态
 *
 * @author iFeng
 * @since 2022-11-22
 */
@Data
public class WfNextRunNodeDealDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     *顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer sort;
    /**
     *办理结果
     */
    @ApiModelProperty(value = "办理结果")
    private Long approveResult;

    /**
     *办理时间
     */
    @ApiModelProperty(value = "办理时间")
    private LocalDateTime curDealTime;

    @ApiModelProperty(value = "当前标记")
    private Integer isCurMark;

    @ApiModelProperty(value = "节点状态")
    private Integer curStatus;
    @ApiModelProperty(value = "节点ID")
    private Long curNodeId;
    @ApiModelProperty(value = "节点办理人")
    private Long curNodeUserId;
}
