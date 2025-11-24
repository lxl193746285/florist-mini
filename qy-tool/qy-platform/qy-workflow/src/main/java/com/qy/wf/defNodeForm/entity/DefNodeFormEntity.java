package com.qy.wf.defNodeForm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流_设计_表单
 *
 * @author syf
 * @since 2022-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_def_node_form")
public class DefNodeFormEntity implements Serializable {
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
