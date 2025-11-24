package com.qy.customer.api.client;

import com.qy.customer.api.command.CreateCustomerCommand;
import com.qy.customer.api.command.CustomerOpenAccountCommand;
import com.qy.customer.api.command.UpdateCustomerCommand;
import com.qy.customer.api.dto.CustomerDTO;
import com.qy.customer.api.dto.CustomerIdDTO;
import com.qy.feign.config.FeignTokenRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 客户管理接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-crm-customer", contextId = "qy-crm-customer-manage", configuration = FeignTokenRequestInterceptor.class)
public interface CustomerManageClient {
    /**
     * 获取单个客户
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/crm/customer/customers/{id}")
    CustomerDTO getCustomer(
            @PathVariable(value = "id") Long id
    );

    /**
     * 创建单个客户
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/customers")
    CustomerIdDTO createCustomer(
            @Valid @RequestBody CreateCustomerCommand command
    );

    /**
     * 修改单个客户
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/crm/customer/customers/{id}")
    void updateCustomer(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdateCustomerCommand command
    );

    /**
     * 删除单个客户
     *
     * @param id
     */
    @DeleteMapping("/v4/crm/customer/customers/{id}")
    void deleteCustomer(
            @PathVariable(value = "id") Long id
    );

    /**
     * 客户开户
     *
     * @param id
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/customers/{id}/open-account")
    void openAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CustomerOpenAccountCommand command
    );
}