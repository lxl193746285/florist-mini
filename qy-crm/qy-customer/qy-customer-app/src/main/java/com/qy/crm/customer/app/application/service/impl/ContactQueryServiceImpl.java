package com.qy.crm.customer.app.application.service.impl;

import com.qy.crm.customer.app.application.assembler.ContactAssembler;
import com.qy.crm.customer.app.application.dto.ContactBasicDTO;
import com.qy.crm.customer.app.application.dto.ContactDTO;
import com.qy.crm.customer.app.application.query.ContactQuery;
import com.qy.crm.customer.app.application.security.ContactPermission;
import com.qy.crm.customer.app.application.service.ContactQueryService;
import com.qy.crm.customer.app.infrastructure.persistence.ContactDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactDO;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OpenAccountInfoDTO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户联系人查询服务实现
 *
 * @author legendjw
 */
@Service
public class ContactQueryServiceImpl implements ContactQueryService {
    private ContactAssembler contactAssembler;
    private ContactDataRepository contactDataRepository;
    private ContactPermission contactPermission;
    private OrganizationClient organizationClient;

    public ContactQueryServiceImpl(ContactAssembler contactAssembler, ContactDataRepository contactDataRepository, ContactPermission contactPermission, OrganizationClient organizationClient) {
        this.contactAssembler = contactAssembler;
        this.contactDataRepository = contactDataRepository;
        this.contactPermission = contactPermission;
        this.organizationClient = organizationClient;
    }

    @Override
    public Page<ContactDTO> getContacts(ContactQuery query, Identity identity) {
        MultiOrganizationFilterQuery filterQuery = contactPermission.getFilterQuery(identity, ContactPermission.LIST);
        Page<ContactDO> contactDOPage = contactDataRepository.findByQuery(query, filterQuery);
        Page<ContactDTO> contactDTOPage = contactDOPage.map(contact -> contactAssembler.toDTO(contact, identity));
        return contactDTOPage;
    }

    @Override
    public List<ContactDTO> getContactsByRelatedModule(String relatedModuleId, Long relatedDataId) {
        List<Long> ids = contactDataRepository.getRelatedContactIds(relatedModuleId, relatedDataId);
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<ContactDO> contactDOS = contactDataRepository.findByIds(ids);
        return contactDOS.stream().map(c -> contactAssembler.toDTO(c, null)).collect(Collectors.toList());
    }

    @Override
    public ContactDTO getSuperAdminContact(String relatedModuleId, Long relatedDataId) {
        ContactDO contactDO = contactDataRepository.findRelatedSuperAdmin(relatedModuleId, relatedDataId);
        if (contactDO == null) {
            return null;
        }
        //查询超管联系人需要获取开户目前真正的超管手机号显示到前端
        OpenAccountInfoDTO openAccountInfoDTO = organizationClient.getOpenAccountInfo(relatedModuleId, relatedDataId);
        if (openAccountInfoDTO != null && openAccountInfoDTO.getSuperAdmin() != null) {
            contactDO.setTel(openAccountInfoDTO.getSuperAdmin().getPhone());
        }
        return contactAssembler.toDTO(contactDO, null);
    }

    @Override
    public ContactDTO getContactById(Long id, Identity identity) {
        ContactDO contactDO = contactDataRepository.findById(id);
        return contactAssembler.toDTO(contactDO, identity);
    }

    @Override
    public ContactDTO getContactById(Long id) {
        ContactDO contactDO = contactDataRepository.findById(id);
        return contactAssembler.toDTO(contactDO, null);
    }

    @Override
    public ContactBasicDTO getBasicContactById(Long id) {
        ContactDO contactDO = contactDataRepository.findById(id);
        return contactAssembler.toBasicDTO(contactDO);
    }
}
