package com.qy.customer.api.client;

import com.qy.customer.api.command.SaveBusinessLicenseCommand;
import com.qy.customer.api.dto.BusinessLicenseDTO;
import com.qy.customer.api.dto.BusinessLicenseIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 营业执照管理接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-crm-customer", contextId = "qy-crm-customer-business-license")
public interface BusinessLicenseClient {
    /**
     * 根据关联模块获取营业执照
     *
     * @return
     */
    @GetMapping("/v4/api/crm/customer/business-licenses/by-related-module")
    BusinessLicenseDTO getBusinessLicenseByRelatedModule(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    );

    /**
     * 保存一个营业执照
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/crm/customer/business-licenses/save")
    BusinessLicenseIdDTO saveBusinessLicense(
            @Valid @RequestBody SaveBusinessLicenseCommand command
    );
}
