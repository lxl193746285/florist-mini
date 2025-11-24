package com.qy.rbac.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类权限菜单
 *
 * @author legendjw
 */
@Data
public class CategoryPermissionMenuDTO {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 权限菜单
     */
    @ApiModelProperty("权限菜单")
    private List<PermissionMenuDTO> permissions = new ArrayList<>();
}
