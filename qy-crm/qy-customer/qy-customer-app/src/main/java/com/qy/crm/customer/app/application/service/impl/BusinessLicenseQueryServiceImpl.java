package com.qy.crm.customer.app.application.service.impl;

import com.qy.crm.customer.app.application.assembler.BusinessLicenseAssembler;
import com.qy.crm.customer.app.application.dto.BusinessLicenseDTO;
import com.qy.crm.customer.app.application.query.BusinessLicenseQuery;
import com.qy.crm.customer.app.application.security.BusinessLicensePermission;
import com.qy.crm.customer.app.application.service.BusinessLicenseQueryService;
import com.qy.crm.customer.app.infrastructure.persistence.BusinessLicenseDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.BusinessLicenseDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 营业执照查询服务实现
 *
 * @author legendjw
 */
@Service
public class BusinessLicenseQueryServiceImpl implements BusinessLicenseQueryService {
    private BusinessLicenseAssembler businessLicenseAssembler;
    private BusinessLicenseDataRepository businessLicenseDataRepository;
    private BusinessLicensePermission businessLicensePermission;

    public BusinessLicenseQueryServiceImpl(BusinessLicenseAssembler businessLicenseAssembler, BusinessLicenseDataRepository businessLicenseDataRepository, BusinessLicensePermission businessLicensePermission) {
        this.businessLicenseAssembler = businessLicenseAssembler;
        this.businessLicenseDataRepository = businessLicenseDataRepository;
        this.businessLicensePermission = businessLicensePermission;
    }

    @Override
    public Page<BusinessLicenseDTO> getBusinessLicenses(BusinessLicenseQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = businessLicensePermission.getFilterQuery(identity, BusinessLicensePermission.LIST);
        Page<BusinessLicenseDO> businessLicenseDOPage = businessLicenseDataRepository.findByQuery(query, filterQuery);
        Page<BusinessLicenseDTO> businessLicenseDTOPage = businessLicenseDOPage.map(businessLicense -> businessLicenseAssembler.toDTO(businessLicense, identity));
        return businessLicenseDTOPage;
    }

    @Override
    public BusinessLicenseDTO getBusinessLicenseByRelatedModule(String relatedModuleId, Long relatedDataId) {
        List<Long> ids = businessLicenseDataRepository.getRelatedBusinessLicenseIds(relatedModuleId, relatedDataId);
        if (ids.isEmpty()) {
            return null;
        }
        return getBusinessLicenseById(ids.get(0));
    }

    @Override
    public BusinessLicenseDTO getBusinessLicenseById(Long id, Identity identity) {
        BusinessLicenseDO businessLicenseDO = businessLicenseDataRepository.findById(id);
        return businessLicenseAssembler.toDTO(businessLicenseDO, identity);
    }

    @Override
    public BusinessLicenseDTO getBusinessLicenseById(Long id) {
        BusinessLicenseDO businessLicenseDO = businessLicenseDataRepository.findById(id);
        return businessLicenseAssembler.toDTO(businessLicenseDO, null);
    }
}
