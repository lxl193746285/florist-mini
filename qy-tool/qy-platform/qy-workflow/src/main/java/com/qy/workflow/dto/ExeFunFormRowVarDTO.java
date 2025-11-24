package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台_开发工具_设计器_窗体
 *
 * @author yinfeng
 * @since 2023-01-04
 */
@Data
public class ExeFunFormRowVarDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 列
     */
    @ApiModelProperty(value = "列")
    private String col;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String value;

}
