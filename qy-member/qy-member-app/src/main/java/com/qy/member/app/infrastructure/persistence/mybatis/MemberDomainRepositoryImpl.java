package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.repository.AccountDomainRepository;
import com.qy.member.app.domain.repository.AccountWexinDomainRepository;
import com.qy.member.app.domain.repository.MemberDomainRepository;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.converter.MemberConverter;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.sequence.Sequence;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class MemberDomainRepositoryImpl implements MemberDomainRepository {
    private MemberConverter memberConverter;
    private MemberMapper memberMapper;
    private AccountDomainRepository accountDomainRepository;
    private AccountWexinDomainRepository accountWexinDomainRepository;
    private Sequence sequence;

    public MemberDomainRepositoryImpl(MemberConverter memberConverter, MemberMapper memberMapper, @Lazy AccountDomainRepository accountDomainRepository, AccountWexinDomainRepository accountWexinDomainRepository, Sequence sequence) {
        this.memberConverter = memberConverter;
        this.memberMapper = memberMapper;
        this.accountDomainRepository = accountDomainRepository;
        this.accountWexinDomainRepository = accountWexinDomainRepository;
        this.sequence = sequence;
    }

    @Override
    public Member findById(MemberId memberId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getId, memberId.getId());
        MemberDO memberDO = memberMapper.selectOne(queryWrapper);
        if (memberDO == null){
            return null;
        }
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(memberDO.getAccountId()));
        if (memberAccount != null){
            memberAccount.changePhone(memberDO.getPhone());
        }
        return memberConverter.toEntity(memberDO, memberAccount);
    }

    @Override
    public Member findByMemberSystemAndPhone(MemberSystemId memberSystemId, PhoneNumber phone, Long organizationId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getSystemId, memberSystemId.getId())
                .eq(MemberDO::getPhone, phone.getNumber())
                .eq(MemberDO::getOrganizationId, organizationId);
        List<MemberDO> memberDOs = memberMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(memberDOs)){
            return null;
        }
        MemberDO memberDO = memberDOs.get(0);
        if (memberDOs.size() > 1){
            List<Integer> auditStatusIds = new ArrayList<>();
            auditStatusIds.add(AuditStatus.STORE_PASSED.getId());
            auditStatusIds.add(AuditStatus.PLATFORM_PASSED.getId());
            List<MemberDO> enableMembers = memberDOs.stream().filter(m -> m.getStatusId()
                    ==EnableDisableStatus.ENABLE.getId() && auditStatusIds.contains(m.getAuditStatusId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(enableMembers)){
                memberDO = enableMembers.get(0);
            }
        }
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(memberDO.getAccountId()));
        return memberConverter.toEntity(memberDO, memberAccount);
    }

    @Override
    public Member findByMemberSystemAndAccountId(MemberSystemId memberSystemId, AccountId accountId, OrganizationId organizationId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getSystemId, memberSystemId.getId())
                .eq(MemberDO::getAccountId, accountId.getId())
                .eq(MemberDO::getOrganizationId, organizationId.getId());
        List<MemberDO> memberDOs = memberMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(memberDOs)){
            return null;
        }
        MemberDO memberDO = memberDOs.get(0);
        if (memberDOs.size() > 1){
            List<Integer> auditStatusIds = new ArrayList<>();
            auditStatusIds.add(AuditStatus.STORE_PASSED.getId());
            auditStatusIds.add(AuditStatus.PLATFORM_PASSED.getId());
            List<MemberDO> enableMembers = memberDOs.stream().filter(m -> m.getStatusId()
                    ==EnableDisableStatus.ENABLE.getId() && auditStatusIds.contains(m.getAuditStatusId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(enableMembers)){
                memberDO = enableMembers.get(0);
            }
        }
        MemberAccount memberAccount = accountDomainRepository.findById(accountId);
        return memberConverter.toEntity(memberDO, memberAccount);
    }

    @Override
    public Member findDefaultByMemberSystemAndAccountId(MemberSystemId memberSystemId, AccountId accountId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getSystemId, memberSystemId.getId())
                .eq(MemberDO::getAccountId, accountId.getId());
        List<MemberDO> memberDOs = memberMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(memberDOs)){
            return null;
        }
        MemberDO memberDO = memberDOs.get(0);
        if (memberDOs.size() > 1){
            List<Integer> auditStatusIds = new ArrayList<>();
            auditStatusIds.add(AuditStatus.STORE_PASSED.getId());
            auditStatusIds.add(AuditStatus.PLATFORM_PASSED.getId());
            List<MemberDO> enableMembers = memberDOs.stream().filter(m -> m.getStatusId()
                    ==EnableDisableStatus.ENABLE.getId() && auditStatusIds.contains(m.getAuditStatusId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(enableMembers)){
                memberDO = enableMembers.get(0);
            }
        }
        MemberAccount memberAccount = accountDomainRepository.findById(accountId);
        return memberConverter.toEntity(memberDO, memberAccount);
    }

    @Override
    public List<Member> findByAccountId(AccountId accountId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getAccountId, accountId.getId())
                .orderByAsc(MemberDO::getCreateTime);
        List<MemberDO> memberDOS = memberMapper.selectList(queryWrapper);
        if (memberDOS.isEmpty()) { return new ArrayList<>(); }
        MemberAccount memberAccount = accountDomainRepository.findById(accountId);
        return memberDOS.stream().map(m -> memberConverter.toEntity(m, memberAccount)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MemberId saveMember(Member member) {
        MemberDO memberDO = memberConverter.toDO(member);
        if (memberDO.getId() == null) {
            memberDO.setId(sequence.nextId());
        }
        if (memberMapper.selectById(memberDO.getId()) == null) {
            memberMapper.insert(memberDO);
        }
        else {
            memberDO.setUpdateTime(LocalDateTime.now());
            memberMapper.updateById(memberDO);
        }
        accountDomainRepository.saveAccount(member.getPrimaryAccount());
        return new MemberId(memberDO.getId());
    }

    @Override
    public MemberId saveMemberWithoutAccount(Member member) {
        MemberDO memberDO = memberConverter.toDO(member);
        if (memberDO.getId() == null) {
            memberDO.setId(sequence.nextId());
        }
        if (memberMapper.selectById(memberDO.getId()) == null) {
            memberMapper.insert(memberDO);
        }
        else {
            memberDO.setUpdateTime(LocalDateTime.now());
            memberMapper.updateById(memberDO);
        }
        return new MemberId(memberDO.getId());
    }

    @Override
    public boolean isAccountAllMemberRemoved(AccountId accountId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getAccountId, accountId.getId());
        return memberMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public void removeMember(MemberId memberId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getId, memberId.getId());
        MemberDO memberDO = memberMapper.selectOne(queryWrapper);
        if (memberDO == null) { return; }
        AccountId accountId = new AccountId(memberDO.getAccountId());
        memberDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        memberDO.setDeleteTime(LocalDateTime.now());
        memberMapper.updateById(memberDO);

        //删除会员系统下会员绑定的微信
        accountWexinDomainRepository.removeMemberAccountWeixinByAccountIdAndSystemId(accountId, new MemberSystemId(memberDO.getSystemId()));
        //如果会员账号下绑定的会员全部已经删除了，则删除会员账号以及绑定微信关系
        if (isAccountAllMemberRemoved(accountId)) {
            accountDomainRepository.removeAccountByAccountId(accountId);
            accountWexinDomainRepository.removeMemberAccountWeixinByAccountId(accountId);
        }
    }

    @Override
    public void resetMemberPassword(MemberId memberId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getId, memberId.getId());
        MemberDO memberDO = memberMapper.selectOne(queryWrapper);
        if (memberDO == null) { return; }
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(memberDO.getAccountId()));
        memberAccount.modifyPassword("12345678");
        accountDomainRepository.saveAccount(memberAccount);
    }

    @Override
    public void unbindMemberWeixin(MemberId memberId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getId, memberId.getId());
        MemberDO memberDO = memberMapper.selectOne(queryWrapper);
        if (memberDO == null) { return; }
        accountWexinDomainRepository.removeMemberAccountWeixinByAccountIdAndSystemId(new AccountId(memberDO.getAccountId()), new MemberSystemId(memberDO.getSystemId()));
    }
}
