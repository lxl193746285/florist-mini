package com.qy.organization.app.infrastructure.persistence.mybatis.converter;

import com.qy.organization.app.domain.entity.OrganizationInvitationLink;
import com.qy.organization.app.domain.valueobject.InvitationCode;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.organization.app.domain.valueobject.User;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.InvitationCodeDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 验证码转化器
 *
 * @author legendjw
 */
@Mapper
public interface InvitationCodeConverter {
    @Mappings({
            @Mapping(source = "organizationId.id", target = "organizationId"),
            @Mapping(source = "code.code", target = "code"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
    })
    InvitationCodeDO toDO(OrganizationInvitationLink invitationLink);

    default OrganizationInvitationLink toEntity(InvitationCodeDO invitationCodeDO) {
        if (invitationCodeDO == null) { return null; }
        User creator = new User(invitationCodeDO.getCreatorId(), invitationCodeDO.getCreatorName());
        return new OrganizationInvitationLink(
                new OrganizationId(invitationCodeDO.getOrganizationId()),
                new InvitationCode(invitationCodeDO.getCode()),
                invitationCodeDO.getCreateTime(),
                invitationCodeDO.getFailureTime(),
                creator
        );
    }
}