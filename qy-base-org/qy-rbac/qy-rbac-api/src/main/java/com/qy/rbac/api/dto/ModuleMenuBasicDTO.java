package com.qy.rbac.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块菜单
 *
 * @author legendjw
 */
@Data
public class ModuleMenuBasicDTO {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId = 0L;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 功能标示
     */
    @ApiModelProperty("功能标示")
    private String code;

    /**
     * 子项菜单
     */
    @ApiModelProperty("子项菜单")
    private List<MenuBasicDTO> children = new ArrayList<>();
}
