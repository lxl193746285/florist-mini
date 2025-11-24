package com.qy.crm.customer.app.interfaces.web;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.ContactDTO;
import com.qy.crm.customer.app.application.dto.ContactIdDTO;
import com.qy.crm.customer.app.application.query.ContactQuery;
import com.qy.crm.customer.app.application.service.ContactCommandService;
import com.qy.crm.customer.app.application.service.ContactQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 联系人
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/crm/customer/contacts")
public class ContactController {
    private OrganizationSessionContext sessionContext;
    private ContactCommandService contactCommandService;
    private ContactQueryService contactQueryService;

    public ContactController(OrganizationSessionContext sessionContext, ContactCommandService contactCommandService, ContactQueryService contactQueryService) {
        this.sessionContext = sessionContext;
        this.contactCommandService = contactCommandService;
        this.contactQueryService = contactQueryService;
    }

    /**
     * 获取联系人列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getContacts(ContactQuery query) {
        Page<ContactDTO> page = contactQueryService.getContacts(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个联系人
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContact(
        @PathVariable(value = "id") Long id
    ) {
        ContactDTO contactDTO = contactQueryService.getContactById(id);
        if (contactDTO == null) {
            throw new NotFoundException("未找到指定的联系人");
        }
        return ResponseUtils.ok().body(contactDTO);
    }

    /**
     * 创建单个联系人
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<ContactIdDTO> createContact(
            @Valid @RequestBody CreateContactCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        ContactIdDTO contactIdDTO = contactCommandService.createContact(command);

        return ResponseUtils.ok("联系人创建成功").body(contactIdDTO);
    }

    /**
     * 修改单个联系人
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateContact(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateContactCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        ContactDTO contactDTO = getContactById(id);

        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(contactDTO.getOrganizationId()));
        contactCommandService.updateContact(command);

        return ResponseUtils.ok("联系人修改成功").build();
    }

    /**
     * 批量保存联系人命令
     *
     * @param command
     * @return
     */
    @PostMapping("/batch-save")
    public ResponseEntity<List<Long>> batchSaveContact(
            @Valid @RequestBody BatchSaveContactCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        List<Long> ids = contactCommandService.batchSaveContact(command);

        return ResponseUtils.ok("联系人创建成功").body(ids);
    }

    /**
     * 删除单个联系人
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteContact(
        @PathVariable(value = "id") Long id
    ) {
        ContactDTO contactDTO = getContactById(id);
        DeleteContactCommand command = new DeleteContactCommand();
        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(contactDTO.getOrganizationId()));
        contactCommandService.deleteContact(command);

        return ResponseUtils.noContent("删除联系人成功").build();
    }

    /**
     * 删除关联信息的联系人
     *
     * @param command
     */
    @DeleteMapping("/by-related-data")
    public ResponseEntity<Object> deleteByRelatedData(
            DeleteRelatedDataCommand command
    ) {
        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        contactCommandService.deleteRelatedContact(command);

        return ResponseUtils.noContent("删除联系人成功").build();
    }

    /**
     * 根据id获取联系人
     *
     * @param id
     * @return
     */
    private ContactDTO getContactById(Long id) {
        ContactDTO contactDTO = contactQueryService.getContactById(id);
        if (contactDTO == null) {
            throw new NotFoundException("未找到指定的联系人");
        }
        return contactDTO;
    }
}

