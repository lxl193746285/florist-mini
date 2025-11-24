package com.qy.organization.app.application.service.impl;

import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.app.application.assembler.OrganizationAssembler;
import com.qy.organization.app.application.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.dto.OrganizationDTO;
import com.qy.organization.app.application.dto.OrganizationWithIdentityDTO;
import com.qy.organization.app.application.query.OrganizationQuery;
import com.qy.organization.app.application.service.OrganizationQueryService;
import com.qy.organization.app.infrastructure.persistence.OrganizationDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.dto.ContextDTO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织查询服务实现
 *
 * @author legendjw
 */
@Service
public class OrganizationQueryServiceImpl implements OrganizationQueryService {
    private OrganizationDataRepository organizationDataRepository;
    private OrganizationAssembler organizationAssembler;
    private EmployeeClient employeeClient;
    private AuthClient authClient;

    public OrganizationQueryServiceImpl(OrganizationDataRepository organizationDataRepository, OrganizationAssembler organizationAssembler,
                                        EmployeeClient employeeClient, AuthClient authClient) {
        this.organizationDataRepository = organizationDataRepository;
        this.organizationAssembler = organizationAssembler;
        this.employeeClient = employeeClient;
        this.authClient = authClient;
    }

    @Override
    public Page<OrganizationDTO> getOrganizations(OrganizationQuery query, Identity identity) {
        Page<OrganizationDO> organizationDOPage = organizationDataRepository.findPageByQuery(query);
        return organizationDOPage.map(organizationDO -> organizationAssembler.toDTO(organizationDO, identity));
    }

    @Override
    public List<OrganizationBasicDTO> getAllBasicOrganizations() {
        List<OrganizationDO> organizationDOS = organizationDataRepository.findAllOrganizations();
        return organizationDOS.stream().map(organizationDO -> organizationAssembler.toBasicDTO(organizationDO)).collect(Collectors.toList());
    }

    @Override
    public OrganizationBasicDTO getUserJoinPrimaryOrganization(Long userId) {
        List<OrganizationDO> organizationDOS = organizationDataRepository.findUserJoinOrganizations(userId);
        return organizationDOS.isEmpty() ? null : organizationAssembler.toBasicDTO(organizationDOS.get(0));
    }

    @Override
    public List<OrganizationBasicDTO> getUserJoinOrganizations(Long userId) {
        List<OrganizationDO> organizationDOS = organizationDataRepository.findUserJoinOrganizations(userId);
        return organizationDOS.stream().map(organizationDO -> organizationAssembler.toBasicDTO(organizationDO)).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationBasicDTO> getUserHasPermissionOrganizations(Long userId, String permission) {
        List<ContextDTO> contextDTOS = authClient.getHasPermissionContexts(userId.toString(), OrganizationSessionContext.contextId, permission, 1L);
        List<Long> contextIds = contextDTOS.stream().map(c -> Long.valueOf(c.getContextId())).collect(Collectors.toList());
        if (contextDTOS.isEmpty() || contextIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<OrganizationDO> organizationDOS = organizationDataRepository.findByIds(contextIds);
        return organizationDOS.stream().map(organizationDO -> organizationAssembler.toBasicDTO(organizationDO)).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationBasicDTO> getBasicOrganizationsByParentId(Long parentId) {
        List<OrganizationDO> organizationDOS = organizationDataRepository.findByParentId(parentId);
        return organizationDOS.stream().map(organizationDO -> organizationAssembler.toBasicDTO(organizationDO)).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationWithIdentityDTO> getJoinOrganizationWithIdentity(Long userId) {
        List<OrganizationBasicDTO> organizationDTOS = getUserJoinOrganizations(userId);
        return organizationDTOS.stream().map(o -> {
            OrganizationWithIdentityDTO identityDTO = new OrganizationWithIdentityDTO();
            identityDTO.setId(o.getId());
            identityDTO.setName(o.getName());
            identityDTO.setShortName(o.getShortName());
            identityDTO.setLogo(o.getLogo());
            identityDTO.setEmployee(employeeClient.getUserJoinOrganizationBasicEmployee(o.getId(), userId));
            return identityDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO getOrganizationById(Long id) {
        OrganizationDO organizationDO = organizationDataRepository.findById(id);
        return organizationDO != null ? organizationAssembler.toDTO(organizationDO, null) : null;
    }

    @Override
    public OrganizationBasicDTO getBasicOrganizationById(Long id) {
        OrganizationDO organizationDO = organizationDataRepository.findById(id);
        return organizationDO != null ? organizationAssembler.toBasicDTO(organizationDO) : null;
    }

    @Override
    public List<OrganizationBasicDTO> getBasicOrganizationsByIds(List<Long> ids) {
        List<OrganizationDO> organizationDOs = organizationDataRepository.findByIds(ids);
        return organizationDOs.stream().map(o -> organizationAssembler.toBasicDTO(o)).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationBasicDTO> getBasicOrganizationsByIdsAndKeywords(List<Long> ids, String keywords) {
        List<OrganizationDO> organizationDOs = organizationDataRepository.findByIdsAndKeywords(ids, keywords);
        return organizationDOs.stream().map(o -> organizationAssembler.toBasicDTO(o)).collect(Collectors.toList());
    }
}