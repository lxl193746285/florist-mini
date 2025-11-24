package com.qy.organization.app.application.service;

import com.qy.organization.app.application.dto.OrgDatasourceDTO;
import com.qy.organization.app.application.query.OrgDatasourceQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.MemberIdentity;

import java.util.List;

/**
 * 组织查询服务
 *
 * @author legendjw
 */
public interface OrgDatasourceQueryService {
    /**
     * 获取查询条件获取组织
     *
     * @param query
     * @param identity
     * @return
     */
    Page<OrgDatasourceDTO> getOrgDatasource(OrgDatasourceQuery query, MemberIdentity identity);

    /**
     * 根据ID查询组织
     *
     * @param id
     * @return
     */
    OrgDatasourceDTO getOrgDatasourceById(Long id);

    OrgDatasourceDTO getOrgDatasourceByOrg(Long orgId);
    List<OrgDatasourceDTO> getOrgDatasourceByOrgs(List<Long> orgIds);

}