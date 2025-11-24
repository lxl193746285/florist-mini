package com.qy.workflow.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点与线的组合
 *
 * @author iFeng
 * @since 2022-11-26
 */
@Data
public class WfDefNodeAndRelationEntity extends com.qy.workflow.entity.WfDefNodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点编码
     */
    @ApiModelProperty(value = "关系表达式")
    private String relationRuleExp;
    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "关系规则")
    private Integer relationRule;

}
