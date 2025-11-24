package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.application.query.OrgDatasourceQuery;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrgDatasourceDO;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 组织数据资源库
 *
 * @author legendjw
 */
public interface OrgDatasourceDataRepository {
    /**
     * 根据查询获取分页组织数据库关系
     *
     * @param query
     * @return
     */
    Page<OrgDatasourceDO> findPageByQuery(OrgDatasourceQuery query);

    /**
     * 根据id获取组织数据库关系
     *
     * @param id
     * @return
     */
    OrgDatasourceDO findById(Long id);

    /**
     * 保存组织数据库关系
     *
     * @param orgDatasourceDO
     */
    void save(OrgDatasourceDO orgDatasourceDO);

    /**
     * 保存组织数据库关系
     *
     * @param id
     * @param userId
     */
    void remove(Long id, Long userId);

    /**
     * 根据组织id获取组织数据库关系
     * @param orgId
     * @return
     */
    OrgDatasourceDO findByOrgId(Long orgId);
    List<OrgDatasourceDO> findByOrgIds(List<Long> orgIds);
}
