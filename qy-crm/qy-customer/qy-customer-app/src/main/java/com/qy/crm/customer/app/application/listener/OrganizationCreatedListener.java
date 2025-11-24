package com.qy.crm.customer.app.application.listener;

import com.qy.crm.customer.app.application.service.CustomerCommandService;
import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.api.dto.OrganizationDTO;
import com.qy.organization.api.enums.OrganizationSource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 组织消息监听
 *
 * @author legendjw
 */
@Component
public class OrganizationCreatedListener {
    private OrganizationClient organizationClient;
    private EmployeeClient employeeClient;
    private CustomerCommandService customerCommandService;

    public OrganizationCreatedListener(OrganizationClient organizationClient, EmployeeClient employeeClient, CustomerCommandService customerCommandService) {
        this.organizationClient = organizationClient;
        this.employeeClient = employeeClient;
        this.customerCommandService = customerCommandService;
    }

    /**
     * 创建组织对应的客户
     *
     * @param event
     */
    @Async
    @EventListener
    public void createOrganizationCustomer(OrganizationCreatedEvent event) {
        if (event.getCreateSource().equals(OrganizationSource.REGISTER.getId())) {
            OrganizationDTO organizationDTO = organizationClient.getOrganizationById(event.getOrganizationId());
            EmployeeBasicDTO superAdmin = employeeClient.getOrganizationCreator(organizationDTO.getId());
            customerCommandService.createOrganizationCustomer(organizationDTO, superAdmin);
        }
    }
}
