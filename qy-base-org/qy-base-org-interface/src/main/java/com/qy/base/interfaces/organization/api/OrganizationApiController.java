package com.qy.base.interfaces.organization.api;

import com.qy.organization.app.application.command.OpenAccountCommand;
import com.qy.organization.app.application.command.UpdateOpenAccountCommand;
import com.qy.organization.app.application.dto.OpenAccountInfoDTO;
import com.qy.organization.app.application.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.OrganizationDTO;
import com.qy.organization.app.application.dto.OrganizationWithIdentityDTO;
import com.qy.organization.app.application.service.OpenAccountService;
import com.qy.organization.app.application.service.OrganizationCommandService;
import com.qy.organization.app.application.service.OrganizationQueryService;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织内部服务接口
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/organization")
public class OrganizationApiController {
    private OrganizationSessionContext sessionContext;
    private OrganizationQueryService organizationQueryService;
    private OpenAccountService openAccountService;

    public OrganizationApiController(OrganizationSessionContext sessionContext,
                                     OrganizationQueryService organizationQueryService, OpenAccountService openAccountService) {
        this.sessionContext = sessionContext;
        this.organizationQueryService = organizationQueryService;
        this.openAccountService = openAccountService;
    }

    /**
     * 获取所有的组织
     *
     * @return
     */
    @GetMapping("/basic-organizations/all")
    public ResponseEntity<List<OrganizationBasicDTO>> getAllBasicOrganizations() {
        return ResponseUtils.ok().body(organizationQueryService.getAllBasicOrganizations());
    }

    /**
     * 获取组织基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/basic-organizations/{id}")
    public ResponseEntity<OrganizationBasicDTO> getBasicOrganizationById(
            @PathVariable(value = "id") Long id
    ) {
        OrganizationBasicDTO organizationDTO = organizationQueryService.getBasicOrganizationById(id);
        return ResponseUtils.ok().body(organizationDTO);
    }

    /**
     * 获取多个组织基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/basic-organizations")
    public ResponseEntity<List<OrganizationBasicDTO>> getBasicOrganizationsByIds(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        return ResponseUtils.ok().body(organizationQueryService.getBasicOrganizationsByIds(ids));
    }

    /**
     * 根据关键字获取多个组织基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/basic-organizations/by-keywords")
    public ResponseEntity<List<OrganizationBasicDTO>> getBasicOrganizationsByIdsAndKeywords(
            @RequestParam(value = "ids") List<Long> ids,
            @RequestParam(value = "keywords", required = false) String keywords
    ) {
        return ResponseUtils.ok().body(organizationQueryService.getBasicOrganizationsByIdsAndKeywords(ids, keywords));
    }

    /**
     * 获取组织信息
     *
     * @param id
     * @return
     */
    @GetMapping("/organizations/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(
            @PathVariable(value = "id") Long id
    ) {
        OrganizationDTO organizationDTO = organizationQueryService.getOrganizationById(id);
        return ResponseUtils.ok().body(organizationDTO);
    }

    /**
     * 获取指定用户加入的主组织（第一个组织）
     *
     * @param userId
     * @return
     */
    @GetMapping("/user-join-primary-organization")
    public ResponseEntity<OrganizationBasicDTO> getUserJoinPrimaryOrganization(
            @RequestParam(value = "user_id") Long userId
    ) {
        return ResponseUtils.ok().body(organizationQueryService.getUserJoinPrimaryOrganization(userId));
    }

    /**
     * 获取当前用户加入的组织列表
     *
     * @return
     */
    @GetMapping("/user-join-organizations")
    public ResponseEntity<List<OrganizationBasicDTO>> getUserJoinOrganizations(
            @RequestParam(value = "user_id") Long userId
    ) {
        return ResponseUtils.ok().body(organizationQueryService.getUserJoinOrganizations(userId));
    }

    /**
     * 获取当前用户加入的组织以及对应的身份列表
     *
     * @return
     */
    @GetMapping("/user-join-organizations-with-identity")
    public ResponseEntity<List<OrganizationWithIdentityDTO>> getJoinOrganizationsWithIdentity(
            @RequestParam(value = "user_id") Long userId
    ) {
        return ResponseUtils.ok().body(organizationQueryService.getJoinOrganizationWithIdentity(userId));
    }

    /**
     * 获取当前用户有指定权限的组织
     *
     * @return
     */
    @GetMapping("/user-has-permission-organizations")
    public ResponseEntity<List<OrganizationBasicDTO>> getUserHasPermissionOrganizations(
            @RequestParam(value = "permission") String permission
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(organizationQueryService.getUserHasPermissionOrganizations(identity.getId(), permission));
    }

    /**
     * 获取父级组织下的组织
     *
     * @return
     */
    @GetMapping("/basic-organizations/by-parent")
    public ResponseEntity<List<OrganizationBasicDTO>> getBasicOrganizationsByParentId(
            @RequestParam(value = "parent_id") Long parentId
    ) {
        return ResponseUtils.ok().body(organizationQueryService.getBasicOrganizationsByParentId(parentId));
    }

    /**
     * 获取指定来源模块开户的信息
     *
     * @param source
     * @param sourceDataId
     * @return
     */
    @GetMapping("/open-account-info")
    public ResponseEntity<OpenAccountInfoDTO> getOpenAccountInfo(
            @RequestParam(value = "source") String source,
            @RequestParam(value = "source_data_id") Long sourceDataId
    ) {
        return ResponseUtils.ok().body(openAccountService.getOpenAccountInfo(source, sourceDataId));
    }

    /**
     * 获取指定组织开户的信息
     *
     * @param organizationId
     * @return
     */
    @GetMapping("/{organizationId}/open-account-info")
    public ResponseEntity<OpenAccountInfoDTO> getOpenAccountInfo(
            @PathVariable(value = "organizationId") Long organizationId
    ) {
        return ResponseUtils.ok().body(openAccountService.getOpenAccountInfo(organizationId));
    }

    /**
     * 组织开户
     *
     * @param command
     * @return
     */
    @PostMapping("/open-account")
    public ResponseEntity<OrganizationBasicDTO> openAccount(
            @Valid @RequestBody OpenAccountCommand command
    ) {
        OrganizationId organizationId = openAccountService.openAccount(command);

        return ResponseUtils.ok("开户成功").body(organizationQueryService.getBasicOrganizationById(organizationId.getId()));
    }

    /**
     * 修改开户信息
     *
     * @param command
     * @return
     */
    @PatchMapping("/open-account")
    public ResponseEntity<Object> updateOpenAccount(
            @Valid @RequestBody UpdateOpenAccountCommand command
    ) {
        openAccountService.updateOpenAccount(command);

        return ResponseUtils.ok("修改开户信息成功").build();
    }
}