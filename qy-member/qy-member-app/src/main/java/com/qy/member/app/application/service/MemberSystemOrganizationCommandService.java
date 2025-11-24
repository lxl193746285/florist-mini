package com.qy.member.app.application.service;

import com.qy.member.app.application.command.MemberSystemAuthorizationCommand;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemOrganizationDO;
import com.qy.security.session.EmployeeIdentity;

import java.util.List;

/**
 * 会员系统组织关系命令服务
 *
 * @author wwd
 */
public interface MemberSystemOrganizationCommandService {
    /**
     * 创建会员系统组织关系命令
     *
     * @param command
     * @param identity
     * @return
     */
    void createMemberSystemOrganization(MemberSystemAuthorizationCommand command, EmployeeIdentity identity);

    /**
     * 删除会员系统组织关系命令
     *
     * @param organizationId
     */
    void removeMemberSystemOrganization(Long organizationId);

}
