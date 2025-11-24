package com.qy.crm.customer.app.application.service.impl;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.ContactBasicDTO;
import com.qy.crm.customer.app.application.dto.OpenBankIdDTO;
import com.qy.crm.customer.app.application.dto.RelatedModuleDataDTO;
import com.qy.crm.customer.app.application.service.ContactQueryService;
import com.qy.crm.customer.app.application.service.OpenBankCommandService;
import com.qy.crm.customer.app.infrastructure.persistence.OpenBankDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.OpenBankDO;
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
 * 开户行命令实现
 *
 * @author legendjw
 */
@Service
public class OpenBankCommandServiceImpl implements OpenBankCommandService {
    private OpenBankDataRepository openBankDataRepository;
    private ContactQueryService contactQueryService;

    public OpenBankCommandServiceImpl(OpenBankDataRepository openBankDataRepository, ContactQueryService contactQueryService) {
        this.openBankDataRepository = openBankDataRepository;
        this.contactQueryService = contactQueryService;
    }

    @Override
    @Transactional
    public OpenBankIdDTO createOpenBank(CreateOpenBankCommand command) {
        EmployeeIdentity identity = command.getIdentity();

        OpenBankDO openBankDO = new OpenBankDO();
        BeanUtils.copyProperties(command, openBankDO, "identity");
        fillFormRelatedData(openBankDO);
        openBankDO.setOrganizationId(identity != null ? identity.getOrganizationId() : command.getOrganizationId());
        openBankDO.setCreatorId(identity != null ? identity.getId() : 0);
        openBankDO.setCreatorName(identity != null ? identity.getName() : "");
        openBankDO.setCreateTime(LocalDateTime.now());
        openBankDataRepository.save(openBankDO);

        //保存关联关系
        if (command.getRelatedModuleData() != null && !command.getRelatedModuleData().isEmpty()) {
            for (RelatedModuleDataDTO relatedModuleDatum : command.getRelatedModuleData()) {
                openBankDataRepository.saveRelation(relatedModuleDatum.getRelatedModuleId(), relatedModuleDatum.getRelatedDataId(), openBankDO.getId());
            }
        }
        return new OpenBankIdDTO(openBankDO.getId());
    }

    @Override
    @Transactional
    public void updateOpenBank(UpdateOpenBankCommand command) {
        OpenBankDO openBankDO = openBankDataRepository.findById(command.getId());
        if (openBankDO == null) {
            throw new NotFoundException("未找到指定的开户行");
        }
        EmployeeIdentity identity = command.getIdentity();

        BeanUtils.copyProperties(command, openBankDO, "identity");
        fillFormRelatedData(openBankDO);
        openBankDO.setUpdatorId(identity != null ? identity.getId() : 0);
        openBankDO.setUpdatorName(identity != null ? identity.getName() : "");
        openBankDO.setUpdateTime(LocalDateTime.now());
        openBankDataRepository.save(openBankDO);
    }

    @Override
    @Transactional
    public List<Long> batchSaveOpenBank(BatchSaveOpenBankCommand command) {
        RelatedModuleDataDTO relatedModuleDataDTO = command.getRelatedModuleData();
        List<Long> openBankIds = new ArrayList<>();
        //保存开户行
        List<Long> oldOpenBankIds = openBankDataRepository.getRelatedOpenBankIds(relatedModuleDataDTO.getRelatedModuleId(), relatedModuleDataDTO.getRelatedDataId());
        for (OpenBankForm openBank : command.getOpenBanks()) {
            if (openBank.getId() == null) {
                CreateOpenBankCommand createOpenBankCommand = new CreateOpenBankCommand();
                BeanUtils.copyProperties(openBank, createOpenBankCommand);
                createOpenBankCommand.setIdentity(command.getIdentity());
                createOpenBankCommand.setOrganizationId(command.getOrganizationId());
                createOpenBankCommand.setLegalPersonId(command.getLegalPersonId() != null ? command.getLegalPersonId() : 0L);
                createOpenBankCommand.setLegalPersonName(command.getLegalPersonName() != null ? command.getLegalPersonName() : "");
                List<RelatedModuleDataDTO> openBankModuleDataDTOS = new ArrayList<>();
                openBankModuleDataDTOS.add(new RelatedModuleDataDTO(relatedModuleDataDTO.getRelatedModuleId(), relatedModuleDataDTO.getRelatedDataId()));
                createOpenBankCommand.setRelatedModuleData(openBankModuleDataDTOS);
                createOpenBank(createOpenBankCommand);
            }
            else {
                UpdateOpenBankCommand updateOpenBankCommand = new UpdateOpenBankCommand();
                BeanUtils.copyProperties(openBank, updateOpenBankCommand);
                updateOpenBankCommand.setIdentity(command.getIdentity());
                updateOpenBankCommand.setOrganizationId(command.getOrganizationId());
                updateOpenBankCommand.setLegalPersonId(command.getLegalPersonId() != null ? command.getLegalPersonId() : 0L);
                updateOpenBankCommand.setLegalPersonName(command.getLegalPersonName() != null ? command.getLegalPersonName() : "");
                updateOpenBank(updateOpenBankCommand);
            }
        }
        //删除开户行
        for (Long openBankId : oldOpenBankIds) {
            if (!command.getOpenBanks().stream().anyMatch(c -> c.getId() != null && c.getId().equals(openBankId))) {
                DeleteOpenBankCommand deleteOpenBankCommand = new DeleteOpenBankCommand();
                deleteOpenBankCommand.setId(openBankId);
                deleteOpenBankCommand.setIdentity(command.getIdentity());
                deleteOpenBank(deleteOpenBankCommand);
            }
        }
        return openBankIds;
    }

    @Override
    @Transactional
    public void deleteOpenBank(DeleteOpenBankCommand command) {
        EmployeeIdentity identity = command.getIdentity();

        openBankDataRepository.remove(command.getId(), identity);
        openBankDataRepository.removeRelation(command.getId());
    }

    @Override
    @Transactional
    public void deleteRelatedOpenBank(DeleteRelatedDataCommand command) {
        List<Long> openBankIds = openBankDataRepository.getRelatedOpenBankIds(command.getRelatedModuleId(), command.getRelatedDataId());
        if (!openBankIds.isEmpty()) {
            BatchDeleteContactCommand batchDeleteContactCommand = new BatchDeleteContactCommand();
            batchDeleteContactCommand.setIdentity(command.getIdentity());
            batchDeleteContactCommand.setIds(openBankIds);
            batchDeleteOpenBank(batchDeleteContactCommand);
        }
    }

    @Override
    @Transactional
    public void batchDeleteOpenBank(BatchDeleteContactCommand command) {
        if (command.getIds() == null || command.getIds().isEmpty()) {
            return;
        }
        for (Long id : command.getIds()) {
            DeleteOpenBankCommand deleteOpenBankCommand = new DeleteOpenBankCommand();
            deleteOpenBankCommand.setId(id);
            deleteOpenBankCommand.setIdentity(command.getIdentity());
            deleteOpenBank(deleteOpenBankCommand);
        }
    }

    /**
     * 填充表单关联数据
     *
     * @param openBankDO
     */
    private void fillFormRelatedData(OpenBankDO openBankDO) {
        if (openBankDO.getLegalPersonId() != null) {
            ContactBasicDTO contactBasicDTO = contactQueryService.getBasicContactById(openBankDO.getLegalPersonId());
            if (contactBasicDTO != null) {
                openBankDO.setLegalPersonName(contactBasicDTO.getName());
            }
        }
        else {
            openBankDO.setLegalPersonName("");
        }
    }
}