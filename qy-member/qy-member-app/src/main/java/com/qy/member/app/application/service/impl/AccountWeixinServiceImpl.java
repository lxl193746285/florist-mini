package com.qy.member.app.application.service.impl;

import com.qy.member.app.application.command.BindWeixinBySessionCommand;
import com.qy.member.app.application.command.BindWeixinCommand;
import com.qy.member.app.application.dto.BindWeixinInfoDTO;
import com.qy.member.app.application.query.WeixinAppUserQuery;
import com.qy.member.app.application.repository.*;
import com.qy.member.app.application.service.AccountWeixinService;
import com.qy.member.app.application.service.WeixinService;
import com.qy.member.app.domain.entity.AccountWeixin;
import com.qy.member.app.domain.enums.WeixinAppType;
import com.qy.member.app.domain.repository.AccountWexinDomainRepository;
import com.qy.member.app.domain.valueobject.AccountId;
import com.qy.member.app.domain.valueobject.MemberId;
import com.qy.member.app.domain.valueobject.OrganizationId;
import com.qy.member.app.domain.valueobject.WxAuthUser;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.*;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.MemberIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 账号微信服务实现
 *
 * @author legendjw
 */
@Service
public class AccountWeixinServiceImpl implements AccountWeixinService {
    private static final Logger logger = LoggerFactory.getLogger(AccountWeixinServiceImpl.class);
    private AccountWexinDomainRepository accountWexinDomainRepository;
    private WeixinSessionDataRepository weixinSessionDataRepository;
    private MemberWeixinDataRepository memberWeixinDataRepository;
    private AccountDataRepository accountDataRepository;
    private MemberDataRepository memberDataRepository;
    private MemberSystemDataRepository memberSystemDataRepository;
    private WeixinService weixinService;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;

    public AccountWeixinServiceImpl(AccountWexinDomainRepository accountWexinDomainRepository,
                                    WeixinSessionDataRepository weixinSessionDataRepository,
                                    MemberWeixinDataRepository memberWeixinDataRepository,
                                    AccountDataRepository accountDataRepository,
                                    MemberDataRepository memberDataRepository,
                                    MemberSystemDataRepository memberSystemDataRepository,
                                    @Lazy WeixinService weixinService,
                                    MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository) {
        this.accountWexinDomainRepository = accountWexinDomainRepository;
        this.weixinSessionDataRepository = weixinSessionDataRepository;
        this.memberWeixinDataRepository = memberWeixinDataRepository;
        this.accountDataRepository = accountDataRepository;
        this.memberDataRepository = memberDataRepository;
        this.memberSystemDataRepository = memberSystemDataRepository;
        this.weixinService = weixinService;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
    }

    @Override
    public boolean isBindWeixin(String appId, Long accountId, Long organizationId) {
        AccountWeixin accountWeixin = accountWexinDomainRepository.findAccountWeixinByAccountId(appId, new AccountId(accountId), organizationId);
        if (accountWeixin == null) {
            return false;
        }
        return accountWeixin.isBindWeixin();
    }

    @Override
    public void bindWeixin(BindWeixinCommand command, MemberIdentity identity) {
        AccountWeixin accountWeixin = accountWexinDomainRepository.findAccountWeixinByAccountId(command.getAppId(),
                new AccountId(identity.getAccountId()), identity.getOrganizationId());
        //查找微信授权用户
        WeixinAppUserQuery weixinAppUserQuery = new WeixinAppUserQuery();
        weixinAppUserQuery.setAppId(command.getAppId());
        weixinAppUserQuery.setCode(command.getCode());
        WxAuthUser wxOAuth2UserInfo = weixinService.getWxUserInfo(weixinAppUserQuery);
        if (wxOAuth2UserInfo == null) {
            throw new ValidationException("微信获取授权用户信息失败");
        }
        if (accountWexinDomainRepository.countAccountWeixinByOpenIdAndUnionId(
                command.getAppId(),
                wxOAuth2UserInfo.getOpenId(),
                wxOAuth2UserInfo.getUnionId()) > 0) {
            throw new ValidationException("此微信账号已经绑定了其他账号");
        }

        accountWeixin.bindWeixin(wxOAuth2UserInfo);
        accountWexinDomainRepository.save(accountWeixin);
    }

    @Override
    public void unbindWeixin(BindWeixinCommand command, MemberIdentity identity) {
        MemberSystemWeixinAppDO weixinAppDO = memberSystemWeixinAppDataRepository.findByClientId(command.getClientId());
        if (weixinAppDO == null) {
            throw new ValidationException("微信应用不存在");
        }
        //查找微信授权用户
        WeixinAppUserQuery weixinAppUserQuery = new WeixinAppUserQuery();
        weixinAppUserQuery.setAppId(weixinAppDO.getAppId());
        weixinAppUserQuery.setCode(command.getCode());
        weixinAppUserQuery.setSystemId(weixinAppDO.getSystemId().toString());
        WxAuthUser wxOAuth2UserInfo = weixinService.getWxUserInfo(weixinAppUserQuery);
        if (wxOAuth2UserInfo == null) {
            throw new ValidationException("微信获取授权用户信息失败");
        }
        AccountWeixin accountWeixin = accountWexinDomainRepository
                .findAccountWeixinByOrganization(weixinAppDO.getAppId(), wxOAuth2UserInfo.getOpenId(),
                        wxOAuth2UserInfo.getUnionId(), identity.getOrganizationId());
        if (accountWeixin == null){
            throw new ValidationException("微信账号未绑定");
        }
        accountWeixin.unbindWeixin(wxOAuth2UserInfo);
        accountWexinDomainRepository.save(accountWeixin);
    }

    @Override
    public void bindWeixin(BindWeixinBySessionCommand command) {
        //查找微信授权用户
        WeixinSessionDO weixinSessionDO = weixinSessionDataRepository.findByWeixinToken(command.getWeixinToken());
        if (weixinSessionDO == null) {
            return;
        }
        MemberDO memberDO = memberDataRepository.findByAccountSystem(command.getAccountId(),
                command.getSystemId(), command.getOrganizationId());

        AccountWeixin accountWeixin = accountWexinDomainRepository.findAccountWeixinByMemberId(weixinSessionDO.getAppId(),
                new MemberId(memberDO.getId()),new AccountId(command.getAccountId()));
        //如果账号已经绑定了微信则不再绑定 2022-07改为报错，不允许登录
        if (accountWeixin == null || (accountWeixin.isBindWeixin() && accountWeixin.getWxUser().getOpenId().equals(weixinSessionDO.getOpenId()))) {
            return;
        }
        if (accountWeixin.isBindWeixin() && !accountWeixin.getWxUser().getOpenId().equals(weixinSessionDO.getOpenId())) {
            throw new ValidationException("当前账号已绑定其他微信");
        }
        accountWeixin.getMemberAccount().modifyMemberId(new MemberId(memberDO.getId()));
        accountWeixin.getMemberAccount().modifyOrganizationId(new OrganizationId(command.getOrganizationId()));
        //如果此微信已经绑定了其他账号则不再绑定 20220-07改为报错，不允许登录
        if (accountWexinDomainRepository.countAccountWeixinByOrganization(
                weixinSessionDO.getAppId(),
                weixinSessionDO.getOpenId(),
                weixinSessionDO.getUnionId(),
                command.getOrganizationId()) > 0) {
            throw new ValidationException("此微信已经绑定了其他账号");
        }

        WxAuthUser wxOAuth2UserInfo = WxAuthUser.builder()
                .openId(weixinSessionDO.getOpenId())
                .unionId(weixinSessionDO.getUnionId())
                .build();

        accountWeixin.bindWeixin(wxOAuth2UserInfo);
        accountWexinDomainRepository.save(accountWeixin);
    }

    @Override
    public BindWeixinInfoDTO getAccountBindWeixinInfo(String appId, Long accountId) {
        AccountWeixin accountWeixin = accountWexinDomainRepository.findAccountWeixinByAccountId(appId, new AccountId(accountId), null);
        if (accountWeixin == null) {
            return null;
        }
        BindWeixinInfoDTO bindWeixinInfoDTO = new BindWeixinInfoDTO();
        bindWeixinInfoDTO.setOpenId(accountWeixin.getWxUser() != null ? accountWeixin.getWxUser().getOpenId() : null);
        bindWeixinInfoDTO.setUnionId(accountWeixin.getWxUser() != null ? accountWeixin.getWxUser().getUnionId() : null);
        return bindWeixinInfoDTO;
    }

    @Override
    public BindWeixinInfoDTO getAccountBindWeixinInfoByPayWay(String subPayWay, Long memberId) {
        MemberDO memberDO = memberDataRepository.findById(memberId);
        if (memberDO == null) {
            return null;
        }

        //jsapi支付使用绑定公众号的会员信息
        MemberWeixinDO memberWeixinDO = null;
        if (subPayWay.equals("JSAPI") || subPayWay.equals("WAP")) {
            memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndAppTypeAndAccountId(memberDO.getSystemId(), WeixinAppType.MP.getId(), memberDO.getAccountId());
            //此处兼容项目使用单个公众号切换门店的场景
            if (memberWeixinDO != null) {
                MemberSystemDO defaultMemberSystem = memberSystemDataRepository.findDefaultMemberSystem();
                if (defaultMemberSystem != null) {
                    memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndAppTypeAndAccountId(defaultMemberSystem.getId(), WeixinAppType.MP.getId(), memberDO.getAccountId());
                }
            }
        }
        //小程序支付使用绑定小程序的会员信息
        else if (subPayWay.equals("MINI")) {
            memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndAppTypeAndAccountId(memberDO.getSystemId(), WeixinAppType.MINI.getId(), memberDO.getAccountId());
            //此处兼容项目使用单个小程序切换门店的场景
            if (memberWeixinDO != null) {
                MemberSystemDO defaultMemberSystem = memberSystemDataRepository.findDefaultMemberSystem();
                if (defaultMemberSystem != null) {
                    memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndAppTypeAndAccountId(defaultMemberSystem.getId(), WeixinAppType.MP.getId(), memberDO.getAccountId());
                }
            }
        }

        if (memberWeixinDO == null) {
            return null;
        }
        BindWeixinInfoDTO bindWeixinInfoDTO = new BindWeixinInfoDTO();
        bindWeixinInfoDTO.setOpenId(memberWeixinDO.getOpenId());
        bindWeixinInfoDTO.setUnionId(memberWeixinDO.getUnionId());
        return bindWeixinInfoDTO;
    }
}
