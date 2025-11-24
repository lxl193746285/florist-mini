package com.qy.wf.defDefine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_设计_定义
 *
 * @author wch
 * @since 2022-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_def_define")
public class DefDefineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Long companyId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 工作流类型（wf_def_define_wf_type）
     */
    @ApiModelProperty(value = "工作流类型")
    private Integer wfType;

    /**
     * 工作流简介
     */
    @ApiModelProperty(value = "工作流简介")
    private String wfDescribe;
    /**
     * 工作流分组 用户代码表user_wf_group_id
     */
    @ApiModelProperty(value = "工作流分组 用户代码表user_wf_group_id")
    private Integer groupId;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long creatorId;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatorId;
    /**
     * 是否删除0.未删除1.已删除
     */
    @ApiModelProperty(value = "是否删除0.未删除1.已删除")
    private Integer isDeleted;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deletorId;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String creatorName;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updatorName;
    /**
     * 删除者
     */
    @ApiModelProperty(value = "删除者")
    private String deletorName;
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
