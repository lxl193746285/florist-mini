package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_获取节点表
 *
 * @author iFeng
 * @since 2022-12-06
 */
@Data
public class WfGetTableDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型 1表示主表 工作流整理查看，2表示子表 工作流节点查看")
    private Integer type;

    /**
     * 工作流节点执行主键ID
     */
    @ApiModelProperty(value = "工作流节点执行主键ID 参数2使用")
    private Long wfRunNodeId;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID 参数1使用")
    private Long wfId;

    /**
     * 是否为开始节点
     */
    @ApiModelProperty(value = "是否为开始节点默认值0非开始节点")
    private Integer isStartNode=0;

    /**
     * 工作流执行Id
     */
    @ApiModelProperty(value = "工作流执行Id 参数1使用")
    private Long wfRunId;

    @ApiModelProperty(value = "平台 默认显示1 PC")
    private Integer platform=1;

    @ApiModelProperty(value = "平台 默认显示详情")
    private Integer showType=1;

    /**
     * 0-发起, 1-同意, 2-拒绝, 3-退回, 4-作废, 5-撤回, 6-办理, 7-撤销, 8-打回
     */
    @ApiModelProperty(value = "办理结果 默认-1 同意显示")
    private int dealResult=-1;

}
