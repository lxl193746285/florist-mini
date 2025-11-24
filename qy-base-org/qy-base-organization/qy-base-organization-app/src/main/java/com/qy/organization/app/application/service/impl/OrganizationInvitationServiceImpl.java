package com.qy.organization.app.application.service.impl;

import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.app.application.command.InviteJoinOrganizationCommand;
import com.qy.organization.app.application.command.SendOrganizationInviteLinkCommand;
import com.qy.organization.app.application.dto.OrganizationInvitationInfoDTO;
import com.qy.organization.app.application.dto.OrganizationInvitationLinkDTO;
import com.qy.organization.app.application.service.OrganizationInvitationService;
import com.qy.organization.app.application.service.OrganizationQueryService;
import com.qy.organization.app.domain.entity.OrganizationInvitationLink;
import com.qy.organization.app.domain.valueobject.InvitationCode;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.organization.app.domain.valueobject.User;
import com.qy.organization.app.domain.valueobject.UserId;
import com.qy.organization.app.infrastructure.persistence.OrganizationInvitationDomainRepository;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * 组织邀请服务实现
 *
 * @author legendjw
 */
@Service
public class OrganizationInvitationServiceImpl implements OrganizationInvitationService {
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT);
    @Value("${qy.invitation.front-access-url}")
    private String invitationFrontAccessUrl;

    private OrganizationInvitationDomainRepository organizationInvitationDomainRepository;
    private OrganizationQueryService organizationQueryService;
    private EmployeeClient employeeClient;

    public OrganizationInvitationServiceImpl(OrganizationInvitationDomainRepository organizationInvitationDomainRepository,
                                             OrganizationQueryService organizationQueryService, EmployeeClient employeeClient) {
        this.organizationInvitationDomainRepository = organizationInvitationDomainRepository;
        this.organizationQueryService = organizationQueryService;
        this.employeeClient = employeeClient;
    }

    @Override
    public OrganizationInvitationLinkDTO getOrganizationInvitationLink(Long organizationId, EmployeeIdentity identity) {
        InvitationCode invitationCode = new InvitationCode();
        User user = new User(identity.getId(), identity.getName());
        OrganizationInvitationLink invitationLink = new OrganizationInvitationLink(new OrganizationId(organizationId), invitationCode, user);
        organizationInvitationDomainRepository.save(invitationLink);

        OrganizationInvitationLinkDTO invitationLinkDTO = new OrganizationInvitationLinkDTO();
        invitationLinkDTO.setLink(invitationLink.getLink(invitationFrontAccessUrl));
        invitationLinkDTO.setCreateTime(invitationLink.getCreateTime().format(dateTimeFormatter));
        invitationLinkDTO.setFailureTime(invitationLink.getFailureTime().format(dateTimeFormatter));
        return invitationLinkDTO;
    }

    @Override
    public OrganizationInvitationInfoDTO getOrganizationInvitationInfo(String code) {
        OrganizationInvitationLink invitationLink = organizationInvitationDomainRepository.findByCode(new InvitationCode(code));
        if (invitationLink == null || !invitationLink.isValid()) {
            throw new ValidationException("非法的邀请链接");
        }
        OrganizationInvitationInfoDTO invitationInfoDTO = new OrganizationInvitationInfoDTO();
        invitationInfoDTO.setOrganization(organizationQueryService.getBasicOrganizationById(invitationLink.getOrganizationId().getId()));
        invitationInfoDTO.setInviterName(invitationLink.getCreator().getName());
        invitationInfoDTO.setCreateTime(invitationLink.getCreateTime().format(dateTimeFormatter));
        invitationInfoDTO.setFailureTime(invitationLink.getFailureTime().format(dateTimeFormatter));

        return invitationInfoDTO;
    }

    @Override
    public void inviteJoinOrganization(InviteJoinOrganizationCommand command) {
        OrganizationInvitationLink invitationLink = organizationInvitationDomainRepository.findByCode(new InvitationCode(command.getCode()));
        if (invitationLink == null || !invitationLink.isValid()) {
            throw new ValidationException("非法的邀请链接");
        }
        //TODO 加入组织
//        employeeService.joinOrganization(
//                new UserId(command.getUserId()),
//                new OrganizationId(invitationLink.getOrganizationId().getId()),
//                invitationLink.getCreator().getId(),
//                invitationLink.getCreator().getName()
//        );
    }

    @Override
    public void sendOrganizationInviteLink(SendOrganizationInviteLinkCommand command, EmployeeIdentity identity) {
        OrganizationInvitationLinkDTO invitationLinkDTO = getOrganizationInvitationLink(command.getOrganizationId(), identity);
        //发送邀请链接
    }
}
