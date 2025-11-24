package com.qy.wf.defNodeUser.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点人员
 *
 * @author syf
 * @since 2022-11-14
 */
@Data
public class DefNodeUserFormDTO implements Serializable {
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
     * 节点编码id
     */
    @ApiModelProperty(value = "节点编码id")
    private String nodeCode;

    /**
     * 1办理人，2抄送人
     */
    @ApiModelProperty(value = "1办理人，2抄送人")
    private Integer type;

    /**
     * 人员ID
     */
    @ApiModelProperty(value = "人员ID")
    private Long userId;

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
