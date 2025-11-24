package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.app.domain.entity.OrganizationInvitationLink;
import com.qy.organization.app.domain.valueobject.InvitationCode;
import com.qy.organization.app.infrastructure.persistence.OrganizationInvitationDomainRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.converter.InvitationCodeConverter;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.InvitationCodeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.InvitationCodeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

/**
 * 组织邀请领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class OrganizationInvitationDomainRepositoryImpl implements OrganizationInvitationDomainRepository {
    private InvitationCodeConverter invitationCodeConverter;
    private InvitationCodeMapper invitationCodeMapper;

    public OrganizationInvitationDomainRepositoryImpl(InvitationCodeConverter invitationCodeConverter, InvitationCodeMapper invitationCodeMapper) {
        this.invitationCodeConverter = invitationCodeConverter;
        this.invitationCodeMapper = invitationCodeMapper;
    }

    @Override
    public void save(OrganizationInvitationLink invitationLink) {
        InvitationCodeDO invitationCodeDO = invitationCodeConverter.toDO(invitationLink);
        invitationCodeDO.setType("ORGANIZATION_INVITATION");
        invitationCodeMapper.insert(invitationCodeDO);
    }

    @Override
    public OrganizationInvitationLink findByCode(InvitationCode code) {
        LambdaQueryWrapper<InvitationCodeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(InvitationCodeDO::getCode, code.getCode())
                .orderByDesc(InvitationCodeDO::getCreateTime)
                .last("limit 1");
        InvitationCodeDO invitationCodeDO = invitationCodeMapper.selectOne(queryWrapper);
        return invitationCodeConverter.toEntity(invitationCodeDO);
    }
}
