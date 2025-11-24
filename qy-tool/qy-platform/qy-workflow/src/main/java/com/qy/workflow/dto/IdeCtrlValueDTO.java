package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 平台_开发工具_组件值用于传递变量使用
 *
 * @author yinfeng
 * @since 2024-06-06
 */
@Data
public class IdeCtrlValueDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 功能ID
     */
    @ApiModelProperty(value = "功能ID")
    private String funId;

    /**
     * 窗体ID
     */
    @ApiModelProperty(value = "窗体ID")
    private String formId;

    /**
     * 组件名
     */
    @ApiModelProperty(value = "组件名")
    private String formCtrlName;

    /**
     * 窗体组件变量
     */
    @ApiModelProperty(value = "窗体组件变量")
    private List<ExeFunFormVarDTO> formVars;
    /**
     * 行变量
     */
    @ApiModelProperty(value = "行变量")
    private List<ExeFunFormRowObjectDTO> rowVars;

    /**
     * 集合变量 使用方式：1.集合变量 2.集合变量+行变量
     */
    @ApiModelProperty(value = "集合变量 使用方式：1.集合变量 2.集合变量+行变量")
    private List<IdeCollectionObjectDTO> collectionObjects;

}
