package com.qy.crm.customer.app.application.service.impl;

import com.qy.crm.customer.app.application.assembler.OpenBankAssembler;
import com.qy.crm.customer.app.application.dto.OpenBankDTO;
import com.qy.crm.customer.app.application.query.OpenBankQuery;
import com.qy.crm.customer.app.application.security.OpenBankPermission;
import com.qy.crm.customer.app.application.service.OpenBankQueryService;
import com.qy.crm.customer.app.infrastructure.persistence.OpenBankDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.OpenBankDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户开户行查询服务实现
 *
 * @author legendjw
 */
@Service
public class OpenBankQueryServiceImpl implements OpenBankQueryService {
    private OpenBankAssembler openBankAssembler;
    private OpenBankDataRepository openBankDataRepository;
    private OpenBankPermission openBankPermission;

    public OpenBankQueryServiceImpl(OpenBankAssembler openBankAssembler, OpenBankDataRepository openBankDataRepository, OpenBankPermission openBankPermission) {
        this.openBankAssembler = openBankAssembler;
        this.openBankDataRepository = openBankDataRepository;
        this.openBankPermission = openBankPermission;
    }

    @Override
    public Page<OpenBankDTO> getOpenBanks(OpenBankQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = openBankPermission.getFilterQuery(identity, OpenBankPermission.LIST);
        Page<OpenBankDO> openBankDOPage = openBankDataRepository.findByQuery(query, filterQuery);
        Page<OpenBankDTO> openBankDTOPage = openBankDOPage.map(openBank -> openBankAssembler.toDTO(openBank, identity));
        return openBankDTOPage;
    }

    @Override
    public List<OpenBankDTO> getOpenBanksByRelatedModule(String relatedModuleId, Long relatedDataId) {
        List<Long> ids = openBankDataRepository.getRelatedOpenBankIds(relatedModuleId, relatedDataId);
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<OpenBankDO> openBankDOS = openBankDataRepository.findByIds(ids);
        return openBankDOS.stream().map(o -> openBankAssembler.toDTO(o, null)).collect(Collectors.toList());
    }

    @Override
    public OpenBankDTO getOpenBankById(Long id, Identity identity) {
        OpenBankDO openBankDO = openBankDataRepository.findById(id);
        return openBankDO == null ? null : openBankAssembler.toDTO(openBankDO, identity);
    }

    @Override
    public OpenBankDTO getOpenBankById(Long id) {
        OpenBankDO openBankDO = openBankDataRepository.findById(id);
        return openBankDO == null ? null : openBankAssembler.toDTO(openBankDO);
    }
}
