package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.application.command.*;
import com.qy.organization.app.application.dto.OrgDatasourceDTO;
import com.qy.organization.app.application.dto.OrganizationDTO;
import com.qy.organization.app.application.query.OrgDatasourceQuery;
import com.qy.organization.app.application.query.OrganizationQuery;
import com.qy.organization.app.application.service.*;
import com.qy.organization.app.domain.enums.OrganizationSource;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织
 *
 * @author legendjw
 * @since 2021-07-22
 */
@RestController
@Api(tags = "组织")
@RequestMapping("/v4/organization/ord-datasource")
public class OrgDatasourceController {
    private MemberSystemSessionContext memberSystemSessionContext;
    private OrgDatasourceQueryService orgDatasourceQueryService;
    private OrgDatasourceCommandService orgDatasourceCommandService;

    public OrgDatasourceController(MemberSystemSessionContext memberSystemSessionContext,
                                   OrgDatasourceQueryService orgDatasourceQueryService,
                                   OrgDatasourceCommandService orgDatasourceCommandService) {
        this.memberSystemSessionContext = memberSystemSessionContext;
        this.orgDatasourceQueryService = orgDatasourceQueryService;
        this.orgDatasourceCommandService = orgDatasourceCommandService;
    }

    /**
     * 获取组织数据源列表
     *
     * @return
     */
    @ApiOperation("获取组织数据源列表")
    @GetMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_org_datasource', 'index')")
    public ResponseEntity<List<OrgDatasourceDTO>> getOrgDatasource(OrgDatasourceQuery query) {
        MemberIdentity identity = memberSystemSessionContext.getMember();
        Page<OrgDatasourceDTO> page = orgDatasourceQueryService.getOrgDatasource(query, identity);
        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个组织数据源
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个组织数据源")
    @GetMapping("/{id}")
    public ResponseEntity<OrgDatasourceDTO> getOrganization(
        @PathVariable(value = "id") Long id
    ) {
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceQueryService.getOrgDatasourceById(id);
        if (orgDatasourceDTO == null) {
            throw new NotFoundException("未找到指定的组织数据源");
        }
        return ResponseUtils.ok().body(orgDatasourceDTO);
    }

    /**
     * 创建单个组织数据源
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个组织数据源")
    @PostMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_org_datasource', 'create')")
    public ResponseEntity<Object> createOrganization(
        @Valid @RequestBody OrgDatasourceCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setUser(memberSystemSessionContext.getMember());
        orgDatasourceCommandService.createOrgDatasource(command);

        return ResponseUtils.ok("组织创建成功").build();
    }

    /**
     * 修改单个组织数据源
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个组织数据源")
    @PatchMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_org_datasource', 'update')")
    public ResponseEntity<Object> updateOrganization(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody OrgDatasourceCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setUser(memberSystemSessionContext.getMember());
        orgDatasourceCommandService.updateOrgDatasource(command);

        return ResponseUtils.ok("组织修改成功").build();
    }

    /**
     * 删除单个组织数据源
     *
     * @param id
     */
    @ApiOperation("删除单个组织数据源")
    @DeleteMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_org_datasource', 'delete')")
    public ResponseEntity<Object> deleteOrganization(
        @PathVariable(value = "id") Long id
    ) {
        orgDatasourceCommandService.deleteOrganization(id, memberSystemSessionContext.getMember());

        return ResponseUtils.noContent("删除成功").build();
    }
}

