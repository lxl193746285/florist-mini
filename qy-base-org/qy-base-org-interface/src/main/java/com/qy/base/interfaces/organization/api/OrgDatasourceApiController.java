package com.qy.base.interfaces.organization.api;

import com.qy.organization.app.application.command.OpenAccountCommand;
import com.qy.organization.app.application.command.UpdateOpenAccountCommand;
import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.application.service.OpenAccountService;
import com.qy.organization.app.application.service.OrgDatasourceCommandService;
import com.qy.organization.app.application.service.OrgDatasourceQueryService;
import com.qy.organization.app.application.service.OrganizationQueryService;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织数据源内部服务接口
 *
 */
@RestController
@RequestMapping("/v4/api/org-datasource")
public class OrgDatasourceApiController {
    private OrgDatasourceQueryService orgDatasourceQueryService;

    public OrgDatasourceApiController(OrgDatasourceQueryService orgDatasourceQueryService) {
        this.orgDatasourceQueryService = orgDatasourceQueryService;
    }

    /**
     * 获取组织数据源
     *
     * @param orgId
     * @return
     */
    @GetMapping
    public OrgDatasourceDTO getBasicOrganizationByOrgId(
            @RequestParam(value = "org_id") Long orgId
    ) {
        return orgDatasourceQueryService.getOrgDatasourceByOrg(orgId);
    }

    /**
     * 获取组织数据源
     *
     * @return
     */
    @GetMapping("/datasources")
    public List<OrgDatasourceDTO> getBasicOrganizationByOrgIds(
            @RequestParam(value = "org_ids") List<Long> orgIds
    ) {
        return orgDatasourceQueryService.getOrgDatasourceByOrgs(orgIds);
    }
}