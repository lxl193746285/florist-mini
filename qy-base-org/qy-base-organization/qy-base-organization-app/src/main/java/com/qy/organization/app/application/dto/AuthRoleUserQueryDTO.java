package com.qy.organization.app.application.dto;

import com.qy.rest.pagination.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthRoleUserQueryDTO extends PageQuery {

    @ApiModelProperty("id")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;
}
