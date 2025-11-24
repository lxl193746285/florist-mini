package com.qy.organization.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthRoleUserDTO {

    /**
     * 部门
     */
    @ApiModelProperty("部门")
    private String departmentName;

    /**
     * 员工编码
     */
    @ApiModelProperty("员工编码")
    private String staffNo;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;
}
