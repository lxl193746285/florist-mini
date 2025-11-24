package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.dto.BusinessLicenseDTO;
import com.qy.crm.customer.app.application.query.BusinessLicenseQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

/**
 * 营业执照查询服务
 *
 * @author legendjw
 */
public interface BusinessLicenseQueryService {
    /**
     * 查询客户营业执照
     *
     * @param query
     * @param identity
     * @return
     */
    Page<BusinessLicenseDTO> getBusinessLicenses(BusinessLicenseQuery query, Identity identity);

    /**
     * 根据关联模块获取营业执照
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    BusinessLicenseDTO getBusinessLicenseByRelatedModule(String relatedModuleId, Long relatedDataId);

    /**
     * 根据ID查询客户营业执照
     *
     * @param id
     * @return
     */
    BusinessLicenseDTO getBusinessLicenseById(Long id, Identity identity);

    /**
     * 根据ID查询客户营业执照
     *
     * @param id
     * @return
     */
    BusinessLicenseDTO getBusinessLicenseById(Long id);
}