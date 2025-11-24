package com.qy.crm.customer.app.interfaces.api;

import com.qy.crm.customer.app.application.command.SaveBusinessLicenseCommand;
import com.qy.crm.customer.app.application.dto.BusinessLicenseDTO;
import com.qy.crm.customer.app.application.dto.BusinessLicenseIdDTO;
import com.qy.crm.customer.app.application.service.BusinessLicenseCommandService;
import com.qy.crm.customer.app.application.service.BusinessLicenseQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 营业执照内部接口
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/crm/customer/business-licenses")
public class BusinessLicenseApiController {
    private OrganizationSessionContext sessionContext;
    private BusinessLicenseCommandService businessLicenseCommandService;
    private BusinessLicenseQueryService businessLicenseQueryService;

    public BusinessLicenseApiController(OrganizationSessionContext sessionContext, BusinessLicenseCommandService businessLicenseCommandService, BusinessLicenseQueryService businessLicenseQueryService) {
        this.sessionContext = sessionContext;
        this.businessLicenseCommandService = businessLicenseCommandService;
        this.businessLicenseQueryService = businessLicenseQueryService;
    }

    /**
     * 根据关联模块获取营业执照
     *
     * @return
     */
    @GetMapping("/by-related-module")
    public ResponseEntity<BusinessLicenseDTO> getBusinessLicenseByRelatedModule(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    ) {
        return ResponseUtils.ok().body(businessLicenseQueryService.getBusinessLicenseByRelatedModule(relatedModuleId, relatedDataId));
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

        BusinessLicenseIdDTO businessLicenseIdDTO = businessLicenseCommandService.saveBusinessLicense(command);

        return ResponseUtils.ok("营业执照保存成功").body(businessLicenseIdDTO);
    }
}

