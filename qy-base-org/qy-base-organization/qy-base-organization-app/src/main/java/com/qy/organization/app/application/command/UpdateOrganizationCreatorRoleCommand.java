package com.qy.organization.app.application.command;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 修改组织创始人的角色
 *
 * @author legendjw
 */
@Data
public class UpdateOrganizationCreatorRoleCommand implements Serializable {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 权限组ID集合
     */
    private List<Long> roleIds;
}