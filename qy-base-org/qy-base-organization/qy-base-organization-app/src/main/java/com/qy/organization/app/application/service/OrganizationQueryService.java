package com.qy.organization.app.application.service;

import com.qy.organization.app.application.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.OrganizationDTO;
import com.qy.organization.app.application.dto.OrganizationWithIdentityDTO;
import com.qy.organization.app.application.query.OrganizationQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 组织查询服务
 *
 * @author legendjw
 */
public interface OrganizationQueryService {
    /**
     * 获取查询条件获取组织
     *
     * @param query
     * @param identity
     * @return
     */
    Page<OrganizationDTO> getOrganizations(OrganizationQuery query, Identity identity);

    /**
     * 获取所有的组织
     *
     * @return
     */
    List<OrganizationBasicDTO> getAllBasicOrganizations();

    /**
     * 获取指定用户加入的主组织（第一个组织）
     *
     * @return
     */
    OrganizationBasicDTO getUserJoinPrimaryOrganization(Long userId);

    /**
     * 获取指定用户加入的所有组织
     *
     * @return
     */
    List<OrganizationBasicDTO> getUserJoinOrganizations(Long userId);

    /**
     * 获取指定用户拥有指定权限的组织
     *
     * @return
     */
    List<OrganizationBasicDTO> getUserHasPermissionOrganizations(Long userId, String permission);

    /**
     * 获取父级组织下的组织
     *
     * @return
     */
    List<OrganizationBasicDTO> getBasicOrganizationsByParentId(Long parentId);

    /**
     * 获取指定用户加入的公司以及员工身份
     *
     * @param userId
     * @return
     */
    List<OrganizationWithIdentityDTO> getJoinOrganizationWithIdentity(Long userId);

    /**
     * 根据ID查询组织
     *
     * @param id
     * @return
     */
    OrganizationDTO getOrganizationById(Long id);

    /**
     * 根据id查询组织基本信息
     *
     * @param id
     * @return
     */
    OrganizationBasicDTO getBasicOrganizationById(Long id);

    /**
     * 根据id集合查询多个组织基本信息
     *
     * @param ids
     * @return
     */
    List<OrganizationBasicDTO> getBasicOrganizationsByIds(List<Long> ids);

    /**
     * 根据id集合以及查询条件查询多个组织基本信息
     *
     * @param ids
     * @param keywords
     * @return
     */
    List<OrganizationBasicDTO> getBasicOrganizationsByIdsAndKeywords(List<Long> ids, String keywords);
}