package com.qy.rbac.app.application.query;

import com.qy.rest.pagination.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户端查询
 *
 * @author legendjw
 */
@Data
public class ClientQuery extends PageQuery {
    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("会员系统")
    private Long memberSystemId;
}
