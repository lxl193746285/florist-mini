package com.qy.customer.api.client;

import com.qy.customer.api.command.CreateBusinessLicenseCommand;
import com.qy.customer.api.command.DeleteRelatedDataCommand;
import com.qy.customer.api.command.SaveBusinessLicenseCommand;
import com.qy.customer.api.command.UpdateBusinessLicenseCommand;
import com.qy.customer.api.dto.BusinessLicenseDTO;
import com.qy.customer.api.dto.BusinessLicenseIdDTO;
import com.qy.customer.api.query.BusinessLicenseQuery;
import com.qy.feign.config.FeignTokenRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 营业执照管理接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-crm-customer", contextId = "qy-crm-customer-business-license-manage", configuration = FeignTokenRequestInterceptor.class)
public interface BusinessLicenseManageClient {
    /**
     * 获取营业执照列表
     *
     * @return
     */
    @GetMapping("/v4/crm/customer/business-licenses")
    List<BusinessLicenseDTO> getBusinessLicenses(@SpringQueryMap BusinessLicenseQuery query);

    /**
     * 获取单个营业执照
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/crm/customer/business-licenses/{id}")
    BusinessLicenseDTO getBusinessLicense(
            @PathVariable(value = "id") Long id
    );

    /**
     * 创建单个营业执照
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/business-licenses")
    BusinessLicenseIdDTO createBusinessLicense(
            @Valid @RequestBody CreateBusinessLicenseCommand command
    );

    /**
     * 修改单个营业执照
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/crm/customer/business-licenses/{id}")
    void updateBusinessLicense(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdateBusinessLicenseCommand command
    );

    /**
     * 保存一个营业执照
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/business-licenses/save")
    public ResponseEntity<BusinessLicenseIdDTO> saveBusinessLicense(
            @Valid @RequestBody SaveBusinessLicenseCommand command
    );

    /**
     * 删除单个营业执照
     *
     * @param id
     */
    @DeleteMapping("/v4/crm/customer/business-licenses/{id}")
    void deleteBusinessLicense(
            @PathVariable(value = "id") Long id
    );

    /**
     * 删除关联信息的营业执照
     *
     * @param command
     */
    @DeleteMapping("/v4/crm/customer/business-licenses/by-related-data")
    void deleteByRelatedData(@SpringQueryMap DeleteRelatedDataCommand command);
}
