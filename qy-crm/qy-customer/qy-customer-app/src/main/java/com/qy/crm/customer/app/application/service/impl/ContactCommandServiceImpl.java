package com.qy.crm.customer.app.application.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.ContactIdDTO;
import com.qy.crm.customer.app.application.dto.RelatedModuleDataDTO;
import com.qy.crm.customer.app.application.service.ContactCommandService;
import com.qy.crm.customer.app.infrastructure.persistence.ContactDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactDO;
import com.qy.crm.customer.app.application.command.*;
import com.qy.region.api.AreaClient;
import com.qy.rest.exception.NotFoundException;
import com.qy.security.session.EmployeeIdentity;
import com.qy.util.PinyinUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户联系人命令实现
 *
 * @author legendjw
 */
@Service
public class ContactCommandServiceImpl implements ContactCommandService {
    private ContactDataRepository contactDataRepository;
    private CodeTableClient codeTableClient;
    private AreaClient areaClient;

    public ContactCommandServiceImpl(ContactDataRepository contactDataRepository, CodeTableClient codeTableClient, AreaClient areaClient) {
        this.contactDataRepository = contactDataRepository;
        this.codeTableClient = codeTableClient;
        this.areaClient = areaClient;
    }

    @Override
    @Transactional
    public ContactIdDTO createContact(CreateContactCommand command) {
        EmployeeIdentity identity = command.getIdentity();

        ContactDO contactDO = new ContactDO();
        contactDO.setOrganizationId(identity != null ? identity.getOrganizationId(): command.getOrganizationId());
        BeanUtils.copyProperties(command, contactDO, "identity");
        contactDO.setCreatorId(identity != null ? identity.getId() : 0);
        contactDO.setCreatorName(identity != null ? identity.getName() : "");
        contactDO.setCreateTime(LocalDateTime.now());
        fillFormRelatedData(contactDO);
        contactDataRepository.save(contactDO);

        //保存关联关系
        if (command.getRelatedModuleData() != null && !command.getRelatedModuleData().isEmpty()) {
            for (RelatedModuleDataDTO relatedModuleDatum : command.getRelatedModuleData()) {
                contactDataRepository.saveRelation(relatedModuleDatum.getRelatedModuleId(), relatedModuleDatum.getRelatedDataId(), contactDO.getId());
            }
        }
        return new ContactIdDTO(contactDO.getId());
    }

    @Override
    @Transactional
    public void updateContact(UpdateContactCommand command) {
        ContactDO contactDO = contactDataRepository.findById(command.getId());
        if (contactDO == null) {
            throw new NotFoundException("未找到指定的客户联系人");
        }
        EmployeeIdentity identity = command.getIdentity();

        BeanUtils.copyProperties(command, contactDO, "identity");
        contactDO.setUpdatorId(identity != null ? identity.getId() : 0);
        contactDO.setUpdatorName(identity != null ? identity.getName() : "");
        contactDO.setUpdateTime(LocalDateTime.now());
        fillFormRelatedData(contactDO);
        contactDataRepository.save(contactDO);
    }

    @Override
    @Transactional
    public List<Long> batchSaveContact(BatchSaveContactCommand command) {
        RelatedModuleDataDTO relatedModuleDataDTO = command.getRelatedModuleData();
        List<Long> contactIds = new ArrayList<>();
        //保存联系人
        List<Long> oldContactIds = contactDataRepository.getRelatedContactIds(relatedModuleDataDTO.getRelatedModuleId(), relatedModuleDataDTO.getRelatedDataId());
        for (ContactForm contact : command.getContacts()) {
            if (contact.getId() == null) {
                CreateContactCommand createContactCommand = new CreateContactCommand();
                BeanUtils.copyProperties(contact, createContactCommand);
                createContactCommand.setIdentity(command.getIdentity());
                createContactCommand.setOrganizationId(command.getOrganizationId());
                List<RelatedModuleDataDTO> contactModuleDataDTOS = new ArrayList<>();
                contactModuleDataDTOS.add(new RelatedModuleDataDTO(relatedModuleDataDTO.getRelatedModuleId(), relatedModuleDataDTO.getRelatedDataId()));
                createContactCommand.setRelatedModuleData(contactModuleDataDTOS);
                ContactIdDTO contactIdDTO = createContact(createContactCommand);
                contactIds.add(contactIdDTO.getId());
            }
            else {
                UpdateContactCommand updateContactCommand = new UpdateContactCommand();
                BeanUtils.copyProperties(contact, updateContactCommand);
                updateContactCommand.setIdentity(command.getIdentity());
                updateContact(updateContactCommand);
                contactIds.add(contact.getId());
            }
        }
        //删除联系人
        for (Long contactId : oldContactIds) {
            if (!command.getContacts().stream().anyMatch(c -> c.getId() != null && c.getId().equals(contactId))) {
                DeleteContactCommand deleteContactCommand = new DeleteContactCommand();
                deleteContactCommand.setId(contactId);
                deleteContactCommand.setIdentity(command.getIdentity());
                deleteContact(deleteContactCommand);
            }
        }
        return contactIds;
    }

    @Override
    @Transactional
    public void deleteContact(DeleteContactCommand command) {
        EmployeeIdentity identity = command.getIdentity();

        contactDataRepository.remove(command.getId(), identity);
        contactDataRepository.removeRelation(command.getId());
    }

    @Override
    @Transactional
    public void batchDeleteContact(BatchDeleteContactCommand command) {
        if (command.getIds() == null || command.getIds().isEmpty()) {
            return;
        }
        for (Long id : command.getIds()) {
            DeleteContactCommand deleteContactCommand = new DeleteContactCommand();
            deleteContactCommand.setId(id);
            deleteContactCommand.setIdentity(command.getIdentity());
            deleteContact(deleteContactCommand);
        }
    }

    @Override
    public void deleteRelatedContact(DeleteRelatedDataCommand command) {
        List<Long> ids = contactDataRepository.getRelatedContactIds(command.getRelatedModuleId(), command.getRelatedDataId());
        if (!ids.isEmpty()) {
            BatchDeleteContactCommand batchDeleteContactCommand = new BatchDeleteContactCommand();
            batchDeleteContactCommand.setIdentity(command.getIdentity());
            batchDeleteContactCommand.setIds(ids);
            batchDeleteContact(batchDeleteContactCommand);
        }
    }

    @Override
    @Transactional
    public void setContactIsSuperAdmin(SetContactIsSuperAdminCommand command) {
        contactDataRepository.setContactIsSuperAdmin(command.getRelatedModuleId(), command.getRelatedDataId(), command.getId());
    }

    /**
     * 填充表单关联数据
     *
     * @param contactDO
     */
    private void fillFormRelatedData(ContactDO contactDO) {
        contactDO.setNamePinyin(PinyinUtils.getAlpha(contactDO.getName()));
        if (contactDO.getGenderId() != null) {
            contactDO.setGenderName(codeTableClient.getSystemCodeTableItemName("gender", String.valueOf(contactDO.getGenderId())));
        }
        //地区
        if (contactDO.getProvinceId() != null && contactDO.getProvinceId().intValue() != 0) {
            contactDO.setProvinceName(areaClient.getAreaNameById(contactDO.getProvinceId()));
        }
        if (contactDO.getCityId() != null && contactDO.getCityId().intValue() != 0) {
            contactDO.setCityName(areaClient.getAreaNameById(contactDO.getCityId()));
        }
        if (contactDO.getAreaId() != null && contactDO.getAreaId().intValue() != 0) {
            contactDO.setAreaName(areaClient.getAreaNameById(contactDO.getAreaId()));
        }
        if (contactDO.getStreetId() != null && contactDO.getStreetId().intValue() != 0) {
            contactDO.setStreetName(areaClient.getAreaNameById(contactDO.getStreetId()));
        }
    }
}