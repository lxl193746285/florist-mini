package com.qy.organization.app.application.service.impl;

import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.app.application.assembler.OrgDatasourceAssembler;
import com.qy.organization.app.application.assembler.OrganizationAssembler;
import com.qy.organization.app.application.dto.OrgDatasourceDTO;
import com.qy.organization.app.application.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.OrganizationWithIdentityDTO;
import com.qy.organization.app.application.query.OrgDatasourceQuery;
import com.qy.organization.app.application.service.OrgDatasourceQueryService;
import com.qy.organization.app.infrastructure.persistence.OrgDatasourceDataRepository;
import com.qy.organization.app.infrastructure.persistence.OrganizationDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrgDatasourceDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.dto.ContextDTO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织数据源查询服务实现
 *
 */
@Service
public class OrgDatasourceQueryServiceImpl implements OrgDatasourceQueryService {
    private OrgDatasourceDataRepository orgDatasourceDataRepository;
    private OrgDatasourceAssembler orgDatasourceAssembler;

    public OrgDatasourceQueryServiceImpl(OrgDatasourceDataRepository orgDatasourceDataRepository,
                                         OrgDatasourceAssembler orgDatasourceAssembler) {
        this.orgDatasourceDataRepository = orgDatasourceDataRepository;
        this.orgDatasourceAssembler = orgDatasourceAssembler;
    }

    @Override
    public Page<OrgDatasourceDTO> getOrgDatasource(OrgDatasourceQuery query, MemberIdentity identity) {
        Page<OrgDatasourceDO> orgDatasourceDOPage = orgDatasourceDataRepository.findPageByQuery(query);
        return orgDatasourceDOPage.map(organizationDO -> orgDatasourceAssembler.toDTO(organizationDO, identity));
    }

    @Override
    public OrgDatasourceDTO getOrgDatasourceById(Long id) {
        OrgDatasourceDO organizationDO = orgDatasourceDataRepository.findById(id);
        return organizationDO != null ? orgDatasourceAssembler.toDTO(organizationDO, null) : null;
    }

    @Override
    public OrgDatasourceDTO getOrgDatasourceByOrg(Long orgId) {
        return orgDatasourceAssembler.toDTO(orgDatasourceDataRepository.findByOrgId(orgId), null);
    }

    @Override
    public List<OrgDatasourceDTO> getOrgDatasourceByOrgs(List<Long> orgIds) {
        List<OrgDatasourceDO> orgDatasourceDOS = orgDatasourceDataRepository.findByOrgIds(orgIds);
        return orgDatasourceDOS.stream().map(orgDatasourceDO -> orgDatasourceAssembler.toDTO(orgDatasourceDO, null)).collect(Collectors.toList());
    }
}