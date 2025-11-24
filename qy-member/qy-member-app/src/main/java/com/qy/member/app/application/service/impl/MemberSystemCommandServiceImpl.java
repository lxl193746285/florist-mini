package com.qy.member.app.application.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.MemberSystemWeixinAppDTO;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.application.service.MemberSystemCommandService;
import com.qy.member.app.application.service.MemberSystemOrganizationCommandService;
import com.qy.member.app.application.service.MemberSystemWeixinAppCommandService;
import com.qy.member.app.application.service.MemberSystemWeixinAppQueryService;
import com.qy.member.app.domain.enums.MSSource;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.rbac.api.client.AppClient;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员系统命令实现
 *
 * @author legendjw
 */
@Service
public class MemberSystemCommandServiceImpl implements MemberSystemCommandService {
    public static String commonStatusCodeTable = "common_status";
    public static String systemTypeCodeTable = "member_system_type";
    public static String memberTypeCodeTable = "member_type";
    private MemberSystemDataRepository memberSystemDataRepository;
    private CodeTableClient codeTableClient;
    private MemberSystemWeixinAppCommandService memberSystemWeixinAppCommandService;
    private MemberSystemWeixinAppQueryService memberSystemWeixinAppQueryService;
    private MemberSystemOrganizationCommandService memberSystemOrganizationCommandService;
    private AppClient appClient;

    public MemberSystemCommandServiceImpl(MemberSystemDataRepository memberSystemDataRepository, CodeTableClient codeTableClient,
                                          MemberSystemWeixinAppCommandService memberSystemWeixinAppCommandService,
                                          MemberSystemWeixinAppQueryService memberSystemWeixinAppQueryService,
                                          MemberSystemOrganizationCommandService memberSystemOrganizationCommandService,
                                          AppClient appClient) {
        this.memberSystemDataRepository = memberSystemDataRepository;
        this.codeTableClient = codeTableClient;
        this.memberSystemWeixinAppCommandService = memberSystemWeixinAppCommandService;
        this.memberSystemWeixinAppQueryService = memberSystemWeixinAppQueryService;
        this.memberSystemOrganizationCommandService = memberSystemOrganizationCommandService;
        this.appClient = appClient;
    }

    @Override
    @Transactional
    public Long createMemberSystem(CreateMemberSystemCommand command, EmployeeIdentity identity) {
        if (memberSystemDataRepository.countByOrganizationIdAndName(command.getOrganizationId(), command.getName(), null) > 0) {
            throw new ValidationException("组织下已经存在同名的会员系统，请更换新的会员系统名称");
        }

        MemberSystemDO memberSystemDO = new MemberSystemDO();
        BeanUtils.copyProperties(command, memberSystemDO);
        fillFormRelatedData(memberSystemDO);
        memberSystemDO.setCreatorId(identity != null ? identity.getId() : 0L);
        memberSystemDO.setCreatorName(identity != null ? identity.getName() : "");
        memberSystemDO.setCreateTime(LocalDateTime.now());
        memberSystemDataRepository.save(memberSystemDO);

        //创建会员系统组织关系
        MemberSystemAuthorizationCommand memberSystemAuthorizationCommand = new MemberSystemAuthorizationCommand();
        memberSystemAuthorizationCommand.setOrganizationId(memberSystemDO.getOrganizationId());
        memberSystemAuthorizationCommand.setSystemIds(new ArrayList<>(Collections.singleton(memberSystemDO.getId())));
        memberSystemAuthorizationCommand.setSource(MSSource.CREATE.getId());
        memberSystemOrganizationCommandService.createMemberSystemOrganization(memberSystemAuthorizationCommand, identity);

        //创建绑定微信应用
        for (MemberSystemWeixinAppForm weixinApp : command.getWeixinApps()) {
            CreateMemberSystemWeixinAppCommand createMemberSystemWeixinAppCommand = new CreateMemberSystemWeixinAppCommand();
            createMemberSystemWeixinAppCommand.setOrganizationId(memberSystemDO.getOrganizationId());
//            createMemberSystemWeixinAppCommand.setSystemId(memberSystemDO.getId());
            BeanUtils.copyProperties(weixinApp, createMemberSystemWeixinAppCommand);
            memberSystemWeixinAppCommandService.createMemberSystemWeixinApp(createMemberSystemWeixinAppCommand, identity);
        }
        return memberSystemDO.getId();
    }

    @Override
    @Transactional
    public void updateMemberSystem(UpdateMemberSystemCommand command, EmployeeIdentity identity) {
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findById(command.getId());
        if (memberSystemDO == null) {
            throw new NotFoundException("未找到指定的会员系统");
        }
        if (memberSystemDataRepository.countByOrganizationIdAndName(memberSystemDO.getOrganizationId(), command.getName(), memberSystemDO.getId()) > 0) {
            throw new ValidationException("组织下已经存在同名的会员系统，请更换新的会员系统名称");
        }

        BeanUtils.copyProperties(command, memberSystemDO);
        fillFormRelatedData(memberSystemDO);
        memberSystemDO.setUpdatorId(identity != null ? identity.getId() : 0L);
        memberSystemDO.setUpdatorName(identity != null ? identity.getName() : "");
        memberSystemDO.setUpdateTime(LocalDateTime.now());
        memberSystemDataRepository.save(memberSystemDO);

        //修改绑定微信应用
        List<MemberSystemWeixinAppDTO> weixinAppDTOList = memberSystemWeixinAppQueryService.getMemberSystemWeixinAppBySystemId(memberSystemDO.getId());
        for (MemberSystemWeixinAppForm weixinApp : command.getWeixinApps()) {
            if (weixinApp.getId() == null) {
                CreateMemberSystemWeixinAppCommand createMemberSystemWeixinAppCommand = new CreateMemberSystemWeixinAppCommand();
                createMemberSystemWeixinAppCommand.setOrganizationId(memberSystemDO.getOrganizationId());
//                createMemberSystemWeixinAppCommand.setSystemId(memberSystemDO.getId());
                BeanUtils.copyProperties(weixinApp, createMemberSystemWeixinAppCommand);
                memberSystemWeixinAppCommandService.createMemberSystemWeixinApp(createMemberSystemWeixinAppCommand, identity);
            }
            else {
                UpdateMemberSystemWeixinAppCommand updateMemberSystemWeixinAppCommand = new UpdateMemberSystemWeixinAppCommand();
                BeanUtils.copyProperties(weixinApp, updateMemberSystemWeixinAppCommand);
                memberSystemWeixinAppCommandService.updateMemberSystemWeixinApp(updateMemberSystemWeixinAppCommand, identity);
            }
        }
        //删除微信应用
        for (MemberSystemWeixinAppDTO memberSystemWeixinAppDTO : weixinAppDTOList) {
            if (!command.getWeixinApps().stream().anyMatch(c -> c.getId() != null && c.getId().equals(memberSystemWeixinAppDTO.getId()))) {
                memberSystemWeixinAppCommandService.deleteMemberSystemWeixinApp(memberSystemWeixinAppDTO.getId(), identity);
            }
        }
    }

    @Override
    @Transactional
    public void deleteMemberSystem(Long id, EmployeeIdentity identity) {
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findById(id);
        if (memberSystemDO == null) {
            throw new NotFoundException("未找到指定的会员系统");
        }
        memberSystemDataRepository.remove(id, identity);

        //删除对应的微信应用
        List<MemberSystemWeixinAppDTO> weixinAppDTOList = memberSystemWeixinAppQueryService.getMemberSystemWeixinAppBySystemId(memberSystemDO.getId());
        if (!weixinAppDTOList.isEmpty()) {
            memberSystemWeixinAppCommandService.batchDeleteMemberSystemWeixinApp(weixinAppDTOList.stream().map(MemberSystemWeixinAppDTO::getId).collect(Collectors.toList()), identity);
        }
    }

    private void fillFormRelatedData(MemberSystemDO memberSystemDO) {
        String statusName = memberSystemDO.getStatusId() != null ?
                codeTableClient.getSystemCodeTableItemName(commonStatusCodeTable, String.valueOf(memberSystemDO.getStatusId())) : "";
        String typeName = memberSystemDO.getTypeId() != null ?
                codeTableClient.getSystemCodeTableItemName(systemTypeCodeTable, String.valueOf(memberSystemDO.getTypeId())) : "";
        String memberTypeName = memberSystemDO.getMemberTypeId() != null ?
                codeTableClient.getSystemCodeTableItemName(memberTypeCodeTable, String.valueOf(memberSystemDO.getMemberTypeId())) : "";

        memberSystemDO.setStatusName(statusName);
        memberSystemDO.setTypeName(typeName);
        memberSystemDO.setMemberTypeName(memberTypeName);
    }
}