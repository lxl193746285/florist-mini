package com.qy.organization.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qy.security.session.EmployeeIdentity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建组织顶级部门命令
 *
 * @author lxl
 */
@Data
public class CreateTopDepartmentCommand implements Serializable {
    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    @NotNull(message = "请选择部门所属组织")
    private Long organizationId;

}
