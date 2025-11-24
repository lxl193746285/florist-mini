package com.qy.codetable.app.application.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码表默认项查询
 *
 * @author legendjw
 */
@Data
public class CodeTableDefaultItemQuery {
    /**
     * 类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码
     */
    @ApiModelProperty("类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码")
    private String type;

    /**
     * 标示码
     */
    @ApiModelProperty("标示码")
    private String code;

    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer status;
}
