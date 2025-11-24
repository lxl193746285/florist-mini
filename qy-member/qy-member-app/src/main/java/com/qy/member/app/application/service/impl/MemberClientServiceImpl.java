package com.qy.member.app.application.service.impl;

import com.qy.member.app.application.command.CreateMemberClientCommand;
import com.qy.member.app.application.dto.MemberClientDTO;
import com.qy.member.app.application.repository.MemberClientRepository;
import com.qy.member.app.application.service.MemberClientService;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberClientDO;
import com.qy.security.session.MemberIdentity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员查询服务实现
 *
 * @author legendjw
 */
@Service
public class MemberClientServiceImpl implements MemberClientService {
    private MemberClientRepository memberClientRepository;

    public MemberClientServiceImpl( MemberClientRepository memberClientRepository) {
        this.memberClientRepository = memberClientRepository;
    }

    @Override
    public MemberClientDTO getMemberClient(Long memberId, String clientId) {
        return memberClientRepository.findMemberClientList(memberId, clientId);
    }

    @Override
    public List<MemberClientDTO> getMemberClients(Long memberId) {
        return memberClientRepository.findMemberClientList(memberId);
    }

    @Override
    public void createBatch(CreateMemberClientCommand command, MemberIdentity user) {
        memberClientRepository.removeMemberClient(command.getMemberId());
        List<MemberClientDO> memberClientDOS = new ArrayList<>();
        command.getClientIds().forEach(clientId -> {
            MemberClientDO memberClientDO = new MemberClientDO();
            memberClientDO.setMemberId(command.getMemberId());
            memberClientDO.setClientId(clientId);
            memberClientDO.setCreatorId(user.getId());
            memberClientDO.setCreateTime(LocalDateTime.now());
            memberClientDOS.add(memberClientDO);
        });
        memberClientRepository.saveBatch(memberClientDOS);
    }

    @Override
    public void createDefaultClient(Long memberId, Integer identityType) {

    }
}