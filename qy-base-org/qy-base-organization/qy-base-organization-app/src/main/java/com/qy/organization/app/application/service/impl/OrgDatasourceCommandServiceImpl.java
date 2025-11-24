package com.qy.organization.app.application.service.impl;

import com.qy.organization.app.application.command.OrgDatasourceCommand;
import com.qy.organization.app.application.service.OrgDatasourceCommandService;
import com.qy.organization.app.infrastructure.persistence.OrgDatasourceDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrgDatasourceDO;
import com.qy.security.session.MemberIdentity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 组织数据源命令实现
 *
 */
@Service
public class OrgDatasourceCommandServiceImpl implements OrgDatasourceCommandService {
    private OrgDatasourceDataRepository orgDatasourceDataRepository;

    public OrgDatasourceCommandServiceImpl(OrgDatasourceDataRepository orgDatasourceDataRepository) {
        this.orgDatasourceDataRepository = orgDatasourceDataRepository;
    }

    @Override
    public void createOrgDatasource(OrgDatasourceCommand command) {
        OrgDatasourceDO orgDatasourceDO = new OrgDatasourceDO();
        orgDatasourceDO.setDatasourceName(command.getDatasourceName());
        orgDatasourceDO.setOrgId(command.getOrgId());
        orgDatasourceDO.setCreateTime(LocalDateTime.now());
        orgDatasourceDO.setCreatorId(command.getUser().getId());
        orgDatasourceDataRepository.save(orgDatasourceDO);
    }

    @Override
    public void updateOrgDatasource(OrgDatasourceCommand command) {
        OrgDatasourceDO orgDatasourceDO = new OrgDatasourceDO();
        orgDatasourceDO.setDatasourceName(command.getDatasourceName());
        orgDatasourceDO.setOrgId(command.getOrgId());
        orgDatasourceDO.setUpdateTime(LocalDateTime.now());
        orgDatasourceDO.setUpdatorId(command.getUser().getId());
        orgDatasourceDataRepository.save(orgDatasourceDO);
    }

    @Override
    public void deleteOrganization(Long id, MemberIdentity user) {
        orgDatasourceDataRepository.remove(id, user.getId());
    }
}
