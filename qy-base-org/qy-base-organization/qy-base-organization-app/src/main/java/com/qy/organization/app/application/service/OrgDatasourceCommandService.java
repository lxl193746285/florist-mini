package com.qy.organization.app.application.service;

import com.qy.organization.app.application.command.OrgDatasourceCommand;
import com.qy.security.session.MemberIdentity;

/**
 * 组织数据源命令服务
 *
 */
public interface OrgDatasourceCommandService {
    /**
     * 创建一个组织数据源
     *
     * @param command
     * @return
     */
    void createOrgDatasource(OrgDatasourceCommand command);

    /**
     * 修改一个组织数据源
     *
     * @param command
     */
    void updateOrgDatasource(OrgDatasourceCommand command);

    /**
     * 删除一个组织数据源
     *
     */
    void deleteOrganization(Long id, MemberIdentity user);
}