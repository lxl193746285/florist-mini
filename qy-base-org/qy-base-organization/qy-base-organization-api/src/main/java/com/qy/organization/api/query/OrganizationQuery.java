package com.qy.organization.api.query;

import com.qy.rest.pagination.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 权限组查询
 *
 * @author legendjw
 */
@Data
public class OrganizationQuery extends PageQuery {
    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;

    /**
     * 加入人id
     */
    @ApiModelProperty("加入人id")
    private Long joinId;

    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Long creatorId;
    /**
     * 组织机构ids
     */
    private List<Long> organizationIds;
}
