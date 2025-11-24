package com.qy.organization.app.application.service;

import com.qy.organization.app.application.command.CreateOrganizationCommand;
import com.qy.organization.app.application.command.DeleteOrganizationCommand;
import com.qy.organization.app.application.command.UpdateOrganizationCommand;
import com.qy.organization.app.domain.valueobject.OrganizationId;

/**
 * 组织命令服务
 *
 * @author legendjw
 */
public interface OrganizationCommandService {
    /**
     * 创建一个组织
     *
     * @param command
     * @return
     */
    OrganizationId createOrganization(CreateOrganizationCommand command);

    /**
     * 修改一个组织信息
     *
     * @param command
     */
    void updateOrganization(UpdateOrganizationCommand command);

    /**
     * 删除一个组织
     *
     * @param command
     */
    void deleteOrganization(DeleteOrganizationCommand command);
}