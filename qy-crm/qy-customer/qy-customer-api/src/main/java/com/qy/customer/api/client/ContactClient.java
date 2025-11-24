package com.qy.customer.api.client;

import com.qy.customer.api.command.BatchSaveContactCommand;
import com.qy.customer.api.command.SetContactIsSuperAdminCommand;
import com.qy.customer.api.dto.ContactDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 联系人管理接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-crm-customer", contextId = "qy-crm-customer-contact")
public interface ContactClient {
    /**
     * 根据关联模块获取联系人
     *
     * @return
     */
    @GetMapping("/v4/api/crm/customer/contacts/by-related-module")
    List<ContactDTO> getContactsByRelatedModule(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    );

    /**
     * 根据关联模块获取下面设置的超管联系人
     *
     * @return
     */
    @GetMapping("/v4/api/crm/customer/contacts/super-admin-contact")
    ContactDTO getSuperAdminContact(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    );

    /**
     * 根据关联模块获取下面设置的超管联系人
     *
     * @return
     */
    @PostMapping("/v4/api/crm/customer/contacts/set-super-admin-contact")
    void setContactIsSuperAdmin(
            @Valid @RequestBody SetContactIsSuperAdminCommand command
    );

    /**
     * 批量保存联系人命令
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/crm/customer/contacts/batch-save")
    List<Long> batchSaveContact(
            @Valid @RequestBody BatchSaveContactCommand command
    );
}