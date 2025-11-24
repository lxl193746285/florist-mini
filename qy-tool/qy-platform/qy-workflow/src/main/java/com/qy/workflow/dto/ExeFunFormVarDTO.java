package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台_开发工具_设计器_窗体
 *
 * @author yinfeng
 * @since 2022-12-28
 */
@Data
public class ExeFunFormVarDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 变量ID
     */
    @ApiModelProperty(value = "变量ID")
    private String varId;

    /**
     * 变量值
     */
    @ApiModelProperty(value = "变量值")
    private String varValue;

}
