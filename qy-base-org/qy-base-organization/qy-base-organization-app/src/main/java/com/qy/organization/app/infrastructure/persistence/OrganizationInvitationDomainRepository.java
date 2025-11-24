package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.domain.entity.OrganizationInvitationLink;
import com.qy.organization.app.domain.valueobject.InvitationCode;

/**
 * 组织邀请领域资源库
 *
 * @author legendjw
 */
public interface OrganizationInvitationDomainRepository {
    /**
     * 保存邀请链接
     *
     * @param invitationLink
     */
    void save(OrganizationInvitationLink invitationLink);

    /**
     * 根据邀请码查询一个邀请链接
     *
     * @param code
     * @return
     */
    OrganizationInvitationLink findByCode(InvitationCode code);
}