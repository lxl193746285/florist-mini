package com.qy.wf.defDefine.dto;

import com.qy.common.dto.ArkBaseDTO;
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
public class DefDefineDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     *  流程类型（码表：wf_def_define_wf_type）
     */
    @ApiModelProperty(value = "流程类型")
    private Integer wfType;
    @ApiModelProperty(value = "流程类型（码表：wf_def_define_wf_type）")
    private String wfTypeName;

    /**
     * 工作流简介
     */
    @ApiModelProperty(value = "描述")
    private String wfDescribe;

    /**
     * 工作流分组 用户代码表user_wf_group_id
     */
    @ApiModelProperty(value = "工作流分组 用户代码表user_wf_group_id")
    private Integer groupId;
    @ApiModelProperty(value = "工作流分组 用户代码表user_wf_group_id")
    private String groupName;

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

    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String statusName;

    /**
     * 标题设置
     */
    @ApiModelProperty(value = "标题设置")
    private int wfTitleConfig;

    /**
     * 标题设置-名称
     */
    @ApiModelProperty(value = "标题设置-名称")
    private String wfTitleConfigName;

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
     * 摘要设置名称
     */
    @ApiModelProperty(value = "摘要设置名称")
    private String wfNoteConfigName;

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
    @ApiModelProperty(value = "是否能新增发起")
    private String canStartName;
}
