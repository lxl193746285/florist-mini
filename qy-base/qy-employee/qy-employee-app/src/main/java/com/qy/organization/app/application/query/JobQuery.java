package com.qy.organization.app.application.query;

import com.qy.rest.pagination.PageQuery;
import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位查询
 *
 * @author legendjw
 */
@Data
public class JobQuery extends PageQuery {
    /**
     * 当前用户
     */
    @JsonIgnore
    private Identity identity;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keywords;
}
