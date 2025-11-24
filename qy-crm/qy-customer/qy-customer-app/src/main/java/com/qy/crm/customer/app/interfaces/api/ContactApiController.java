package com.qy.crm.customer.app.interfaces.api;

import com.qy.crm.customer.app.application.command.BatchSaveContactCommand;
import com.qy.crm.customer.app.application.command.SetContactIsSuperAdminCommand;
import com.qy.crm.customer.app.application.dto.ContactDTO;
import com.qy.crm.customer.app.application.service.ContactCommandService;
import com.qy.crm.customer.app.application.service.ContactQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 联系人内部接口
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/crm/customer/contacts")
public class ContactApiController {
    private OrganizationSessionContext sessionContext;
    private ContactCommandService contactCommandService;
    private ContactQueryService contactQueryService;

    public ContactApiController(OrganizationSessionContext sessionContext, ContactCommandService contactCommandService, ContactQueryService contactQueryService) {
        this.sessionContext = sessionContext;
        this.contactCommandService = contactCommandService;
        this.contactQueryService = contactQueryService;
    }

    /**
     * 根据关联模块获取联系人
     *
     * @return
     */
    @GetMapping("/by-related-module")
    public ResponseEntity<List<ContactDTO>> getContactsByRelatedModule(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    ) {
        return ResponseUtils.ok().body(contactQueryService.getContactsByRelatedModule(relatedModuleId, relatedDataId));
    }

    /**
     * 根据关联模块获取下面设置的超管联系人
     *
     * @return
     */
    @GetMapping("/super-admin-contact")
    public ResponseEntity<ContactDTO> getSuperAdminContact(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    ) {
        return ResponseUtils.ok().body(contactQueryService.getSuperAdminContact(relatedModuleId, relatedDataId));
    }

    /**
     * 根据关联模块获取下面设置的超管联系人
     *
     * @return
     */
    @PostMapping("/set-super-admin-contact")
    public ResponseEntity<Object> setContactIsSuperAdmin(
            @RequestBody SetContactIsSuperAdminCommand command
    ) {
        contactCommandService.setContactIsSuperAdmin(command);
        return ResponseUtils.ok("设置超管成功").build();
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

        List<Long> ids = contactCommandService.batchSaveContact(command);

        return ResponseUtils.ok("联系人创建成功").body(ids);
    }
}

