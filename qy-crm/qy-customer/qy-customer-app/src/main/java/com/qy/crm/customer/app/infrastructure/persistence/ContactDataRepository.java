package com.qy.crm.customer.app.infrastructure.persistence;

import com.qy.crm.customer.app.application.query.ContactQuery;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 客户联系人数据资源库
 *
 * @author legendjw
 */
public interface ContactDataRepository {
    /**
     * 分页查询联系人
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<ContactDO> findByQuery(ContactQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 根据id集合查询联系人
     *
     * @param ids
     * @return
     */
    List<ContactDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询联系人
     *
     * @param id
     * @return
     */
    ContactDO findById(Long id);

    /**
     * 查找关联下的超管
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    ContactDO findRelatedSuperAdmin(String relatedModuleId, Long relatedDataId);

    /**
     * 保存一个联系人
     *
     * @param contactDO
     * @return
     */
    Long save(ContactDO contactDO);

    /**
     * 移除一个联系人
     *
     * @param id
     * @param identity
     */
    void remove(Long id, EmployeeIdentity identity);

    /**
     * 查询指定模块数据关联的联系人ID集合
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    List<Long> getRelatedContactIds(String relatedModuleId, Long relatedDataId);

    /**
     * 保存一个联系人关系
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @param contactId
     * @return
     */
    Long saveRelation(String relatedModuleId, Long relatedDataId, Long contactId);

    /**
     * 移除联系人关联的所有关系
     *
     * @param contactId
     * @return
     */
    int removeRelation(Long contactId);

    /**
     * 设置指定联系人为超管
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @param contactId
     */
    void setContactIsSuperAdmin(String relatedModuleId, Long relatedDataId, Long contactId);
}
