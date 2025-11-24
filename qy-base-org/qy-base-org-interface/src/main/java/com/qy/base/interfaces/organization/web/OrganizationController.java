package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.application.command.*;
import com.qy.organization.app.application.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.OrganizationDTO;
import com.qy.organization.app.application.query.OrganizationQuery;
import com.qy.organization.app.application.service.OpenAccountService;
import com.qy.organization.app.application.service.OrganizationCommandService;
import com.qy.organization.app.application.service.OrganizationQueryService;
import com.qy.organization.app.domain.enums.OrganizationSource;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.security.session.SessionContext;
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
@RequestMapping("/v4/organization/organizations")
public class OrganizationController {
    private SessionContext sessionContext;
    private OrganizationCommandService organizationCommandService;
    private OrganizationQueryService organizationQueryService;
    private OpenAccountService openAccountService;
    private OrganizationSessionContext organizationSessionContext;

    public OrganizationController(SessionContext sessionContext, OrganizationCommandService organizationCommandService,
                                  OrganizationQueryService organizationQueryService, OpenAccountService openAccountService,
                                  OrganizationSessionContext organizationSessionContext) {
        this.sessionContext = sessionContext;
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
        this.openAccountService = openAccountService;
        this.organizationSessionContext = organizationSessionContext;
    }

    /**
     * 获取我加入的组织列表
     *
     * @apiNote 模块id：organization
     *          操作以及权限节点
     *          首页（index） organization/organization/index
     *          查看 (view) organization/organization/view
     *          创建（create） organization/organization/create
     *          编辑（update） organization/organization/update
     *          删除（delete） organization/organization/delete
     *
     *          代码表：
     *          组织行业代码表：organization_industry（系统）
     *          组织规模代码表：organization_scale（系统）
     *
     * @return
     */
    @ApiOperation("获取我加入的组织及下级子公司列表")
    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getJoinOrganizations(OrganizationQuery query) {
        Identity identity = sessionContext.getUser();
        query.setJoinId(identity.getId());
        Page<OrganizationDTO> page = organizationQueryService.getOrganizations(query, identity);
        return ResponseUtils.ok(page).body(page.getRecords());
    }

    @ApiOperation("获取全部公司列表")
    @GetMapping("/all")
    public ResponseEntity<List<OrganizationBasicDTO>> getAllOrganizations() {
        Identity identity = sessionContext.getUser();
        List<OrganizationBasicDTO> organizations = organizationQueryService.getAllBasicOrganizations();
        return ResponseUtils.ok().body(organizations);
    }

    /**
     * 获取单个组织
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个组织")
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganization(
        @PathVariable(value = "id") Long id
    ) {
        OrganizationDTO organizationDTO = organizationQueryService.getOrganizationById(id);
        if (organizationDTO == null) {
            throw new NotFoundException("未找到指定的组织");
        }
        return ResponseUtils.ok().body(organizationDTO);
    }

    /**
     * 创建单个组织
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个组织")
    @PostMapping
    public ResponseEntity<Object> createOrganization(
        @Valid @RequestBody CreateOrganizationCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setCreatorId(sessionContext.getUser().getId());
        command.setCreatorName(sessionContext.getUser().getName());
        command.setSource(OrganizationSource.REGISTER.getId());
        command.setSourceName(OrganizationSource.REGISTER.getName());
        if (command.getParentId() == null){
            command.setParentId(organizationSessionContext.getEmployee().getOrganizationId());
        }
        organizationCommandService.createOrganization(command);

        return ResponseUtils.ok("组织创建成功").build();
    }

    /**
     * 修改单个组织
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个组织")
    @PatchMapping("/{id}")
    @PreAuthorize("@sessionContext.user.hasPermission(@organizationPermission, @organizationPermission.UPDATE, #id)")
    public ResponseEntity<Object> updateOrganization(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateOrganizationCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setUpdatorId(sessionContext.getUser().getId());
        command.setUpdatorName(sessionContext.getUser().getName());
        organizationCommandService.updateOrganization(command);

        return ResponseUtils.ok("组织修改成功").build();
    }

    /**
     * 删除单个组织
     *
     * @param id
     */
    @ApiOperation("删除单个组织")
    @DeleteMapping("/{id}")
    @PreAuthorize("@sessionContext.user.hasPermission(@organizationPermission, @organizationPermission.DELETE, #id)")
    public ResponseEntity<Object> deleteOrganization(
        @PathVariable(value = "id") Long id
    ) {
        DeleteOrganizationCommand command = new DeleteOrganizationCommand();
        command.setId(id);
        command.setUser(sessionContext.getUser());
        organizationCommandService.deleteOrganization(command);

        return ResponseUtils.noContent("删除组织成功").build();
    }

    /**
     * 组织开户
     *
     * @param command
     * @return
     */
    @ApiOperation("组织开户")
    @PostMapping("/open-account/{id}")
    @PreAuthorize("@sessionContext.user.hasPermission(@organizationPermission, @organizationPermission.OPEN_ACCOUNT, #id)")
    public ResponseEntity<Object> openAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody OpenAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setOpenOrganizationId(id);
        command.setOrganizationId(organizationSessionContext.getEmployee().getOrganizationId());
        openAccountService.openAccount(command);

        return ResponseUtils.ok("组织开户成功").build();
    }

    /**
     * 修改开户信息
     *
     * @param command
     * @return
     */
    @ApiOperation("修改开户信息")
    @PatchMapping("/open-account/{id}")
    @PreAuthorize("@sessionContext.user.hasPermission(@organizationPermission, @organizationPermission.CHANGE_ACCOUNT, #id)")
    public ResponseEntity<Object> updateOpenAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdateOpenAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setOpenAccountId(id);
        openAccountService.updateOpenAccount(command);

        return ResponseUtils.ok("修改开户信息成功").build();
    }

}

