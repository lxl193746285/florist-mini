package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_获取节点表
 *
 * @author iFeng
 * @since 2024-06-07
 */
@Data
public class WfGetTableByFunNameDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型 1表示主表 工作流整理查看，2表示子表 工作流节点查看")
    private Integer type;

    /**
     * 类型
     */
    @ApiModelProperty(value = "功能名称")
    private String funName;

    @ApiModelProperty(value = "平台 默认显示1 PC")
    private Integer platform=1;

    @ApiModelProperty(value = "平台 默认显示详情")
    private Integer showType=1;

}
