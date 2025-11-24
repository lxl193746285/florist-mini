package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.repository.MemberLoginCredentialRepository;
import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AccountType;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.repository.AccountDomainRepository;
import com.qy.member.app.domain.repository.MemberDomainRepository;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.converter.AccountConverter;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberLoginCredentialDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberAccountMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.sequence.Sequence;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员账号领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class AccountDomainRepositoryImpl implements AccountDomainRepository {
    private AccountConverter accountConverter;
    private MemberAccountMapper memberAccountMapper;
    private MemberDomainRepository memberDomainRepository;
    private Sequence sequence;
    private MemberLoginCredentialRepository memberLoginCredentialRepository;

    public AccountDomainRepositoryImpl(AccountConverter accountConverter, MemberAccountMapper memberAccountMapper,
                                       @Lazy MemberDomainRepository memberDomainRepository, Sequence sequence,
                                       MemberLoginCredentialRepository memberLoginCredentialRepository) {
        this.accountConverter = accountConverter;
        this.memberAccountMapper = memberAccountMapper;
        this.memberDomainRepository = memberDomainRepository;
        this.sequence = sequence;
        this.memberLoginCredentialRepository = memberLoginCredentialRepository;
    }

    @Override
    public MemberAccount findById(AccountId accountId) {
        LambdaQueryWrapper<MemberAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberAccountDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberAccountDO::getId, accountId.getId());
        MemberAccountDO accountDO = memberAccountMapper.selectOne(queryWrapper);
        return accountConverter.toEntity(accountDO);
    }

    @Override
    public MemberAccount findLoginAccount(MemberSystemId memberSystemId, PhoneNumber phone, OrganizationId organizationId) {
        MemberLoginCredentialDO credentialDO = memberLoginCredentialRepository.findByCredential(phone.getNumber());
        if (credentialDO == null){
            return null;
        }
        MemberAccountDO accountDO = memberAccountMapper.selectById(credentialDO.getAccountId());
        if (accountDO == null || accountDO.getIsDeleted() == LogicDeleteConstant.DELETED) {
            return null;
        }
        AccountId accountId = new AccountId(accountDO.getId());

        //查找指定账号绑定的会员，如果有会员系统则找会员系统下的会员，如果没有则获取账号绑定的第一个会员
        Member member = null;
        if (memberSystemId != null) {
            if (organizationId != null) {
                member = memberDomainRepository.findByMemberSystemAndAccountId(memberSystemId, accountId, organizationId);
            } else {
                member = memberDomainRepository.findDefaultByMemberSystemAndAccountId(memberSystemId, accountId);
            }
        } else {
            List<Member> members = memberDomainRepository.findByAccountId(accountId);
            List<Integer> auditStatusIds = new ArrayList<>();
            auditStatusIds.add(AuditStatus.STORE_PASSED.getId());
            auditStatusIds.add(AuditStatus.PLATFORM_PASSED.getId());
            if (!members.isEmpty()) {
                List<Member> enableMembers = members.stream().filter(m -> m.getStatus()
                        .equals(EnableDisableStatus.ENABLE) && auditStatusIds.contains(m.getAuditStatus().getId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(enableMembers)) {
                    member = enableMembers.get(0);
                } else {
                    member = members.get(0);
                }
            }
        }
        if (member == null) {
            return null;
        }

        return accountConverter.toEntity(accountDO, member);
    }

    @Override
    public MemberAccount findLoginAccount(PhoneNumber phone) {
        MemberLoginCredentialDO credentialDO = memberLoginCredentialRepository.findByCredential(phone.getNumber());
        if (credentialDO == null){
            return null;
        }
        MemberAccountDO accountDO = memberAccountMapper.selectById(credentialDO.getAccountId());
        if (accountDO == null || accountDO.getIsDeleted() == LogicDeleteConstant.DELETED) {
            return null;
        }
        return accountConverter.toEntity(accountDO);
    }

    @Override
    public MemberAccount findLoginAccount(MemberSystemId memberSystemId, AccountId accountId, OrganizationId organizationId) {
        LambdaQueryWrapper<MemberAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberAccountDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberAccountDO::getId, accountId.getId());
        MemberAccountDO accountDO = memberAccountMapper.selectOne(queryWrapper);
        if (accountDO == null) {
            return null;
        }
        Member member = null;
        if (memberSystemId != null) {
            if (organizationId != null) {
                member = memberDomainRepository.findByMemberSystemAndAccountId(memberSystemId, accountId, organizationId);
            } else {
                member = memberDomainRepository.findDefaultByMemberSystemAndAccountId(memberSystemId, accountId);
            }
        } else {
            List<Member> members = memberDomainRepository.findByAccountId(accountId);
            List<Integer> auditStatusIds = new ArrayList<>();
            auditStatusIds.add(AuditStatus.STORE_PASSED.getId());
            auditStatusIds.add(AuditStatus.PLATFORM_PASSED.getId());
            if (!members.isEmpty()) {
                List<Member> enableMembers = members.stream().filter(m -> m.getStatus()
                        .equals(EnableDisableStatus.ENABLE) && auditStatusIds.contains(m.getAuditStatus().getId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(enableMembers)) {
                    member = enableMembers.get(0);
                } else {
                    member = members.get(0);
                }
            }
        }
        if (member == null) {
            return null;
        }
        return accountConverter.toEntity(accountDO, member);
    }

    @Override
    public MemberAccount findByPhone(PhoneNumber phone) {
        MemberLoginCredentialDO credentialDO = memberLoginCredentialRepository.findByCredential(phone.getNumber());
        if (credentialDO == null){
            return null;
        }
        MemberAccountDO accountDO = memberAccountMapper.selectById(credentialDO.getAccountId());
        if (accountDO == null || accountDO.getIsDeleted() == LogicDeleteConstant.DELETED) {
            return null;
        }
        return accountConverter.toEntity(accountDO);
    }

    @Override
    public AccountId saveAccount(MemberAccount memberAccount) {
        MemberAccountDO accountDO = accountConverter.toDO(memberAccount);
        if (accountDO.getId() == null) {
            accountDO.setId(sequence.nextId());
        }
        if (memberAccountMapper.selectById(accountDO.getId()) == null) {
            memberAccountMapper.insert(accountDO);
        } else {
            accountDO.setUpdateTime(LocalDateTime.now());
            memberAccountMapper.updateById(accountDO);
        }
        if (memberAccount.getPhone() != null){
            MemberLoginCredentialDO credentialDO = new MemberLoginCredentialDO();
            credentialDO.setAccountId(accountDO.getId());
            credentialDO.setCredentialValue(memberAccount.getPhone().getNumber());
            memberLoginCredentialRepository.saveData(credentialDO);
        }
        return new AccountId(accountDO.getId());
    }

    @Override
    public void removeAccountByAccountId(AccountId accountId) {
        MemberAccountDO update = new MemberAccountDO();
        update.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        update.setDeleteTime(LocalDateTime.now());

        LambdaQueryWrapper<MemberAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberAccountDO::getId, accountId.getId());

        memberAccountMapper.update(update, queryWrapper);
        memberLoginCredentialRepository.removeByAccountId(accountId.getId());
    }
}
