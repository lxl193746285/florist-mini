package com.qy.rbac.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单规则
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class MenuRuleFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 范围id
     */
    @ApiModelProperty("范围id")
    private String scopeId;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
}