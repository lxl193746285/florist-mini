package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_执行_工作流
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Data
public class WfRunWfQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 创建日期-开始(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-开始")
    private String startCreateDate;

    /**
    * 创建日期-结束(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-结束")
    private String endCreateDate;

    /**
    * 1启用0禁用
    */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
