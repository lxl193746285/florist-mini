package com.qy.member.app.infrastructure.persistence.mybatis.converter;

import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.enums.MemberType;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.rest.enums.EnableDisableStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 会员转化器
 *
 * @author legendjw
 */
@Mapper
public interface MemberConverter {
    @Mappings({
            @Mapping(source = "memberId.id", target = "id"),
            @Mapping(source = "organizationId.id", target = "organizationId"),
            @Mapping(source = "memberSystemId.id", target = "systemId"),
            @Mapping(source = "primaryAccount.accountId.id", target = "accountId"),
            @Mapping(source = "name.name", target = "name"),
            @Mapping(source = "name.namePinyin", target = "namePinyin"),
            @Mapping(source = "phone.number", target = "phone"),
            @Mapping(source = "avatar.url", target = "avatar"),
            @Mapping(source = "status.id", target = "statusId"),
            @Mapping(source = "status.name", target = "statusName"),
            @Mapping(source = "auditStatus.id", target = "auditStatusId"),
            @Mapping(source = "auditStatus.name", target = "auditStatusName"),
            @Mapping(source = "memberType.id", target = "memberTypeId"),
            @Mapping(source = "memberType.name", target = "memberTypeName"),
    })
    MemberDO toDO(Member member);

    default Member toEntity(MemberDO memberDO, MemberAccount memberAccount) {
        if (memberDO == null) {
            return null;
        }
        return Member.builder()
                .memberId(new MemberId(memberDO.getId()))
                .organizationId(new OrganizationId(memberDO.getOrganizationId()))
                .memberSystemId(new MemberSystemId(memberDO.getSystemId()))
                .name(new PinyinName(memberDO.getName(), memberDO.getNamePinyin()))
                .phone(new PhoneNumber(memberDO.getPhone()))
                .avatar(new Avatar(memberDO.getAvatar()))
                .status(EnableDisableStatus.getById(memberDO.getStatusId()))
                .auditStatus(AuditStatus.getById(memberDO.getAuditStatusId()))
                .invitationCode(memberDO.getInvitationCode())
                .isOpenAccount(memberDO.getIsOpenAccount().intValue() == 1 ? true : false)
                .memberType(MemberType.getById(memberDO.getMemberTypeId()))
                .remark(memberDO.getRemark())
                .createTime(memberDO.getCreateTime())
                .primaryAccount(memberAccount)
                .build();
    }
}