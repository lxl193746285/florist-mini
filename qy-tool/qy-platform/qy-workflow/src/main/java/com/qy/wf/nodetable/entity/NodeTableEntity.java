package com.qy.wf.nodetable.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_设计_节点表单
 *
 * @author hh
 * @since 2022-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_def_node_table")
public class NodeTableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
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
     * 节点ID
     */
    @ApiModelProperty(value = "节点ID")
    private Long nodeId;
    /**
     * 节点编码id
     */
    @ApiModelProperty(value = "节点编码id")
    private String nodeCode;

    @ApiModelProperty(value = "平台 1-PC 2-uniapp（混合移动端） 3-微信小程序 4-APP  wf_def_node_table.platform")
    private Integer platform;

    @ApiModelProperty(value = "显示方式 1.直接显示 2.显示按钮链接 wf_def_node_table.show_mode")
    private Integer showMode;

    /**
     * 1查询表单
     */
    @ApiModelProperty(value = "1查询表单")
    private Integer tableType;
    /**
     * 查询表单ID
     */
    @ApiModelProperty(value = "查询表单ID")
    private Long tableId;
    /**
     * 显示时机1：详细使用,2:办理使用
     */
    @ApiModelProperty(value = "显示时机1：详细使用,2:办理使用")
    private Integer showType;
    /**
     * 表单路径
     */
    @ApiModelProperty(value = "表单路径")
    private String tablePath;
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
    @ApiModelProperty(value = "办理意见 0-发起, 1-同意, 2-拒绝, 3-退回, 4-作废, 5-撤回, 6-办理, 7-撤销, 8-打回")
    private String dealResult;

}
