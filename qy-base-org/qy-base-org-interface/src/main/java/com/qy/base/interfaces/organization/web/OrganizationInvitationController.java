package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.application.command.InviteJoinOrganizationCommand;
import com.qy.organization.app.application.command.SendOrganizationInviteLinkCommand;
import com.qy.organization.app.application.dto.OrganizationInvitationInfoDTO;
import com.qy.organization.app.application.dto.OrganizationInvitationLinkDTO;
import com.qy.organization.app.application.service.OrganizationInvitationService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 组织邀请
 *
 * @author legendjw
 * @since 2021-07-22
 */
@RestController
@RequestMapping("/v4/organization/invitations")
public class OrganizationInvitationController {
    private OrganizationSessionContext sessionContext;
    private OrganizationInvitationService organizationInvitationService;

    public OrganizationInvitationController(OrganizationSessionContext sessionContext, OrganizationInvitationService organizationInvitationService) {
        this.sessionContext = sessionContext;
        this.organizationInvitationService = organizationInvitationService;
    }

    /**
     * 获取组织邀请链接
     *
     * @param organizationId 组织id
     * @return
     */
    @GetMapping("/link")
    public ResponseEntity<OrganizationInvitationLinkDTO> getOrganizationInvitationLink(
            @RequestParam(value = "organization_id") Long organizationId
    ) {
        EmployeeIdentity identity = sessionContext.getEmployee(organizationId);
        return ResponseUtils.ok().body(organizationInvitationService.getOrganizationInvitationLink(organizationId, identity));
    }

    /**
     * 根据邀请码获取邀请信息
     *
     * @param code 邀请码
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<OrganizationInvitationInfoDTO> getOrganizationInvitationInfo(
            @RequestParam(value = "code") String code
    ) {
        return ResponseUtils.ok().body(organizationInvitationService.getOrganizationInvitationInfo(code));
    }

    /**
     * 邀请加入公司
     *
     * @param command
     * @return
     */
    @PostMapping("/join-organization")
    public ResponseEntity<Object> inviteJoinOrganization(
        @Valid @RequestBody InviteJoinOrganizationCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Identity identity = sessionContext.getUser();
        command.setUserId(identity.getId());
        organizationInvitationService.inviteJoinOrganization(command);

        return ResponseUtils.ok("加入组织成功").build();
    }

    /**
     * 发送组织邀请链接
     *
     * @param command
     * @return
     */
    @PostMapping("/send-invite-link")
    public ResponseEntity<Object> sendOrganizationInviteLink(
            @Valid @RequestBody SendOrganizationInviteLinkCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        EmployeeIdentity identity = sessionContext.getEmployee(command.getOrganizationId());
        organizationInvitationService.sendOrganizationInviteLink(command, identity);

        return ResponseUtils.ok("发送邀请链接成功").build();
    }
}

