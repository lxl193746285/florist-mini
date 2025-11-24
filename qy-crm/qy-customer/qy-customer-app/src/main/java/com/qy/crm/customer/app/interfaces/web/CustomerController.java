package com.qy.crm.customer.app.interfaces.web;

import com.qy.audit.api.client.AuditLogClient;
import com.qy.audit.api.dto.AuditLogDTO;
import com.qy.audit.api.query.AuditLogQuery;
import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.*;
import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.*;
import com.qy.crm.customer.app.application.enums.Module;
import com.qy.crm.customer.app.application.query.BusinessLicenseQuery;
import com.qy.crm.customer.app.application.query.ContactQuery;
import com.qy.crm.customer.app.application.query.CustomerQuery;
import com.qy.crm.customer.app.application.query.OpenBankQuery;
import com.qy.crm.customer.app.application.service.*;
import com.qy.member.api.dto.OpenMemberInfoDTO;
import com.qy.crm.customer.app.application.service.*;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageQuery;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/crm/customer/customers")
public class CustomerController {
    private OrganizationSessionContext sessionContext;
    private CustomerCommandService customerCommandService;
    private CustomerQueryService customerQueryService;
    private ContactCommandService contactCommandService;
    private ContactQueryService contactQueryService;
    private BusinessLicenseCommandService businessLicenseCommandService;
    private BusinessLicenseQueryService businessLicenseQueryService;
    private OpenBankCommandService openBankCommandService;
    private OpenBankQueryService openBankQueryService;
    private AuditLogClient auditLogClient;

    public CustomerController(OrganizationSessionContext sessionContext, CustomerCommandService customerCommandService, CustomerQueryService customerQueryService, ContactCommandService contactCommandService, ContactQueryService contactQueryService, BusinessLicenseCommandService businessLicenseCommandService, BusinessLicenseQueryService businessLicenseQueryService, OpenBankCommandService openBankCommandService, OpenBankQueryService openBankQueryService, AuditLogClient auditLogClient) {
        this.sessionContext = sessionContext;
        this.customerCommandService = customerCommandService;
        this.customerQueryService = customerQueryService;
        this.contactCommandService = contactCommandService;
        this.contactQueryService = contactQueryService;
        this.businessLicenseCommandService = businessLicenseCommandService;
        this.businessLicenseQueryService = businessLicenseQueryService;
        this.openBankCommandService = openBankCommandService;
        this.openBankQueryService = openBankQueryService;
        this.auditLogClient = auditLogClient;
    }

    /**
     * 获取客户列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers(CustomerQuery query) {
        Page<CustomerDTO> page = customerQueryService.getCustomers(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个客户
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailDTO> getCustomer(
        @PathVariable(value = "id") Long id
    ) {
        CustomerDetailDTO customerDTO = customerQueryService.getCustomerById(id, sessionContext.getUser());
        if (customerDTO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        return ResponseUtils.ok().body(customerDTO);
    }

    /**
     * 创建单个客户
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<CustomerIdDTO> createCustomer(
            @Valid @RequestBody CreateCustomerCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        CustomerIdDTO customerIdDTO = customerCommandService.createCustomer(command);

        return ResponseUtils.ok("客户创建成功").body(customerIdDTO);
    }

    /**
     * 修改单个客户
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateCustomerCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        customerCommandService.updateCustomer(command);

        return ResponseUtils.ok("客户修改成功").build();
    }

    /**
     * 删除单个客户
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(
        @PathVariable(value = "id") Long id
    ) {
        CustomerDTO customerDTO = customerQueryService.getCustomerById(id);
        if (customerDTO == null) {
            throw new NotFoundException("未找到需要删除的客户");
        }

        DeleteCustomerCommand command = new DeleteCustomerCommand();
        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(customerDTO.getOrganizationId()));
        customerCommandService.deleteCustomer(command);

        return ResponseUtils.noContent("删除客户成功").build();
    }

    /**
     * 提交审核客户
     *
     * @return
     */
    @PostMapping("/{id}/submit-audit")
    public ResponseEntity<Object> submitAuditCustomer(
            @PathVariable(value = "id") Long id
    ) {
        CustomerDTO customerDTO = customerQueryService.getCustomerById(id);
        if (customerDTO == null) {
            throw new NotFoundException("未找到需要提交审核的客户");
        }
        EmployeeIdentity identity = sessionContext.getEmployee(customerDTO.getOrganizationId());
        SubmitAuditCustomerCommand command = new SubmitAuditCustomerCommand();
        command.setId(id);
        customerCommandService.submitAuditCustomer(command, identity);

        return ResponseUtils.ok("提交客户审核成功").build();
    }

    /**
     * 审核客户
     *
     * @param command
     * @return
     */
    @PostMapping("/{id}/audit")
    public ResponseEntity<Object> auditCustomer(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AuditCustomerCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        CustomerDTO customerDTO = customerQueryService.getCustomerById(id);
        if (customerDTO == null) {
            throw new NotFoundException("未找到需要审核的客户");
        }
        EmployeeIdentity identity = sessionContext.getEmployee(customerDTO.getOrganizationId());
        command.setId(id);
        customerCommandService.auditCustomer(command, identity);

        return ResponseUtils.ok("审核成功").build();
    }

    /**
     * 批量审核客户
     *
     * @param command
     * @return
     */
    @PostMapping("/batch-audit")
    public ResponseEntity<Object> batchAuditCustomer(
            @Valid @RequestBody BatchAuditCustomerCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        for (Long id : command.getIds()) {
            CustomerDTO customerDTO = customerQueryService.getCustomerById(id);
            if (customerDTO == null) {
                throw new NotFoundException("未找到需要审核的客户");
            }
            EmployeeIdentity identity = sessionContext.getEmployee(customerDTO.getOrganizationId());
            AuditCustomerCommand auditCustomerCommand = new AuditCustomerCommand();
            BeanUtils.copyProperties(command, auditCustomerCommand, "ids");
            auditCustomerCommand.setId(id);
            customerCommandService.auditCustomer(auditCustomerCommand, identity);
        }

        return ResponseUtils.ok("审核成功").build();
    }

    /**
     * 获取客户审核日志
     *
     * @return
     */
    @GetMapping("/{id}/audit-logs")
    public ResponseEntity<List<CustomerAuditLogDTO>> getCustomerAuditLogs(
            @PathVariable(value = "id") Long id,
            PageQuery pageQuery
    ) {
        CustomerDTO customerDTO = customerQueryService.getCustomerById(id);
        if (customerDTO == null) {
            throw new NotFoundException("未找到指定的客户");
        }

        AuditLogQuery auditLogQuery = new AuditLogQuery();
        auditLogQuery.setOrganizationId(customerDTO.getOrganizationId());
        auditLogQuery.setModuleId(Module.CUSTOMER.getId());
        auditLogQuery.setDataId(id);
        auditLogQuery.setPage(pageQuery.getPage());
        auditLogQuery.setPerPage(pageQuery.getPerPage());
        Page<AuditLogDTO> page = auditLogClient.getAuditLogs(auditLogQuery);
        Page<CustomerAuditLogDTO> customerPage = page.map(auditLogDTO -> {
            CustomerAuditLogDTO customerAuditLogDTO = new CustomerAuditLogDTO();
            BeanUtils.copyProperties(auditLogDTO, customerAuditLogDTO);
            customerAuditLogDTO.setStatus(auditLogDTO.getTypeId());
            customerAuditLogDTO.setStatusName(auditLogDTO.getTypeName());
            return customerAuditLogDTO;
        });

        return ResponseUtils.ok(page).body(customerPage.getRecords());
    }

    /**
     * 获取客户开户的信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/open-account-info")
    public ResponseEntity<CustomerOpenAccountInfoDTO> getOpenAccountInfo(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(customerQueryService.getOpenAccountInfo(id));
    }

    /**
     * 客户开户
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/{id}/open-account")
    public ResponseEntity<Object> openAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CustomerOpenAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        customerCommandService.openAccount(command);

        return ResponseUtils.ok("开户成功").build();
    }

    /**
     * 客户修改开户信息
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PatchMapping("/{id}/open-account")
    public ResponseEntity<Object> updateOpenAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CustomerModifyOpenAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        customerCommandService.updateOpenAccount(command);

        return ResponseUtils.ok("修改开户信息成功").build();
    }

    /**
     * 客户开通会员
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/{id}/open-member")
    public ResponseEntity<Object> openMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CustomerOpenMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        customerCommandService.openMember(command);

        return ResponseUtils.ok("开通会员成功").build();
    }

    /**
     * 客户修改会员
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PatchMapping("/{id}/open-member")
    public ResponseEntity<Object> updateOpenMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CustomerUpdateOpenMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        customerCommandService.updateOpenMember(command);

        return ResponseUtils.ok("修改会员成功").build();
    }

    /**
     * 获取客户开通会员信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/open-member")
    public ResponseEntity<OpenMemberInfoDTO> getOpenMemberInfo(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(customerQueryService.getOpenMemberInfo(id));
    }

    /**
     * 获取客户联系人列表
     *
     * @return
     */
    @GetMapping("/{id}/contacts")
    public ResponseEntity<List<ContactDTO>> getContacts(
            @PathVariable(value = "id") Long id,
            ContactQuery query
    ) {
        CustomerDTO customerDTO = getCustomerById(id);
        query.setOrganizationId(customerDTO.getOrganizationId());
        query.setRelatedModuleId(Module.CUSTOMER.getId());
        query.setRelatedDataId(customerDTO.getId());
        Page<ContactDTO> page = contactQueryService.getContacts(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 创建客户联系人
     *
     * @param command
     * @return
     */
    @PostMapping("/{id}/contacts")
    public ResponseEntity<Object> createContact(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CreateContactCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        CustomerDTO customerDTO = getCustomerById(id);
        command.setIdentity(sessionContext.getEmployee(customerDTO.getOrganizationId()));
        command.setOrganizationId(customerDTO.getOrganizationId());
        List<RelatedModuleDataDTO> moduleDataDTOS = new ArrayList<>();
        RelatedModuleDataDTO moduleDataDTO = new RelatedModuleDataDTO(Module.CUSTOMER.getId(), customerDTO.getId());
        moduleDataDTOS.add(moduleDataDTO);
        command.setRelatedModuleData(moduleDataDTOS);

        contactCommandService.createContact(command);

        return ResponseUtils.ok("联系人创建成功").build();
    }

    /**
     * 获取客户营业执照列表
     *
     * @return
     */
    @GetMapping("/{id}/business-licenses")
    public ResponseEntity<List<BusinessLicenseDTO>> getBusinessLicenses(
            @PathVariable(value = "id") Long id,
            BusinessLicenseQuery query
    ) {
        CustomerDTO customerDTO = getCustomerById(id);
        query.setOrganizationId(customerDTO.getOrganizationId());
        query.setRelatedModuleId(Module.CUSTOMER.getId());
        query.setRelatedDataId(customerDTO.getId());
        Page<BusinessLicenseDTO> page = businessLicenseQueryService.getBusinessLicenses(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 创建客户营业执照
     *
     * @param command
     * @return
     */
    @PostMapping("/{id}/business-licenses")
    public ResponseEntity<Object> createBusinessLicense(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CreateBusinessLicenseCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        CustomerDTO customerDTO = getCustomerById(id);
        command.setIdentity(sessionContext.getEmployee(customerDTO.getOrganizationId()));
        command.setOrganizationId(customerDTO.getOrganizationId());
        List<RelatedModuleDataDTO> moduleDataDTOS = new ArrayList<>();
        RelatedModuleDataDTO moduleDataDTO = new RelatedModuleDataDTO(Module.CUSTOMER.getId(), customerDTO.getId());
        moduleDataDTOS.add(moduleDataDTO);
        command.setRelatedModuleData(moduleDataDTOS);
        businessLicenseCommandService.createBusinessLicense(command);

        return ResponseUtils.ok("营业执照创建成功").build();
    }

    /**
     * 获取客户开户行列表
     *
     * @return
     */
    @GetMapping("/{id}/open-banks")
    public ResponseEntity<List<OpenBankDTO>> getOpenBanks(
            @PathVariable(value = "id") Long id,
            OpenBankQuery query
    ) {
        CustomerDTO customerDTO = getCustomerById(id);
        query.setOrganizationId(customerDTO.getOrganizationId());
        query.setRelatedModuleId(Module.CUSTOMER.getId());
        query.setRelatedDataId(customerDTO.getId());
        Page<OpenBankDTO> page = openBankQueryService.getOpenBanks(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 创建客户开户行
     *
     * @param command
     * @return
     */
    @PostMapping("/{id}/open-banks")
    public ResponseEntity<Object> createOpenBank(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CreateOpenBankCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        CustomerDTO customerDTO = getCustomerById(id);
        command.setIdentity(sessionContext.getEmployee(customerDTO.getOrganizationId()));
        command.setOrganizationId(customerDTO.getOrganizationId());
        List<RelatedModuleDataDTO> moduleDataDTOS = new ArrayList<>();
        RelatedModuleDataDTO moduleDataDTO = new RelatedModuleDataDTO(Module.CUSTOMER.getId(), customerDTO.getId());
        moduleDataDTOS.add(moduleDataDTO);
        command.setRelatedModuleData(moduleDataDTOS);
        openBankCommandService.createOpenBank(command);

        return ResponseUtils.ok("开户行创建成功").build();
    }

    /**
     * 根据id获取客户
     *
     * @param id
     * @return
     */
    private CustomerDTO getCustomerById(Long id) {
        CustomerDTO customerDTO = customerQueryService.getCustomerById(id);
        if (customerDTO == null) {
            throw new NotFoundException("未找到指定的客户");
        }
        return customerDTO;
    }
}