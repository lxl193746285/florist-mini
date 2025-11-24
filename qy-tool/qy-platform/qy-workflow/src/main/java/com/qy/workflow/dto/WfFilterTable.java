package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 平台_开发工具_工作流查询表单关联查询
 *
 * @author yinfeng
 * @since 2024-06-15
 */
@Data
public class WfFilterTable implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表单ID
     */
    @ApiModelProperty(value = "表单ID")
    private String id;

    /**
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    private String name;
}
