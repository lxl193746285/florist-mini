package com.qy.wf.defDefine.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_定义
 *
 * @author wch
 * @since 2022-11-12
 */
@Data
public class DefDefineQueryDTO implements Serializable {
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
    * 名称
    */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
    * 1启用0禁用
    */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 工作流分组 用户代码表user_wf_group_id
     */
    @ApiModelProperty(value = "工作流分组 用户代码表user_wf_group_id")
    private Integer groupId;

    /**
     *  工作流类型（wf_def_define_wf_type）
     */
    @ApiModelProperty(value = "工作流类型（wf_def_define_wf_type）")
    private Integer wfType;

    /**
     * 是否能新增发起（1是0否）
     */
    @ApiModelProperty(value = "是否能新增发起")
    private Integer canStart;

}
