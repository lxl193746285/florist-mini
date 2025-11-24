package com.qy.rbac.app.application.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 规则范围查询
 *
 * @author legendjw
 */
@Data
public class RuleScopeQuery {
    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;
}
