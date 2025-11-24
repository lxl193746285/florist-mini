package com.qy.wf.defDefine.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工作流_设计_定义
 *
 * @author wch
 * @since 2022-11-12
 */
@Data
public class DefDefineFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司
     */
//    @NotNull(message = "公司 不能为空")
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 名称
     */
    @NotBlank(message = "名称 不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 工作流简介
     */
    @NotBlank(message = "工作流简介不得为空")
    @ApiModelProperty(value = "工作流简介")
    private String wfDescribe;

    /**
     * 工作流分组 用户代码表user_wf_group_id
     */
    @NotNull(message = "工作流分组不得为空")
    @ApiModelProperty(value = "工作流分组 用户代码表user_wf_group_id")
    private Integer groupId;

    /**
     * 顺序
     */
    @NotNull(message = "顺序 不能为空")
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
//    @NotBlank(message = "备注 不能为空")
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @NotNull(message = "1启用0禁用 不能为空")
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;


    /**
     *  工作流类型（wf_def_define_wf_type）
     */
//    @NotBlank(message = "工作流类型不得为空")
    @ApiModelProperty(value = "工作流类型")
    private Integer wfType;

    /**
     * 流程串
     */
    @ApiModelProperty(value = "流程串")
    private String wfStr;

    /**
     * 标题设置
     */
    @ApiModelProperty(value = "标题设置")
    private int wfTitleConfig;

    /**
     * 标题表达式
     */
    @ApiModelProperty(value = "标题表达式")
    private String wfTitleExp;

    /**
     * 摘要设置
     */
    @ApiModelProperty(value = "摘要设置")
    private int wfNoteConfig;

    /**
     * 摘要表达式
     */
    @ApiModelProperty(value = "摘要表达式")
    private String wfNoteExp;

    /**
     * 是否能新增发起（1是0否）
     */
    @ApiModelProperty(value = "是否能新增发起")
    private Integer canStart;
}
