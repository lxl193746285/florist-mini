package com.qy.member.app.domain.service;

import com.qy.member.app.application.command.CreateChildAccountCommand;
import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.valueobject.*;
import com.qy.security.session.EmployeeIdentity;

/**
 * 会员服务
 *
 * @author legendjw
 */
public interface MemberService {
    /**
     * 创建会员
     *
     * @param memberSystemId
     * @param name
     * @param gender
     * @param avatar
     * @param address
     * @param invitationCode
     * @param phoneNumber
     * @param password
     * @param auditStatus
     * @param memberType
     * @return
     */
    Member createMember(
            MemberSystemId memberSystemId,
            OrganizationId organizationId,
            String name,
            Gender gender,
            String avatar,
            Address address,
            String invitationCode,
            PhoneNumber phoneNumber,
            String password,
            AuditStatus auditStatus,
            Integer memberType
    );

    /**
     * 创建会员,不创建账号
     *
     * @param memberSystemId
     * @param name
     * @param gender
     * @param avatar
     * @param address
     * @param invitationCode
     * @param phoneNumber
     * @param memberType
     * @return
     */
    Member createMemberWithoutAccount(
            MemberSystemId memberSystemId,
            OrganizationId organizationId,
            String name,
            Gender gender,
            String avatar,
            Address address,
            String invitationCode,
            PhoneNumber phoneNumber,
            Integer memberType
    );

    /**
     * 创建会员子账号
     *
     * @param command
     * @param identity
     * @return
     */
    MemberAccount createChildAccount(CreateChildAccountCommand command, EmployeeIdentity identity);

}