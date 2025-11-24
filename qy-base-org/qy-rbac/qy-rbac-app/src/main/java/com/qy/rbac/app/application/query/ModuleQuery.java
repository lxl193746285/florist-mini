package com.qy.rbac.app.application.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模块查询
 *
 * @author legendjw
 */
@Data
public class ModuleQuery {
    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;

    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;
}
