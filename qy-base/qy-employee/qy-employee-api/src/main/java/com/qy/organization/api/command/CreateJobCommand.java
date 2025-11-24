package com.qy.organization.api.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 创建岗位命令
 *
 * @author legendjw
 */
@Data
public class CreateJobCommand implements Serializable {

    private String jobCode;

    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity employee;

    /**
     * 组织id
     */
    @NotNull(message = "请选择岗位所属组织")
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