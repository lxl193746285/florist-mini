package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.CustomerIdDTO;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.api.dto.OrganizationDTO;
import com.qy.security.session.EmployeeIdentity;

/**
 * 客户命令服务
 *
 * @author legendjw
 */
public interface CustomerCommandService {
    /**
     * 创建客户命令
     *
     * @param command
     * @return
     */
    CustomerIdDTO createCustomer(CreateCustomerCommand command);

    /**
     * 编辑客户命令
     *
     * @param command
     */
    void updateCustomer(UpdateCustomerCommand command);

    /**
     * 删除客户命令
     *
     * @param command
     */
    void deleteCustomer(DeleteCustomerCommand command);

    /**
     * 客户开户
     *
     * @param command
     */
    void openAccount(CustomerOpenAccountCommand command);

    /**
     * 客户修改开户信息
     *
     * @param command
     */
    void updateOpenAccount(CustomerModifyOpenAccountCommand command);

    /**
     * 客户开通会员
     *
     * @param command
     */
    void openMember(CustomerOpenMemberCommand command);

    /**
     * 客户修改会员
     *
     * @param command
     */
    void updateOpenMember(CustomerUpdateOpenMemberCommand command);

    /**
     * 审核客户
     *
     * @param command
     * @param identity
     */
    void auditCustomer(AuditCustomerCommand command, EmployeeIdentity identity);

    /**
     * 提交审核客户
     *
     * @param command
     * @param identity
     */
    void submitAuditCustomer(SubmitAuditCustomerCommand command, EmployeeIdentity identity);

    /**
     * 创建用户对应的客户
     *
     * @param userBasicDTO
     */
    void createUserCustomer(UserBasicDTO userBasicDTO);

    /**
     * 创建组织对应的客户
     *
     * @param organizationDTO
     * @param superAdmin
     * @param
     */
    void createOrganizationCustomer(OrganizationDTO organizationDTO, EmployeeBasicDTO superAdmin);
}
