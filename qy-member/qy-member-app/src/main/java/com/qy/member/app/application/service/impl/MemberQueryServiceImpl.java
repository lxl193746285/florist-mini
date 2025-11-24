package com.qy.member.app.application.service.impl;

import com.qy.audit.api.client.AuditLogClient;
import com.qy.audit.api.dto.AuditLogDTO;
import com.qy.audit.api.query.AuditLogQuery;
import com.qy.customer.api.client.BusinessLicenseClient;
import com.qy.customer.api.client.ContactClient;
import com.qy.customer.api.dto.ContactDTO;
import com.qy.member.app.application.assembler.MemberAssembler;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.member.app.application.repository.AccountDataRepository;
import com.qy.member.app.application.repository.MemberDataRepository;
import com.qy.member.app.application.security.MemberPermission;
import com.qy.member.app.application.service.MemberQueryService;
import com.qy.member.app.domain.enums.Module;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.RoleUserDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员查询服务实现
 *
 * @author legendjw
 */
@Service
public class MemberQueryServiceImpl implements MemberQueryService {
    private MemberAssembler memberAssembler;
    private MemberDataRepository memberDataRepository;
    private AccountDataRepository accountDataRepository;
    private MemberPermission memberPermission;
    private RoleManageClient roleManageClient;
    private ContactClient contactClient;
    private BusinessLicenseClient businessLicenseClient;
    private AuditLogClient auditLogClient;

    public MemberQueryServiceImpl(MemberAssembler memberAssembler, MemberDataRepository memberDataRepository,
                                  AccountDataRepository accountDataRepository, MemberPermission memberPermission,
                                  RoleManageClient roleManageClient, ContactClient contactClient,
                                  BusinessLicenseClient businessLicenseClient, AuditLogClient auditLogClient) {
        this.memberAssembler = memberAssembler;
        this.memberDataRepository = memberDataRepository;
        this.accountDataRepository = accountDataRepository;
        this.memberPermission = memberPermission;
        this.roleManageClient = roleManageClient;
        this.contactClient = contactClient;
        this.businessLicenseClient = businessLicenseClient;
        this.auditLogClient = auditLogClient;
    }

    @Override
    public Page<MemberDTO> getMembers(MemberQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = memberPermission.getFilterQuery(identity, MemberPermission.LIST);
        //权限组查询
        if (query.getRoleId() != null) {
            List<RoleUserDTO> roleUserDTOS = roleManageClient.getRoleUsers(query.getRoleId());
            List<Long> memberIds = roleUserDTOS.stream().map(RoleUserDTO::getId).collect(Collectors.toList());
            if (memberIds.isEmpty()) {
                memberIds.add(0L);
            }
            query.setIds(memberIds);
        }
        Page<MemberDO> memberDOPage = memberDataRepository.findMemberByQuery(query, filterQuery);
        return memberDOPage.map(memberDO -> memberAssembler.toDTO(memberDO, identity));
    }

    @Override
    public MemberBasicDTO getBasicMember(Long id) {
        MemberDO memberDO = memberDataRepository.findById(id);
        return memberAssembler.toBasicDTO(memberDO);
    }

    @Override
    public MemberDTO getMember(Long id) {
        MemberDO memberDO = memberDataRepository.findById(id);
        return memberAssembler.toDTO(memberDO, null);
    }

    @Override
    public MemberDetailDTO getDetailMember(Long id, Identity identity) {
        MemberDO memberDO = memberDataRepository.findById(id);
        MemberDetailDTO memberDetailDTO = memberAssembler.toDetailDTO(memberDO, identity);
//        List<ContactDTO> contactDTOS = contactClient.getContactsByRelatedModule(Module.MEMBER.getId(), memberDetailDTO.getId());
//        memberDetailDTO.setLegalPerson(contactDTOS.isEmpty() ? null : contactDTOS.get(0));
//        memberDetailDTO.setBusinessLicense(businessLicenseClient.getBusinessLicenseByRelatedModule(Module.MEMBER.getId(), memberDetailDTO.getId()));

        return memberDetailDTO;
    }

    @Override
    public List<MemberBasicDTO> getBasicMembers(List<Long> ids) {
        List<MemberDO> memberDOs = memberDataRepository.findByIds(ids);
        return memberDOs.stream().map(m -> memberAssembler.toBasicDTO(m)).collect(Collectors.toList());
    }

    @Override
    public OpenMemberInfoDTO getOpenMemberInfo(Long memberId) {
        MemberBasicDTO memberBasicDTO = getBasicMember(memberId);
        if (memberBasicDTO == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        OpenMemberInfoDTO openMemberInfoDTO = new OpenMemberInfoDTO();
        openMemberInfoDTO.setOpenMember(memberBasicDTO);
        MemberAccountDO memberAccountDO = accountDataRepository.findMemberPrimaryAccount(memberBasicDTO.getId());
//        List<RoleBasicDTO> roleBasicDTOS = roleManageClient.getRolesByUser(memberAccountDO.getOrganizationId(), memberAccountDO.getId());
//        openMemberInfoDTO.setRoles(roleBasicDTOS);

        return openMemberInfoDTO;
    }

    @Override
    public MemberAuditInfoDTO getMemberAuditInfo(Long memberId) {
        MemberDO memberDO = memberDataRepository.findById(memberId);
        if (memberDO == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        AuditLogQuery query = new AuditLogQuery();
        query.setOrganizationId(memberDO.getOrganizationId());
        query.setModuleId(Module.MEMBER.getId());
        query.setDataId(memberId);
        query.setSortType("asc");
        List<AuditLogDTO> auditLogDTOS = auditLogClient.getAllAuditLogs(query);
        AuditLogDTO lastedAuditLog = auditLogDTOS.isEmpty() ? null : auditLogDTOS.get(auditLogDTOS.size() - 1);

        MemberAuditInfoDTO memberAuditInfoDTO = new MemberAuditInfoDTO();
        memberAuditInfoDTO.setAuditStatusId(memberDO.getAuditStatusId());
        memberAuditInfoDTO.setAuditStatusName(memberDO.getAuditStatusName());
        if (lastedAuditLog != null) {
            memberAuditInfoDTO.setAuditTimeName(lastedAuditLog.getAuditTime());
            memberAuditInfoDTO.setAuditReason(lastedAuditLog.getReason());
            memberAuditInfoDTO.setAuditRemark(lastedAuditLog.getRemark());
        }
        memberAuditInfoDTO.setAuditLogs(auditLogDTOS.stream().map(a -> {
            AuditBasicInfoDTO auditBasicInfoDTO = new AuditBasicInfoDTO();
            auditBasicInfoDTO.setTypeId(a.getTypeId());
            auditBasicInfoDTO.setTypeName(a.getTypeName());
            auditBasicInfoDTO.setAuditTimeName(a.getAuditTime());
            return auditBasicInfoDTO;
        }).collect(Collectors.toList()));
        return memberAuditInfoDTO;
    }

    @Override
    public MemberBasicDTO getMemberByPhoneSystemId(String phone, Long systemId, Long orgId) {
        MemberDO memberDO = memberDataRepository.findByPhoneSystem(phone, systemId, orgId);
        return memberAssembler.toBasicDTO(memberDO);
    }

    @Override
    public MemberBasicDTO getMemberByAccountSystemId(Long accountId, Long systemId, Long orgId) {
        MemberDO memberDO = memberDataRepository.findByAccountSystem(accountId, systemId, orgId);
        return memberAssembler.toBasicDTO(memberDO);
    }

    @Override
    public List<MemberBasicDTO> getMembersByAccount(Long accountId) {
        return memberDataRepository.getMembersByAccount(accountId);
    }

    @Override
    public List<MemberBasicDTO> getMembersByAccountAndSystemId(Long accountId, Long systemId) {
        List<MemberDO> memberDOS = memberDataRepository.getMembersByAccountAndSystemId(accountId, systemId);
        return memberDOS.stream().map(m -> memberAssembler.toBasicDTO(m)).collect(Collectors.toList());
    }
}