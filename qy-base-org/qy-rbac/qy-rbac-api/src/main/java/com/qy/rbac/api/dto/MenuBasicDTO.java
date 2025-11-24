package com.qy.rbac.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 基本菜单
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class MenuBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 模块id
     */
    @ApiModelProperty("模块id")
    private Long moduleId;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 功能标示
     */
    @ApiModelProperty("功能标示")
    private String code;

    /**
     * url地址
     */
    @ApiModelProperty("url地址")
    private String url;

    /**
     * 权限节点
     */
    @ApiModelProperty("权限节点")
    private String authItem;

    /**
     * 子类
     */
    @ApiModelProperty("子类")
    private List<MenuBasicDTO> children = new ArrayList<>();
}