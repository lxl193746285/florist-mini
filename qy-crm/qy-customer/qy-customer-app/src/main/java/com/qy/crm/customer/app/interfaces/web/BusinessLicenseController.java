package com.qy.crm.customer.app.interfaces.web;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.BusinessLicenseDTO;
import com.qy.crm.customer.app.application.dto.BusinessLicenseIdDTO;
import com.qy.crm.customer.app.application.query.BusinessLicenseQuery;
import com.qy.crm.customer.app.application.service.BusinessLicenseCommandService;
import com.qy.crm.customer.app.application.service.BusinessLicenseQueryService;
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
 * 营业执照
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/crm/customer/business-licenses")
public class BusinessLicenseController {
    private OrganizationSessionContext sessionContext;
    private BusinessLicenseCommandService businessLicenseCommandService;
    private BusinessLicenseQueryService businessLicenseQueryService;

    public BusinessLicenseController(OrganizationSessionContext sessionContext, BusinessLicenseCommandService businessLicenseCommandService, BusinessLicenseQueryService businessLicenseQueryService) {
        this.sessionContext = sessionContext;
        this.businessLicenseCommandService = businessLicenseCommandService;
        this.businessLicenseQueryService = businessLicenseQueryService;
    }

    /**
     * 获取营业执照列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<BusinessLicenseDTO>> getBusinessLicenses(BusinessLicenseQuery query) {
        Page<BusinessLicenseDTO> page = businessLicenseQueryService.getBusinessLicenses(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个营业执照
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<BusinessLicenseDTO> getBusinessLicense(
        @PathVariable(value = "id") Long id
    ) {
        BusinessLicenseDTO businessLicenseDTO = businessLicenseQueryService.getBusinessLicenseById(id, sessionContext.getUser());
        if (businessLicenseDTO == null) {
            throw new NotFoundException("未找到指定的营业执照");
        }
        return ResponseUtils.ok().body(businessLicenseDTO);
    }

    /**
     * 创建单个营业执照
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<BusinessLicenseIdDTO> createBusinessLicense(
            @Valid @RequestBody CreateBusinessLicenseCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        BusinessLicenseIdDTO businessLicenseIdDTO = businessLicenseCommandService.createBusinessLicense(command);

        return ResponseUtils.ok("营业执照创建成功").body(businessLicenseIdDTO);
    }

    /**
     * 修改单个营业执照
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBusinessLicense(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateBusinessLicenseCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        BusinessLicenseDTO businessLicenseDTO = getBusinessLicenseById(id);

        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(businessLicenseDTO.getOrganizationId()));
        businessLicenseCommandService.updateBusinessLicense(command);

        return ResponseUtils.ok("营业执照修改成功").build();
    }

    /**
     * 保存一个营业执照
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<BusinessLicenseIdDTO> saveBusinessLicense(
            @Valid @RequestBody SaveBusinessLicenseCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        BusinessLicenseIdDTO businessLicenseIdDTO = businessLicenseCommandService.saveBusinessLicense(command);

        return ResponseUtils.ok("营业执照保存成功").body(businessLicenseIdDTO);
    }

    /**
     * 删除单个营业执照
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBusinessLicense(
        @PathVariable(value = "id") Long id
    ) {
        BusinessLicenseDTO businessLicenseDTO = getBusinessLicenseById(id);
        DeleteBusinessLicenseCommand command = new DeleteBusinessLicenseCommand();
        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(businessLicenseDTO.getOrganizationId()));
        businessLicenseCommandService.deleteBusinessLicense(command);

        return ResponseUtils.noContent("删除营业执照成功").build();
    }

    /**
     * 删除关联信息的营业执照
     *
     * @param command
     */
    @DeleteMapping("/by-related-data")
    public ResponseEntity<Object> deleteByRelatedData(
            DeleteRelatedDataCommand command
    ) {
        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        businessLicenseCommandService.deleteRelatedBusinessLicense(command);

        return ResponseUtils.noContent("删除营业执照成功").build();
    }

    /**
     * 根据id获取营业执照
     *
     * @param id
     * @return
     */
    private BusinessLicenseDTO getBusinessLicenseById(Long id) {
        BusinessLicenseDTO businessLicenseDTO = businessLicenseQueryService.getBusinessLicenseById(id);
        if (businessLicenseDTO == null) {
            throw new NotFoundException("未找到指定的营业执照");
        }
        return businessLicenseDTO;
    }
}

