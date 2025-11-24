package com.qy.organization.api.client;

import com.qy.organization.api.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-organization-datasource")
public interface OrgDatasourceClient {
    /**
     * 获取所有的组织
     *
     * @return
     */
    @GetMapping("/v4/api/org-datasource")
    OrgDatasourceDTO getBasicOrganizationByOrgId(
            @RequestParam(value = "org_id") Long orgId);

    /**
     * 获取组织数据源
     *
     * @return
     */
    @GetMapping("/v4/api/org-datasource/datasources")
    public List<OrgDatasourceDTO> getBasicOrganizationByOrgIds(
            @RequestParam(value = "org_ids") List<Long> orgIds);
}
