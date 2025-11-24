package com.qy.wf.defNodeForm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_表单
 *
 * @author syf
 * @since 2022-11-14
 */
@Data
public class DefNodeFormQueryDTO implements Serializable {
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
