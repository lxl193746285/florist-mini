package com.qy.customer.api.client;

import com.qy.customer.api.command.SaveBusinessLicenseCommand;
import com.qy.customer.api.dto.BusinessLicenseDTO;

/**
 * 营业执照客户端占位接口.
 */
public interface BusinessLicenseClient {

    /**
     * 保存营业执照信息
     */
    default void saveBusinessLicense(SaveBusinessLicenseCommand command) {
        // no-op placeholder
    }

    /**
     * 根据关联模块查询营业执照
     */
    default BusinessLicenseDTO getBusinessLicenseByRelatedModule(Integer relatedModuleId, Long relatedDataId) {
        return null;
    }
}
