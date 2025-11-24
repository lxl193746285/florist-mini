package com.qy.organization.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单勾选DTO
 *
 * @author legendjw
 */
@Data
public class MenuCheckDTO {
    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private Long id;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;
    /**
     * 勾选情况 0：不勾 1: 全勾  2: 半勾
     */
    @ApiModelProperty("勾选情况 0：不勾 1: 全勾  2: 半勾")
    private int checked;
}
