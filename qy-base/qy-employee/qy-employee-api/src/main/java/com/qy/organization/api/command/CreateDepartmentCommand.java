package com.qy.organization.api.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建部门命令
 *
 * @author legendjw
 */
@Data
public class CreateDepartmentCommand implements Serializable {
    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity employee;

    /**
     * 组织id
     */
    @NotNull(message = "请选择部门所属组织")
    private Long organizationId;

    /**
     * 父级id
     */
    @NotNull(message = "请选择父级部门")
    private Long parentId;

    /**
     * 名称
     */
    @NotBlank(message = "请输入部门名称")
    private String name;

    /**
     * 部门负责人
     */
    private Long leaderId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     */
    private Integer statusId;

    /**
     * 备注
     */
    private String remark;
}
