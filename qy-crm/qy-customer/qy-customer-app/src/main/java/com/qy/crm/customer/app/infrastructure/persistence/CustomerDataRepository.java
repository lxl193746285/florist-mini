package com.qy.crm.customer.app.infrastructure.persistence;

import com.qy.crm.customer.app.application.query.CustomerQuery;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.CustomerDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 客户数据资源库
 *
 * @author legendjw
 */
public interface CustomerDataRepository {
    /**
     * 分页查询岗位
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<CustomerDO> findByQuery(CustomerQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 根据id集合查询岗位
     *
     * @param ids
     * @return
     */
    List<CustomerDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询岗位
     *
     * @param id
     * @return
     */
    CustomerDO findById(Long id);

    /**
     * 保存一个岗位
     *
     * @param customerDO
     * @return
     */
    Long save(CustomerDO customerDO);

    /**
     * 移除一个岗位
     *
     * @param id
     * @param identity
     */
    void remove(Long id, EmployeeIdentity identity);

    /**
     * 查找指定组织指定名称的数量
     *
     * @param organizationId
     * @param name
     * @param excludeId
     * @return
     */
    int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId);
}
