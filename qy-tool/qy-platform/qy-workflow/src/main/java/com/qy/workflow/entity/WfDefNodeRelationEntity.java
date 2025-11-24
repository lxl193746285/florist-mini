package com.qy.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_设计_节点关系
 *
 * @author iFeng
 * @since 2022-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_def_node_relation")
public class WfDefNodeRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
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
     * 连线名称，显示在设计图上
     */
    @ApiModelProperty(value = "连线名称，显示在设计图上")
    private String name;
    /**
     * 前节点
     */
    @ApiModelProperty(value = "前节点")
    private Long fromNodeId;
    /**
     * 后节点
     */
    @ApiModelProperty(value = "后节点")
    private Long toNodeId;
    /**
     * 1直接执行2SQL表达式
     */
    @ApiModelProperty(value = "1直接执行2SQL表达式")
    private Integer rule;
    /**
     * 执行表达式
     */
    @ApiModelProperty(value = "执行表达式")
    private String ruleExp;
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
}
