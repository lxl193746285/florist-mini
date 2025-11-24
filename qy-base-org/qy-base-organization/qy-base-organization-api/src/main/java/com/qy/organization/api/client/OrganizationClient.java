package com.qy.organization.api.client;

import com.qy.organization.api.command.OpenAccountCommand;
import com.qy.organization.api.command.UpdateOpenAccountCommand;
import com.qy.organization.api.dto.OpenAccountInfoDTO;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.api.dto.OrganizationDTO;
import com.qy.organization.api.dto.OrganizationWithIdentityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-organization-organization")
public interface OrganizationClient {
    /**
     * 获取所有的组织
     *
     * @return
     */
    @GetMapping("/v4/api/organization/basic-organizations/all")
    List<OrganizationBasicDTO> getAllBasicOrganizations();

    /**
     * 获取组织基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/organization/basic-organizations/{id}")
    OrganizationBasicDTO getBasicOrganizationById(
            @PathVariable(value = "id") Long id
    );

    /**
     * 获取多个组织基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/v4/api/organization/basic-organizations")
    List<OrganizationBasicDTO> getBasicOrganizationsByIds(
            @RequestParam(value = "ids") List<Long> ids
    );

    /**
     * 根据关键字获取多个组织基本信息
     *
     * @param ids
     * @param keywords
     * @return
     */
    @GetMapping("/v4/api/organization/basic-organizations/by-keywords")
    List<OrganizationBasicDTO> getBasicOrganizationsByIdsAndKeywords(
            @RequestParam(value = "ids") List<Long> ids,
            @RequestParam(value = "keywords") String keywords
    );

    /**
     * 获取组织信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/organization/organizations/{id}")
    OrganizationDTO getOrganizationById(
            @PathVariable(value = "id") Long id
    );

    /**
     * 获取指定用户加入的主组织（第一个组织）
     *
     * @param userId
     * @return
     */
    @GetMapping("/v4/api/organization/user-join-primary-organization")
    OrganizationBasicDTO getUserJoinPrimaryOrganization(
            @RequestParam(value = "user_id") Long userId
    );

    /**
     * 获取当前用户加入的组织列表
     *
     * @return
     */
    @GetMapping("/v4/api/organization/user-join-organizations")
    List<OrganizationBasicDTO> getUserJoinOrganizations(
            @RequestParam(value = "user_id") Long userId
    );

    /**
     * 获取当前用户加入的组织以及对应的身份列表
     *
     * @return
     */
    @GetMapping("/v4/api/organization/user-join-organizations-with-identity")
    List<OrganizationWithIdentityDTO> getUserJoinOrganizationsWithIdentity(
            @RequestParam(value = "user_id") Long userId
    );

    /**
     * 获取当前用户有指定权限的组织
     *
     * @return
     */
    @GetMapping("/v4/api/organization/user-has-permission-organizations")
    List<OrganizationBasicDTO> getUserHasPermissionOrganizations(
            @RequestParam(value = "permission") String permission
    );

    /**
     * 获取父级组织下的组织
     *
     * @return
     */
    @GetMapping("/v4/api/organization/basic-organizations/by-parent")
    List<OrganizationBasicDTO> getBasicOrganizationsByParentId(
            @RequestParam(value = "parent_id") Long parentId
    );

    /**
     * 组织开户
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/organization/open-account")
    OrganizationBasicDTO openAccount(
            @Valid @RequestBody OpenAccountCommand command
    );

    /**
     * 修改开户信息
     *
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/organization/open-account")
    void updateOpenAccount(
            @Valid @RequestBody UpdateOpenAccountCommand command
    );

    /**
     * 获取指定来源模块开户的信息
     *
     * @param source
     * @param sourceDataId
     * @return
     */
    @GetMapping("/v4/api/organization/open-account-info")
    OpenAccountInfoDTO getOpenAccountInfo(
            @RequestParam(value = "source") String source,
            @RequestParam(value = "source_data_id") Long sourceDataId
    );

    /**
     * 获取指定组织开户的信息
     *
     * @param organizationId
     * @return
     */
    @GetMapping("/v4/api/organization/{organizationId}/open-account-info")
    OpenAccountInfoDTO getOpenAccountInfo(
            @PathVariable(value = "organizationId") Long organizationId
    );
}
