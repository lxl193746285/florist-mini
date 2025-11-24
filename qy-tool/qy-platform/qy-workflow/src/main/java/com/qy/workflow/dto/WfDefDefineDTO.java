package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_定义
 *
 * @author iFeng
 * @since 2022-11-21
 */
@Data
public class WfDefDefineDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工作流类型
     */
    @ApiModelProperty(value = "工作流类型")
    private Integer wfType;

    /**
     * 工作流名称
     */
    @ApiModelProperty(value = "工作流名称")
    private String name;

    /**
     * 标题设置
     */
    @ApiModelProperty(value = "标题设置")
    private Integer wfTitleConfig;

    /**
     * 标题表达式
     */
    @ApiModelProperty(value = "标题表达式")
    private String wfTitleExp;

    /**
     * 摘要设置
     */
    @ApiModelProperty(value = "摘要设置")
    private Integer wfNoteConfig;

    /**
     * 摘要表达式
     */
    @ApiModelProperty(value = "摘要表达式")
    private String wfNoteExp;

    /**
     * 工作流名称
     */
    @ApiModelProperty(value = "工作流名称")
    private String wfTypeName;

}
