package com.qy.member.app.domain.service.impl;

import com.qy.member.app.application.command.CreateChildAccountCommand;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AccountType;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.enums.MemberType;
import com.qy.member.app.domain.repository.AccountDomainRepository;
import com.qy.member.app.domain.repository.MemberDomainRepository;
import com.qy.member.app.domain.service.AvatarService;
import com.qy.member.app.domain.service.MemberService;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.sequence.Sequence;
import com.qy.security.session.EmployeeIdentity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 会员服务实现
 *
 * @author legendjw
 */
@Service
public class MemberServiceImpl implements MemberService {
    private MemberDomainRepository memberDomainRepository;
    private AccountDomainRepository accountDomainRepository;
    private MemberSystemDataRepository memberSystemDataRepository;
    private AvatarService avatarService;
    private Sequence sequence;

    public MemberServiceImpl(MemberDomainRepository memberDomainRepository, AccountDomainRepository accountDomainRepository, MemberSystemDataRepository memberSystemDataRepository, AvatarService avatarService, Sequence sequence) {
        this.memberDomainRepository = memberDomainRepository;
        this.accountDomainRepository = accountDomainRepository;
        this.memberSystemDataRepository = memberSystemDataRepository;
        this.avatarService = avatarService;
        this.sequence = sequence;
    }

    @Override
    public Member createMember(MemberSystemId memberSystemId, OrganizationId organizationId, String name, Gender gender,
                               String avatar, Address address, String invitationCode, PhoneNumber phoneNumber, String password,
                               AuditStatus auditStatus, Integer memberType) {
        //验证会员系统
        MemberSystemDO memberSystemDO = memberSystemId != null ? memberSystemDataRepository.findById(memberSystemId.getId()) : memberSystemDataRepository.findDefaultMemberSystem();
        if (memberSystemDO == null) {
            throw new ValidationException("非法的会员系统");
        }
        if (organizationId == null) {
            organizationId = new OrganizationId(memberSystemDO.getOrganizationId());
        }
        MemberId memberId = new MemberId(sequence.nextId());

        //验证手机号
        if (memberDomainRepository.findByMemberSystemAndPhone(memberSystemId, phoneNumber, organizationId.getId()) != null) {
            throw new ValidationException("此手机号已经注册会员");
        }
        //处理密码
        Password hashPassword = password != null ? new Password(password) : null;
        //名称处理
        if (StringUtils.isBlank(name)) {
            name = generateRandomNickname();
        }
        //生成头像
        Avatar memberAvatar = StringUtils.isBlank(avatar) ? avatarService.generateAvatarByName(name, memberId.getId().toString()) : avatarService.generateAvatarByImageUrl(avatar, memberId.getId().toString());;
        //处理审核状态
        if (auditStatus == null) {
            auditStatus = memberSystemDO.getIsMemberAudit().intValue() == 1 ? AuditStatus.WAITING_AUDIT : AuditStatus.PLATFORM_PASSED;
        }

        //如果未注册会员账号则创建账号
        MemberAccount memberAccount = accountDomainRepository.findByPhone(phoneNumber);
        if (memberAccount == null) {
            memberAccount = MemberAccount.builder()
                    .accountId(new AccountId(sequence.nextId()))
                    .name(name)
                    .type(AccountType.PRIMARY)
                    .avatar(memberAvatar)
                    .phone(phoneNumber)
                    .password(hashPassword)
                    .status(EnableDisableStatus.ENABLE)
                    .createTime(LocalDateTime.now())
                    .build();
        } else {
            memberAccount.changePhone(phoneNumber.getNumber());
        }

        //创建会员
        Member member = Member.builder()
                .memberId(memberId)
                .organizationId(organizationId)
                .memberSystemId(memberSystemId)
                .invitationCode(invitationCode)
                .name(new PinyinName(name))
                .phone(phoneNumber)
                .avatar(memberAvatar)
                .gender(gender)
                .address(address)
                .isOpenAccount(false)
                .status(EnableDisableStatus.ENABLE)
                .auditStatus(auditStatus)
                .createTime(LocalDateTime.now())
                .primaryAccount(memberAccount)
                .memberType(MemberType.getById(memberType))
                .build();
        return member;
    }

    @Override
    public Member createMemberWithoutAccount(MemberSystemId memberSystemId, OrganizationId organizationId, String name, Gender gender, String avatar, Address address, String invitationCode, PhoneNumber phoneNumber, Integer memberType) {
        //验证会员系统
        MemberSystemDO memberSystemDO = memberSystemId != null ? memberSystemDataRepository.findById(memberSystemId.getId()) : memberSystemDataRepository.findDefaultMemberSystem();
        if (memberSystemDO == null) {
            throw new ValidationException("非法的会员系统");
        }
        if (organizationId == null) {
            organizationId = new OrganizationId(memberSystemDO.getOrganizationId());
        }
        MemberId memberId = new MemberId(sequence.nextId());

        //验证手机号
        if (memberDomainRepository.findByMemberSystemAndPhone(memberSystemId, phoneNumber, organizationId.getId()) != null) {
            throw new ValidationException("此手机号已经注册会员");
        }
        //名称处理
        if (StringUtils.isBlank(name)) {
            name = generateRandomNickname();
        }
        //生成头像
        Avatar memberAvatar = StringUtils.isBlank(avatar) ? avatarService.generateAvatarByName(name, memberId.getId().toString()) : avatarService.generateAvatarByImageUrl(avatar, memberId.getId().toString());;
        //处理审核状态

        //创建会员
        Member member = Member.builder()
                .memberId(memberId)
                .organizationId(organizationId)
                .memberSystemId(memberSystemId)
                .invitationCode(invitationCode)
                .name(new PinyinName(name))
                .phone(phoneNumber)
                .avatar(memberAvatar)
                .gender(gender)
                .address(address)
                .isOpenAccount(false)
                .status(EnableDisableStatus.ENABLE)
                .auditStatus(AuditStatus.WAITING_AUDIT)
                .createTime(LocalDateTime.now())
                .memberType(MemberType.getById(memberType))
                .build();
        return member;
    }

    @Override
    public MemberAccount createChildAccount(CreateChildAccountCommand command, EmployeeIdentity identity) {
        MemberId memberId = new MemberId(command.getMemberId());
        Member member = memberDomainRepository.findById(memberId);
        if (member == null) {
            throw new NotFoundException("创建子账号未找到指定的会员");
        }
        //验证手机号
        PhoneNumber phoneNumber = new PhoneNumber(command.getPhone().trim());
        if (accountDomainRepository.findByPhone(phoneNumber) != null) {
            throw new ValidationException("此手机号已经注册过会员账号");
        }
        AccountId accountId = new AccountId(sequence.nextId());
        //生成头像
        Avatar avatar = avatarService.generateAvatarByName(command.getName(), accountId.getId().toString());

        MemberAccount memberAccount = MemberAccount.builder()
                .accountId(accountId)
                .memberId(memberId)
                .name(command.getName())
                .memberSystemId(member.getMemberSystemId())
                .type(AccountType.CHILD)
                .avatar(avatar)
                .phone(new PhoneNumber(command.getPhone()))
                .password(Password.defaultPassword())
                .status(EnableDisableStatus.ENABLE)
                .createTime(LocalDateTime.now())
                .creator(new User(identity.getId(), identity.getName()))
                .build();
        return memberAccount;
    }

    /**
     * 生成随机昵称
     *
     * @return
     */
    private String generateRandomNickname() {
        return RandomStringUtils.randomAlphabetic(6);
    }
}