package com.qy.rbac.app.application.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用查询
 *
 * @author legendjw
 */
@Data
public class AppQuery {
    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private Long clientId;
}
