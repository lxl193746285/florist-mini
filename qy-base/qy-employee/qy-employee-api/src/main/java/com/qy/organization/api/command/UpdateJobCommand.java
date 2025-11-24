package com.qy.organization.api.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 编辑岗位命令
 *
 * @author legendjw
 */
@Data
public class UpdateJobCommand implements Serializable {
    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity employee;

    private String jobCode;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 名称
     */
    @NotBlank(message = "请输入岗位名称")
    private String name;

    /**
     * 员工id集合
     */
    private List<Long> employeeIds;

    /**
     * 岗位描述
     */
    private String description;

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