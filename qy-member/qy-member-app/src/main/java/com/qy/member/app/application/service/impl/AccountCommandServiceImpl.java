package com.qy.member.app.application.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.attachment.api.client.AttachmentClient;
import com.qy.authorization.api.client.AuthorizationClient;
import com.qy.authorization.api.command.GenerateAccessTokenCommand;
import com.qy.authorization.api.command.RefreshAccessTokenCommand;
import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.authorization.api.dto.ValidateClientResultDTO;
import com.qy.member.api.client.MemberClient;
import com.qy.member.app.application.assembler.MemberSystemAssembler;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.query.WeixinAppUserQuery;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.repository.WeixinSessionDataRepository;
import com.qy.member.app.application.service.*;
import com.qy.member.app.domain.entity.AccountWeixin;
import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.enums.WeixinLoginStatus;
import com.qy.member.app.domain.event.MemberAccountUpdatedEvent;
import com.qy.member.app.domain.repository.AccountDomainRepository;
import com.qy.member.app.domain.repository.AccountWexinDomainRepository;
import com.qy.member.app.domain.repository.MemberDomainRepository;
import com.qy.member.app.domain.service.AvatarService;
import com.qy.member.app.domain.service.MemberService;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberClientDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.command.AssignRoleToUserCommand;
import com.qy.rbac.api.client.ClientClient;
import com.qy.rbac.api.dto.ClientDTO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.BusinessException;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.UnauthorizedException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.system.api.client.LoginLogClient;
import com.qy.system.api.client.UniqueCodeClient;
import com.qy.system.api.command.LoginLogFormDTO;
import com.qy.system.api.command.UserUniqueCodeFormDTO;
import com.qy.util.IpUtils;
import com.qy.verification.api.client.VerificationCodeClient;
import com.qy.verification.api.dto.ValidateCodeResultDTO;
import com.qy.verification.api.query.ValidateVerificationCodeQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 账号命令服务实现
 *
 * @author legendjw
 */
@Service
public class AccountCommandServiceImpl implements AccountCommandService {
    private MemberDomainRepository memberDomainRepository;
    private AccountDomainRepository accountDomainRepository;
    private MemberSystemDataRepository memberSystemDataRepository;
    private AccountWexinDomainRepository accountWexinDomainRepository;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    private MemberService memberService;
    private WeixinService weixinService;
    private WeixinSessionService weixinSessionService;
    private WeixinSessionDataRepository weixinSessionDataRepository;
    private AccountWeixinService accountWeixinService;
    private VerificationCodeClient verificationCodeClient;
    private AuthorizationClient authorizationClient;
    private AttachmentClient attachmentClient;
    private AvatarService avatarService;
    private RoleManageClient roleManageClient;
    private ApplicationEventPublisher applicationEventPublisher;
    private ServiceMatcher serviceMatcher;
    private final Destination.Factory destinationFactory;
    private MemberSystemAssembler memberSystemAssembler;
    private MemberQueryService memberQueryService;
    private LoginLogClient loginLogClient;
    private UniqueCodeClient uniqueCodeClient;
    private ClientClient clientClient;
    private MemberClientService memberClientService;

    public AccountCommandServiceImpl(MemberDomainRepository memberDomainRepository, AccountDomainRepository accountDomainRepository,
                                     MemberSystemDataRepository memberSystemDataRepository, AccountWexinDomainRepository accountWexinDomainRepository,
                                     MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository,
                                     MemberService memberService, WeixinService weixinService, WeixinSessionService weixinSessionService,
                                     WeixinSessionDataRepository weixinSessionDataRepository, AccountWeixinService accountWeixinService,
                                     VerificationCodeClient verificationCodeClient, AuthorizationClient authorizationClient, AttachmentClient attachmentClient,
                                     AvatarService avatarService, RoleManageClient roleManageClient,
                                     ApplicationEventPublisher applicationEventPublisher, ServiceMatcher serviceMatcher, Destination.Factory destinationFactory,
                                     MemberSystemAssembler memberSystemAssembler, MemberQueryService memberQueryService,
                                     LoginLogClient loginLogClient, UniqueCodeClient uniqueCodeClient, ClientClient clientClient,
                                     MemberClientService memberClientService) {
        this.memberDomainRepository = memberDomainRepository;
        this.accountDomainRepository = accountDomainRepository;
        this.memberSystemDataRepository = memberSystemDataRepository;
        this.accountWexinDomainRepository = accountWexinDomainRepository;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
        this.memberService = memberService;
        this.weixinService = weixinService;
        this.weixinSessionService = weixinSessionService;
        this.weixinSessionDataRepository = weixinSessionDataRepository;
        this.accountWeixinService = accountWeixinService;
        this.verificationCodeClient = verificationCodeClient;
        this.authorizationClient = authorizationClient;
        this.attachmentClient = attachmentClient;
        this.avatarService = avatarService;
        this.roleManageClient = roleManageClient;
        this.applicationEventPublisher = applicationEventPublisher;
        this.serviceMatcher = serviceMatcher;
        this.destinationFactory = destinationFactory;
        this.memberSystemAssembler = memberSystemAssembler;
        this.memberQueryService = memberQueryService;
        this.loginLogClient = loginLogClient;
        this.uniqueCodeClient = uniqueCodeClient;
        this.clientClient = clientClient;
        this.memberClientService = memberClientService;
    }

    @Override
    public AccessTokenDTO loginByPassword(LoginByPasswordCommand command) {
        DynamicDataSourceContextHolder.peek();
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        ClientDTO clientDTO = clientClient.getClient(command.getClientId());
        if (clientDTO == null) {
            throw new ValidationException("未找到指定的客户端");
        }
        //查找会员系统
        MemberSystemDO systemDO = memberSystemDataRepository.findById(clientDTO.getSystemId());
        MemberSystemId memberSystemId = systemDO != null ? new MemberSystemId(systemDO.getId()) : command.getSystemId() != null ? findMemberSystem(command.getSystemId()) : null;
        if (memberSystemId == null) {
            throw new ValidationException("非法的会员系统");
        }

        //验证账号以及密码
        MemberAccount memberAccount = accountDomainRepository.findLoginAccount(memberSystemId,
                new PhoneNumber(command.getUsername()), command.getOrganizationId() != null ? new OrganizationId(command.getOrganizationId()) : null);
        if (memberAccount == null) {
            throw new NotFoundException("此账号尚未注册");
        }
        if (!memberAccount.getPassword().matches(command.getPassword())) {
            throw new ValidationException("账号密码错误");
        }
        if (memberAccount.getStatus().equals(EnableDisableStatus.DISABLE) || memberAccount.getMemberStatus().equals(EnableDisableStatus.DISABLE)) {
            throw new ValidationException("账号已被禁用");
        }
        if (memberAccount.getAuditStatus().equals(AuditStatus.WAITING_AUDIT)) {
            throw new ValidationException("账号审核中");
        }
        if (memberAccount.getAuditStatus().equals(AuditStatus.STORE_REFUSED) || memberAccount.getAuditStatus().equals(AuditStatus.PLATFORM_REFUSED)) {
            throw new ValidationException("账号审核不通过");
        }
        //检测是否需要自动绑定微信
        if (StringUtils.isNotBlank(command.getWeixinToken())) {
            BindWeixinBySessionCommand bindWeixinBySessionCommand = new BindWeixinBySessionCommand();
            bindWeixinBySessionCommand.setAccountId(memberAccount.getAccountId().getId());
            bindWeixinBySessionCommand.setWeixinToken(command.getWeixinToken());
            bindWeixinBySessionCommand.setOrganizationId(command.getOrganizationId());
            bindWeixinBySessionCommand.setSystemId(memberSystemId.getId());
            accountWeixinService.bindWeixin(bindWeixinBySessionCommand);
        }
        return generateAccessToken(command.getClientId(), memberAccount, command.getAppId());
    }

    @Override
    public AccessTokenDTO loginByPhone(LoginByPhoneCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        ClientDTO clientDTO = clientClient.getClient(command.getClientId());
        if (clientDTO == null) {
            throw new ValidationException("未找到指定的客户端");
        }
        //查找会员系统
        MemberSystemDO systemDO = memberSystemDataRepository.findById(clientDTO.getSystemId());
        MemberSystemId memberSystemId = systemDO != null ? new MemberSystemId(systemDO.getId()) : command.getSystemId() != null ? findMemberSystem(command.getSystemId()) : null;

        //验证验证码
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("MEMBER_LOGIN", "SMS",
                command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }

        //验证账号
        MemberAccount memberAccount = accountDomainRepository.findLoginAccount(memberSystemId, new PhoneNumber(command.getPhone()), command.getOrganizationId() != null ? new OrganizationId(command.getOrganizationId()) : null);
        if (memberAccount == null) {
            throw new NotFoundException("此账号尚未注册");
        }
        //账号登录
        if (memberAccount.getStatus().equals(EnableDisableStatus.DISABLE) || memberAccount.getMemberStatus().equals(EnableDisableStatus.DISABLE)) {
            throw new ValidationException("账号已被禁用");
        }

        //检测是否需要自动绑定微信
        if (StringUtils.isNotBlank(command.getWeixinToken())) {
            BindWeixinBySessionCommand bindWeixinBySessionCommand = new BindWeixinBySessionCommand();
            bindWeixinBySessionCommand.setAccountId(memberAccount.getAccountId().getId());
            bindWeixinBySessionCommand.setWeixinToken(command.getWeixinToken());
            accountWeixinService.bindWeixin(bindWeixinBySessionCommand);
        }
        return generateAccessToken(command.getClientId(), memberAccount, command.getAppId());
    }

    @Override
    public WeixinLoginDTO loginByWeixin(LoginByWeixinCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //查找会员系统
        ClientDTO clientDTO = clientClient.getClient(command.getClientId());
        if (clientDTO == null) {
            throw new ValidationException("未找到指定的客户端");
        }
        MemberSystemDO systemDO = memberSystemDataRepository.findById(clientDTO.getSystemId());
        if (systemDO == null) {
            throw new ValidationException("非法的会员系统");
        }
        // 找到微信配置
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findByClientId(command.getClientId());

        //验证账号
        WeixinAppUserQuery weixinAppUserQuery = new WeixinAppUserQuery();
        MemberSystemId memberSystemId = new MemberSystemId(systemDO.getId());
        weixinAppUserQuery.setSystemId(memberSystemId.getId().toString());
        weixinAppUserQuery.setAppId(memberSystemWeixinAppDO.getAppId());
        weixinAppUserQuery.setCode(command.getCode());
        WxAuthUser wxOAuth2UserInfo = weixinService.getWxUserInfo(weixinAppUserQuery);
        if (wxOAuth2UserInfo == null) {
            throw new ValidationException("微信获取授权用户信息失败");
        }
        AccountWeixin accountWeixin = accountWexinDomainRepository.findAccountWeixinByOrganization(
                memberSystemWeixinAppDO.getAppId(),
                wxOAuth2UserInfo.getOpenId(),
                wxOAuth2UserInfo.getUnionId(),
                command.getOrganizationId()
        );
        WeixinLoginDTO weixinLoginDTO = new WeixinLoginDTO();
        //判断是否关注公众号
        if (wxOAuth2UserInfo.getSubscribe() != null && !wxOAuth2UserInfo.getSubscribe()) {
            WeixinSessionDTO weixinSessionDTO = weixinSessionService.createWeixinSession(memberSystemWeixinAppDO.getAppId(), wxOAuth2UserInfo);
            weixinLoginDTO.setStatus(WeixinLoginStatus.NOT_FOLLOW.getId());
            weixinLoginDTO.setWeixinSession(weixinSessionDTO);
            return weixinLoginDTO;
        }
        //未找到微信对应绑定的会员
        if (accountWeixin == null) {
            WeixinSessionDTO weixinSessionDTO = weixinSessionService.createWeixinSession(memberSystemWeixinAppDO.getAppId(), wxOAuth2UserInfo);
            weixinLoginDTO.setStatus(WeixinLoginStatus.NOT_BIND.getId());
            weixinLoginDTO.setWeixinSession(weixinSessionDTO);
            return weixinLoginDTO;
        }
        //找到微信对应绑定的会员
        MemberAccount memberAccount = accountDomainRepository.findLoginAccount(new MemberSystemId(Long.valueOf(memberSystemId.getId())),
                accountWeixin.getMemberAccount().getAccountId(), command.getOrganizationId() != null ? new OrganizationId(command.getOrganizationId()) : null);
        System.out.println("微信会员：" + memberAccount);
        if (memberAccount == null){
            throw new ValidationException("账号不存在");
        }
        //账号登录验证
        if (memberAccount.getStatus().equals(EnableDisableStatus.DISABLE) || memberAccount.getMemberStatus().equals(EnableDisableStatus.DISABLE)) {
            throw new ValidationException("账号已被禁用");
        }
        //如果查找会员账号是通过开放平台查到的，且还没有绑定此微信应用则自动绑定，比如会员一开始绑定了公众号，后来使用小程序登录则自动绑定小程序
        if (!accountWeixin.getAppId().equals(memberSystemWeixinAppDO.getAppId()) && !accountWeixinService.isBindWeixin(memberSystemWeixinAppDO.getAppId(), memberAccount.getAccountId().getId(), command.getOrganizationId())) {
            WeixinSessionDTO weixinSessionDTO = weixinSessionService.createWeixinSession(memberSystemWeixinAppDO.getAppId(), wxOAuth2UserInfo);
            BindWeixinBySessionCommand bindWeixinBySessionCommand = new BindWeixinBySessionCommand();
            bindWeixinBySessionCommand.setAccountId(memberAccount.getAccountId().getId());
            bindWeixinBySessionCommand.setWeixinToken(weixinSessionDTO.getWeixinToken());
            accountWeixinService.bindWeixin(bindWeixinBySessionCommand);
        }

        weixinLoginDTO.setStatus(WeixinLoginStatus.SUCCESS.getId());
        weixinLoginDTO.setAccessToken(generateAccessToken(command.getClientId(), memberAccount, memberSystemWeixinAppDO.getAppId()));
        return weixinLoginDTO;
    }

    @Override
    public AccessTokenDTO switchMemberSystem(SwitchMemberSystemCommand command) {
        ClientDTO clientDTO = clientClient.getClient(command.getClientId());
        if (clientDTO == null) {
            throw new BusinessException("未找到指定的客户端");
        }
        MemberAccount memberAccount = accountDomainRepository.findLoginAccount(new MemberSystemId(clientDTO.getSystemId()), new AccountId(command.getAccountId()), new OrganizationId(command.getOrganizationId()));
        if (memberAccount == null) {
            throw new BusinessException("切换到非法的会员系统");
        }
        if (memberAccount.getStatus().equals(EnableDisableStatus.DISABLE) || memberAccount.getMemberStatus().equals(EnableDisableStatus.DISABLE)) {
            throw new BusinessException("账号已被禁用");
        }
        MemberClientDTO memberClient = memberClientService.getMemberClient(memberAccount.getMemberId().getId(), command.getClientId());
        if (memberClient != null){
            throw new UnauthorizedException("暂无登录权限");
        }

        LoginLogFormDTO formDTO = new LoginLogFormDTO();
        BeanUtils.copyProperties(command, formDTO);
        formDTO.setType(1);
        formDTO.setUserId(memberAccount.getMemberId().getId());
        formDTO.setOrganizationId(command.getOrganizationId());
        formDTO.setExtraData(command.getExtraData());
        formDTO.setIsException(command.getIsException());
        formDTO.setOperatingSystem(command.getOperatingSystem());
        formDTO.setPhoneModel(command.getPhoneModel());
        AccessTokenDTO dto = generateAccessToken(command.getClientId(), memberAccount, command.getAppId());
        dto.setLogId(createLog(formDTO));
        return dto;
    }

    @Override
    public AccessTokenDTO refreshToken(RefreshTokenCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        RefreshAccessTokenCommand refreshAccessTokenCommand = new RefreshAccessTokenCommand();
        refreshAccessTokenCommand.setContextId(MemberSystemSessionContext.contextId);
        refreshAccessTokenCommand.setClientId(command.getClientId());
        refreshAccessTokenCommand.setRefreshToken(command.getRefreshToken());
        return authorizationClient.refreshAccessToken(refreshAccessTokenCommand);
    }

    @Override
    public List<MemberSystemBasicDTO> getSystemsByPassword(LoginByPasswordCommand command) {
        //验证账号以及密码
        MemberAccount memberAccount = accountDomainRepository.findLoginAccount(new PhoneNumber(command.getUsername()));
        if (memberAccount == null) {
            throw new NotFoundException("此账号尚未注册");
        }
        if (!memberAccount.getPassword().matches(command.getPassword())) {
            throw new ValidationException("账号密码错误");
        }
        List<Member> members = memberDomainRepository.findByAccountId(memberAccount.getAccountId());
        if (CollectionUtils.isEmpty(members)) {
            throw new NotFoundException("此账号尚未注册");
        }
        List<MemberSystemDO> memberSystemDOS = memberSystemDataRepository.findByIds(members.stream().map(o -> o.getMemberSystemId().getId()).collect(Collectors.toList()));
        return memberSystemDOS.stream().map(memberSystemDO -> memberSystemAssembler.toBasicDTO(memberSystemDO)).collect(Collectors.toList());
    }

    @Override
    public List<MemberOrganizationBasicDTO> getOrganizations(MemberIdentity identity) {

        return memberSystemDataRepository.findByOrganizationsByAccount(identity.getAccountId(), identity.getMemberSystemId());
    }

    @Override
    public List<MemberSystemBasicDTO> getSystemsByPhone(LoginByPhoneCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //验证验证码
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("MEMBER_LOGIN", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCodeNoUsed(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }
        //验证账号以及密码
        MemberAccount memberAccount = accountDomainRepository.findLoginAccount(new PhoneNumber(command.getPhone()));
        if (memberAccount == null) {
            throw new NotFoundException("此账号尚未注册");
        }
        List<Member> members = memberDomainRepository.findByAccountId(memberAccount.getAccountId());
        if (CollectionUtils.isEmpty(members)) {
            throw new NotFoundException("此账号尚未注册");
        }
        List<MemberSystemDO> memberSystemDOS = memberSystemDataRepository.findByIds(members.stream().map(o -> o.getMemberSystemId().getId()).collect(Collectors.toList()));
        return memberSystemDOS.stream().map(memberSystemDO -> memberSystemAssembler.toBasicDTO(memberSystemDO)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void retrievePasswordByPhone(RetrievePasswordByPhoneCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //查找会员系统
        MemberSystemId memberSystemId = command.getSystemId() != null ? findMemberSystem(command.getSystemId()) : null;

        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("RETRIEVE_PASSWORD",
                "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }

        //验证账号
        MemberAccount memberAccount = accountDomainRepository.findByPhone(new PhoneNumber(command.getPhone()));
        if (memberAccount == null) {
            throw new NotFoundException("此手机号尚未注册无法找回密码");
        }

        memberAccount.modifyPassword(command.getPassword());
        accountDomainRepository.saveAccount(memberAccount);
    }

    @Override
    @Transactional
    public void modifyAccountInfo(ModifyAccountInfoCommand command, MemberIdentity identity) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(identity.getAccountId()));
        memberAccount.modifyName(command.getName());
        if (command.getAvatarAttachmentId() != null) {
            Avatar avatar = avatarService.generateAvatarByAttachment(attachmentClient.getAttachment(command.getAvatarAttachmentId()), memberAccount.getAccountId().getId().toString());
            memberAccount.modifyAvatar(avatar);
        }
        accountDomainRepository.saveAccount(memberAccount);

        //发布会员账号更新事件
        applicationEventPublisher.publishEvent(new MemberAccountUpdatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                memberAccount.getType().getId(),
                memberAccount.getMemberId().getId(),
                memberAccount.getAccountId().getId(),
                memberAccount.getName(),
                memberAccount.getPhone().getNumber(),
                memberAccount.getAvatar().getUrl()
        ));
    }

    @Override
    public void modifyAccountMobileId(ModifyAccountMobileIdCommand command, MemberIdentity identity) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(identity.getAccountId()));
        memberAccount.modifyMobileId(command.getMobileId());
        accountDomainRepository.saveAccount(memberAccount);
    }

    @Override
    @Transactional
    public void changeAccountPhone(ChangePhoneCommand command, MemberIdentity identity) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(identity.getAccountId()));
        //验证密码
        if (!memberAccount.getPassword().matches(command.getPassword())) {
            throw new ValidationException("密码错误，无法更换手机号");
        }
        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("MEMBER_CHANGE_PHONE", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }
        //验证手机账号
        MemberAccount find = accountDomainRepository.findByPhone(new PhoneNumber(command.getPhone()));
        if (find != null) {
            throw new ValidationException("此手机号已经注册账号");
        }

        //更换账号手机号
        memberAccount.changePhone(command.getPhone());
        accountDomainRepository.saveAccount(memberAccount);

        //更换手机号同步更换所有绑定的会员的手机号
        List<Member> members = memberDomainRepository.findByAccountId(memberAccount.getAccountId());
        for (Member member : members) {
            member.changePhone(memberAccount.getPhone().getNumber());
            memberDomainRepository.saveMember(member);
        }

        //发布会员账号更新事件
        applicationEventPublisher.publishEvent(new MemberAccountUpdatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                memberAccount.getType().getId(),
                memberAccount.getMemberId().getId(),
                memberAccount.getAccountId().getId(),
                memberAccount.getName(),
                memberAccount.getPhone().getNumber(),
                memberAccount.getAvatar().getUrl()
        ));
    }

    @Override
    @Transactional
    public void modifyAccountPasswordByPhone(ModifyPasswordCommand command, MemberIdentity identity) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(identity.getAccountId()));
        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("MODIFY_MEMBER_PASSWORD", "SMS", memberAccount.getPhone().getNumber(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }
        memberAccount.modifyPassword(command.getPassword());
        accountDomainRepository.saveAccount(memberAccount);
        authorizationClient.removeToken(identity.getId());
    }

    @Override
    @Transactional
    public void modifyPasswordByPassword(ModifyPasswordCommand command, MemberIdentity identity) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(identity.getAccountId()));
        //验证旧密码
        if (!memberAccount.getPassword().matches(command.getOldPassword())) {
            throw new ValidationException("旧密码错误");
        }
        memberAccount.modifyPassword(command.getPassword());
        accountDomainRepository.saveAccount(memberAccount);
        authorizationClient.removeToken(identity.getId());
    }

    @Override
    @Transactional
    public void modifyPasswordByPhoneAndVerificationCode(com.qy.member.api.command.ModifyPasswordCommand command) {
        MemberAccount memberAccount = accountDomainRepository.findByPhone(new PhoneNumber(command.getPhone()));
        if (memberAccount == null) {
            throw new NotFoundException("此手机号尚未注册无法找回密码");
        }
        memberAccount.modifyPassword(command.getPassword());
        accountDomainRepository.saveAccount(memberAccount);
    }

    @Override
    @Transactional
    public void modifyAccountStatus(ModifyAccountStatusCommand command, EmployeeIdentity identity) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(command.getId()));
        if (memberAccount == null) {
            throw new NotFoundException("未找到指定的会员账号");
        }
        EnableDisableStatus status = EnableDisableStatus.getById(command.getStatusId());
        if (status == null) {
            throw new ValidationException("非法的会员账号状态");
        }
        memberAccount.modifyStatus(status);
        accountDomainRepository.saveAccount(memberAccount);
    }

    @Override
    @Transactional
    public void assignRoleToAccount(AssignRoleToAccountCommand command, EmployeeIdentity identity) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(command.getAccountId()));
        if (memberAccount == null) {
            throw new NotFoundException("未找到指定的会员账号");
        }
        AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
        assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
        assignRoleToUserCommand.setContextId(memberAccount.getMemberSystemId().getId().toString());
        assignRoleToUserCommand.setRoleIds(command.getRoleIds());
        assignRoleToUserCommand.setUserId(memberAccount.getAccountId().getId());
        assignRoleToUserCommand.setUserName(memberAccount.getName());
        roleManageClient.assignRoleToUser(assignRoleToUserCommand);
    }

    @Override
    @Transactional
    public void createChildAccount(CreateChildAccountCommand command, EmployeeIdentity identity) {
        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("MEMBER_CREATE_ACCOUNT", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }

        MemberAccount memberAccount = memberService.createChildAccount(command, identity);
        accountDomainRepository.saveAccount(memberAccount);
    }

    /**
     * 生成访问令牌
     *
     * @param clientId
     * @param memberAccount
     * @return
     */
    private AccessTokenDTO generateAccessToken(String clientId, MemberAccount memberAccount, String appId) {
        GenerateAccessTokenCommand userAccessTokenCommand = new GenerateAccessTokenCommand();
        userAccessTokenCommand.setContextId(MemberSystemSessionContext.contextId);
        userAccessTokenCommand.setClientId(clientId);
        userAccessTokenCommand.setUserId(memberAccount.getAccountId().getId().toString());
        Map<String, String> extraData = new HashMap<>();
        extraData.put("member_id", memberAccount.getMemberId().getId().toString());
        extraData.put("system_id", memberAccount.getMemberSystemId().getId().toString());
        extraData.put("organization_id", memberAccount.getOrganizationId().getId().toString());
        if (appId != null && !"".equals(appId)) {
            extraData.put("app_id", appId);
        }
        userAccessTokenCommand.setExtraData(extraData);
        return authorizationClient.generateAccessToken(userAccessTokenCommand);
    }

    /**
     * 查找会员系统
     *
     * @param systemId
     * @return
     */
    private MemberSystemId findMemberSystem(String systemId) {
        //验证会员系统
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findBySystemId(systemId);
        if (memberSystemDO == null) {
            throw new ValidationException("非法的会员系统");
        }
        return new MemberSystemId(memberSystemDO.getId());
    }

    /**
     * 验证客户端
     *
     * @param clientId
     * @param clientSecret
     */
    private void validateClient(String clientId, String clientSecret) {
        //客户端验证
        ValidateClientResultDTO clientResultDTO = authorizationClient.validateClient(clientId, clientSecret);
        if (!clientResultDTO.isValid()) {
            throw new ValidationException(clientResultDTO.getErrorMessage());
        }
    }


    @Override
    @Transactional
    public void updateAccountPhone(UpdatePhoneCommand command, Long accountId) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(accountId));
        //验证手机账号
        MemberAccount find = accountDomainRepository.findByPhone(new PhoneNumber(command.getPhone()));
        if (find != null) {
            throw new ValidationException("此手机号已经注册账号");
        }

        //更换账号手机号
        memberAccount.changePhone(command.getPhone());
        accountDomainRepository.saveAccount(memberAccount);

        //更换手机号同步更换所有绑定的会员的手机号
        List<Member> members = memberDomainRepository.findByAccountId(memberAccount.getAccountId());
        for (Member member : members) {
            member.changePhone(memberAccount.getPhone().getNumber());
            memberDomainRepository.saveMember(member);
        }

        //发布会员账号更新事件
        applicationEventPublisher.publishEvent(new MemberAccountUpdatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                memberAccount.getType().getId(),
                memberAccount.getMemberId().getId(),
                memberAccount.getAccountId().getId(),
                memberAccount.getName(),
                memberAccount.getPhone().getNumber(),
                memberAccount.getAvatar().getUrl()
        ));
    }

    @Override
    public void modifyAccountPassword(ModifyPasswordCommand command, Long accountId) {
        MemberAccount memberAccount = accountDomainRepository.findById(new AccountId(accountId));
        memberAccount.modifyPassword(command.getPassword());
        accountDomainRepository.saveAccount(memberAccount);
    }

    @Override
    public void logout(LogoutCommand command) {
        authorizationClient.removeToken(command.getMemberId());
        LoginLogFormDTO formDTO = new LoginLogFormDTO();
        BeanUtils.copyProperties(command, formDTO);
        formDTO.setUserId(command.getMemberId());
        formDTO.setType(2);
        formDTO.setOrganizationId(command.getOrganizationId());
        createLog(formDTO);
    }

    @Override
    public void verifyDevice(VerifyDeviceCommand command, MemberIdentity identity) {
        UserUniqueCodeFormDTO formDTO = new UserUniqueCodeFormDTO();
        formDTO.setCode(command.getCode());
        formDTO.setPhone(command.getPhone());
        formDTO.setUserId(identity.getId());
        formDTO.setOrganizationId(identity.getOrganizationId());
        formDTO.setRegistrationId(command.getRegistrationId());
        formDTO.setUniqueCode(command.getUniqueCode());
        formDTO.setLogId(command.getLogId());
        uniqueCodeClient.createLoginLog(formDTO);
    }

    private Long createLog(LoginLogFormDTO formDTO) {
        formDTO.setOperateTime(LocalDateTime.now());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            formDTO.setOperateIp(IpUtils.getIpAddr(request));
            formDTO.setUserAgent(request.getHeader("User-Agent"));
        }
        return loginLogClient.createLoginLog(formDTO);
    }

    public MemberBasicDTO validAccount(String username, String password, Long systemId, Long organizationId) {
        //查找会员系统
        MemberSystemDO systemDO = memberSystemDataRepository.findById(systemId);
        MemberSystemId memberSystemId = systemDO != null ? new MemberSystemId(systemDO.getId()) : systemId != null ? findMemberSystem(systemId.toString()) : null;
        if (memberSystemId == null) {
            throw new ValidationException("非法的会员系统");
        }
        //验证账号以及密码
        MemberAccount memberAccount = accountDomainRepository.findLoginAccount(memberSystemId,
                new PhoneNumber(username), organizationId != null ? new OrganizationId(organizationId) : null);
        if (memberAccount == null) {
            throw new NotFoundException("此账号尚未注册");
        }
        if (!memberAccount.getPassword().matches(password)) {
            throw new ValidationException("账号密码错误");
        }
        if (memberAccount.getStatus().equals(EnableDisableStatus.DISABLE) || memberAccount.getMemberStatus().equals(EnableDisableStatus.DISABLE)) {
            throw new ValidationException("账号已被禁用");
        }
        if (memberAccount.getAuditStatus().equals(AuditStatus.WAITING_AUDIT)) {
            throw new ValidationException("账号审核中");
        }
        if (memberAccount.getAuditStatus().equals(AuditStatus.STORE_REFUSED) || memberAccount.getAuditStatus().equals(AuditStatus.PLATFORM_REFUSED)) {
            throw new ValidationException("账号审核不通过");
        }

        MemberBasicDTO member = memberAccount.getMemberId().getId() != null ? memberQueryService.getBasicMember(memberAccount.getMemberId().getId()) : null;
        return member;
    }

}