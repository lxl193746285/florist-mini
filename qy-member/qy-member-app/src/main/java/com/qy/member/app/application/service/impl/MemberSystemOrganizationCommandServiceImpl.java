package com.qy.member.app.application.service.impl;

import com.qy.member.app.application.command.MemberSystemAuthorizationCommand;
import com.qy.member.app.application.repository.MemberSystemOrganizationDataRepository;
import com.qy.member.app.application.service.MemberSystemOrganizationCommandService;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemOrganizationDO;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import org.springframework.stereotype.Service;

/**
 * 会员系统命令实现
 *
 * @author legendjw
 */
@Service
public class MemberSystemOrganizationCommandServiceImpl implements MemberSystemOrganizationCommandService {
    private MemberSystemOrganizationDataRepository memberSystemOrganizationDataRepository;

    public MemberSystemOrganizationCommandServiceImpl(MemberSystemOrganizationDataRepository memberSystemOrganizationDataRepository) {
        this.memberSystemOrganizationDataRepository = memberSystemOrganizationDataRepository;
    }

    @Override
    public void createMemberSystemOrganization(MemberSystemAuthorizationCommand command, EmployeeIdentity identity) {
        if (command.getOrganizationId() == null){
            throw new ValidationException("请选择授权组织");
        }
        for (Long systemId : command.getSystemIds()){
            MemberSystemOrganizationDO memberSystemOrganizationDO = new MemberSystemOrganizationDO();
            memberSystemOrganizationDO.setSystemId(systemId);
            memberSystemOrganizationDO.setOrganizationId(command.getOrganizationId());
            memberSystemOrganizationDO.setSource(command.getSource());
            memberSystemOrganizationDataRepository.save(memberSystemOrganizationDO);
        }
    }

    @Override
    public void removeMemberSystemOrganization(Long organizationId) {
        memberSystemOrganizationDataRepository.removeByOrganizationId(organizationId);
    }
}