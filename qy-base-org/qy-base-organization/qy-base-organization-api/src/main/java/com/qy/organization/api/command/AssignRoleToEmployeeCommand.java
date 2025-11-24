package com.qy.organization.api.command;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分配角色给指定员工
 *
 * @author legendjw
 */
@Data
public class AssignRoleToEmployeeCommand {
    /**
     * 员工id
     */
    private Long employeeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds = new ArrayList<>();
}