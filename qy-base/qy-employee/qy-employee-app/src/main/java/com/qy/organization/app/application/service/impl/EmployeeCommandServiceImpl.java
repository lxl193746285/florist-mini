package com.qy.organization.app.application.service.impl;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.command.RelateAttachmentsCommand;
import com.qy.authorization.api.client.AuthorizationClient;
import com.qy.authorization.api.command.GenerateAccessTokenCommand;
import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.identity.api.client.UserClient;
import com.qy.identity.api.command.ModifyAccountCommand;
import com.qy.member.api.client.AccountClient;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.command.ModifyPasswordCommand;
import com.qy.member.api.command.UpdateMemberCommand;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.app.application.command.*;
import com.qy.organization.app.application.service.*;
import com.qy.organization.app.config.OrganizationConfig;
import com.qy.organization.app.config.OrganizationMode;
import com.qy.organization.app.domain.entity.*;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.enums.EmployeePermissionType;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.domain.service.EmployeeService;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.infrastructure.persistence.*;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.EmployeeMapper;
import com.qy.region.api.AreaClient;
import com.qy.rest.exception.BusinessException;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.sequence.Sequence;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.Identity;
import com.qy.security.session.SessionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工命令服务实现
 *
 * @author legendjw
 */
@Service
public class EmployeeCommandServiceImpl implements EmployeeCommandService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeCommandServiceImpl.class);
    @Autowired
    private EmployeeCommandService employeeCommandService;

    //默认密码
    public static String defaultPassword = "12345678";
    private EmployeeDataRepository employeeDataRepository;
    private EmployeeDomainRepository employeeDomainRepository;
    private DepartmentDomainRepository departmentDomainRepository;
    private JobDomainRepository jobDomainRepository;
    private OrganizationClient organizationClient;
    private EmployeeService employeeService;
    private AvatarService avatarService;
    private CodeTableClient codeTableClient;
    private AreaClient areaClient;
    private AttachmentClient attachmentClient;
    private AuthorizationClient authorizationClient;
    private UserClient userClient;
    private Sequence sequence;
    private MemberClient memberClient;
    private AccountClient accountClient;
    private EmployeeMapper employeeMapper;
    private OrganizationConfig organizationConfig;

    public EmployeeCommandServiceImpl(EmployeeDataRepository employeeDataRepository, EmployeeDomainRepository employeeDomainRepository,
                                      DepartmentDomainRepository departmentDomainRepository, JobDomainRepository jobDomainRepository,
                                      OrganizationClient organizationClient, EmployeeService employeeService, AvatarService avatarService,
                                      CodeTableClient codeTableClient, AreaClient areaClient, AttachmentClient attachmentClient,
                                      AuthorizationClient authorizationClient, UserClient userClient, Sequence sequence, OrganizationConfig organizationConfig,
                                      EmployeeMapper employeeMapper, MemberClient memberClient, AccountClient accountClient) {
        this.employeeDataRepository = employeeDataRepository;
        this.employeeDomainRepository = employeeDomainRepository;
        this.departmentDomainRepository = departmentDomainRepository;
        this.jobDomainRepository = jobDomainRepository;
        this.organizationClient = organizationClient;
        this.employeeService = employeeService;
        this.avatarService = avatarService;
        this.codeTableClient = codeTableClient;
        this.areaClient = areaClient;
        this.attachmentClient = attachmentClient;
        this.authorizationClient = authorizationClient;
        this.userClient = userClient;
        this.sequence = sequence;
        this.organizationConfig = organizationConfig;
        this.employeeMapper = employeeMapper;
        this.memberClient = memberClient;
        this.accountClient = accountClient;
    }

    @Override
    @Transactional
    public EmployeeId createEmployee(CreateEmployeeCommand command, Identity identity) {
        EmployeeIdentity employeeIdentity = command.getEmployee();
        if (OrganizationMode.SINGLE.name().equals(organizationConfig.organizationMode)) {
            if (employeeDomainRepository.countByOrganizationAndEmployeePhone(new OrganizationId(null), command.getPhone(), null) > 0) {
                throw new ValidationException("该手机号已在其他组织注册账号");
            }
        }

        if (employeeDomainRepository.countByOrganizationAndEmployeePhone(new OrganizationId(command.getOrganizationId()), command.getPhone(), null) > 0) {
            throw new ValidationException("组织下已经存在此手机号对应的员工");
        }
        if (StringUtils.isNotBlank(command.getEmail()) && employeeDomainRepository.countByOrganizationAndEmployeeEmail(new OrganizationId(command.getOrganizationId()), command.getEmail(), null) > 0) {
            throw new ValidationException("组织下已经存在此邮箱对应的员工");
        }
        if (organizationConfig.enableEmployeeAccountValidate) {
            //验证手机号是否注册
            if (StringUtils.isNotBlank(command.getPhone()) && userClient.isPhoneRegistered(command.getPhone())) {
                throw new ValidationException("指定手机号已经注册账号，您可使用邀请功能邀请此用户加入当前组织");
            }
            //验证邮箱是否注册
            if (StringUtils.isNotBlank(command.getEmail()) && userClient.isEmailRegistered(command.getEmail())) {
                throw new ValidationException("指定邮箱已经注册账号，您可使用邀请功能邀请此用户加入当前组织");
            }
        }

        //地区
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
        //性别
        if (command.getGenderId() != null) {
            CodeTableItemBasicDTO gender = codeTableClient.getSystemCodeTableItem("gender", command.getGenderId().toString());
            command.setGenderName(gender != null ? gender.getName() : "");
        }
        //用户分类
        if (command.getCategoryId() != null) {
            CodeTableItemBasicDTO category = codeTableClient.getSystemCodeTableItem("user_category", command.getCategoryId().toString());
            command.setCategoryName(category != null ? category.getName() : "");
        }
        //查找上级领导
        Supervisor supervisor = findSupervisor(command.getSupervisorId());
        //查找部门
        Department department = departmentDomainRepository.findById(new DepartmentId(command.getDepartmentId()));
        if (department == null) {
            throw new ValidationException("请选择员工所属的部门");
        }
        //查找岗位
        List<Job> jobs = command.getJobIds() != null && !command.getJobIds().isEmpty() ? jobDomainRepository.findByIds(command.getJobIds()) : new ArrayList<>();
        //离职原因
        if (command.getLeaveReasonId() != null) {
            CodeTableItemBasicDTO leaveReason = codeTableClient.getSystemCodeTableItem("employee_leave_reason", command.getLeaveReasonId().toString());
            command.setLeaveReasonName(leaveReason != null ? leaveReason.getName() : "");
        }
        //离职经办人
        if (command.getLeaveOperatorId() != null) {
            Employee employee = employeeDomainRepository.findById(new EmployeeId(command.getLeaveOperatorId()));
            if (employee != null) {
                command.setLeaveOperatorName(employee.getName().getName());
            }
        }

        EmployeeId employeeId = new EmployeeId(sequence.nextId());
        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .id(employeeId)
                .organizationId(new OrganizationId(command.getOrganizationId()))
                .department(department)
                .jobs(jobs)
                .name(new PinyinName(command.getName()))
                .phone(new PhoneNumber(command.getPhone()))
                .email(StringUtils.isNotBlank(command.getEmail()) ? new Email(command.getEmail()) : null)
                .gender(command.getGenderId() != null ? new Gender(command.getGenderId(), command.getGenderName()) : null)
                .category(command.getCategoryId() != null ? new EmployeeCategory(command.getCategoryId(), command.getCategoryName()) : null)
                .number(command.getNumber())
                .jobTitle(command.getJobTitle())
                .supervisor(supervisor)
                .jobStatus(JobStatus.ON_JOB)
                .isShowBook(command.getIsShowBook())
                .identityType(EmployeeIdentityType.EMPLOYEE)
                .creator(new User(employeeIdentity.getId(), employeeIdentity.getName()))
                .createTime(LocalDateTime.now())
                .entryTime(command.getEntryTime())
                .conversionTime(command.getConversionTime())
                .leaveTime(command.getLeaveTime())
                .leaveReasonId(command.getLeaveReasonId())
                .leaveReasonName(command.getLeaveReasonName())
                .leaveOperator(new User(command.getLeaveOperatorId(), command.getLeaveOperatorName()))
                .leaveHandoverContent(command.getLeaveHandoverContent())
                .leaveRemark(command.getLeaveRemark())
                .qq(command.getQq())
                .wechat(command.getWechat())
                .idNumber(StringUtils.isNotBlank(command.getIdNumber()) ? new IdNumber(command.getIdNumber()) : null)
                .birthday(command.getBirthday())
                .nation(command.getNation())
                .politicalStatus(command.getPoliticalStatus())
                .maritalStatus(command.getMaritalStatus())
                .passportNo(command.getPassportNo())
                .internalExperience(command.getInternalExperience())
                .workingExperience(command.getWorkingExperience())
                .emergencyContactPerson(command.getEmergencyContactPerson())
                .emergencyContact(command.getEmergencyContact())
                .emergencyContactRelationship(command.getEmergencyContactRelationship())
                .memberOfFamily(command.getMemberOfFamily())
                .educationExperience(command.getEducationExperience())
                .address(Address.builder()
                        .provinceId(command.getProvinceId())
                        .provinceName(command.getProvinceName())
                        .cityId(command.getCityId())
                        .cityName(command.getCityName())
                        .areaId(command.getAreaId())
                        .areaName(command.getAreaName())
                        .streetId(command.getStreetId())
                        .streetName(command.getStreetName())
                        .address(command.getAddress())
                        .build()
                )
                .remark(command.getRemark())
                .sourceType(command.getCreateType() != null && command.getCreateType() == 1 ? 1 : 0)
                .jobStatusId(command.getJobStatusId() != null && command.getJobStatusId() == 1 ? 1 : 0)
                .username(command.getUsername())
                .build();

        //生成默认用户头像
        if (command.getAvatarAttachmentId() == null || command.getAvatarAttachmentId().longValue() == 0L) {
            Avatar avatar = avatarService.generateAvatarByName(employeeInfo.getName().getName(), employeeId.getId().toString());
            employeeInfo.modifyAvatar(avatar);
        } else {
            Avatar avatar = avatarService.generateAvatarByAttachment(attachmentClient.getAttachment(command.getAvatarAttachmentId()), employeeId.getId().toString());
            employeeInfo.modifyAvatar(avatar);
        }
        //关联离职附件
        if (command.getLeaveFiles() != null && !command.getLeaveFiles().isEmpty()) {
            RelateAttachmentsCommand relateAttachmentsCommand = new RelateAttachmentsCommand();
            relateAttachmentsCommand.setAttachmentIds(command.getLeaveFiles());
            relateAttachmentsCommand.setModuleId("employee");
            relateAttachmentsCommand.setDataId(employeeId.getId());
            relateAttachmentsCommand.setType("leave_files");
        }
        employeeDomainRepository.saveInfo(employeeInfo);

        // 默认授权为无权限
        SetEmployeeIdentityCommand identityCommand = new SetEmployeeIdentityCommand();
        identityCommand.setId(employeeId.getId());
        identityCommand.setIdentityTypeId(0);
        employeeCommandService.setIdentityAndPermission(identityCommand, identity);

        return employeeId;
    }

    @Override
    @Transactional
    public void updateEmployee(UpdateEmployeeCommand command) {
        EmployeeInfo employeeInfo = employeeDomainRepository.findInfoById(new EmployeeId(command.getId()));
        if (command.getOrganizationId() != null) {
            OrganizationId organizationId = new OrganizationId(command.getOrganizationId());
            employeeInfo.setOrganizationId(organizationId);
        }
        if (employeeInfo == null) {
            throw new NotFoundException("未找到指定的员工");
        }

        if (employeeDomainRepository.countByOrganizationAndEmployeePhone(new OrganizationId(command.getOrganizationId()), command.getPhone(), employeeInfo.getId().getId()) > 0) {
            throw new ValidationException("组织下已经存在此手机号对应的员工");
        }
        if (StringUtils.isNotBlank(command.getEmail()) && employeeDomainRepository.countByOrganizationAndEmployeeEmail(new OrganizationId(command.getOrganizationId()), command.getEmail(), employeeInfo.getId().getId()) > 0) {
            throw new ValidationException("组织下已经存在此邮箱对应的员工");
        }
        if (organizationConfig.enableEmployeeAccountValidate) {
            //验证手机号是否注册
            if (StringUtils.isNotBlank(command.getPhone())
                    && employeeInfo.getPhone() != null
                    && !employeeInfo.getPhone().getNumber().equals(command.getPhone())
                    && !employeeInfo.hasCreateAccount()
                    && userClient.isPhoneRegistered(command.getPhone())
            ) {
                throw new ValidationException("指定手机号已经注册账号，您可使用邀请功能邀请此用户加入当前组织");
            }
            //验证邮箱是否注册
            if (StringUtils.isNotBlank(command.getEmail())
                    && employeeInfo.getEmail() != null
                    && !employeeInfo.getEmail().getAddress().equals(command.getEmail())
                    && !employeeInfo.hasCreateAccount()
                    && userClient.isEmailRegistered(command.getEmail())
            ) {
                throw new ValidationException("指定邮箱已经注册账号，您可使用邀请功能邀请此用户加入当前组织");
            }
        }

        //地区
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
        //性别
        if (command.getGenderId() != null) {
            CodeTableItemBasicDTO gender = codeTableClient.getSystemCodeTableItem("gender", command.getGenderId().toString());
            command.setGenderName(gender != null ? gender.getName() : "");
        }
        //用户分类
        if (command.getCategoryId() != null) {
            CodeTableItemBasicDTO category = codeTableClient.getSystemCodeTableItem("user_category", command.getCategoryId().toString());
            command.setCategoryName(category != null ? category.getName() : "");
        }
        //离职原因
        if (command.getLeaveReasonId() != null) {
            command.setLeaveReasonName(codeTableClient.getOrganizationCodeTableItemName(employeeInfo.getOrganizationId().getId(), "employee_leave_reason", command.getLeaveReasonId().toString()));
        }
        //离职经办人
        if (command.getLeaveOperatorId() != null) {
            Employee employee = employeeDomainRepository.findById(new EmployeeId(command.getLeaveOperatorId()));
            if (employee != null) {
                command.setLeaveOperatorName(employee.getName().getName());
            }
        }
        //查找上级领导
        Supervisor supervisor = findSupervisor(command.getSupervisorId());
        if (supervisor != null) {
            command.setSupervisorName(supervisor.getName());
        }
        //查找部门
        Department department = departmentDomainRepository.findById(new DepartmentId(command.getDepartmentId()));
        if (department == null) {
            throw new ValidationException("请选择员工所属的部门");
        }
        //查找岗位
        List<Job> jobs = command.getJobIds() != null && !command.getJobIds().isEmpty() ? jobDomainRepository.findByIds(command.getJobIds()) : new ArrayList<>();
        employeeInfo.modifySupervisor(supervisor);
        employeeInfo.modifyDepartment(department);
        employeeInfo.modifyJob(jobs);
        employeeInfo.modifyInfo(command);
        if (command.getJobStatusId() != null) {
            if (command.getJobStatusId() == 1) {
                employeeInfo.setJobStatus(JobStatus.ON_JOB);
            } else {
                employeeInfo.setJobStatus(JobStatus.LEAVE_JOB);
            }
        }
        //修改头像
        if (command.getAvatarAttachmentId() != null && command.getAvatarAttachmentId().longValue() > 0L) {
            Avatar avatar = avatarService.generateAvatarByAttachment(attachmentClient.getAttachment(command.getAvatarAttachmentId()), employeeInfo.getId().getId().toString());
            employeeInfo.modifyAvatar(avatar);
        }
        //关联离职附件
        if (command.getLeaveFiles() != null) {
            RelateAttachmentsCommand relateAttachmentsCommand = new RelateAttachmentsCommand();
            relateAttachmentsCommand.setAttachmentIds(command.getLeaveFiles());
            relateAttachmentsCommand.setModuleId("employee");
            relateAttachmentsCommand.setDataId(employeeInfo.getId().getId());
            relateAttachmentsCommand.setType("leave_files");
            attachmentClient.relateAttachments(relateAttachmentsCommand);
        }
        //修改用户userId
        if (command.getUserId() != null) {
            employeeInfo.setUserId(new UserId(command.getUserId()));
        }
        employeeInfo.setUsername(command.getUsername());

        employeeDomainRepository.saveInfo(employeeInfo);
    }

    @Override
    @Transactional
    public void updateIdentityEmployee(UpdateIdentityEmployeeCommand command) {
        EmployeeIdentity identity = command.getIdentity();
        EmployeeInfo employeeInfo = employeeDomainRepository.findInfoByMemberId(identity.getId());
        if (employeeInfo == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        //if (employeeDomainRepository.countByOrganizationAndEmployeePhone(new OrganizationId(identity.getOrganizationId()), command.getPhone(), employee.getId().getId()) > 0) {
        //    throw new ValidationException("组织下已经存在此手机号对应的员工");
        //}

        //性别
        if (command.getGenderId() != null) {
            CodeTableItemBasicDTO gender = codeTableClient.getSystemCodeTableItem("gender", command.getGenderId().toString());
            command.setGenderName(gender != null ? gender.getName() : "");
        }
        employeeInfo.modifyInfo(command);
        UpdateMemberCommand memberCommand = new UpdateMemberCommand();
        //修改头像
        if (command.getAvatarAttachmentId() != null && command.getAvatarAttachmentId().longValue() > 0L
                && command.getAvatarAttachmentId().longValue() != identity.getId().longValue()) {
            Avatar avatar = avatarService.generateAvatarByAttachment(attachmentClient.getAttachment(command.getAvatarAttachmentId()), employeeInfo.getId().getId().toString());
            employeeInfo.modifyAvatar(avatar);
            memberCommand.setAvatarAttachmentId(command.getAvatarAttachmentId());
        }

        memberCommand.setId(identity.getId());
        memberCommand.setName(command.getName());
        memberCommand.setGenderId(command.getGenderId());
        memberCommand.setGenderName(command.getGenderName());
        memberClient.updateMember(memberCommand);

        employeeDomainRepository.saveInfo(employeeInfo);
    }

    @Override
    public void deleteEmployee(DeleteEmployeeCommand command) {
        EmployeeIdentity identity = command.getEmployee();
        Employee employee = employeeDomainRepository.findById(new EmployeeId(command.getId()));
        employeeDomainRepository.remove(new EmployeeId(command.getId()), identity);
        if (employee.getUserId() != null) {
            MemberBasicDTO basicDTO = memberClient.getBasicAccountSystemMember(employee.getUserId().getId(), 1L, identity.getOrganizationId());
            if (basicDTO != null) {
                memberClient.deleteMember(basicDTO.getId());
            }
        }
    }

    @Override
    public void onJob(EmployeeOnJobCommand command) {
        EmployeeInfo employeeInfo = employeeDomainRepository.findInfoById(new EmployeeId(command.getId()));
        if (employeeInfo == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        employeeInfo.modifyJobStatus(JobStatus.ON_JOB);
        employeeDomainRepository.saveInfo(employeeInfo);
    }

    @Override
    @Transactional
    public void leaveJob(EmployeeLeaveJobCommand command) {
        EmployeeInfo employeeInfo = employeeDomainRepository.findInfoById(new EmployeeId(command.getId()));
        if (employeeInfo == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        //离职原因
        if (command.getLeaveReasonId() != null) {
            command.setLeaveReasonName(codeTableClient.getOrganizationCodeTableItemName(employeeInfo.getOrganizationId().getId(), "employee_leave_reason", command.getLeaveReasonId().toString()));
        }
        //离职经办人
        if (command.getLeaveOperatorId() != null) {
            Employee employee = employeeDomainRepository.findById(new EmployeeId(command.getLeaveOperatorId()));
            if (employee != null) {
                command.setLeaveOperatorName(employee.getName().getName());
            }
        }
        employeeInfo.modifyJobStatus(
                JobStatus.LEAVE_JOB,
                StringUtils.isNotBlank(command.getLeaveTime()) ? LocalDate.parse(command.getLeaveTime()) : null,
                command.getLeaveReasonId(),
                command.getLeaveReasonName(),
                new User(command.getLeaveOperatorId(), command.getLeaveOperatorName()),
                command.getLeaveHandoverContent(),
                command.getLeaveRemark()
        );
        employeeDomainRepository.saveInfo(employeeInfo);

        //关联离职附件
        if (command.getLeaveFiles() != null) {
            RelateAttachmentsCommand relateAttachmentsCommand = new RelateAttachmentsCommand();
            relateAttachmentsCommand.setAttachmentIds(command.getLeaveFiles());
            relateAttachmentsCommand.setModuleId("employee");
            relateAttachmentsCommand.setDataId(employeeInfo.getId().getId());
            relateAttachmentsCommand.setType("leave_files");
            attachmentClient.relateAttachments(relateAttachmentsCommand);
        }

        //员工离职后权限将设置为无权限
        employeeService.changeToEmployee(employeeInfo.getId());
        authorizationClient.removeToken(employeeInfo.getMemberId().getId());
    }

    @Override
    @Transactional
    public void setIdentityAndPermission(SetEmployeeIdentityCommand command, Identity identity) {

        Employee employee = employeeDomainRepository.findById(new EmployeeId(command.getId()));
        if (employee == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        EmployeeIdentityType employeeIdentityType = EmployeeIdentityType.getById(command.getIdentityTypeId());
        //转为管理员
        if (employeeIdentityType.equals(EmployeeIdentityType.ADMIN)) {
            employeeService.changeToAdmin(employee.getId());
        }
        //转为操作员
        else if (employeeIdentityType.equals(EmployeeIdentityType.OPERATOR)) {
            employeeService.changeToOperator(employee.getId(), EmployeePermissionType.getById(command.getPermissionTypeId()), command.getRoleIds(), command.getMenuPermissions(), identity);
        }
        //转为员工
        else if (employeeIdentityType.equals(EmployeeIdentityType.EMPLOYEE)) {
            employeeService.changeToEmployee(employee.getId());
            //设置无权限，将对用的用户表数据删除，员工表用户id置为0
            EmployeeDO employeeDO = new EmployeeDO();
            employeeDO.setId(command.getId());
            employeeDO.setUserId(new Long(0));
            employeeMapper.updateById(employeeDO);
        }
    }

    @Override
    public void changeEmployeeUsername(ChangeEmployeeUsernameCommand command) {
        Employee employee = employeeDomainRepository.findById(new EmployeeId(command.getId()));
        if (employee == null) {
            throw new NotFoundException("未找到指定的员工");
        }

        ModifyAccountCommand modifyAccountCommand = new ModifyAccountCommand();
        modifyAccountCommand.setUsername(command.getUsername());
        userClient.modifyAccount(employee.getUserId().getId(), modifyAccountCommand);
    }

    @Override
    public void changeEmployeeUserPhone(ChangeEmployeeUserPhoneCommand command) {
        Employee employee = employeeDomainRepository.findById(new EmployeeId(command.getId()));
        if (employee == null) {
            throw new NotFoundException("未找到指定的员工");
        }

        ModifyAccountCommand modifyAccountCommand = new ModifyAccountCommand();
        modifyAccountCommand.setPhone(command.getPhone());
        userClient.modifyAccount(employee.getUserId().getId(), modifyAccountCommand);
    }

    @Override
    public void resetPassword(Long employeeId) {
        Employee employee = employeeDomainRepository.findById(new EmployeeId(employeeId));
        if (employee == null) {
            throw new NotFoundException("未找到指定的员工");
        }
        if (employee.getUserId() == null || employee.getUserId().getId() == 0L) {
            return;
        }

        ModifyPasswordCommand modifyPasswordCommand = new ModifyPasswordCommand();
        modifyPasswordCommand.setPassword(defaultPassword);
        accountClient.modifyPassword(employee.getUserId().getId(), modifyPasswordCommand);
        MemberBasicDTO basicDTO = memberClient.getBasicAccountSystemMember(employee.getUserId().getId(), 1L, employee.getOrganizationId().getId());
        if (basicDTO != null) {
            authorizationClient.removeToken(basicDTO.getId());
        }
    }

    @Override
    public AccessTokenDTO switchOrganization(SwitchOrganizationCommand command, Identity identity) {
        List<OrganizationBasicDTO> userJoinOrganizations = organizationClient.getUserJoinOrganizations(identity.getId());
        if (!userJoinOrganizations.stream().anyMatch(o -> o.getId().equals(command.getOrganizationId()))) {
            throw new BusinessException("切换到非法的组织");
        }
        //切换公司生成新token
        MemberBasicDTO memberBasicDTO = memberClient.getBasicAccountSystemMember(identity.getId(), command.getSystemId(), command.getOrganizationId());
        if (memberBasicDTO == null) {
            throw new NotFoundException("账号不存在");
        }
        GenerateAccessTokenCommand generateAccessTokenCommand = new GenerateAccessTokenCommand();
        generateAccessTokenCommand.setContextId(SessionContext.contextId);
        generateAccessTokenCommand.setUserId(identity.getId().toString());
        generateAccessTokenCommand.setClientId(command.getClientId());
        Map<String, String> extraData = new HashMap<>();
        extraData.put("organization_id", command.getOrganizationId().toString());
        extraData.put("member_id", memberBasicDTO.getId().toString());
        extraData.put("system_id", command.getSystemId().toString());
        generateAccessTokenCommand.setExtraData(extraData);
        return authorizationClient.generateAccessToken(generateAccessTokenCommand);
    }

    /**
     * 根据id查找上级领导
     *
     * @param supervisorId
     * @return
     */
    private Supervisor findSupervisor(Long supervisorId) {
        Supervisor supervisor = null;
        if (supervisorId != null && supervisorId.longValue() != 0L) {
            EmployeeDO supervisorEmployee = employeeDataRepository.findById(supervisorId);
            if (supervisorEmployee != null) {
                supervisor = new Supervisor(supervisorEmployee.getId(), supervisorEmployee.getName());
            }
        }
        return supervisor;
    }
}