package com.qy.organization.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门子部门以及人员信息
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Data
public class DepartmentChildInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 子部门信息
     */
    @ApiModelProperty("子部门信息")
    private List<DepartmentChildInfoDTO> children = new ArrayList<>();

    /**
     * 员工
     */
    @ApiModelProperty("员工")
    private List<EmployeeBasicDTO> employees = new ArrayList<>();
}
