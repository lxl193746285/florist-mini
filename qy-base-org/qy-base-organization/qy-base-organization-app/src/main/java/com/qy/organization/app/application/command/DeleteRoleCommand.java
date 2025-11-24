package com.qy.organization.app.application.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除权限组命令
 *
 * @author legendjw
 */
@Data
public class DeleteRoleCommand implements Serializable {
    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity employee;
    /**
     * id
     */
    private Long id;
}
