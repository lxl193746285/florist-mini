package com.qy.rbac.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限以及规则
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class PermissionWithRuleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     * 权限节点
     */
    @ApiModelProperty("权限节点")
    private String permission;

    /**
     * 规则范围id
     */
    @ApiModelProperty("规则范围id")
    private String ruleScopeId;

    /**
     * 规则范围数据
     */
    @ApiModelProperty("规则范围数据")
    private Object ruleScopeData;

    @ApiModelProperty("会员系统id")
    private Long systemId;
}