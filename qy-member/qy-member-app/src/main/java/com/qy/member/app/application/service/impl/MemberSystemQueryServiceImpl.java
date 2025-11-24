package com.qy.member.app.application.service.impl;

import com.qy.member.app.application.assembler.MemberSystemAssembler;
import com.qy.member.app.application.dto.MemberSystemBasicDTO;
import com.qy.member.app.application.dto.MemberSystemBasicExtendDTO;
import com.qy.member.app.application.dto.MemberSystemDTO;
import com.qy.member.app.application.dto.MemberSystemDetailDTO;
import com.qy.member.app.application.query.MemberSystemQuery;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.application.repository.MemberSystemOrganizationDataRepository;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.security.MemberSystemPermission;
import com.qy.member.app.application.service.MemberSystemQueryService;
import com.qy.member.app.domain.enums.WeixinAppType;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemOrganizationDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员系统查询服务实现
 *
 * @author legendjw
 */
@Service
public class MemberSystemQueryServiceImpl implements MemberSystemQueryService {
    private MemberSystemAssembler memberSystemAssembler;
    private MemberSystemDataRepository memberSystemDataRepository;
    private MemberSystemPermission memberSystemPermission;
    private OrganizationClient organizationClient;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    private MemberSystemOrganizationDataRepository memberSystemOrganizationDataRepository;

    public MemberSystemQueryServiceImpl(MemberSystemAssembler memberSystemAssembler, MemberSystemDataRepository memberSystemDataRepository, MemberSystemPermission memberSystemPermission, OrganizationClient organizationClient, MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository, MemberSystemOrganizationDataRepository memberSystemOrganizationDataRepository) {
        this.memberSystemAssembler = memberSystemAssembler;
        this.memberSystemDataRepository = memberSystemDataRepository;
        this.memberSystemPermission = memberSystemPermission;
        this.organizationClient = organizationClient;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
        this.memberSystemOrganizationDataRepository = memberSystemOrganizationDataRepository;
    }

    @Override
    public Page<MemberSystemDTO> getMemberSystems(MemberSystemQuery query, Identity identity) {
//        MultiOrganizationFilterQuery filterQuery = memberSystemPermission.getFilterQuery(identity, MemberSystemPermission.LIST);
        Page<MemberSystemDO> memberSystemDOPage = memberSystemDataRepository.findByQuery(query);
        List<OrganizationBasicDTO> organizationBasicDTOS = memberSystemDOPage.getRecords().isEmpty()
                ? new ArrayList<>()
                : organizationClient.getBasicOrganizationsByIds(memberSystemDOPage.getRecords().stream()
                .map(MemberSystemDO::getOrganizationId).collect(Collectors.toList()));
        return memberSystemDOPage.map(memberSystem -> memberSystemAssembler.toDTO(memberSystem, organizationBasicDTOS, identity));
    }

    @Override
    public MemberSystemDTO getMemberSystemById(Long id, Identity identity) {
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findById(id);
        List<OrganizationBasicDTO> organizationBasicDTOS = memberSystemDO != null ? organizationClient.getBasicOrganizationsByIds(Arrays.asList(memberSystemDO.getOrganizationId())) : new ArrayList<>();
        return memberSystemAssembler.toDTO(memberSystemDO, organizationBasicDTOS, identity);
    }

    @Override
    public List<MemberSystemBasicDTO> getBasicMemberSystemsByIds(List<Long> ids) {
        List<MemberSystemDO> memberSystemDOS = memberSystemDataRepository.findByIds(ids);
        return memberSystemDOS.stream().map(memberSystemDO -> memberSystemAssembler.toBasicDTO(memberSystemDO)).collect(Collectors.toList());
    }

    @Override
    public MemberSystemDetailDTO getMemberSystemDetailById(Long id, Identity identity) {
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findById(id);
        List<OrganizationBasicDTO> organizationBasicDTOS = memberSystemDO != null ? organizationClient.getBasicOrganizationsByIds(Arrays.asList(memberSystemDO.getOrganizationId())) : new ArrayList<>();
        return memberSystemAssembler.toDetailDTO(memberSystemDO, organizationBasicDTOS, identity);
    }

    @Override
    public MemberSystemBasicDTO getBasicMemberSystemById(Long id) {
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findById(id);
        return memberSystemAssembler.toBasicDTO(memberSystemDO);
    }

    @Override
    public List<MemberSystemBasicDTO> getBasicMemberSystemsByOrganizationId(Long organizationId) {
        List<MemberSystemOrganizationDO> memberSystemOrganizationDOS = memberSystemOrganizationDataRepository.findByOrganizationId(organizationId);
        if (memberSystemOrganizationDOS == null || memberSystemOrganizationDOS.size() < 1) {
            return new ArrayList<>();
        }
        List<Long> systemIds = memberSystemOrganizationDOS.stream().map(MemberSystemOrganizationDO::getSystemId).collect(Collectors.toList());
        List<MemberSystemDO> memberSystemDOS = memberSystemDataRepository.findByIds(systemIds);
        return memberSystemDOS.stream().map(memberSystemDO -> memberSystemAssembler.toBasicDTO(memberSystemDO)).collect(Collectors.toList());
    }

    @Override
    public List<MemberSystemBasicExtendDTO> getBasicMemberSystemsExtendByOrganizationId(Long organizationId) {
        List<MemberSystemBasicDTO> memberSystemBasicDTOS = getBasicMemberSystemsByOrganizationId(organizationId);
        List<MemberSystemBasicExtendDTO> extendDTOS = new ArrayList<>();
        for (MemberSystemBasicDTO memberSystemBasicDTO : memberSystemBasicDTOS) {
            MemberSystemBasicExtendDTO extendDTO = new MemberSystemBasicExtendDTO();
            BeanUtils.copyProperties(memberSystemBasicDTO, extendDTO);
            MemberSystemWeixinAppDO weixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppType(memberSystemBasicDTO.getId(), WeixinAppType.MP.getId());
            if (null != weixinAppDO) {
                extendDTO.setAppId(weixinAppDO.getAppId());
            }
            extendDTOS.add(extendDTO);
        }
        return extendDTOS;
    }

    @Override
    public List<MemberSystemBasicDTO> getBasicMemberSystemsByOrganizationIdAndSource(Long organizationId, Integer source) {
        List<MemberSystemOrganizationDO> memberSystemOrganizationDOS = memberSystemOrganizationDataRepository.findOrganizationIdAndSource(organizationId, source);
        if (memberSystemOrganizationDOS == null || memberSystemOrganizationDOS.size() < 1) {
            return new ArrayList<>();
        }
        List<Long> systemIds = memberSystemOrganizationDOS.stream().map(MemberSystemOrganizationDO::getSystemId).collect(Collectors.toList());
        List<MemberSystemDO> memberSystemDOS = memberSystemDataRepository.findByIds(systemIds);
        return memberSystemDOS.stream().map(memberSystemDO -> memberSystemAssembler.toBasicDTO(memberSystemDO)).collect(Collectors.toList());
    }
}
