package com.qy.organization.app.application.command;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 删除员工命令
 *
 * @author legendjw
 */
@Data
public class OrganizationCreatorCommand implements Serializable {

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 权限组ids
     */
    private List<Long> roleIds;

    /**
     * 用户id
     */
    private Long userId;
}