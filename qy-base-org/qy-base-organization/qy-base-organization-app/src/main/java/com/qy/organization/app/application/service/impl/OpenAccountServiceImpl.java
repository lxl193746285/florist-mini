package com.qy.organization.app.application.service.impl;

import com.qy.identity.api.client.UserClient;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.member.api.client.AccountClient;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.client.MemberSystemClient;
import com.qy.member.api.command.CreateMemberCommand;
import com.qy.member.api.command.MemberSystemAuthorizationCommand;
import com.qy.member.api.dto.AccountBasicDTO;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.member.api.dto.MemberIdDTO;
import com.qy.member.api.query.MemberQuery;
import com.qy.organization.api.client.DepartmentClient;
import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.command.CreateTopDepartmentCommand;
import com.qy.organization.api.command.OrganizationCreatorCommand;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.organization.app.application.assembler.OrganizationAssembler;
import com.qy.organization.app.application.command.OpenAccountCommand;
import com.qy.organization.app.application.command.UpdateOpenAccountCommand;
import com.qy.organization.app.application.command.SystemRoleCommand;
import com.qy.organization.app.application.dto.OpenAccountInfoDTO;
import com.qy.organization.app.application.dto.RoleBasicDTO;
import com.qy.organization.app.application.service.OpenAccountService;
import com.qy.organization.app.application.service.OrganizationCommandService;
import com.qy.organization.app.application.service.RoleQueryService;
import com.qy.organization.app.config.OrganizationConfig;
import com.qy.organization.app.domain.entity.Organization;
import com.qy.organization.app.domain.enums.DefaultRole;
import com.qy.organization.app.domain.enums.OpenAccountStatus;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.infrastructure.persistence.OrganizationDataRepository;
import com.qy.organization.app.infrastructure.persistence.OrganizationDomainRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.rest.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 开户服务实现
 *
 * @author legendjw
 */
@Service
public class OpenAccountServiceImpl implements OpenAccountService {
    @Value("${qy.organization.ark-system-id}")
    private String arkSystemId;

    private OrganizationAssembler organizationAssembler;
    private OrganizationDataRepository organizationDataRepository;
    private OrganizationDomainRepository organizationDomainRepository;
    private OrganizationCommandService organizationCommandService;
    private UserClient userClient;
    private EmployeeClient employeeClient;
    private DepartmentClient departmentClient;
    private RoleQueryService roleQueryService;
    private MemberClient memberClient;
    private MemberSystemClient memberSystemClient;
    private AccountClient accountClient;
    private OrganizationConfig organizationConfig;
    private ApplicationEventPublisher applicationEventPublisher;
    private ServiceMatcher serviceMatcher;
    private final Destination.Factory destinationFactory;


    public OpenAccountServiceImpl(OrganizationAssembler organizationAssembler, OrganizationDataRepository organizationDataRepository,
                                  OrganizationCommandService organizationCommandService, UserClient userClient, EmployeeClient employeeClient, DepartmentClient departmentClient,
                                  RoleQueryService roleQueryService, MemberClient memberClient, MemberSystemClient memberSystemClient, ApplicationEventPublisher applicationEventPublisher,
                                  ServiceMatcher serviceMatcher, Destination.Factory destinationFactory,
                                  AccountClient accountClient, OrganizationConfig organizationConfig, OrganizationDomainRepository organizationDomainRepository) {
        this.organizationAssembler = organizationAssembler;
        this.organizationDataRepository = organizationDataRepository;
        this.organizationCommandService = organizationCommandService;
        this.userClient = userClient;
        this.employeeClient = employeeClient;
        this.departmentClient = departmentClient;
        this.roleQueryService = roleQueryService;
        this.memberClient = memberClient;
        this.memberSystemClient = memberSystemClient;
        this.accountClient = accountClient;
        this.organizationConfig = organizationConfig;
        this.organizationDomainRepository = organizationDomainRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.serviceMatcher = serviceMatcher;
        this.destinationFactory = destinationFactory;
    }

    @Override
    public OpenAccountInfoDTO getOpenAccountInfo(String source, Long sourceDataId) {
        OrganizationDO organizationDO = organizationDataRepository.findBySource(source, sourceDataId);
        if (organizationDO == null) {
            return null;
        }
        OpenAccountInfoDTO openAccountInfoDTO = new OpenAccountInfoDTO();
        openAccountInfoDTO.setOrganization(organizationAssembler.toDTO(organizationDO, null));

        EmployeeBasicDTO employeeBasicDTO = employeeClient.getOrganizationCreator(organizationDO.getId());
        if (employeeBasicDTO == null) {
            return null;
        }

        UserBasicDTO userBasicDTO = userClient.getBasicUserById(employeeBasicDTO.getUserId());
        openAccountInfoDTO.setSuperAdmin(userBasicDTO);

        List<RoleBasicDTO> roleDTOs = roleQueryService.getRolesByOrganizationAndUser(employeeBasicDTO.getOrganizationId(), employeeBasicDTO.getUserId(), Long.valueOf(arkSystemId));
        openAccountInfoDTO.setRoles(roleDTOs);

        return openAccountInfoDTO;
    }

    @Override
    public OpenAccountInfoDTO getOpenAccountInfo(Long organizationId) {
        OrganizationDO organizationDO = organizationDataRepository.findById(organizationId);
        if (organizationDO == null) {
            return null;
        }
        OpenAccountInfoDTO openAccountInfoDTO = new OpenAccountInfoDTO();
        openAccountInfoDTO.setOrganization(organizationAssembler.toDTO(organizationDO, null));

        EmployeeBasicDTO employeeBasicDTO = employeeClient.getOrganizationCreator(organizationDO.getId());
        if (employeeBasicDTO == null) {
            return null;
        }

//        UserBasicDTO userBasicDTO = userClient.getBasicUserById(employeeBasicDTO.getUserId());
        AccountBasicDTO basicDTO = accountClient.getBasicAccount(employeeBasicDTO.getUserId());
        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setId(basicDTO.getId());
        openAccountInfoDTO.setSuperAdmin(userBasicDTO);

        List<RoleBasicDTO> roleDTOs = roleQueryService.getRolesByOrganizationAndUser(employeeBasicDTO.getOrganizationId(), employeeBasicDTO.getUserId(), Long.valueOf(arkSystemId));
        openAccountInfoDTO.setRoles(roleDTOs);

        return openAccountInfoDTO;
    }

    @Override
    public OrganizationId openAccount(OpenAccountCommand command) {
        if (command.getAccountNickname() == null || command.getAccountPhone() == null) {
            throw new ValidationException("超管名称和手机号信息不完整，无法开户");
        }
        OrganizationDO organizationDO = organizationDataRepository.findById(command.getOpenOrganizationId());
        if (organizationDO == null) {
            throw new ValidationException("未找到对应的组织信息，无法开户");
        }
        if (organizationDO.getOpenStatusId().equals(OpenAccountStatus.OPENED)) {
            throw new ValidationException("当前组织已开户，请勿重复开户");
        }

        // 1.分别提取会员系统和权限的数组
        List<Long> roleIds = new ArrayList<>();
        List<Long> systemIds = new ArrayList<>();
        if (command.getSystemRoles() == null || command.getSystemRoles().size() == 0) {
            // 未授权，则取方舟系统默认权限组
            RoleBasicDTO roleBasicDTO = roleQueryService.getDefaultRole(DefaultRole.MEMBER_SYSTEM, Long.parseLong(arkSystemId), command.getOrganizationId());
            if (roleBasicDTO != null) {
                roleIds.add(roleBasicDTO.getId());
                systemIds.add(Long.parseLong(arkSystemId));
            }
        }
        else {
            // 存在授权
            // 将方舟系统的权限组取出来（tip：方舟系统id固定为1）
            List<SystemRoleCommand> arkSystemRoleDTOs = command.getSystemRoles().stream().filter(memberSystemRoleDTO -> memberSystemRoleDTO.getSystemId().equals(Long.parseLong(arkSystemId))).collect(Collectors.toList());
            if (arkSystemRoleDTOs.size() == 0) {
                // 如果没有会员系统-方舟系统，则创建方舟系统，权限组取默认权限组
                RoleBasicDTO roleBasicDTO = roleQueryService.getDefaultRole(DefaultRole.MEMBER_SYSTEM, Long.parseLong(arkSystemId), command.getOrganizationId());
                if (roleBasicDTO != null) {
                    roleIds.add(roleBasicDTO.getId());
                    systemIds.add(Long.parseLong(arkSystemId));
                }
            }
            // 将权限组过滤重复
            List<Long> distinctRoleIds = command.getSystemRoles().stream().map(SystemRoleCommand::getRoleIds).flatMap(List::stream).distinct().collect(Collectors.toList());
            // 合并权限组（注释：权限组和会员系统是分开的，授权是将所有会员系统下的权限组一起授予）
            roleIds.addAll(distinctRoleIds);
            // 将会员系统过滤重复
            List<Long> distinctSystemIds = command.getSystemRoles().stream().map(SystemRoleCommand::getSystemId).distinct().collect(Collectors.toList());
            // 合并会员系统
            systemIds.addAll(distinctSystemIds);
        }

        // 2.创建组织和会员系统关系表
        MemberSystemAuthorizationCommand memberSystemAuthorizationCommand = new MemberSystemAuthorizationCommand();
        memberSystemAuthorizationCommand.setOrganizationId(command.getOpenOrganizationId());
        memberSystemAuthorizationCommand.setSystemIds(systemIds);
        memberSystemAuthorizationCommand.setSource(2);
        memberSystemClient.createMemberSystemOrganization(memberSystemAuthorizationCommand);

        // 3.创建组织初始部门
        CreateTopDepartmentCommand createTopDepartmentCommand = new CreateTopDepartmentCommand();
        createTopDepartmentCommand.setOrganizationId(command.getOpenOrganizationId());
        Long departmentId = departmentClient.createOrganizationTopDepartment(createTopDepartmentCommand);

        // 4.创建组织超管的账号
        // 4.1创建组织超管的会员账号
        CreateMemberCommand memberCommand = new CreateMemberCommand();
        memberCommand.setOrganizationId(command.getOpenOrganizationId());
        memberCommand.setName(command.getAccountNickname());
        memberCommand.setPhone(command.getAccountPhone());
        memberCommand.setSystemId(arkSystemId);
        memberCommand.setMemberType(1);
        MemberIdDTO memberIdDTO = memberClient.createMember(memberCommand);
        // 4.2创建组织超管的员工账号
        // 3.创建组织初始部门
        employeeClient.createOrganizationCreatorEmployee(command.getOpenOrganizationId(), departmentId, memberIdDTO.getAccountId(), roleIds);

        // 5.更新组织表开户状态
        Organization organization = Organization.builder()
                .id(new OrganizationId(command.getOpenOrganizationId()))
                .openStatus(OpenAccountStatus.OPENED)
                .build();
        organizationDomainRepository.save(organization);

        return new OrganizationId(command.getOpenOrganizationId());
    }

    @Override
    public void updateOpenAccount(UpdateOpenAccountCommand command) {
        //验证码验证
        //ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("OPEN_ACCOUNT", "SMS", command.getAccountPhone(), command.getVerificationCode());
        //ValidateCodeResultDTO codeResultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        //if (!codeResultDTO.isValid()) {
        //    throw new ValidationException(codeResultDTO.getErrorMessage());
        //}
        OpenAccountInfoDTO openAccountInfo = command.getOpenAccountId() != null ?
                getOpenAccountInfo(command.getOpenAccountId()) :
                getOpenAccountInfo(command.getSource(), command.getSourceDataId());
        if (openAccountInfo == null) {
            throw new ValidationException("未找到对应的开户信息，无法修改开户信息");
        }

        // 检测如果超管手机号是否已注册为方舟系统下的会员
        MemberQuery query = new MemberQuery();
        query.setOrganizationId(openAccountInfo.getOrganization().getId());
        query.setSystemId(Long.valueOf(arkSystemId));
        query.setPhone(command.getAccountPhone());
        MemberBasicDTO user = memberClient.getBasicSystemMember(query);
        if (user == null) {//未注册则先注册为会员
            user = new MemberBasicDTO();
            CreateMemberCommand createMemberCommand = new CreateMemberCommand();
            createMemberCommand.setSystemId(arkSystemId);
            createMemberCommand.setPhone(command.getAccountPhone());
            createMemberCommand.setName(command.getAccountNickname());
            createMemberCommand.setOrganizationId(openAccountInfo.getOrganization().getId());
            createMemberCommand.setMemberType(1);
            MemberIdDTO memberIdDTO = memberClient.createMember(createMemberCommand);
            user.setAccountId(memberIdDTO.getAccountId());
        }

//        //更新组织
//        UpdateOrganizationCommand updateOrganizationCommand = new UpdateOrganizationCommand();
//        BeanUtils.copyProperties(command, updateOrganizationCommand, "accountNickname", "accountPhone", "verificationCode");
//        updateOrganizationCommand.setId(openAccountInfo.getOrganization().getId());
//        updateOrganizationCommand.setUpdatorId(command.getUpdatorId());
//        updateOrganizationCommand.setUpdatorName(command.getUpdatorName());
//        organizationCommandService.updateOrganization(updateOrganizationCommand);

        List<SystemRoleCommand> arkSystemRoleDTOs = command.getSystemRoles().stream().filter(memberSystemRoleDTO
                -> memberSystemRoleDTO.getSystemId().equals(Long.parseLong(arkSystemId))).collect(Collectors.toList());
        List<Long> roleIds =  new ArrayList<>();
        List<Long> systemIds =  new ArrayList<>();
        if (arkSystemRoleDTOs.size() == 0) {
            // 如果没有会员系统-方舟系统，则创建方舟系统，权限组取默认权限组
            RoleBasicDTO roleBasicDTO = roleQueryService.getDefaultRole(DefaultRole.MEMBER_SYSTEM, Long.parseLong(arkSystemId), openAccountInfo.getOrganization().getParentId());
            if (roleBasicDTO != null) {
                roleIds.add(roleBasicDTO.getId());
            }
        }
        else {
            SystemRoleCommand arkSystemRoleDTO = arkSystemRoleDTOs.get(0);
            if (arkSystemRoleDTO.getRoleIds() == null || arkSystemRoleDTO.getRoleIds().size() == 0) {
                RoleBasicDTO roleBasicDTO = roleQueryService.getDefaultRole(DefaultRole.MEMBER_SYSTEM, Long.parseLong(arkSystemId),
                        openAccountInfo.getOrganization().getParentId());
                if (roleBasicDTO != null) {
                    roleIds.add(roleBasicDTO.getId());
                }
            }
            // 将权限组过滤重复
            List<Long> distinctRoleIds = command.getSystemRoles().stream().map(SystemRoleCommand::getRoleIds).flatMap(List::stream)
                    .distinct().collect(Collectors.toList());
            // 合并权限组（注释：权限组和会员系统是分开的，授权是将所有会员系统下的权限组一起授予）
            roleIds.addAll(distinctRoleIds);
            // 将会员系统过滤重复
            List<Long> distinctSystemIds = command.getSystemRoles().stream().map(SystemRoleCommand::getSystemId).distinct().collect(Collectors.toList());
            // 合并会员系统
            systemIds.addAll(distinctSystemIds);
        }

        OrganizationCreatorCommand creatorCommand = new OrganizationCreatorCommand();
        //只更改权限组+会员系统
        if (user.getAccountId().equals(openAccountInfo.getSuperAdmin().getId())) {
            // 更新权限组
            creatorCommand.setOrganizationId(openAccountInfo.getOrganization().getId());
            creatorCommand.setRoleIds(roleIds);
            creatorCommand.setUserId(user.getAccountId());
            employeeClient.updateOrganizationCreatorRole(creatorCommand);
        }
        //更换超管且更换权限组+会员系统
        else {
            creatorCommand.setOrganizationId(openAccountInfo.getOrganization().getId());
            creatorCommand.setUserId(user.getAccountId());
            creatorCommand.setRoleIds(roleIds);
            employeeClient.changeOrganizationCreator(creatorCommand);
        }
        // 更新会员系统与组织关系
        memberSystemClient.deleteMemberSystemOrganization(openAccountInfo.getOrganization().getId());
        MemberSystemAuthorizationCommand memberSystemAuthorizationCommand = new MemberSystemAuthorizationCommand();
        memberSystemAuthorizationCommand.setOrganizationId(openAccountInfo.getOrganization().getId());
        memberSystemAuthorizationCommand.setSystemIds(systemIds);
        memberSystemAuthorizationCommand.setSource(2);
        memberSystemClient.createMemberSystemOrganization(memberSystemAuthorizationCommand);
    }
}
