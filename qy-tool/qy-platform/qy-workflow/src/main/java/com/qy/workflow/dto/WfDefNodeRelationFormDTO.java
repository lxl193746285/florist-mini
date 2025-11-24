package com.qy.workflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工作流_设计_节点关系
 *
 * @author iFeng
 * @since 2022-11-15
 */
@Data
public class WfDefNodeRelationFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司
     */
    @NotNull(message = "公司 不能为空")
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 工作流ID
     */
    @NotNull(message = "工作流ID 不能为空")
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 连线名称，显示在设计图上
     */
    @NotBlank(message = "连线名称，显示在设计图上 不能为空")
    @ApiModelProperty(value = "连线名称，显示在设计图上")
    private String name;

    /**
     * 前节点
     */
    @NotNull(message = "前节点 不能为空")
    @ApiModelProperty(value = "前节点")
    private Long fromNodeId;

    /**
     * 后节点
     */
    @NotNull(message = "后节点 不能为空")
    @ApiModelProperty(value = "后节点")
    private Long toNodeId;

    /**
     * 1直接执行2SQL表达式
     */
    @NotNull(message = "1直接执行2SQL表达式 不能为空")
    @ApiModelProperty(value = "1直接执行2SQL表达式")
    private Integer rule;

    /**
     * 执行表达式
     */
    @NotBlank(message = "执行表达式 不能为空")
    @ApiModelProperty(value = "执行表达式")
    private String ruleExp;

    /**
     * 顺序
     */
    @NotNull(message = "顺序 不能为空")
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
    @NotBlank(message = "备注 不能为空")
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @NotNull(message = "1启用0禁用 不能为空")
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
