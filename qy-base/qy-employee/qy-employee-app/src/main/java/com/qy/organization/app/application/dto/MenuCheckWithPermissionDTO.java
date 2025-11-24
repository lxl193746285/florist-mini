package com.qy.organization.app.application.dto;

import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单勾选以及权限DTO
 *
 * @author legendjw
 */
@Data
public class MenuCheckWithPermissionDTO {
    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private Long id;

    /**
     * 勾选情况 0：不勾 1: 全勾  2: 半勾
     */
    @ApiModelProperty("勾选情况 0：不勾 1: 全勾  2: 半勾")
    private int checked;

    /**
     * 授权的权限节点
     */
    @ApiModelProperty("授权的权限节点")
    private List<PermissionWithRuleDTO> permissions;
}
