package com.qy.organization.app.application.service;

import com.qy.organization.app.application.command.InviteJoinOrganizationCommand;
import com.qy.organization.app.application.command.SendOrganizationInviteLinkCommand;
import com.qy.organization.app.application.dto.OrganizationInvitationInfoDTO;
import com.qy.organization.app.application.dto.OrganizationInvitationLinkDTO;
import com.qy.security.session.EmployeeIdentity;

/**
 * 组织邀请服务
 *
 * @author legendjw
 */
public interface OrganizationInvitationService {
    /**
     * 获取组织邀请链接
     *
     * @param organizationId
     * @param identity
     * @return
     */
    OrganizationInvitationLinkDTO getOrganizationInvitationLink(Long organizationId, EmployeeIdentity identity);

    /**
     * 根据邀请码获取邀请信息
     *
     * @param code
     * @return
     */
    OrganizationInvitationInfoDTO getOrganizationInvitationInfo(String code);

    /**
     * 邀请加入公司
     *
     * @param command
     */
    void inviteJoinOrganization(InviteJoinOrganizationCommand command);

    /**
     * 发送组织邀请链接
     *
     * @param command
     * @param identity
     */
    void sendOrganizationInviteLink(SendOrganizationInviteLinkCommand command, EmployeeIdentity identity);
}
