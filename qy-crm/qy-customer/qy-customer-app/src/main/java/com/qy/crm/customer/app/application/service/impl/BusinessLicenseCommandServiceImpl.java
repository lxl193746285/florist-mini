package com.qy.crm.customer.app.application.service.impl;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.BusinessLicenseDTO;
import com.qy.crm.customer.app.application.dto.BusinessLicenseIdDTO;
import com.qy.crm.customer.app.application.dto.ContactBasicDTO;
import com.qy.crm.customer.app.application.dto.RelatedModuleDataDTO;
import com.qy.crm.customer.app.application.service.BusinessLicenseCommandService;
import com.qy.crm.customer.app.application.service.BusinessLicenseQueryService;
import com.qy.crm.customer.app.application.service.ContactQueryService;
import com.qy.crm.customer.app.infrastructure.persistence.BusinessLicenseDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.BusinessLicenseDO;
import com.qy.crm.customer.app.application.command.*;
import com.qy.rest.exception.NotFoundException;
import com.qy.security.session.EmployeeIdentity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 营业执照命令实现
 *
 * @author legendjw
 */
@Service
public class BusinessLicenseCommandServiceImpl implements BusinessLicenseCommandService {
    private BusinessLicenseDataRepository businessLicenseDataRepository;
    private ContactQueryService contactQueryService;
    private BusinessLicenseQueryService businessLicenseQueryService;

    public BusinessLicenseCommandServiceImpl(BusinessLicenseDataRepository businessLicenseDataRepository, ContactQueryService contactQueryService, BusinessLicenseQueryService businessLicenseQueryService) {
        this.businessLicenseDataRepository = businessLicenseDataRepository;
        this.contactQueryService = contactQueryService;
        this.businessLicenseQueryService = businessLicenseQueryService;
    }

    @Override
    @Transactional
    public BusinessLicenseIdDTO createBusinessLicense(CreateBusinessLicenseCommand command) {
        EmployeeIdentity identity = command.getIdentity();

        BusinessLicenseDO businessLicenseDO = new BusinessLicenseDO();
        BeanUtils.copyProperties(command, businessLicenseDO, "identity");
        fillFormRelatedData(businessLicenseDO);
        businessLicenseDO.setOrganizationId(identity != null ? identity.getOrganizationId() : command.getOrganizationId());
        businessLicenseDO.setCreatorId(identity != null ? identity.getId() : 0);
        businessLicenseDO.setCreatorName(identity != null ? identity.getName() : "");
        businessLicenseDO.setCreateTime(LocalDateTime.now());
        businessLicenseDataRepository.save(businessLicenseDO);

        //保存关联关系
        if (command.getRelatedModuleData() != null && !command.getRelatedModuleData().isEmpty()) {
            for (RelatedModuleDataDTO relatedModuleDatum : command.getRelatedModuleData()) {
                businessLicenseDataRepository.saveRelation(relatedModuleDatum.getRelatedModuleId(), relatedModuleDatum.getRelatedDataId(), businessLicenseDO.getId());
            }
        }
        return new BusinessLicenseIdDTO(businessLicenseDO.getId());
    }

    @Override
    @Transactional
    public void updateBusinessLicense(UpdateBusinessLicenseCommand command) {
        BusinessLicenseDO businessLicenseDO = businessLicenseDataRepository.findById(command.getId());
        if (businessLicenseDO == null) {
            throw new NotFoundException("未找到指定的营业执照");
        }
        EmployeeIdentity identity = command.getIdentity();

        BeanUtils.copyProperties(command, businessLicenseDO, "identity");
        fillFormRelatedData(businessLicenseDO);
        businessLicenseDO.setUpdatorId(identity != null ? identity.getId() : 0);
        businessLicenseDO.setUpdatorName(identity != null ? identity.getName() : "");
        businessLicenseDO.setUpdateTime(LocalDateTime.now());
        businessLicenseDataRepository.save(businessLicenseDO);
    }

    @Override
    @Transactional
    public BusinessLicenseIdDTO saveBusinessLicense(SaveBusinessLicenseCommand command) {
        RelatedModuleDataDTO relatedModuleDataDTO = command.getRelatedModuleData();
        BusinessLicenseIdDTO businessLicenseIdDTO = null;
        //保存营业执照
        BusinessLicenseDTO businessLicenseDTO = businessLicenseQueryService.getBusinessLicenseByRelatedModule(relatedModuleDataDTO.getRelatedModuleId(), relatedModuleDataDTO.getRelatedDataId());
        if (businessLicenseDTO == null) {
            CreateBusinessLicenseCommand createBusinessLicenseCommand = new CreateBusinessLicenseCommand();
            BeanUtils.copyProperties(command.getBusinessLicense(), createBusinessLicenseCommand);
            createBusinessLicenseCommand.setIdentity(command.getIdentity());
            createBusinessLicenseCommand.setOrganizationId(command.getOrganizationId());
            createBusinessLicenseCommand.setLegalPersonId(command.getLegalPersonId() != null ? command.getLegalPersonId() : 0L);
            createBusinessLicenseCommand.setLegalPersonName(command.getLegalPersonName() != null ? command.getLegalPersonName() : "");
            List<RelatedModuleDataDTO> businessModuleDataDTOS = new ArrayList<>();
            businessModuleDataDTOS.add(new RelatedModuleDataDTO(relatedModuleDataDTO.getRelatedModuleId(), relatedModuleDataDTO.getRelatedDataId()));
            createBusinessLicenseCommand.setRelatedModuleData(businessModuleDataDTOS);
            businessLicenseIdDTO = createBusinessLicense(createBusinessLicenseCommand);
        }
        else {
            UpdateBusinessLicenseCommand updateBusinessLicenseCommand = new UpdateBusinessLicenseCommand();
            BeanUtils.copyProperties(command.getBusinessLicense(), updateBusinessLicenseCommand);
            updateBusinessLicenseCommand.setId(businessLicenseDTO.getId());
            updateBusinessLicenseCommand.setIdentity(command.getIdentity());
            updateBusinessLicenseCommand.setOrganizationId(command.getOrganizationId());
            updateBusinessLicenseCommand.setLegalPersonId(command.getLegalPersonId() != null ? command.getLegalPersonId() : 0L);
            updateBusinessLicenseCommand.setLegalPersonName(command.getLegalPersonName() != null ? command.getLegalPersonName() : "");
            updateBusinessLicense(updateBusinessLicenseCommand);
            businessLicenseIdDTO  = new BusinessLicenseIdDTO(businessLicenseDTO.getId());
        }
        return businessLicenseIdDTO;
    }

    @Override
    public void deleteRelatedBusinessLicense(DeleteRelatedDataCommand command) {
        BusinessLicenseDTO businessLicenseDTO = businessLicenseQueryService.getBusinessLicenseByRelatedModule(command.getRelatedModuleId(), command.getRelatedDataId());
        if (businessLicenseDTO != null) {
            DeleteBusinessLicenseCommand deleteBusinessLicenseCommand = new DeleteBusinessLicenseCommand();
            deleteBusinessLicenseCommand.setIdentity(command.getIdentity());
            deleteBusinessLicenseCommand.setId(businessLicenseDTO.getId());
            deleteBusinessLicense(deleteBusinessLicenseCommand);
        }
    }

    @Override
    public void deleteBusinessLicense(DeleteBusinessLicenseCommand command) {
        EmployeeIdentity identity = command.getIdentity();

        businessLicenseDataRepository.remove(command.getId(), identity);
        businessLicenseDataRepository.removeRelation(command.getId());
    }

    /**
     * 填充表单关联数据
     *
     * @param businessLicenseDO
     */
    private void fillFormRelatedData(BusinessLicenseDO businessLicenseDO) {
        if (businessLicenseDO.getLegalPersonId() != null) {
            ContactBasicDTO contactBasicDTO = contactQueryService.getBasicContactById(businessLicenseDO.getLegalPersonId());
            if (contactBasicDTO != null) {
                businessLicenseDO.setLegalPersonName(contactBasicDTO.getName());
            }
        }
        else {
            businessLicenseDO.setLegalPersonName("");
        }
    }
}