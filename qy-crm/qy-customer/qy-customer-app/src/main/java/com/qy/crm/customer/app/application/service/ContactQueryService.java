package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.dto.ContactBasicDTO;
import com.qy.crm.customer.app.application.dto.ContactDTO;
import com.qy.crm.customer.app.application.query.ContactQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 客户联系人查询服务
 *
 * @author legendjw
 */
public interface ContactQueryService {
    /**
     * 查询客户联系人
     *
     * @param query
     * @param identity
     * @return
     */
    Page<ContactDTO> getContacts(ContactQuery query, Identity identity);

    /**
     * 根据关联模块获取联系人
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    List<ContactDTO> getContactsByRelatedModule(String relatedModuleId, Long relatedDataId);

    /**
     * 根据关联模块获取下面设置的超管联系人
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    ContactDTO getSuperAdminContact(String relatedModuleId, Long relatedDataId);

    /**
     * 根据ID查询客户联系人
     *
     * @param id
     * @return
     */
    ContactDTO getContactById(Long id, Identity identity);

    /**
     * 根据ID查询客户联系人
     *
     * @param id
     * @return
     */
    ContactDTO getContactById(Long id);

    /**
     * 根据ID查询基本客户联系人
     *
     * @param id
     * @return
     */
    ContactBasicDTO getBasicContactById(Long id);
}