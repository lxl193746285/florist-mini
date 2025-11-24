package com.qy.organization.app.application.query;

import com.qy.rest.pagination.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织查询
 *
 * @author legendjw
 */
@Data
public class OrgDatasourceQuery extends PageQuery {

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long orgId;

    /**
     * 数据库名称
     */
    @ApiModelProperty("数据库名称")
    private String datasourceName;
}