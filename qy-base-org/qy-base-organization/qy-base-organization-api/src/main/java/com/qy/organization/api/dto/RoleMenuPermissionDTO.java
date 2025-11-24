package com.qy.organization.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限组菜单权限DTO
 *
 * @author legendjw
 */
@Data
public class RoleMenuPermissionDTO {
    /**
     * 菜单勾选
     */
    @ApiModelProperty("菜单勾选")
    private List<MenuCheckDTO> menuChecks = new ArrayList<>();
    /**
     * 权限
     */
    @ApiModelProperty("权限")
    private List<PermissionWithRuleDTO> permissions = new ArrayList<>();
}
