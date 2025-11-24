package com.qy.crm.customer.app.application.service.impl;

import com.qy.crm.customer.app.application.assembler.CustomerAssembler;
import com.qy.crm.customer.app.application.dto.CustomerBasicDTO;
import com.qy.crm.customer.app.application.dto.CustomerDTO;
import com.qy.crm.customer.app.application.dto.CustomerDetailDTO;
import com.qy.crm.customer.app.application.dto.CustomerOpenAccountInfoDTO;
import com.qy.crm.customer.app.application.enums.Module;
import com.qy.crm.customer.app.application.query.CustomerQuery;
import com.qy.crm.customer.app.application.security.CustomerPermission;
import com.qy.crm.customer.app.application.service.ContactQueryService;
import com.qy.crm.customer.app.application.service.CustomerQueryService;
import com.qy.crm.customer.app.infrastructure.persistence.CustomerDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.CustomerDO;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.OpenMemberInfoDTO;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OpenAccountInfoDTO;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户查询服务实现
 *
 * @author legendjw
 */
@Service
public class CustomerQueryServiceImpl implements CustomerQueryService {
    private CustomerAssembler customerAssembler;
    private CustomerDataRepository customerDataRepository;
    private CustomerPermission customerPermission;
    private ContactQueryService contactQueryService;
    private OrganizationClient organizationClient;
    private MemberClient memberClient;

    public CustomerQueryServiceImpl(CustomerAssembler customerAssembler, CustomerDataRepository customerDataRepository, CustomerPermission customerPermission, ContactQueryService contactQueryService, OrganizationClient organizationClient, MemberClient memberClient) {
        this.customerAssembler = customerAssembler;
        this.customerDataRepository = customerDataRepository;
        this.customerPermission = customerPermission;
        this.contactQueryService = contactQueryService;
        this.organizationClient = organizationClient;
        this.memberClient = memberClient;
    }

    @Override
    public Page<CustomerDTO> getCustomers(CustomerQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = customerPermission.getFilterQuery(identity, CustomerPermission.LIST);
        Page<CustomerDO> customerDOPage = customerDataRepository.findByQuery(query, filterQuery);
        List<CustomerDO> customerDOS = customerDOPage.getRecords();
        List<OrganizationBasicDTO> organizationBasicDTOS = customerDOS.isEmpty() ? new ArrayList<>() : organizationClient.getBasicOrganizationsByIds(customerDOS.stream().map(CustomerDO::getOrganizationId).collect(Collectors.toList()));
        Page<CustomerDTO> customerDTOPage = customerDOPage.map(customer -> customerAssembler.toDTO(customer, organizationBasicDTOS, identity));
        return customerDTOPage;
    }

    @Override
    public CustomerDetailDTO getCustomerById(Long id, Identity identity) {
        CustomerDO customerDO = customerDataRepository.findById(id);
        if (customerDO == null) { return null; }
        List<OrganizationBasicDTO> organizationBasicDTOS = organizationClient.getBasicOrganizationsByIds(Arrays.asList(customerDO.getOrganizationId()));
        return customerAssembler.toDetailDTO(customerDO, organizationBasicDTOS, identity);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        CustomerDO customerDO = customerDataRepository.findById(id);
        return customerAssembler.toDTO(customerDO, null, null);
    }

    @Override
    public CustomerBasicDTO getBasicCustomerById(Long id) {
        CustomerDO customerDO = customerDataRepository.findById(id);
        return customerAssembler.toBasicDTO(customerDO);
    }

    @Override
    public CustomerOpenAccountInfoDTO getOpenAccountInfo(Long id) {
        CustomerDO customerDO = customerDataRepository.findById(id);
        if (customerDO == null) {
            throw new NotFoundException("未找到指定客户");
        }
        if (customerDO.getIsOpenAccount().intValue() != 1) {
            throw new ValidationException("此客户尚未开户");
        }
        OpenAccountInfoDTO openAccountInfoDTO = organizationClient.getOpenAccountInfo(customerDO.getOpenAccountId());
        CustomerOpenAccountInfoDTO customerOpenAccountInfoDTO = new CustomerOpenAccountInfoDTO();
        customerOpenAccountInfoDTO.setOrganization(openAccountInfoDTO.getOrganization());
        customerOpenAccountInfoDTO.setRoles(openAccountInfoDTO.getRoles());
        customerOpenAccountInfoDTO.setSuperAdmin(contactQueryService.getSuperAdminContact(Module.CUSTOMER.getId(), id));
        return customerOpenAccountInfoDTO;
    }

    @Override
    public OpenMemberInfoDTO getOpenMemberInfo(Long id) {
        CustomerDO customerDO = customerDataRepository.findById(id);
        if (customerDO == null) {
            throw new NotFoundException("未找到指定客户");
        }
        if (customerDO.getIsOpenMember().intValue() != 1) {
            throw new ValidationException("此客户尚未开通会员");
        }
        return memberClient.getOpenMemberInfo(customerDO.getOpenMemberId());
    }
}