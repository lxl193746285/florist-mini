package com.qy.rbac.app.application.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用查询
 *
 * @author legendjw
 */
@Data
public class AppMemberSystemQuery {
    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 会员系统id
     */
    @ApiModelProperty("会员系统id")
    private Long systemId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;
}
