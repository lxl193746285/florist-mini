package com.qy.customer.api.client;

import com.qy.customer.api.command.BatchSaveContactCommand;
import com.qy.customer.api.command.CreateContactCommand;
import com.qy.customer.api.command.DeleteRelatedDataCommand;
import com.qy.customer.api.command.UpdateContactCommand;
import com.qy.customer.api.dto.ContactDTO;
import com.qy.customer.api.dto.ContactIdDTO;
import com.qy.customer.api.query.ContactQuery;
import com.qy.feign.config.FeignTokenRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 联系人管理接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-crm-customer", contextId = "qy-crm-customer-contact-manage", configuration = FeignTokenRequestInterceptor.class)
public interface ContactManageClient {
    /**
     * 获取联系人列表
     *
     * @return
     */
    @GetMapping("/v4/crm/customer/contacts")
    List<ContactDTO> getContacts(@SpringQueryMap ContactQuery query);

    /**
     * 获取单个联系人
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/crm/customer/contacts/{id}")
    ContactDTO getContact(
            @PathVariable(value = "id") Long id
    );

    /**
     * 创建单个联系人
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/contacts")
    ContactIdDTO createContact(
            @Valid @RequestBody CreateContactCommand command
    );

    /**
     * 修改单个联系人
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/crm/customer/contacts/{id}")
    void updateContact(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdateContactCommand command
    );

    /**
     * 批量保存联系人命令
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/contacts/batch-save")
    List<Long> batchSaveContact(
            @Valid @RequestBody BatchSaveContactCommand command
    );

    /**
     * 删除单个联系人
     *
     * @param id
     */
    @DeleteMapping("/v4/crm/customer/contacts/{id}")
    void deleteContact(
            @PathVariable(value = "id") Long id
    );

    /**
     * 删除关联信息的联系人
     *
     * @param command
     */
    @DeleteMapping("/v4/crm/customer/contacts/by-related-data")
    public ResponseEntity<Object> deleteByRelatedData(@SpringQueryMap DeleteRelatedDataCommand command);
}
