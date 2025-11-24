package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 平台_开发工具_设计器_窗体
 *
 * @author yinfeng
 * @since 2023-01-04
 */
@Data
public class ExeFunFormRowObjectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 组件名称
     */
    @ApiModelProperty(value = "组件名称")
    private String tableName;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private List<ExeFunFormRowVarDTO> row;

}
