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
public class IdeCollectionObjectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 变量ID
     */
    private String varId;

    /**
     * 嵌套变量集合,集合变量通过 变量[下标][varid]的方式取值
     */
    private List<List<ExeFunFormVarDTO>> varValue;

}

