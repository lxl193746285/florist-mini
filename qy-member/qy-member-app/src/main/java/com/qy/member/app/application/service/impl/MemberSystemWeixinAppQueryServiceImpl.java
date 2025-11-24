package com.qy.member.app.application.service.impl;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.member.app.application.assembler.MemberSystemWeixinAppAssembler;
import com.qy.member.app.application.dto.MemberSystemWeixinAppDTO;
import com.qy.member.app.application.query.MemberSystemWeixinAppQuery;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.security.MemberSystemWeixinAppPermission;
import com.qy.member.app.application.service.MemberSystemWeixinAppQueryService;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员系统查询服务实现
 *
 * @author legendjw
 */
@Service
public class MemberSystemWeixinAppQueryServiceImpl implements MemberSystemWeixinAppQueryService {
    private MemberSystemWeixinAppAssembler memberSystemWeixinAppAssembler;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    private MemberSystemWeixinAppPermission memberSystemWeixinAppPermission;
    private AttachmentClient attachmentClient;

    public MemberSystemWeixinAppQueryServiceImpl(MemberSystemWeixinAppAssembler memberSystemWeixinAppAssembler, MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository, MemberSystemWeixinAppPermission memberSystemWeixinAppPermission, AttachmentClient attachmentClient) {
        this.memberSystemWeixinAppAssembler = memberSystemWeixinAppAssembler;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
        this.memberSystemWeixinAppPermission = memberSystemWeixinAppPermission;
        this.attachmentClient = attachmentClient;
    }

    @Override
    public Page<MemberSystemWeixinAppDTO> getMemberSystemWeixinApps(MemberSystemWeixinAppQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = memberSystemWeixinAppPermission.getFilterQuery(identity, MemberSystemWeixinAppPermission.LIST);
        Page<MemberSystemWeixinAppDO> memberSystemWeixinAppDOPage = memberSystemWeixinAppDataRepository.findByQuery(query, filterQuery);
        return memberSystemWeixinAppDOPage.map(memberSystemWeixinApp -> memberSystemWeixinAppAssembler.toDTO(memberSystemWeixinApp, identity));
    }

    @Override
    public List<MemberSystemWeixinAppDTO> getMemberSystemWeixinAppBySystemId(Long systemId) {
        List<MemberSystemWeixinAppDO> memberSystemWeixinAppDOS = memberSystemWeixinAppDataRepository.findBySystemId(systemId);
        return memberSystemWeixinAppDOS.stream().map(memberSystemWeixinAppDO -> memberSystemWeixinAppAssembler.toDTO(memberSystemWeixinAppDO, null)).collect(Collectors.toList());
    }

    @Override
    public MemberSystemWeixinAppDTO getMemberSystemWeixinAppById(Long id, Identity identity) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findById(id);
        return memberSystemWeixinAppAssembler.toDTO(memberSystemWeixinAppDO, identity);
    }

    @Override
    public MemberSystemWeixinAppDTO getMemberSystemWeixinAppById(Long id) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findById(id);
        return memberSystemWeixinAppAssembler.toDTO(memberSystemWeixinAppDO, null);
    }

    @Override
    public AttachmentBasicDTO getMemberSystemWeixinAppQrCode(Long systemId, String appId) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppId(systemId, appId);
        return memberSystemWeixinAppDO.getQrCode() != null && memberSystemWeixinAppDO.getQrCode() != 0L ? attachmentClient.getBasicAttachment(memberSystemWeixinAppDO.getQrCode()) : null;
    }
}
