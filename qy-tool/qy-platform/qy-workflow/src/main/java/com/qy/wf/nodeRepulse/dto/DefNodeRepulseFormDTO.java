package com.qy.wf.nodeRepulse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_打回节点
 *
 * @author syf
 * @since 2023-08-16
 */
@Data
public class DefNodeRepulseFormDTO implements Serializable {
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


    @ApiModelProperty(value = "当前节点code")
    private String curNodeCode;

    /**
     * 当前节点ID
     */
    @ApiModelProperty(value = "当前节点ID")
    private Long curNodeId;

    /**
     * 打回节点ID
     */
    @ApiModelProperty(value = "打回节点ID")
    private Long nodeId;

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
