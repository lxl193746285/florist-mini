package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.dto.CustomerBasicDTO;
import com.qy.crm.customer.app.application.dto.CustomerDTO;
import com.qy.crm.customer.app.application.dto.CustomerDetailDTO;
import com.qy.crm.customer.app.application.dto.CustomerOpenAccountInfoDTO;
import com.qy.crm.customer.app.application.query.CustomerQuery;
import com.qy.member.api.dto.OpenMemberInfoDTO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

/**
 * 客户查询服务
 *
 * @author legendjw
 */
public interface CustomerQueryService {
    /**
     * 查询客户
     *
     * @param query
     * @param identity
     * @return
     */
    Page<CustomerDTO> getCustomers(CustomerQuery query, Identity identity);

    /**
     * 根据ID查询客户
     *
     * @param id
     * @return
     */
    CustomerDetailDTO getCustomerById(Long id, Identity identity);

    /**
     * 根据ID查询客户
     *
     * @param id
     * @return
     */
    CustomerDTO getCustomerById(Long id);

    /**
     * 根据ID查询客户基本信息
     *
     * @param id
     * @return
     */
    CustomerBasicDTO getBasicCustomerById(Long id);

    /**
     * 获取客户开户的信息
     *
     * @param id
     * @return
     */
    CustomerOpenAccountInfoDTO getOpenAccountInfo(Long id);

    /**
     * 获取开通会员信息
     *
     * @param id
     * @return
     */
    OpenMemberInfoDTO getOpenMemberInfo(Long id);
}