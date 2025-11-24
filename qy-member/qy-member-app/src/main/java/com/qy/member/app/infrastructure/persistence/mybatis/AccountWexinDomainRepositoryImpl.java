package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.domain.entity.AccountWeixin;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.repository.AccountDomainRepository;
import com.qy.member.app.domain.repository.AccountWexinDomainRepository;
import com.qy.member.app.domain.valueobject.AccountId;
import com.qy.member.app.domain.valueobject.MemberId;
import com.qy.member.app.domain.valueobject.MemberSystemId;
import com.qy.member.app.domain.valueobject.WxAuthUser;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberWeixinDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberWeixinMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信账号微信领域资源库实现
 *
 * @author legendjw
 */
@Repository
public class AccountWexinDomainRepositoryImpl implements AccountWexinDomainRepository {
    private MemberWeixinMapper memberWeixinMapper;
    private AccountDomainRepository accountDomainRepository;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;

    public AccountWexinDomainRepositoryImpl(MemberWeixinMapper memberWeixinMapper, AccountDomainRepository accountDomainRepository, MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository) {
        this.memberWeixinMapper = memberWeixinMapper;
        this.accountDomainRepository = accountDomainRepository;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
    }

    @Override
    public AccountWeixin findAccountWeixinByAccountId(String appId, AccountId accountId, Long organizationId) {
        MemberAccount memberAccount = accountDomainRepository.findById(accountId);
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getAppId, appId)
                .eq(MemberWeixinDO::getAccountId, memberAccount.getAccountId().getId())
                .eq(organizationId != null, MemberWeixinDO::getOrganizationId, organizationId);
        MemberWeixinDO memberWeixinDO = memberWeixinMapper.selectOne(queryWrapper);

        WxAuthUser wxUser = null;
        if (memberWeixinDO != null) {
            wxUser = WxAuthUser.builder()
                    .openId(memberWeixinDO.getOpenId())
                    .unionId(memberWeixinDO.getUnionId())
                    .build();
        }
        return new AccountWeixin(appId, memberAccount, wxUser);
    }

    @Override
    public AccountWeixin findAccountWeixinByMemberId(String appId, MemberId memberId, AccountId accountId) {
        MemberAccount memberAccount = accountDomainRepository.findById(accountId);
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getAppId, appId)
                .eq(MemberWeixinDO::getAccountId, memberAccount.getAccountId().getId())
                .eq(MemberWeixinDO::getMemberId, memberId.getId());
        MemberWeixinDO memberWeixinDO = memberWeixinMapper.selectOne(queryWrapper);

        WxAuthUser wxUser = null;
        if (memberWeixinDO != null) {
            wxUser = WxAuthUser.builder()
                    .openId(memberWeixinDO.getOpenId())
                    .unionId(memberWeixinDO.getUnionId())
                    .build();
        }
        return new AccountWeixin(appId, memberAccount, wxUser);
    }

    @Override
    public AccountWeixin findAccountWeixinByOpenIdAndUnionId(String appId, String openId, String unionId) {
        if (StringUtils.isBlank(openId) && StringUtils.isBlank(unionId)) {
            return null;
        }
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .last("limit 1");
        if (StringUtils.isNotBlank(unionId)) {
            queryWrapper.eq(MemberWeixinDO::getUnionId, unionId);
        } else if (StringUtils.isNotBlank(openId)) {
            queryWrapper.eq(MemberWeixinDO::getAppId, appId).eq(MemberWeixinDO::getOpenId, openId);
        }
        MemberWeixinDO memberWeixinDO = memberWeixinMapper.selectOne(queryWrapper);
        if (memberWeixinDO == null) {
            return null;
        }

        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(memberWeixinDO.getAccountId()));
        WxAuthUser wxUser = WxAuthUser.builder()
                .openId(memberWeixinDO.getOpenId())
                .unionId(memberWeixinDO.getUnionId())
                .build();

        return new AccountWeixin(memberWeixinDO.getAppId(), memberAccount, wxUser);
    }

    @Override
    public AccountWeixin findAccountWeixinByOrganization(String appId, String openId, String unionId, Long organizationId) {
        if (StringUtils.isBlank(openId) && StringUtils.isBlank(unionId)) {
            return null;
        }
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .last("limit 1");
        if (StringUtils.isNotBlank(unionId)) {
            queryWrapper.eq(MemberWeixinDO::getUnionId, unionId);
        } else if (StringUtils.isNotBlank(openId)) {
            queryWrapper.eq(MemberWeixinDO::getAppId, appId).eq(MemberWeixinDO::getOpenId, openId);
        }
        queryWrapper.eq(organizationId != null, MemberWeixinDO::getOrganizationId, organizationId);
        MemberWeixinDO memberWeixinDO = memberWeixinMapper.selectOne(queryWrapper);
        if (memberWeixinDO == null) {
            return null;
        }
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(memberWeixinDO.getAccountId()));
        memberAccount.modifyMemberId(new MemberId(memberWeixinDO.getMemberId()));
        WxAuthUser wxUser = WxAuthUser.builder()
                .openId(memberWeixinDO.getOpenId())
                .unionId(memberWeixinDO.getUnionId())
                .build();

        return new AccountWeixin(memberWeixinDO.getAppId(), memberAccount, wxUser);
    }

    @Override
    public int countAccountWeixinByOpenIdAndUnionId(String appId, String openId, String unionId) {
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getAppId, appId);

        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(unionId)) {
            queryWrapper.and(i -> i.eq(MemberWeixinDO::getOpenId, openId).or().eq(MemberWeixinDO::getUnionId, unionId));
        } else if (StringUtils.isNotBlank(openId)) {
            queryWrapper.eq(MemberWeixinDO::getOpenId, openId);
        } else if (StringUtils.isNotBlank(unionId)) {
            queryWrapper.eq(MemberWeixinDO::getUnionId, unionId);
        }
        return memberWeixinMapper.selectCount(queryWrapper);
    }

    @Override
    public int countAccountWeixinByOrganization(String appId, String openId, String unionId, Long organizationId) {
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getAppId, appId);

        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(unionId)) {
            queryWrapper.and(i -> i.eq(MemberWeixinDO::getOpenId, openId).or().eq(MemberWeixinDO::getUnionId, unionId));
        } else if (StringUtils.isNotBlank(openId)) {
            queryWrapper.eq(MemberWeixinDO::getOpenId, openId);
        } else if (StringUtils.isNotBlank(unionId)) {
            queryWrapper.eq(MemberWeixinDO::getUnionId, unionId);
        }
        queryWrapper.eq(organizationId != null, MemberWeixinDO::getOrganizationId, organizationId);
        return memberWeixinMapper.selectCount(queryWrapper);
    }

    @Override
    public void save(AccountWeixin accountWeixin) {
        //解绑
        if (accountWeixin.getWxUser() == null) {
            LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(MemberWeixinDO::getSystemId, accountWeixin.getMemberAccount().getMemberSystemId().getId())
                    .eq(MemberWeixinDO::getMemberId, accountWeixin.getMemberAccount().getMemberId().getId())
                    .eq(MemberWeixinDO::getAccountId, accountWeixin.getMemberAccount().getAccountId().getId())
                    .eq(MemberWeixinDO::getAppId, accountWeixin.getAppId());
//            List<MemberWeixinDO> memberWeixinDOList = memberWeixinMapper.selectList(queryWrapper);
//            if (!CollectionUtils.isEmpty(memberWeixinDOList)) {
//                memberWeixinMapper.delete(queryWrapper);
//                if (StringUtils.isNotBlank(memberWeixinDOList.get(0).getUnionId())) {
//                    LambdaQueryWrapper<MemberWeixinDO> unionIdQueryWrapper = new LambdaQueryWrapper<>();
//                    unionIdQueryWrapper.eq(MemberWeixinDO::getUnionId, memberWeixinDOList.get(0).getUnionId());
//                    memberWeixinMapper.delete(unionIdQueryWrapper);
//                }
//            }
            memberWeixinMapper.delete(queryWrapper);
        }
        //绑定
        else {
            MemberWeixinDO memberWeixinDO = new MemberWeixinDO();
            memberWeixinDO.setSystemId(accountWeixin.getMemberAccount().getMemberSystemId().getId());
            memberWeixinDO.setMemberId(accountWeixin.getMemberAccount().getMemberId().getId());
            memberWeixinDO.setAccountId(accountWeixin.getMemberAccount().getAccountId().getId());
            memberWeixinDO.setAppId(accountWeixin.getAppId());
            memberWeixinDO.setOpenId(accountWeixin.getWxUser().getOpenId());
            memberWeixinDO.setUnionId(accountWeixin.getWxUser().getUnionId());
            memberWeixinDO.setCreateTime(LocalDateTime.now());
            memberWeixinDO.setOrganizationId(accountWeixin.getMemberAccount().getOrganizationId().getId());
            memberWeixinMapper.insert(memberWeixinDO);
        }
    }

    @Override
    public void removeMemberAccountWeixinByAccountId(AccountId accountId) {
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getAccountId, accountId.getId());
        memberWeixinMapper.delete(queryWrapper);
    }

    @Override
    public void removeMemberAccountWeixinByAccountIdAndSystemId(AccountId accountId, MemberSystemId memberSystemId) {
        List<MemberSystemWeixinAppDO> memberSystemWeixinAppDOList = memberSystemWeixinAppDataRepository.findBySystemId(memberSystemId.getId());
        if (CollectionUtils.isEmpty(memberSystemWeixinAppDOList)) {
            return;
        }
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getAccountId, accountId.getId())
                .in(MemberWeixinDO::getAppId, memberSystemWeixinAppDOList.stream().map(MemberSystemWeixinAppDO::getAppId).collect(Collectors.toList()));
        memberWeixinMapper.delete(queryWrapper);
    }
}
