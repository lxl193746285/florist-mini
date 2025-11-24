package com.qy.region.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 地区
 *
 * @author legendjw
 * @since 2021-08-26
 */
@Data
public class AreaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 地区id
     */
    @ApiModelProperty("地区id")
    private Long id;

    /**
     * 父级地区id
     */
    @ApiModelProperty("父级地区id")
    private Long parentId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    private String code;

    /**
     * 简称
     */
    @ApiModelProperty("简称")
    private String shortName;

    /**
     * 层级
     */
    @ApiModelProperty("层级")
    private Integer level;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 人口
     */
    @ApiModelProperty("人口")
    private Integer population;

    private List<Action> actions;


    /**
     * 是否拥有下级
     */
    @ApiModelProperty("是否拥有下级")
    private Boolean hasChildren;

}
