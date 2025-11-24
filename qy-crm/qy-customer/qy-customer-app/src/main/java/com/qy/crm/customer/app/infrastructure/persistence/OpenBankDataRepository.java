package com.qy.crm.customer.app.infrastructure.persistence;

import com.qy.crm.customer.app.application.query.OpenBankQuery;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.OpenBankDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 客户开户行数据资源库
 *
 * @author legendjw
 */
public interface OpenBankDataRepository {
    /**
     * 分页查询开户行
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<OpenBankDO> findByQuery(OpenBankQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 根据id集合查询开户行
     *
     * @param ids
     * @return
     */
    List<OpenBankDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询开户行
     *
     * @param id
     * @return
     */
    OpenBankDO findById(Long id);

    /**
     * 保存一个开户行
     *
     * @param openBankDO
     * @return
     */
    Long save(OpenBankDO openBankDO);

    /**
     * 移除一个开户行
     *
     * @param id
     * @param identity
     */
    void remove(Long id, EmployeeIdentity identity);

    /**
     * 查询指定模块数据关联的开户行ID集合
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    List<Long> getRelatedOpenBankIds(String relatedModuleId, Long relatedDataId);

    /**
     * 保存一个开户行关系
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @param openBankId
     * @return
     */
    Long saveRelation(String relatedModuleId, Long relatedDataId, Long openBankId);

    /**
     * 移除开户行关联的所有关系
     *
     * @param openBankId
     * @return
     */
    int removeRelation(Long openBankId);
}
