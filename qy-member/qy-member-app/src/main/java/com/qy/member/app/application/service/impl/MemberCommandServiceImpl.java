package com.qy.member.app.application.service.impl;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.attachment.api.dto.AttachmentDTO;
import com.qy.audit.api.client.AuditLogClient;
import com.qy.audit.api.command.CreateAuditLogCommand;
import com.qy.audit.api.enums.AuditType;
import com.qy.authorization.api.client.AuthorizationClient;
import com.qy.authorization.api.dto.ValidateClientResultDTO;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.customer.api.client.BusinessLicenseClient;
import com.qy.customer.api.client.ContactClient;
import com.qy.customer.api.command.BatchSaveContactCommand;
import com.qy.customer.api.command.BusinessLicenseForm;
import com.qy.customer.api.command.ContactForm;
import com.qy.customer.api.command.SaveBusinessLicenseCommand;
import com.qy.customer.api.dto.ContactDTO;
import com.qy.customer.api.dto.RelatedModuleDataDTO;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.MemberIdDTO;
import com.qy.member.app.application.repository.WeixinSessionDataRepository;
import com.qy.member.app.application.service.AccountWeixinService;
import com.qy.member.app.application.service.MemberClientService;
import com.qy.member.app.application.service.MemberCommandService;
import com.qy.member.app.domain.entity.Member;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.AccountType;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.enums.Module;
import com.qy.member.app.domain.event.*;
import com.qy.member.app.domain.repository.AccountDomainRepository;
import com.qy.member.app.domain.repository.MemberDomainRepository;
import com.qy.member.app.domain.service.AvatarService;
import com.qy.member.app.domain.service.MemberService;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.WeixinSessionDO;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.command.AssignRoleToUserCommand;
import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.rbac.api.client.MenuClient;
import com.qy.region.api.AreaClient;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.sequence.Sequence;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.verification.api.client.VerificationCodeClient;
import com.qy.verification.api.dto.ValidateCodeResultDTO;
import com.qy.verification.api.query.ValidateVerificationCodeQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 会员命令服务实现
 *
 * @author legendjw
 */
@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private static final Logger logger = LoggerFactory.getLogger(MemberCommandServiceImpl.class);
    public static String genderCodeTable = "gender";
    private MemberDomainRepository memberDomainRepository;
    private AccountDomainRepository accountDomainRepository;
    private WeixinSessionDataRepository weixinSessionDataRepository;
    private MemberService memberService;
    private AccountWeixinService accountWeixinService;
    private CodeTableClient codeTableClient;
    private AreaClient areaClient;
    private AvatarService avatarService;
    private AttachmentClient attachmentClient;
    private RoleManageClient roleManageClient;
    private VerificationCodeClient verificationCodeClient;
    private AuthorizationClient authorizationClient;
    private AuditLogClient auditLogClient;
    private ContactClient contactClient;
    private BusinessLicenseClient businessLicenseClient;
    private MenuClient menuClient;
    private ApplicationEventPublisher applicationEventPublisher;
    private ServiceMatcher serviceMatcher;
    private final Destination.Factory destinationFactory;
    private MemberClientService memberClientService;
    private Sequence sequence;

    public MemberCommandServiceImpl(MemberDomainRepository memberDomainRepository, AccountDomainRepository accountDomainRepository,
                                    WeixinSessionDataRepository weixinSessionDataRepository, MemberService memberService,
                                    AccountWeixinService accountWeixinService, CodeTableClient codeTableClient,
                                    AreaClient areaClient, AvatarService avatarService, AttachmentClient attachmentClient,
                                    RoleManageClient roleManageClient, VerificationCodeClient verificationCodeClient,
                                    AuthorizationClient authorizationClient, AuditLogClient auditLogClient,
                                    ContactClient contactClient, BusinessLicenseClient businessLicenseClient,
                                    MenuClient menuClient, ApplicationEventPublisher applicationEventPublisher,
                                    ServiceMatcher serviceMatcher, Destination.Factory destinationFactory,
                                    MemberClientService memberClientService, Sequence sequence) {
        this.memberDomainRepository = memberDomainRepository;
        this.accountDomainRepository = accountDomainRepository;
        this.weixinSessionDataRepository = weixinSessionDataRepository;
        this.memberService = memberService;
        this.accountWeixinService = accountWeixinService;
        this.codeTableClient = codeTableClient;
        this.areaClient = areaClient;
        this.avatarService = avatarService;
        this.attachmentClient = attachmentClient;
        this.roleManageClient = roleManageClient;
        this.verificationCodeClient = verificationCodeClient;
        this.authorizationClient = authorizationClient;
        this.auditLogClient = auditLogClient;
        this.contactClient = contactClient;
        this.businessLicenseClient = businessLicenseClient;
        this.menuClient = menuClient;
        this.applicationEventPublisher = applicationEventPublisher;
        this.serviceMatcher = serviceMatcher;
        this.destinationFactory = destinationFactory;
        this.memberClientService = memberClientService;
        this.sequence = sequence;
    }

    @Override
    @Transactional
    public MemberIdDTO createMember(CreateMemberCommand command) {
        //补充性别名称
        if (command.getGenderId() != null) {
            command.setGenderName(codeTableClient.getSystemCodeTableItemName(genderCodeTable, command.getGenderId().toString()));
        }
        //补充地址名称
        if (command.getProvinceId() != null && command.getProvinceId().intValue() != 0) {
            command.setProvinceName(areaClient.getAreaNameById(command.getProvinceId()));
        }
        if (command.getCityId() != null && command.getCityId().intValue() != 0) {
            command.setCityName(areaClient.getAreaNameById(command.getCityId()));
        }
        if (command.getAreaId() != null && command.getAreaId().intValue() != 0) {
            command.setAreaName(areaClient.getAreaNameById(command.getAreaId()));
        }
        if (command.getStreetId() != null && command.getStreetId().intValue() != 0) {
            command.setStreetName(areaClient.getAreaNameById(command.getStreetId()));
        }
        Address address = Address.builder()
                .provinceId(command.getProvinceId())
                .provinceName(command.getProvinceName())
                .cityId(command.getCityId())
                .cityName(command.getCityName())
                .areaId(command.getAreaId())
                .areaName(command.getAreaName())
                .streetId(command.getStreetId())
                .streetName(command.getStreetName())
                .address(command.getAddress())
                .build();

        AttachmentBasicDTO avatarAttachment = command.getAvatarAttachmentId() != null ? attachmentClient.getBasicAttachment(command.getAvatarAttachmentId()) : null;
        //注册会员
        Member member = memberService.createMember(
                command.getSystemId() != null ? new MemberSystemId(Long.valueOf(command.getSystemId())) : null,
                new OrganizationId(command.getOrganizationId()),
                command.getName(),
                command.getGenderId() != null ? new Gender(command.getGenderId(), command.getGenderName()) : null,
                avatarAttachment == null ? null : avatarAttachment.getUrl(),
                address,
                command.getInvitationCode(),
                new PhoneNumber(command.getPhone()),
                command.getPassword() == null ? "12345678" : command.getPassword(),
                command.getAuditStatus() != null ? AuditStatus.getById(command.getAuditStatus()) : null,
                command.getMemberType());
        member.modifyRemark(command.getRemark());
        memberDomainRepository.saveMember(member);

        //保存会员法人以及营业执照
        saveMemberLegalPersonAndBusinessLicense(member, command.getLegalPerson(), command.getBusinessLicense());

        MemberAccount primaryMemberAccount = member.getPrimaryAccount();

        //微信绑定
        if (StringUtils.isNotBlank(command.getWeixinToken())) {
            BindWeixinBySessionCommand bindWeixinBySessionCommand = new BindWeixinBySessionCommand();
            bindWeixinBySessionCommand.setAccountId(primaryMemberAccount.getAccountId().getId());
            bindWeixinBySessionCommand.setWeixinToken(command.getWeixinToken());
            accountWeixinService.bindWeixin(bindWeixinBySessionCommand);
        }

        //发布会员账号创建事件
        applicationEventPublisher.publishEvent(new MemberCreatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));
        return new MemberIdDTO(member.getMemberId().getId(), primaryMemberAccount.getAccountId().getId());
    }

    @Override
    @Transactional
    public void updateMember(UpdateMemberCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getId()));
        if (member == null) {
            throw new NotFoundException("未找到需要修改的会员信息");
        }
        member.modifyName(command.getName());

        //修改性别
        if (command.getGenderId() != null) {
            CodeTableItemBasicDTO gender = codeTableClient.getSystemCodeTableItem(genderCodeTable, command.getGenderId().toString());
            if (gender != null) {
                member.modifyGender(new Gender(command.getGenderId(), gender.getName()));
            }
        }
        //修改等级
        if (command.getLevelId() != null) {
            member.modifyLevel(new MemberLevel(command.getLevelId(), command.getLevelName()));
        }
        //修改地址
        String provinceName = command.getProvinceId() != null && command.getProvinceId().intValue() != 0 ? areaClient.getAreaNameById(command.getProvinceId()) : "";
        String cityName = command.getCityId() != null && command.getCityId().intValue() != 0 ? areaClient.getAreaNameById(command.getCityId()) : "";
        String areaName = command.getAreaId() != null && command.getAreaId().intValue() != 0 ? areaClient.getAreaNameById(command.getAreaId()) : "";
        String streeName = command.getStreetId() != null && command.getStreetId().intValue() != 0 ? areaClient.getAreaNameById(command.getStreetId()) : "";
        Address address = Address.builder()
                .provinceId(command.getProvinceId())
                .provinceName(provinceName)
                .cityId(command.getCityId())
                .cityName(cityName)
                .areaId(command.getAreaId())
                .areaName(areaName)
                .streetId(command.getStreetId())
                .streetName(streeName)
                .address(command.getAddress())
                .build();
        member.modifyAddress(address);

        //修改头像
        if (command.getAvatarAttachmentId() != null) {
            Avatar avatar = avatarService.generateAvatarByAttachment(attachmentClient.getAttachment(command.getAvatarAttachmentId()), member.getMemberId().getId().toString());
            member.modifyAvatar(avatar);
        }
        member.modifyRemark(command.getRemark());

        if (command.getOrganizationId() != null) {
            member.modifyOrganization(new OrganizationId(command.getOrganizationId()));
        }
        //保存会员
        memberDomainRepository.saveMember(member);

        //保存会员法人以及营业执照
        saveMemberLegalPersonAndBusinessLicense(member, command.getLegalPerson(), command.getBusinessLicense());

        //发布会员账号更新事件
        MemberAccount primaryMemberAccount = member.getPrimaryAccount();
        applicationEventPublisher.publishEvent(new MemberUpdatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));
    }

    @Override
    @Transactional
    public MemberIdDTO registerMember(RegisterMemberCommand command) {
        //微信授权注册
        if (StringUtils.isNotBlank(command.getWeixinToken())) {
            return weixinRegisterMember(command);
        }
        //账号注册
        else {
            return accountRegisterMember(command);
        }
    }

    @Override
    @Transactional
    public MemberIdDTO accountRegisterMember(RegisterMemberCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("REGISTER_MEMBER", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCodeNoUsed(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }

        //补充性别名称
        if (command.getGenderId() != null) {
            command.setGenderName(codeTableClient.getSystemCodeTableItemName(genderCodeTable, command.getGenderId().toString()));
        }
        //补充地址名称
        if (command.getProvinceId() != null && command.getProvinceId().intValue() != 0) {
            command.setProvinceName(areaClient.getAreaNameById(command.getProvinceId()));
        }
        if (command.getCityId() != null && command.getCityId().intValue() != 0) {
            command.setCityName(areaClient.getAreaNameById(command.getCityId()));
        }
        if (command.getAreaId() != null && command.getAreaId().intValue() != 0) {
            command.setAreaName(areaClient.getAreaNameById(command.getAreaId()));
        }
        if (command.getStreetId() != null && command.getStreetId().intValue() != 0) {
            command.setStreetName(areaClient.getAreaNameById(command.getStreetId()));
        }
        Address address = Address.builder()
                .provinceId(command.getProvinceId())
                .provinceName(command.getProvinceName())
                .cityId(command.getCityId())
                .cityName(command.getCityName())
                .areaId(command.getAreaId())
                .areaName(command.getAreaName())
                .streetId(command.getStreetId())
                .streetName(command.getStreetName())
                .address(command.getAddress())
                .build();

        //注册会员以及主账号
        Member member = memberService.createMember(
                command.getSystemId() != null ? new MemberSystemId(Long.valueOf(command.getSystemId())) : null,
                null,
                command.getName(),
                command.getGenderId() != null ? new Gender(command.getGenderId(), command.getGenderName()) : null,
                null,
                address,
                command.getInvitationCode(),
                new PhoneNumber(command.getPhone()),
                command.getPassword(),
                null,
                command.getMemberType());
        memberDomainRepository.saveMember(member);

        //保存会员法人以及营业执照
        saveMemberLegalPersonAndBusinessLicense(member, command.getLegalPerson(), command.getBusinessLicense());

        MemberAccount primaryMemberAccount = member.getPrimaryAccount();

        //微信绑定
        if (StringUtils.isNotBlank(command.getWeixinToken())) {
            BindWeixinBySessionCommand bindWeixinBySessionCommand = new BindWeixinBySessionCommand();
            bindWeixinBySessionCommand.setAccountId(primaryMemberAccount.getAccountId().getId());
            bindWeixinBySessionCommand.setWeixinToken(command.getWeixinToken());
            accountWeixinService.bindWeixin(bindWeixinBySessionCommand);
        }

        //发布会员账号创建事件
        applicationEventPublisher.publishEvent(new MemberCreatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));

        return new MemberIdDTO(member.getMemberId().getId(), primaryMemberAccount.getAccountId().getId());
    }

    @Override
    @Transactional
    public MemberIdDTO weixinRegisterMember(RegisterMemberCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("REGISTER_MEMBER", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCodeNoUsed(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }
        //验证微信会话
        WeixinSessionDO weixinSessionDO = weixinSessionDataRepository.findByWeixinToken(command.getWeixinToken());
        if (weixinSessionDO == null) {
            throw new ValidationException("非法的微信会话");
        }
        LocalDateTime now = LocalDateTime.now();
        if (weixinSessionDO.getExpireTime().isBefore(now)) {
            throw new ValidationException("微信授权已过期请重新授权");
        }
        //转移微信性别
        Map<Integer, Integer> weixinGenderMap = new HashMap<>();
        weixinGenderMap.put(0, 2);
        weixinGenderMap.put(1, 1);
        weixinGenderMap.put(2, 0);
        command.setGenderId(weixinGenderMap.get(weixinSessionDO.getGender().intValue()));
        command.setGenderName(codeTableClient.getSystemCodeTableItemName(genderCodeTable, command.getGenderId().toString()));
        //组装地址
        Address address = Address.builder()
                .provinceName(weixinSessionDO.getProvince())
                .cityName(weixinSessionDO.getCity())
                .build();

        //注册会员
        Member member = memberService.createMember(
                command.getSystemId() != null ? new MemberSystemId(Long.valueOf(command.getSystemId())) : null,
                null,
                StringUtils.isBlank(command.getName()) ? weixinSessionDO.getNickname() : command.getName(),
                command.getGenderId() != null ? new Gender(command.getGenderId(), command.getGenderName()) : null,
                weixinSessionDO.getAvatar(),
                address,
                command.getInvitationCode(),
                new PhoneNumber(command.getPhone()),
                command.getPassword(),
                null,
                command.getMemberType());
        memberDomainRepository.saveMember(member);

        //保存会员法人以及营业执照
        saveMemberLegalPersonAndBusinessLicense(member, command.getLegalPerson(), command.getBusinessLicense());

        MemberAccount primaryMemberAccount = member.getPrimaryAccount();

        //微信绑定
        if (StringUtils.isNotBlank(command.getWeixinToken())) {
            BindWeixinBySessionCommand bindWeixinBySessionCommand = new BindWeixinBySessionCommand();
            bindWeixinBySessionCommand.setAccountId(primaryMemberAccount.getAccountId().getId());
            bindWeixinBySessionCommand.setWeixinToken(command.getWeixinToken());
            accountWeixinService.bindWeixin(bindWeixinBySessionCommand);
        }

        //发布会员账号创建事件
        applicationEventPublisher.publishEvent(new MemberCreatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));

        return new MemberIdDTO(member.getMemberId().getId(), primaryMemberAccount.getAccountId().getId());
    }

    @Override
    @Transactional
    public void submitMemberInfoAudit(SubmitMemberInfoAuditCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getMemberId()));
        if (member.getAuditStatus().equals(AuditStatus.WAITING_AUDIT)) {
            throw new ValidationException("会员审核中请勿重复提交审核");
        }

        //补充性别名称
        if (command.getGenderId() != null) {
            command.setGenderName(codeTableClient.getSystemCodeTableItemName(genderCodeTable, command.getGenderId().toString()));
        }
        //补充地址名称
        if (command.getProvinceId() != null && command.getProvinceId().intValue() != 0) {
            command.setProvinceName(areaClient.getAreaNameById(command.getProvinceId()));
        }
        if (command.getCityId() != null && command.getCityId().intValue() != 0) {
            command.setCityName(areaClient.getAreaNameById(command.getCityId()));
        }
        if (command.getAreaId() != null && command.getAreaId().intValue() != 0) {
            command.setAreaName(areaClient.getAreaNameById(command.getAreaId()));
        }
        if (command.getStreetId() != null && command.getStreetId().intValue() != 0) {
            command.setStreetName(areaClient.getAreaNameById(command.getStreetId()));
        }
        Address address = Address.builder()
                .provinceId(command.getProvinceId())
                .provinceName(command.getProvinceName())
                .cityId(command.getCityId())
                .cityName(command.getCityName())
                .areaId(command.getAreaId())
                .areaName(command.getAreaName())
                .streetId(command.getStreetId())
                .streetName(command.getStreetName())
                .address(command.getAddress())
                .build();

        member.modifyName(command.getName());
        member.modifyGender(new Gender(command.getGenderId(), command.getGenderName()));
        member.modifyAddress(address);
        member.modifyInvitationCode(command.getInvitationCode());
        member.modifyAuditStatus(AuditStatus.WAITING_AUDIT);
        memberDomainRepository.saveMember(member);

        //保存会员法人以及营业执照
        saveMemberLegalPersonAndBusinessLicense(member, command.getLegalPerson(), command.getBusinessLicense());
    }

    @Override
    @Transactional
    public MemberIdDTO openMember(OpenMemberCommand command) {
        //注册会员
        Member member = memberService.createMember(
                command.getSystemId() != null ? new MemberSystemId(Long.valueOf(command.getSystemId())) : null,
                command.getOrganizationId() != null ? new OrganizationId(Long.valueOf(command.getOrganizationId())) : null,
                command.getName(),
                null,
                null,
                null,
                null,
                new PhoneNumber(command.getPhone()),
                command.getPasswordHash() == null ? Password.defaultPassword().getPassword() : command.getPasswordHash(),
                AuditStatus.getById(command.getAuditStatus()),
                command.getMemberTypeId());
        member.bindOpenAccountId(command.getOpenAccountId());
        MemberId memberId = memberDomainRepository.saveMember(member);
        MemberAccount primaryMemberAccount = member.getPrimaryAccount();

        //授权
        if (command.getRoleIds() != null && !command.getRoleIds().isEmpty()) {
            AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
            assignRoleToUserCommand.setOrganizationId(member.getOrganizationId().getId());
//            assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
            assignRoleToUserCommand.setContextId(member.getMemberSystemId().getId().toString());
            assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
            assignRoleToUserCommand.setContextId(member.getOrganizationId().getId().toString());
            assignRoleToUserCommand.setRoleIds(command.getRoleIds());
            assignRoleToUserCommand.setUserId(primaryMemberAccount.getAccountId().getId());
            assignRoleToUserCommand.setUserName(primaryMemberAccount.getName());
            roleManageClient.assignRoleToUser(assignRoleToUserCommand);
        } else {// 如果没有授权，则将默认权限组授权给会员
            // 获取默认权限组，有则授权
            RoleBasicDTO roleBasicDTO = roleManageClient.getDefaultRole(member.getMemberSystemId().getId(), member.getOrganizationId().getId());
            if (roleBasicDTO != null) {
                List<Long> roleIds = new ArrayList<>();
                roleIds.add(roleBasicDTO.getId());
                AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
                assignRoleToUserCommand.setOrganizationId(member.getOrganizationId().getId());
//            assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
                assignRoleToUserCommand.setContextId(member.getMemberSystemId().getId().toString());
                assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
                assignRoleToUserCommand.setSystemId(Long.parseLong(roleBasicDTO.getContextId()));
                assignRoleToUserCommand.setContextId(member.getOrganizationId().getId().toString());
                assignRoleToUserCommand.setRoleIds(roleIds);
                assignRoleToUserCommand.setUserId(primaryMemberAccount.getAccountId().getId());
                assignRoleToUserCommand.setUserName(primaryMemberAccount.getName());
                roleManageClient.assignRoleToUser(assignRoleToUserCommand);
            }
        }

        memberClientService.createDefaultClient(memberId.getId(), command.getMemberTypeId());

        //发布会员账号创建事件
        applicationEventPublisher.publishEvent(new MemberCreatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));

        return new MemberIdDTO(memberId.getId(), primaryMemberAccount.getAccountId().getId());
    }

    @Override
    @Transactional
    public void updateOpenMember(UpdateOpenMemberCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getMemberId()));
        if (member == null) {
            throw new NotFoundException("未找到需要修改的会员信息");
        }

        //修改会员信息
        member.modifyName(command.getName());
        if (!member.getPhone().getNumber().equals(command.getPhone())) {
            member.changePhone(command.getPhone());
        }
        if (command.getAuditStatus() != null) {
            member.modifyAuditStatus(AuditStatus.getById(command.getAuditStatus()));
        }
        memberDomainRepository.saveMember(member);

        //授权
        if (command.getRoleIds() != null) {
            AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
            assignRoleToUserCommand.setOrganizationId(member.getOrganizationId().getId());
//            assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
            assignRoleToUserCommand.setContextId(member.getMemberSystemId().getId().toString());
            assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
            assignRoleToUserCommand.setContextId(member.getOrganizationId().getId().toString());
            assignRoleToUserCommand.setRoleIds(command.getRoleIds());
            assignRoleToUserCommand.setUserId(member.getPrimaryAccount().getAccountId().getId());
            assignRoleToUserCommand.setUserName(member.getName().getName());
            roleManageClient.assignRoleToUser(assignRoleToUserCommand);
        } else {// 如果没有授权，则将默认权限组授权给会员
            // 获取默认权限组，有则授权
            RoleBasicDTO roleBasicDTO = roleManageClient.getDefaultRole(member.getMemberSystemId().getId(), member.getOrganizationId().getId());
            if (roleBasicDTO != null) {
                List<Long> roleIds = new ArrayList<>();
                roleIds.add(roleBasicDTO.getId());
                AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
                assignRoleToUserCommand.setOrganizationId(member.getOrganizationId().getId());
//            assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
                assignRoleToUserCommand.setContextId(member.getMemberSystemId().getId().toString());
                assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
                assignRoleToUserCommand.setContextId(member.getOrganizationId().getId().toString());
                assignRoleToUserCommand.setRoleIds(roleIds);
                assignRoleToUserCommand.setUserId(member.getPrimaryAccount().getAccountId().getId());
                assignRoleToUserCommand.setUserName(member.getName().getName());
                roleManageClient.assignRoleToUser(assignRoleToUserCommand);
            }
        }

        //发布会员账号更新事件
        MemberAccount primaryMemberAccount = member.getPrimaryAccount();
        applicationEventPublisher.publishEvent(new MemberUpdatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));
    }

    @Override
    @Transactional
    public void bindMemberOpenAccount(BindMemberOpenAccountCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getMemberId()));
        if (member == null) {
            throw new NotFoundException("未找到绑定开户组织的会员");
        }
        member.bindOpenAccountId(command.getOpenAccountId());
        memberDomainRepository.saveMember(member);
    }

    @Override
    @Transactional
    public void modifyMemberInfo(ModifyMemberInfoCommand command, MemberIdentity identity) {
        Member member = memberDomainRepository.findById(new MemberId(identity.getId()));
        member.modifyName(command.getName());

        //修改性别
        if (command.getGenderId() != null) {
            CodeTableItemBasicDTO gender = codeTableClient.getSystemCodeTableItem(genderCodeTable, command.getGenderId().toString());
            if (gender != null) {
                member.modifyGender(new Gender(command.getGenderId(), gender.getName()));
            }
        }
        //修改地址
        String provinceName = command.getProvinceId() != null && command.getProvinceId().intValue() != 0 ? areaClient.getAreaNameById(command.getProvinceId()) : "";
        String cityName = command.getCityId() != null && command.getCityId().intValue() != 0 ? areaClient.getAreaNameById(command.getCityId()) : "";
        String areaName = command.getAreaId() != null && command.getAreaId().intValue() != 0 ? areaClient.getAreaNameById(command.getAreaId()) : "";
        String streeName = command.getStreetId() != null && command.getStreetId().intValue() != 0 ? areaClient.getAreaNameById(command.getStreetId()) : "";
        Address address = Address.builder()
                .provinceId(command.getProvinceId())
                .provinceName(provinceName)
                .cityId(command.getCityId())
                .cityName(cityName)
                .areaId(command.getAreaId())
                .areaName(areaName)
                .streetId(command.getStreetId())
                .streetName(streeName)
                .address(command.getAddress())
                .build();
        member.modifyAddress(address);

        //修改头像
        if (command.getAvatarAttachmentId() != null) {
            AttachmentDTO attachmentDTO = attachmentClient.getAttachment(command.getAvatarAttachmentId());
            if (attachmentDTO == null) {
                attachmentDTO = new AttachmentDTO();
            }
            Avatar avatar = new Avatar(attachmentDTO.getUrl());
            member.modifyAvatar(avatar);
        }
        //保存会员
        memberDomainRepository.saveMember(member);

        //发布会员账号更新事件
        MemberAccount primaryMemberAccount = member.getPrimaryAccount();
        applicationEventPublisher.publishEvent(new MemberUpdatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));
    }

    @Override
    @Transactional
    public void modifyMemberStatus(ModifyMemberStatusCommand command, EmployeeIdentity identity) {
        Member member = memberDomainRepository.findById(new MemberId(command.getId()));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        EnableDisableStatus status = EnableDisableStatus.getById(command.getStatusId());
        if (status == null) {
            throw new ValidationException("非法的会员状态");
        }
        member.modifyStatus(status);
        memberDomainRepository.saveMember(member);

        //发布会员状态已更新事件
        applicationEventPublisher.publishEvent(new MemberStatusUpdatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                member.getStatus().getId()
        ));
    }

    @Override
    public void modifyMemberLevel(ModifyMemberLevelCommand command, EmployeeIdentity identity) {
        Member member = memberDomainRepository.findById(new MemberId(command.getId()));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        member.modifyLevel(new MemberLevel(command.getLevelId(), command.getLevelName()));
        memberDomainRepository.saveMember(member);
    }

    @Override
    public void assignRoleToMember(AssignRoleToMemberCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getMemberId()));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        MemberAccount primaryMemberAccount = member.getPrimaryAccount();

        AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
        assignRoleToUserCommand.setOrganizationId(member.getOrganizationId().getId());
//        assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
//        assignRoleToUserCommand.setContextId(member.getMemberSystemId().getId().toString());
        assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
        assignRoleToUserCommand.setContextId(member.getOrganizationId().getId().toString());
        assignRoleToUserCommand.setRoleIds(command.getRoleIds());
        assignRoleToUserCommand.setUserId(primaryMemberAccount.getAccountId().getId());
        assignRoleToUserCommand.setUserName(primaryMemberAccount.getName());
        assignRoleToUserCommand.setSystemId(member.getMemberSystemId().getId());
        roleManageClient.assignRoleToUser(assignRoleToUserCommand);
    }

    @Override
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberDomainRepository.findById(new MemberId(memberId));
        if (member == null) {
            return;
        }
        memberDomainRepository.removeMember(member.getMemberId());
        authorizationClient.removeToken(member.getMemberId().getId());
    }

    @Override
    public void resetMemberPassword(Long memberId) {
        Member member = memberDomainRepository.findById(new MemberId(memberId));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        memberDomainRepository.resetMemberPassword(member.getMemberId());
        authorizationClient.removeToken(member.getMemberId().getId());
    }

    @Override
    public void unbindMemberWeixin(Long memberId) {
        Member member = memberDomainRepository.findById(new MemberId(memberId));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        memberDomainRepository.unbindMemberWeixin(member.getMemberId());
    }

    @Override
    @Transactional
    public void storeAuditMember(AuditMemberCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getId()));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
//        if (!member.getAuditStatus().equals(AuditStatus.WAITING_AUDIT)) {
//            throw new ValidationException("指定的会员不是待审核状态无法审核");
//        }
        AuditType auditType = AuditType.getById(command.getStatusId());
        if (auditType == null) {
            throw new ValidationException("非法的审核操作");
        }
        //如果是一级审核，审核通过则设置为最终的审核通过状态
        if (command.getAuditLevel() != null && command.getAuditLevel().intValue() == 1) {
            member.modifyAuditStatus(auditType.equals(AuditType.PASS) ? AuditStatus.PLATFORM_PASSED : AuditStatus.PLATFORM_REFUSED);
        } else {
            member.modifyAuditStatus(auditType.equals(AuditType.PASS) ? AuditStatus.STORE_PASSED : AuditStatus.STORE_REFUSED);
        }
        memberDomainRepository.saveMember(member);

        //记录审核日志
        CreateAuditLogCommand auditLogCommand = new CreateAuditLogCommand();
        auditLogCommand.setOrganizationId(member.getOrganizationId().getId());
        auditLogCommand.setModuleId(Module.MEMBER.getId());
        auditLogCommand.setDataId(member.getMemberId().getId());
        auditLogCommand.setTypeId(auditType.getId());
        auditLogCommand.setTypeName(String.format("%s%s", menuClient.getMenuNameByAuthItem("member/member/store-audit"), auditType.getName()));
        auditLogCommand.setReason(command.getReason());
        auditLogCommand.setRemark(command.getRemark());
        auditLogCommand.setAuditorId(command.getAuditorId());
        auditLogCommand.setAuditorName(command.getAuditorName());
        auditLogClient.createAuditLog(auditLogCommand);

        //发布审核事件
        applicationEventPublisher.publishEvent(new MemberStoreAuditedEvent(
                this,
                member.getMemberId().getId(),
                command.getAuditLevel(),
                command.getStatusId(),
                command.getReason(),
                command.getRemark(),
                command.getAuditorId(),
                command.getAuditorName()
        ));
    }

    @Override
    @Transactional
    public void platformAuditMember(AuditMemberCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getId()));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        if (!member.getAuditStatus().equals(AuditStatus.STORE_PASSED)) {
            throw new ValidationException("指定的会员不是平台待审核状态无法审核");
        }
        AuditType auditType = AuditType.getById(command.getStatusId());
        if (auditType == null) {
            throw new ValidationException("非法的审核操作");
        }
        member.modifyAuditStatus(auditType.equals(AuditType.PASS) ? AuditStatus.PLATFORM_PASSED : AuditStatus.PLATFORM_REFUSED);
        memberDomainRepository.saveMember(member);

        //记录审核日志
        CreateAuditLogCommand auditLogCommand = new CreateAuditLogCommand();
        auditLogCommand.setOrganizationId(member.getOrganizationId().getId());
        auditLogCommand.setModuleId(Module.MEMBER.getId());
        auditLogCommand.setDataId(member.getMemberId().getId());
        auditLogCommand.setTypeId(auditType.getId());
        auditLogCommand.setTypeName(String.format("%s%s", menuClient.getMenuNameByAuthItem("member/member/platform-audit"), auditType.getName()));
        auditLogCommand.setReason(command.getReason());
        auditLogCommand.setRemark(command.getRemark());
        auditLogCommand.setAuditorId(command.getAuditorId());
        auditLogCommand.setAuditorName(command.getAuditorName());
        auditLogClient.createAuditLog(auditLogCommand);

        //发布审核事件
        applicationEventPublisher.publishEvent(new MemberPlatformAuditedEvent(
                this,
                member.getMemberId().getId(),
                command.getStatusId(),
                command.getReason(),
                command.getRemark(),
                command.getAuditorId(),
                command.getAuditorName()
        ));
    }

    @Override
    public void memberOpenAccount(OpenMemberCommand command) {
        Member member = memberDomainRepository.findById(new MemberId(command.getMemberId()));
        if (member == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        if (command.getAuditStatus() == null) {
            member.modifyAuditStatus(AuditStatus.WAITING_AUDIT);
        } else {
            member.modifyAuditStatus(AuditStatus.getById(command.getAuditStatus()));
        }
//        memberDomainRepository.saveMember(member);

        //如果未注册会员账号则创建账号
        MemberAccount primaryMemberAccount = accountDomainRepository.findByPhone(new PhoneNumber(command.getPhone()));
        if (primaryMemberAccount == null) {
            Password hashPassword = command.getPasswordHash() != null ? new Password(command.getPasswordHash()) : new Password("12345678");
            primaryMemberAccount = MemberAccount.builder()
                    .accountId(new AccountId(sequence.nextId()))
                    .name(member.getName().getName())
                    .type(AccountType.PRIMARY)
                    .avatar(member.getAvatar())
                    .phone(member.getPhone())
                    .password(hashPassword)
                    .status(EnableDisableStatus.ENABLE)
                    .createTime(LocalDateTime.now())
                    .build();
        } else {
            primaryMemberAccount.changePhone(member.getPhone().getNumber());
        }
        member.bindOpenAccountId(primaryMemberAccount.getAccountId().getId());
        Member memberNew = Member.builder()
                .memberId(member.getMemberId())
                .organizationId(member.getOrganizationId())
                .memberSystemId(member.getMemberSystemId())
                .invitationCode(member.getInvitationCode())
                .name(member.getName())
                .phone(member.getPhone())
                .avatar(member.getAvatar())
                .gender(member.getGender())
                .address(member.getAddress())
                .isOpenAccount(true)
                .status(member.getStatus())
                .auditStatus(member.getAuditStatus())
                .createTime(member.getCreateTime())
                .memberType(member.getMemberType())
                .primaryAccount(primaryMemberAccount)
                .build();
        MemberId memberId = memberDomainRepository.saveMember(memberNew);
        //授权
        if (command.getRoleIds() != null && !command.getRoleIds().isEmpty()) {
            AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
            assignRoleToUserCommand.setOrganizationId(member.getOrganizationId().getId());
//            assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
//            assignRoleToUserCommand.setContextId(member.getMemberSystemId().getId().toString());
            assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
            assignRoleToUserCommand.setContextId(member.getOrganizationId().getId().toString());
            assignRoleToUserCommand.setRoleIds(command.getRoleIds());
            assignRoleToUserCommand.setUserId(primaryMemberAccount.getAccountId().getId());
            assignRoleToUserCommand.setUserName(primaryMemberAccount.getName());
            assignRoleToUserCommand.setSystemId(member.getMemberSystemId().getId());
            roleManageClient.assignRoleToUser(assignRoleToUserCommand);
        } else {// 如果没有授权，则将默认权限组授权给会员
            // 获取默认权限组，有则授权
            RoleBasicDTO roleBasicDTO = roleManageClient.getDefaultRole(member.getMemberSystemId().getId(), member.getOrganizationId().getId());
            if (roleBasicDTO != null) {
                List<Long> roleIds = new ArrayList<>();
                roleIds.add(roleBasicDTO.getId());
                AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand();
                assignRoleToUserCommand.setOrganizationId(member.getOrganizationId().getId());
//            assignRoleToUserCommand.setContext(MemberSystemSessionContext.contextId);
//            assignRoleToUserCommand.setContextId(member.getMemberSystemId().getId().toString());
                assignRoleToUserCommand.setContext(OrganizationSessionContext.contextId);
                assignRoleToUserCommand.setContextId(member.getOrganizationId().getId().toString());
                assignRoleToUserCommand.setRoleIds(roleIds);
                assignRoleToUserCommand.setUserId(primaryMemberAccount.getAccountId().getId());
                assignRoleToUserCommand.setUserName(primaryMemberAccount.getName());
                assignRoleToUserCommand.setSystemId(member.getMemberSystemId().getId());
                roleManageClient.assignRoleToUser(assignRoleToUserCommand);
            }
        }

        memberClientService.createDefaultClient(memberId.getId(), command.getMemberTypeId());

        //发布会员账号创建事件
        applicationEventPublisher.publishEvent(new MemberCreatedEvent(this, serviceMatcher.getBusId(), destinationFactory.getDestination("**"),
                member.getMemberId().getId(),
                primaryMemberAccount.getAccountId().getId(),
                member.getName().getName(),
                member.getPhone().getNumber(),
                member.getAvatar().getUrl()
        ));
    }

    @Override
    public MemberIdDTO openMemberWithoutAccount(OpenMemberCommand command) {
        Member member = memberService.createMemberWithoutAccount(
                command.getSystemId() != null ? new MemberSystemId(Long.valueOf(command.getSystemId())) : null,
                command.getOrganizationId() != null ? new OrganizationId(Long.valueOf(command.getOrganizationId())) : null,
                command.getName(),
                null,
                null,
                null,
                null,
                new PhoneNumber(command.getPhone()),
                command.getMemberTypeId());
        memberDomainRepository.saveMemberWithoutAccount(member);
        return new MemberIdDTO(member.getMemberId().getId(), null);
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

    /**
     * 保存会员的法人以及营业执照
     *
     * @param member
     * @param legalPerson
     * @param businessLicense
     */
    private void saveMemberLegalPersonAndBusinessLicense(Member member, ContactForm legalPerson, BusinessLicenseForm businessLicense) {
//        //保存法人
//        RelatedModuleDataDTO relatedModuleData = new RelatedModuleDataDTO(Module.MEMBER.getId(), member.getMemberId().getId());
//        if (legalPerson != null) {
//            BatchSaveContactCommand batchSaveContactCommand = new BatchSaveContactCommand();
//            batchSaveContactCommand.setIdentity(null);
//            batchSaveContactCommand.setOrganizationId(member.getOrganizationId().getId());
//            batchSaveContactCommand.setRelatedModuleData(relatedModuleData);
//            batchSaveContactCommand.setContacts(Arrays.asList(legalPerson));
//            contactClient.batchSaveContact(batchSaveContactCommand);
//        }
//
//        //保存营业执照
//        if (businessLicense != null) {
//            List<ContactDTO> contacts = contactClient.getContactsByRelatedModule(relatedModuleData.getRelatedModuleId(), relatedModuleData.getRelatedDataId());
//            ContactDTO legalContact = contacts.stream().filter(c -> c.getIsLegalPerson().intValue() == 1).findFirst().orElse(null);
//            SaveBusinessLicenseCommand saveBusinessLicenseCommand = new SaveBusinessLicenseCommand();
//            saveBusinessLicenseCommand.setIdentity(null);
//            saveBusinessLicenseCommand.setOrganizationId(member.getOrganizationId().getId());
//            saveBusinessLicenseCommand.setRelatedModuleData(relatedModuleData);
//            saveBusinessLicenseCommand.setBusinessLicense(businessLicense);
//            if (legalContact != null) {
//                saveBusinessLicenseCommand.setLegalPersonId(legalContact.getId());
//                saveBusinessLicenseCommand.setLegalPersonName(legalContact.getName());
//            }
//            businessLicenseClient.saveBusinessLicense(saveBusinessLicenseCommand);
//        }
//
//        //记录提交审核日志
//        if (member.getAuditStatus().equals(AuditStatus.WAITING_AUDIT)) {
//            CreateAuditLogCommand auditLogCommand = new CreateAuditLogCommand();
//            auditLogCommand.setOrganizationId(member.getOrganizationId().getId());
//            auditLogCommand.setModuleId(Module.MEMBER.getId());
//            auditLogCommand.setDataId(member.getMemberId().getId());
//            auditLogCommand.setTypeId(AuditType.SUBMIT.getId());
//            auditLogCommand.setTypeName("提交审核");
//            auditLogClient.createAuditLog(auditLogCommand);
//        }
    }
}
