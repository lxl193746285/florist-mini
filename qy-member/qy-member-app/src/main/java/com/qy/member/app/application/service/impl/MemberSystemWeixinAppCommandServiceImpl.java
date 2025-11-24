package com.qy.member.app.application.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.member.app.application.command.CreateMemberSystemWeixinAppCommand;
import com.qy.member.app.application.command.UpdateMemberSystemWeixinAppCommand;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.service.MemberSystemWeixinAppCommandService;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.rbac.api.client.ClientClient;
import com.qy.rbac.api.dto.ClientDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会员系统微信应用命令实现
 *
 * @author legendjw
 */
@Service
public class MemberSystemWeixinAppCommandServiceImpl implements MemberSystemWeixinAppCommandService {
    public static String weixinAppTypeCodeTable = "weixin_app_type";
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    private CodeTableClient codeTableClient;
    private ClientClient clientClient;

    public MemberSystemWeixinAppCommandServiceImpl(MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository, CodeTableClient codeTableClient, ClientClient clientClient) {
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
        this.codeTableClient = codeTableClient;
        this.clientClient = clientClient;
    }

    @Override
    @Transactional
    public Long createMemberSystemWeixinApp(CreateMemberSystemWeixinAppCommand command, EmployeeIdentity identity) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = new MemberSystemWeixinAppDO();
        BeanUtils.copyProperties(command, memberSystemWeixinAppDO);
        fillFormRelatedData(memberSystemWeixinAppDO);
        memberSystemWeixinAppDO.setQrCode(command.getQrCodeAttachmentId());
        memberSystemWeixinAppDO.setCreatorId(identity != null ? identity.getId() : 0L);
        memberSystemWeixinAppDO.setCreatorName(identity != null ? identity.getName() : "");
        memberSystemWeixinAppDO.setCreateTime(LocalDateTime.now());
        memberSystemWeixinAppDataRepository.save(memberSystemWeixinAppDO);
        return memberSystemWeixinAppDO.getId();
    }

    @Override
    @Transactional
    public void updateMemberSystemWeixinApp(UpdateMemberSystemWeixinAppCommand command, EmployeeIdentity identity) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findById(command.getId());
        if (memberSystemWeixinAppDO == null) {
            throw new NotFoundException("未找到指定的会员系统微信应用");
        }

        BeanUtils.copyProperties(command, memberSystemWeixinAppDO);
        fillFormRelatedData(memberSystemWeixinAppDO);
        memberSystemWeixinAppDO.setQrCode(command.getQrCodeAttachmentId());
        memberSystemWeixinAppDO.setUpdatorId(identity != null ? identity.getId() : 0L);
        memberSystemWeixinAppDO.setUpdatorName(identity != null ? identity.getName() : "");
        memberSystemWeixinAppDO.setUpdateTime(LocalDateTime.now());
        memberSystemWeixinAppDataRepository.save(memberSystemWeixinAppDO);
    }

    @Override
    @Transactional
    public void deleteMemberSystemWeixinApp(Long id, EmployeeIdentity identity) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findById(id);
        if (memberSystemWeixinAppDO == null) {
            throw new NotFoundException("未找到指定的会员系统微信应用");
        }
        memberSystemWeixinAppDataRepository.remove(id, identity);
    }

    @Override
    @Transactional
    public void batchDeleteMemberSystemWeixinApp(List<Long> ids, EmployeeIdentity identity) {
        for (Long id : ids) {
            deleteMemberSystemWeixinApp(id, identity);
        }
    }

    private void fillFormRelatedData(MemberSystemWeixinAppDO memberSystemWeixinAppDO) {
        String typeName = memberSystemWeixinAppDO.getTypeId() != null ?
                codeTableClient.getSystemCodeTableItemName(weixinAppTypeCodeTable, String.valueOf(memberSystemWeixinAppDO.getTypeId())) : "";
        memberSystemWeixinAppDO.setTypeName(typeName);

        ClientDTO clientDTO = clientClient.getClient(memberSystemWeixinAppDO.getClientId());
        if (clientDTO == null) {
            throw new NotFoundException("未找到指定的客户端");
        }
        memberSystemWeixinAppDO.setSystemId(clientDTO.getSystemId());
    }
}