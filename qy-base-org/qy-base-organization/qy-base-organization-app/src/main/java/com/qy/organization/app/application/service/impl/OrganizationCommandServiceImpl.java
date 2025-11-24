package com.qy.organization.app.application.service.impl;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.member.api.client.MemberClient;
import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.command.CreateOrganizationCommand;
import com.qy.organization.app.application.command.DeleteOrganizationCommand;
import com.qy.organization.app.application.command.UpdateOrganizationCommand;
import com.qy.organization.app.application.service.OrganizationCommandService;
import com.qy.organization.app.application.service.RoleQueryService;
import com.qy.organization.app.domain.entity.Organization;
import com.qy.organization.app.domain.enums.OpenAccountStatus;
import com.qy.organization.app.domain.enums.OrganizationStatus;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.infrastructure.persistence.OrganizationDomainRepository;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 组织命令实现
 *
 * @author legendjw
 */
@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {
    public static String industryCodeTableCode = "organization_industry";
    public static String scaleCodeTableCode = "organization_scale";
    private OrganizationDomainRepository organizationDomainRepository;
    private CodeTableClient codeTableClient;
    private AttachmentClient attachmentClient;
    private EmployeeClient employeeClient;
    private RoleQueryService roleQueryService;
    private ApplicationEventPublisher applicationEventPublisher;
    private ServiceMatcher serviceMatcher;
    private final Destination.Factory destinationFactory;
    private MemberClient memberClient;

    public OrganizationCommandServiceImpl(OrganizationDomainRepository organizationDomainRepository, CodeTableClient codeTableClient,
                                          AttachmentClient attachmentClient, RoleQueryService roleQueryService,EmployeeClient employeeClient,
                                          ApplicationEventPublisher applicationEventPublisher, ServiceMatcher serviceMatcher,
                                          Destination.Factory destinationFactory, MemberClient memberClient) {
        this.organizationDomainRepository = organizationDomainRepository;
        this.codeTableClient = codeTableClient;
        this.attachmentClient = attachmentClient;
        this.employeeClient = employeeClient;
        this.roleQueryService = roleQueryService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.serviceMatcher = serviceMatcher;
        this.destinationFactory = destinationFactory;
        this.memberClient = memberClient;
    }

    @Override
    public OrganizationId createOrganization(CreateOrganizationCommand command) {
        if (organizationDomainRepository.countByCreatorAndName(command.getCreatorId(), command.getName(), null) > 0) {
            throw new ValidationException("您已经创建了一个同名的组织，请勿重复创建");
        }

        CodeTableItemBasicDTO industryCodeTableItem = command.getIndustryId() != null ? codeTableClient.getSystemCodeTableItem(industryCodeTableCode, command.getIndustryId().toString()) : null;
        CodeTableItemBasicDTO scaleCodeTableItem = command.getScaleId() != null ? codeTableClient.getSystemCodeTableItem(scaleCodeTableCode, command.getScaleId().toString()) : null;
        //转换logo地址
        if (StringUtils.isNotBlank(command.getLogo())) {
            AttachmentBasicDTO attachmentBasicDTO = attachmentClient.getBasicAttachment(Long.valueOf(command.getLogo()));
            command.setLogo(attachmentBasicDTO != null ? attachmentBasicDTO.getUrl() : "");
        }

        Organization organization = Organization.builder()
                .name(new PinyinName(command.getName()))
                .shortName(StringUtils.isNotBlank(command.getShortName()) ? new PinyinName(command.getShortName()) : null)
                .logo(command.getLogo())
                .tel(command.getTel())
                .contactName(command.getContactName())
                .parentId(command.getParentId() != null ? new ParentId(command.getParentId()) : null)
                .email(command.getEmail())
                .homepage(command.getHomepage())
                .address(command.getAddress())
                .industry(industryCodeTableItem != null ? new OrganizationIndustry(Integer.valueOf(industryCodeTableItem.getId()), industryCodeTableItem.getName()) : null)
                .scale(scaleCodeTableItem != null ? new OrganizationScale(Integer.valueOf(scaleCodeTableItem.getId()), scaleCodeTableItem.getName()) : null)
                .status(OrganizationStatus.ENABLE)
                .sourceData(new OrganizationSourceData(command.getSource(), command.getSourceName(), command.getSourceDataId(), command.getSourceDataName()))
                .creator(new User(command.getCreatorId(), command.getCreatorName()))
                .createTime(LocalDateTime.now())
                .openStatus(OpenAccountStatus.NOT_OPENED)
                .build();
        OrganizationId organizationId = organizationDomainRepository.save(organization);

        return organizationId;
    }

    @Override
    public void updateOrganization(UpdateOrganizationCommand command) {
        Organization organization = findById(new OrganizationId(command.getId()));
        if (organizationDomainRepository.countByCreatorAndName(organization.getCreator().getId(), command.getName(), organization.getId().getId()) > 0) {
            throw new ValidationException("组织名称重复了请更换一个新的名称");
        }

        CodeTableItemBasicDTO industryCodeTableItem = command.getIndustryId() != null ? codeTableClient.getSystemCodeTableItem(industryCodeTableCode, command.getIndustryId().toString()) : null;
        CodeTableItemBasicDTO scaleCodeTableItem = command.getScaleId() != null ? codeTableClient.getSystemCodeTableItem(scaleCodeTableCode, command.getScaleId().toString()) : null;
        command.setIndustryName(industryCodeTableItem != null ? industryCodeTableItem.getName() : "");
        command.setScaleName(scaleCodeTableItem != null ? scaleCodeTableItem.getName() : "");
        //转换logo地址
        if (StringUtils.isNotBlank(command.getLogo())) {
            AttachmentBasicDTO attachmentBasicDTO = attachmentClient.getBasicAttachment(Long.valueOf(command.getLogo()));
            command.setLogo(attachmentBasicDTO != null ? attachmentBasicDTO.getUrl() : "");
        }

        organization.modifyInfo(command);

        organizationDomainRepository.save(organization);
    }

    @Override
    public void deleteOrganization(DeleteOrganizationCommand command) {
        Organization organization = findById(new OrganizationId(command.getId()));
        EmployeeBasicDTO superAdmin = employeeClient.getOrganizationCreator(command.getId());
        boolean isSuperAdmin = command.getUser().getId().equals(superAdmin.getUserId());
        if (!organization.isCreator(command.getUser().getId()) && !isSuperAdmin) {
            throw new ValidationException("您没有权限删除组织");
        }

        organizationDomainRepository.remove(new OrganizationId(command.getId()), command.getUser());
    }

    /**
     * 根据id查找组织
     *
     * @param id
     * @return
     */
    private Organization findById(OrganizationId id) {
        Organization organization = organizationDomainRepository.findById(id);
        if (organization == null) {
            throw new NotFoundException("未找到指定的组织");
        }
        return organization;
    }
}
