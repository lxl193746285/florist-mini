package com.qy.rbac.app.application.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单查询
 *
 * @author legendjw
 */
@Data
public class MenuQuery {
    /**
     * 模块id
     */
    @ApiModelProperty("模块id")
    private Long moduleId;

    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;

    /**
     * 类型id
     */
    @ApiModelProperty("类型id")
    private Integer typeId;
}
