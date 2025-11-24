package com.qy.wf.defMap.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_映射表 记录业务表与工作流id关联
 *
 * @author syf
 * @since 2022-11-21
 */
@Data
public class DefMapDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 功能名称不允许重复，必填
     */
    @ApiModelProperty(value = "功能名称不允许重复，必填")
    private String funName;

    /**
     * 表名称必填
     */
    @ApiModelProperty(value = "表名称必填")
    private String tableName;

    /**
     * 工作流id 必填
     */
    @ApiModelProperty(value = "工作流id 必填")
    private Long wfId;

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

    @ApiModelProperty(value = "1启用0禁用")
    private String statusName;

}
