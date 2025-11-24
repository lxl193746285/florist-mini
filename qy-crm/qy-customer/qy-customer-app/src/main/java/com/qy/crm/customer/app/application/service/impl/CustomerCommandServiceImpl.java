package com.qy.crm.customer.app.application.service.impl;

import com.qy.audit.api.client.AuditLogClient;
import com.qy.audit.api.command.CreateAuditLogCommand;
import com.qy.audit.api.enums.AuditStatus;
import com.qy.audit.api.enums.AuditType;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.*;
import com.qy.crm.customer.app.application.enums.Module;
import com.qy.crm.customer.app.application.service.*;
import com.qy.crm.customer.app.infrastructure.persistence.CustomerDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.CustomerDO;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.command.BindMemberOpenAccountCommand;
import com.qy.member.api.command.OpenMemberCommand;
import com.qy.member.api.command.UpdateOpenMemberCommand;
import com.qy.member.api.dto.MemberIdDTO;
import com.qy.organization.api.client.DepartmentClient;
import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.command.OpenAccountCommand;
import com.qy.organization.api.command.UpdateOpenAccountCommand;
import com.qy.organization.api.dto.DepartmentBasicDTO;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.organization.api.dto.OrganizationDTO;
import com.qy.region.api.AreaClient;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import com.qy.util.PinyinUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户命令实现
 *
 * @author legendjw
 */
@Service
public class CustomerCommandServiceImpl implements CustomerCommandService {
    private CustomerDataRepository customerDataRepository;
    private CodeTableClient codeTableClient;
    private DepartmentClient departmentClient;
    private EmployeeClient employeeClient;
    private OrganizationClient organizationClient;
    private ContactQueryService contactQueryService;
    private ContactCommandService contactCommandService;
    private BusinessLicenseQueryService businessLicenseQueryService;
    private BusinessLicenseCommandService businessLicenseCommandService;
    private OpenBankQueryService openBankQueryService;
    private OpenBankCommandService openBankCommandService;
    private AreaClient areaClient;
    private MemberClient memberClient;
    private AuditLogClient auditLogClient;

    public CustomerCommandServiceImpl(CustomerDataRepository customerDataRepository, CodeTableClient codeTableClient, DepartmentClient departmentClient, EmployeeClient employeeClient, OrganizationClient organizationClient, ContactQueryService contactQueryService, ContactCommandService contactCommandService, BusinessLicenseQueryService businessLicenseQueryService, BusinessLicenseCommandService businessLicenseCommandService, OpenBankQueryService openBankQueryService, OpenBankCommandService openBankCommandService, AreaClient areaClient, MemberClient memberClient, AuditLogClient auditLogClient) {
        this.customerDataRepository = customerDataRepository;
        this.codeTableClient = codeTableClient;
        this.departmentClient = departmentClient;
        this.employeeClient = employeeClient;
        this.organizationClient = organizationClient;
        this.contactQueryService = contactQueryService;
        this.contactCommandService = contactCommandService;
        this.businessLicenseQueryService = businessLicenseQueryService;
        this.businessLicenseCommandService = businessLicenseCommandService;
        this.openBankQueryService = openBankQueryService;
        this.openBankCommandService = openBankCommandService;
        this.areaClient = areaClient;
        this.memberClient = memberClient;
        this.auditLogClient = auditLogClient;
    }

    @Override
    @Transactional
    public CustomerIdDTO createCustomer(CreateCustomerCommand command) {
        if (customerDataRepository.countByOrganizationIdAndName(command.getOrganizationId(), command.getName(), null) > 0) {
            throw new ValidationException("组织下已经存在同名的客户，请更换新的客户名称");
        }

        EmployeeIdentity identity = command.getIdentity();

        //创建客户
        CustomerDO customerDO = new CustomerDO();
        BeanUtils.copyProperties(command, customerDO, "identity");
        customerDO.setStatusId(AuditStatus.WAITING_AUDIT.getId());
        fillFormRelatedData(customerDO);
        customerDO.setCreatorId(identity.getId());
        customerDO.setCreatorName(identity.getName());
        customerDO.setCreateTime(LocalDateTime.now());
        customerDataRepository.save(customerDO);

        RelatedModuleDataDTO relatedModuleData = new RelatedModuleDataDTO(Module.CUSTOMER.getId(), customerDO.getId());
        //保存联系人
        BatchSaveContactCommand batchSaveContactCommand = new BatchSaveContactCommand();
        batchSaveContactCommand.setIdentity(command.getIdentity());
        batchSaveContactCommand.setOrganizationId(command.getOrganizationId());
        batchSaveContactCommand.setRelatedModuleData(relatedModuleData);
        batchSaveContactCommand.setContacts(command.getContacts());
        contactCommandService.batchSaveContact(batchSaveContactCommand);
        List<ContactDTO> contacts = contactQueryService.getContactsByRelatedModule(relatedModuleData.getRelatedModuleId(), relatedModuleData.getRelatedDataId());
        ContactDTO legalPerson = contacts.stream().filter(c -> c.getIsLegalPerson().intValue() == 1).findFirst().orElse(null);

        //保存营业执照
        SaveBusinessLicenseCommand saveBusinessLicenseCommand = new SaveBusinessLicenseCommand();
        saveBusinessLicenseCommand.setIdentity(command.getIdentity());
        saveBusinessLicenseCommand.setOrganizationId(command.getOrganizationId());
        saveBusinessLicenseCommand.setRelatedModuleData(relatedModuleData);
        saveBusinessLicenseCommand.setBusinessLicense(command.getBusinessLicense());
        if (legalPerson != null) {
            saveBusinessLicenseCommand.setLegalPersonId(legalPerson.getId());
            saveBusinessLicenseCommand.setLegalPersonName(legalPerson.getName());
        }
        businessLicenseCommandService.saveBusinessLicense(saveBusinessLicenseCommand);

        //保存开户行
        BatchSaveOpenBankCommand batchSaveOpenBankCommand = new BatchSaveOpenBankCommand();
        batchSaveOpenBankCommand.setIdentity(command.getIdentity());
        batchSaveOpenBankCommand.setOrganizationId(command.getOrganizationId());
        batchSaveOpenBankCommand.setRelatedModuleData(relatedModuleData);
        batchSaveOpenBankCommand.setOpenBanks(command.getOpenBanks());
        if (legalPerson != null) {
            batchSaveOpenBankCommand.setLegalPersonId(legalPerson.getId());
            batchSaveOpenBankCommand.setLegalPersonName(legalPerson.getName());
        }
        openBankCommandService.batchSaveOpenBank(batchSaveOpenBankCommand);

        return new CustomerIdDTO(customerDO.getId());
    }

    @Override
    @Transactional
    public void updateCustomer(UpdateCustomerCommand command) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        if (customerDO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        if (customerDataRepository.countByOrganizationIdAndName(customerDO.getOrganizationId(), command.getName(), customerDO.getId()) > 0) {
            throw new ValidationException("组织下已经存在同名的客户，请更换新的客户名称");
        }
        EmployeeIdentity identity = command.getIdentity();

        //修改客户
        BeanUtils.copyProperties(command, customerDO, "identity");
        customerDO.setStatusId(AuditStatus.WAITING_AUDIT.getId());
        fillFormRelatedData(customerDO);
        customerDO.setUpdatorId(identity.getId());
        customerDO.setUpdatorName(identity.getName());
        customerDO.setUpdateTime(LocalDateTime.now());
        customerDataRepository.save(customerDO);

        RelatedModuleDataDTO relatedModuleData = new RelatedModuleDataDTO(Module.CUSTOMER.getId(), customerDO.getId());
        //保存联系人
        BatchSaveContactCommand batchSaveContactCommand = new BatchSaveContactCommand();
        batchSaveContactCommand.setIdentity(command.getIdentity());
        batchSaveContactCommand.setOrganizationId(command.getOrganizationId());
        batchSaveContactCommand.setRelatedModuleData(relatedModuleData);
        batchSaveContactCommand.setContacts(command.getContacts());
        contactCommandService.batchSaveContact(batchSaveContactCommand);
        List<ContactDTO> contacts = contactQueryService.getContactsByRelatedModule(relatedModuleData.getRelatedModuleId(), relatedModuleData.getRelatedDataId());
        ContactDTO legalPerson = contacts.stream().filter(c -> c.getIsLegalPerson().intValue() == 1).findFirst().orElse(null);

        //保存营业执照
        SaveBusinessLicenseCommand saveBusinessLicenseCommand = new SaveBusinessLicenseCommand();
        saveBusinessLicenseCommand.setIdentity(command.getIdentity());
        saveBusinessLicenseCommand.setOrganizationId(command.getOrganizationId());
        saveBusinessLicenseCommand.setRelatedModuleData(relatedModuleData);
        saveBusinessLicenseCommand.setBusinessLicense(command.getBusinessLicense());
        if (legalPerson != null) {
            saveBusinessLicenseCommand.setLegalPersonId(legalPerson.getId());
            saveBusinessLicenseCommand.setLegalPersonName(legalPerson.getName());
        }
        businessLicenseCommandService.saveBusinessLicense(saveBusinessLicenseCommand);

        //保存开户行
        BatchSaveOpenBankCommand batchSaveOpenBankCommand = new BatchSaveOpenBankCommand();
        batchSaveOpenBankCommand.setIdentity(command.getIdentity());
        batchSaveOpenBankCommand.setOrganizationId(command.getOrganizationId());
        batchSaveOpenBankCommand.setRelatedModuleData(relatedModuleData);
        batchSaveOpenBankCommand.setOpenBanks(command.getOpenBanks());
        if (legalPerson != null) {
            batchSaveOpenBankCommand.setLegalPersonId(legalPerson.getId());
            batchSaveOpenBankCommand.setLegalPersonName(legalPerson.getName());
        }
        openBankCommandService.batchSaveOpenBank(batchSaveOpenBankCommand);
    }

    @Override
    @Transactional
    public void deleteCustomer(DeleteCustomerCommand command) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        EmployeeIdentity identity = command.getIdentity();

        customerDataRepository.remove(command.getId(), identity);

        //删除联系人
        List<ContactDTO> contactDTOS = contactQueryService.getContactsByRelatedModule(Module.CUSTOMER.getId(), customerDO.getId());
        BatchDeleteContactCommand deleteContactCommand = new BatchDeleteContactCommand();
        deleteContactCommand.setIds(contactDTOS.stream().map(ContactDTO::getId).collect(Collectors.toList()));
        deleteContactCommand.setIdentity(command.getIdentity());
        contactCommandService.batchDeleteContact(deleteContactCommand);

        //保存营业执照
        BusinessLicenseDTO businessLicenseDTO = businessLicenseQueryService.getBusinessLicenseByRelatedModule(Module.CUSTOMER.getId(), customerDO.getId());
        if (businessLicenseDTO != null) {
            DeleteBusinessLicenseCommand deleteBusinessLicenseCommand = new DeleteBusinessLicenseCommand();
            deleteBusinessLicenseCommand.setId(businessLicenseDTO.getId());
            deleteBusinessLicenseCommand.setIdentity(command.getIdentity());
            businessLicenseCommandService.deleteBusinessLicense(deleteBusinessLicenseCommand);
        }

        //删除开户行
        List<OpenBankDTO> openBankDTOS = openBankQueryService.getOpenBanksByRelatedModule(Module.CUSTOMER.getId(), customerDO.getId());
        BatchDeleteOpenBankCommand deleteOpenBankCommand = new BatchDeleteOpenBankCommand();
        deleteOpenBankCommand.setIds(openBankDTOS.stream().map(OpenBankDTO::getId).collect(Collectors.toList()));
        deleteOpenBankCommand.setIdentity(command.getIdentity());
        openBankCommandService.batchDeleteOpenBank(deleteContactCommand);
    }

    @Override
    @Transactional
    public void openAccount(CustomerOpenAccountCommand command) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        if (customerDO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        if (customerDO.getIsOpenAccount().intValue() == 1) {
            throw new ValidationException("客户已经开户请勿重复开户");
        }
        //创建开户
        OpenAccountCommand openAccountCommand = new OpenAccountCommand();
        BeanUtils.copyProperties(command, openAccountCommand);
        openAccountCommand.setSource(Module.CUSTOMER.getId());
        openAccountCommand.setSourceName(Module.CUSTOMER.getName());
        openAccountCommand.setSourceDataId(customerDO.getId());
        openAccountCommand.setSourceDataName(customerDO.getName());
        OrganizationBasicDTO organizationBasicDTO = organizationClient.openAccount(openAccountCommand);

        //更新开户状态
        customerDO.setIsOpenAccount((byte) 1);
        customerDO.setOpenAccountId(organizationBasicDTO.getId());
        customerDataRepository.save(customerDO);

        //设置联系人超管
        SetContactIsSuperAdminCommand setContactIsSuperAdminCommand = new SetContactIsSuperAdminCommand();
        setContactIsSuperAdminCommand.setId(command.getContactId());
        setContactIsSuperAdminCommand.setRelatedModuleId(Module.CUSTOMER.getId());
        setContactIsSuperAdminCommand.setRelatedDataId(customerDO.getId());
        contactCommandService.setContactIsSuperAdmin(setContactIsSuperAdminCommand);

        //更新会员绑定开户组织
        if (customerDO.getIsOpenMember().intValue() == 1) {
            BindMemberOpenAccountCommand bindMemberOpenAccountCommand = new BindMemberOpenAccountCommand();
            bindMemberOpenAccountCommand.setMemberId(customerDO.getOpenMemberId());
            bindMemberOpenAccountCommand.setOpenAccountId(customerDO.getOpenAccountId());
            memberClient.bindMemberOpenAccount(bindMemberOpenAccountCommand);
        }
    }

    @Override
    @Transactional
    public void updateOpenAccount(CustomerModifyOpenAccountCommand command) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        if (customerDO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        if (customerDO.getIsOpenAccount().intValue() != 1) {
            throw new ValidationException("客户尚未开户无法修改开户信息");
        }
        //修改开户
        UpdateOpenAccountCommand updateOpenAccountCommand = new UpdateOpenAccountCommand();
        BeanUtils.copyProperties(command, updateOpenAccountCommand);
        updateOpenAccountCommand.setOpenAccountId(customerDO.getOpenAccountId());
        updateOpenAccountCommand.setSource(Module.CUSTOMER.getId());
        updateOpenAccountCommand.setSourceName(Module.CUSTOMER.getName());
        updateOpenAccountCommand.setSourceDataId(customerDO.getId());
        updateOpenAccountCommand.setSourceDataName(customerDO.getName());
        organizationClient.updateOpenAccount(updateOpenAccountCommand);

        //设置联系人超管
        SetContactIsSuperAdminCommand setContactIsSuperAdminCommand = new SetContactIsSuperAdminCommand();
        setContactIsSuperAdminCommand.setId(command.getContactId());
        setContactIsSuperAdminCommand.setRelatedModuleId(Module.CUSTOMER.getId());
        setContactIsSuperAdminCommand.setRelatedDataId(customerDO.getId());
        contactCommandService.setContactIsSuperAdmin(setContactIsSuperAdminCommand);
    }

    @Override
    @Transactional
    public void openMember(CustomerOpenMemberCommand command) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        if (customerDO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        if (customerDO.getIsOpenMember().intValue() == 1) {
            throw new ValidationException("客户已经开通会员请勿重复开通");
        }

        //开通会员
        OpenMemberCommand openMemberCommand = new OpenMemberCommand();
        BeanUtils.copyProperties(command, openMemberCommand);
        openMemberCommand.setOpenAccountId(customerDO.getOpenAccountId());
        MemberIdDTO memberIdDTO = memberClient.openMember(openMemberCommand);

        //更新开通会员状态
        customerDO.setIsOpenMember((byte) 1);
        customerDO.setOpenMemberId(memberIdDTO.getId());
        customerDataRepository.save(customerDO);
    }

    @Override
    public void updateOpenMember(CustomerUpdateOpenMemberCommand command) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        if (customerDO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        if (customerDO.getIsOpenMember().intValue() == 0) {
            throw new ValidationException("客户未开通会员请勿修改会员");
        }

        //修改会员
        UpdateOpenMemberCommand updateOpenMemberCommand = new UpdateOpenMemberCommand();
        BeanUtils.copyProperties(command, updateOpenMemberCommand);
        updateOpenMemberCommand.setMemberId(customerDO.getOpenMemberId());
        updateOpenMemberCommand.setOpenAccountId(customerDO.getOpenAccountId());
        memberClient.updateOpenMember(updateOpenMemberCommand);
    }

    @Override
    @Transactional
    public void auditCustomer(AuditCustomerCommand command, EmployeeIdentity identity) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        if (customerDO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        if (customerDO.getStatusId().intValue() != AuditStatus.WAITING_AUDIT.getId()) {
            throw new ValidationException("客户不是待审核状态无法审核");
        }
        AuditType auditType = AuditType.getById(command.getStatus());
        if (auditType == null) {
            throw new ValidationException("非法的审核操作");
        }

        //修改客户状态
        customerDO.setStatusId(auditType.getAuditStatus().getId());
        String statusName = codeTableClient.getSystemCodeTableItemName("common_audit_status", String.valueOf(customerDO.getStatusId()));
        customerDO.setStatusName(statusName);
        customerDataRepository.save(customerDO);

        //记录审核日志
        CreateAuditLogCommand auditLogCommand = new CreateAuditLogCommand();
        auditLogCommand.setOrganizationId(customerDO.getOrganizationId());
        auditLogCommand.setModuleId(Module.CUSTOMER.getId());
        auditLogCommand.setDataId(customerDO.getId());
        auditLogCommand.setTypeId(auditType.getId());
        auditLogCommand.setReason(command.getReason());
        auditLogCommand.setRemark(command.getRemark());
        auditLogCommand.setAuditorId(identity.getId());
        auditLogCommand.setAuditorName(identity.getName());
        auditLogClient.createAuditLog(auditLogCommand);
    }

    @Override
    @Transactional
    public void submitAuditCustomer(SubmitAuditCustomerCommand command, EmployeeIdentity identity) {
        CustomerDO customerDO = customerDataRepository.findById(command.getId());
        if (customerDO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        if (customerDO.getStatusId().intValue() != AuditStatus.DRAFT.getId()) {
            throw new ValidationException("客户不是草稿状态无法提交审核");
        }

        customerDO.setStatusId(AuditStatus.WAITING_AUDIT.getId());
        String statusName = codeTableClient.getSystemCodeTableItemName("common_audit_status", String.valueOf(customerDO.getStatusId()));
        customerDO.setStatusName(statusName);
        customerDataRepository.save(customerDO);
    }

    @Override
    @Transactional
    public void createUserCustomer(UserBasicDTO userBasicDTO) {
        //创建客户
        CustomerDO customerDO = new CustomerDO();
        customerDO.setOrganizationId(1L);
        customerDO.setName(userBasicDTO.getName());
        customerDO.setTel(userBasicDTO.getPhone());
        customerDO.setStatusId(AuditStatus.PASSED.getId());
        fillFormRelatedData(customerDO);
        customerDO.setCreateTime(LocalDateTime.now());
        customerDataRepository.save(customerDO);

        //保存联系人
        RelatedModuleDataDTO relatedModuleData = new RelatedModuleDataDTO(Module.CUSTOMER.getId(), customerDO.getId());
        List<RelatedModuleDataDTO> relatedModuleDataDTOS = Arrays.asList(relatedModuleData);
        CreateContactCommand createContactCommand = new CreateContactCommand();
        createContactCommand.setIdentity(null);
        createContactCommand.setOrganizationId(customerDO.getOrganizationId());
        createContactCommand.setRelatedModuleData(relatedModuleDataDTOS);
        createContactCommand.setName(userBasicDTO.getName());
        createContactCommand.setTel(userBasicDTO.getPhone());
        createContactCommand.setEmail(userBasicDTO.getEmail());

        contactCommandService.createContact(createContactCommand);
    }

    @Override
    @Transactional
    public void createOrganizationCustomer(OrganizationDTO organizationDTO, EmployeeBasicDTO superAdmin) {
        //创建客户
        CustomerDO customerDO = new CustomerDO();
        customerDO.setOrganizationId(1L);
        customerDO.setName(organizationDTO.getName());
        customerDO.setTel(organizationDTO.getTel());
        customerDO.setEmail(organizationDTO.getEmail());
        customerDO.setHomepage(organizationDTO.getHomepage());
        customerDO.setAddress(organizationDTO.getAddress());
        customerDO.setIndustryId(organizationDTO.getIndustryId());
        customerDO.setIndustryName(organizationDTO.getIndustryName());
        customerDO.setScaleId(organizationDTO.getScaleId());
        customerDO.setScaleName(organizationDTO.getScaleName());
        customerDO.setIsOpenAccount((byte) 1);
        customerDO.setOpenAccountId(organizationDTO.getId());
        customerDO.setStatusId(AuditStatus.PASSED.getId());
        fillFormRelatedData(customerDO);
        customerDO.setCreateTime(LocalDateTime.now());
        customerDataRepository.save(customerDO);

        //保存联系人
        RelatedModuleDataDTO relatedModuleData = new RelatedModuleDataDTO(Module.CUSTOMER.getId(), customerDO.getId());
        List<RelatedModuleDataDTO> relatedModuleDataDTOS = Arrays.asList(relatedModuleData);
        CreateContactCommand createContactCommand = new CreateContactCommand();
        createContactCommand.setIdentity(null);
        createContactCommand.setOrganizationId(customerDO.getOrganizationId());
        createContactCommand.setRelatedModuleData(relatedModuleDataDTOS);
        createContactCommand.setName(superAdmin.getName());
        createContactCommand.setTel(superAdmin.getPhone());
        createContactCommand.setEmail(superAdmin.getEmail());
        createContactCommand.setGenderId(superAdmin.getGenderId());
        createContactCommand.setGenderName(superAdmin.getGenderName());
        createContactCommand.setIsSuperAdmin((byte) 1);

        contactCommandService.createContact(createContactCommand);
    }

    /**
     * 填充表单关联数据
     *
     * @param customerDO
     */
    private void fillFormRelatedData(CustomerDO customerDO) {
        String levelName = customerDO.getLevelId() != null ? codeTableClient.getOrganizationCodeTableItemName(customerDO.getOrganizationId(), "customer_level", String.valueOf(customerDO.getLevelId())) : "";
        String sourceName = customerDO.getSourceId() != null ? codeTableClient.getOrganizationCodeTableItemName(customerDO.getOrganizationId(), "customer_source", String.valueOf(customerDO.getSourceId())) : "";
        String statusName = customerDO.getStatusId() != null ? codeTableClient.getSystemCodeTableItemName("common_audit_status", String.valueOf(customerDO.getStatusId())) : "";
        String industryName = customerDO.getIndustryId() != null ? codeTableClient.getSystemCodeTableItemName("organization_industry", String.valueOf(customerDO.getIndustryId())) : "";
        String scaleName = customerDO.getScaleId() != null ? codeTableClient.getSystemCodeTableItemName("organization_scale", String.valueOf(customerDO.getScaleId())) : "";
        String departmentName = "";
        if (customerDO.getDepartmentId() != null) {
            DepartmentBasicDTO departmentBasicDTO = departmentClient.getBasicDepartmentById(customerDO.getDepartmentId());
            if (departmentBasicDTO != null) {
                departmentName = departmentBasicDTO.getName();
            }
        }
        String accountManagerName = "";
        if (customerDO.getAccountManagerId() != null) {
            EmployeeBasicDTO accountManagerEmployee = employeeClient.getBasicEmployeeById(customerDO.getAccountManagerId());
            if (accountManagerEmployee != null) {
                accountManagerName = accountManagerEmployee.getName();
            }
        }

        String regionalManagerName = "";
        if (customerDO.getRegionalManagerId() != null) {
            EmployeeBasicDTO regionalManagerEmployee = employeeClient.getBasicEmployeeById(customerDO.getRegionalManagerId());
            if (regionalManagerEmployee != null) {
                regionalManagerName = regionalManagerEmployee.getName();
            }
        }

        //地区
        if (customerDO.getProvinceId() != null && customerDO.getProvinceId().intValue() != 0) {
            customerDO.setProvinceName(areaClient.getAreaNameById(customerDO.getProvinceId()));
        }
        if (customerDO.getCityId() != null && customerDO.getCityId().intValue() != 0) {
            customerDO.setCityName(areaClient.getAreaNameById(customerDO.getCityId()));
        }
        if (customerDO.getAreaId() != null && customerDO.getAreaId().intValue() != 0) {
            customerDO.setAreaName(areaClient.getAreaNameById(customerDO.getAreaId()));
        }
        if (customerDO.getStreetId() != null && customerDO.getStreetId().intValue() != 0) {
            customerDO.setStreetName(areaClient.getAreaNameById(customerDO.getStreetId()));
        }

        customerDO.setLevelName(levelName);
        customerDO.setSourceName(sourceName);
        customerDO.setIndustryName(industryName);
        customerDO.setScaleName(scaleName);
        customerDO.setStatusName(statusName);
        customerDO.setDepartmentName(departmentName);
        customerDO.setAccountManagerName(accountManagerName);
        customerDO.setRegionalManagerName(regionalManagerName);
        customerDO.setNamePinyin(PinyinUtils.getAlpha(customerDO.getName()));
    }
}