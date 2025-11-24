package com.qy.member.app.infrastructure.persistence.mybatis.converter;

import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AccountType;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.rest.enums.EnableDisableStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 账号转化器
 *
 * @author legendjw
 */
@Mapper
public interface AccountConverter {
    @Mappings({
            @Mapping(source = "accountId.id", target = "id"),
            @Mapping(source = "memberSystemId.id", target = "systemId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "password.passwordHash", target = "passwordHash"),
            @Mapping(source = "status.id", target = "statusId"),
            @Mapping(source = "status.name", target = "statusName"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
    })
    MemberAccountDO toDO(MemberAccount memberAccount);

    default MemberAccount toEntity(MemberAccountDO accountDO) {
        if (accountDO == null) {
            return null;
        }
        return MemberAccount.builder()
                .accountId(new AccountId(accountDO.getId()))
                .memberSystemId(new MemberSystemId(accountDO.getSystemId()))
                .name(accountDO.getName())
                .password(new Password(null, accountDO.getPasswordHash()))
                .status(EnableDisableStatus.getById(accountDO.getStatusId()))
                .createTime(accountDO.getCreateTime())
                .creator(accountDO.getCreatorId().longValue() > 0L ? new User(accountDO.getCreatorId(), accountDO.getCreatorName()) : null)
                .build();
    }

    default MemberAccount toEntity(MemberAccountDO accountDO, Member member) {
        if (accountDO == null) {
            return null;
        }
        return MemberAccount.builder()
                .accountId(new AccountId(accountDO.getId()))
                .memberSystemId(member.getMemberSystemId())
                .memberId(member.getMemberId())
                .name(accountDO.getName())
                .password(new Password(null, accountDO.getPasswordHash()))
                .status(EnableDisableStatus.getById(accountDO.getStatusId()))
                .createTime(accountDO.getCreateTime())
                .creator(accountDO.getCreatorId().longValue() > 0L ? new User(accountDO.getCreatorId(), accountDO.getCreatorName()) : null)
                .memberStatus(member.getStatus())
                .auditStatus(member.getAuditStatus())
                .organizationId(member.getOrganizationId())
                .build();
    }
}