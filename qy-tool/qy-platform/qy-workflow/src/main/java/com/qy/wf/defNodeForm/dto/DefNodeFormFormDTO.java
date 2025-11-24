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
public class DefNodeFormFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 节点ID
     */
    @ApiModelProperty(value = "节点ID")
    private Long nodeId;

    /**
     * 0无，1自定义表单
     */
    @ApiModelProperty(value = "0无，1自定义表单")
    private Integer formType;

    /**
     * 自定义表单ID
     */
    @ApiModelProperty(value = "自定义表单ID")
    private Long fromId;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
