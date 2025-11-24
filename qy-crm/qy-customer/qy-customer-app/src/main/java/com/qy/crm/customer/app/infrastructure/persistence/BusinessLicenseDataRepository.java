package com.qy.crm.customer.app.infrastructure.persistence;

import com.qy.crm.customer.app.application.query.BusinessLicenseQuery;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.BusinessLicenseDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 营业执照数据资源库
 *
 * @author legendjw
 */
public interface BusinessLicenseDataRepository {
    /**
     * 分页查询营业执照
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<BusinessLicenseDO> findByQuery(BusinessLicenseQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 根据id集合查询营业执照
     *
     * @param ids
     * @return
     */
    List<BusinessLicenseDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询营业执照
     *
     * @param id
     * @return
     */
    BusinessLicenseDO findById(Long id);

    /**
     * 保存一个营业执照
     *
     * @param businessLicenseDO
     * @return
     */
    Long save(BusinessLicenseDO businessLicenseDO);

    /**
     * 移除一个营业执照
     *
     * @param id
     * @param identity
     */
    void remove(Long id, EmployeeIdentity identity);

    /**
     * 查询指定模块数据关联的营业执照ID集合
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    List<Long> getRelatedBusinessLicenseIds(String relatedModuleId, Long relatedDataId);

    /**
     * 保存一个营业执照关系
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @param businessLicenseId
     * @return
     */
    Long saveRelation(String relatedModuleId, Long relatedDataId, Long businessLicenseId);

    /**
     * 移除营业执照关联的所有关系
     *
     * @param businessLicenseId
     * @return
     */
    int removeRelation(Long businessLicenseId);
}
