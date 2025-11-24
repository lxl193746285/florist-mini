package com.qy.wf.defNodeRelation.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点关系
 *
 * @author syf
 * @since 2022-11-14
 */
@Data
public class DefNodeRelationDTO  extends ArkBaseDTO implements Serializable {
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
     * 节点编码id
     */
    @ApiModelProperty(value = "节点编码id")
    private String nodeCode;
    /**
     * 前节点编码id
     */
    @ApiModelProperty(value = "前节点编码id")
    private String fromNodeCode;
    /**
     * 后节点编码id
     */
    @ApiModelProperty(value = "后节点编码id")
    private String toNodeCode;

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
     * 1直接执行2表达式验证
     */
    @ApiModelProperty(value = "1直接执行2表达式验证")
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

}
