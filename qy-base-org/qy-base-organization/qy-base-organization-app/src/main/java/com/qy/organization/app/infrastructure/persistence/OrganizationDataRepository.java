package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.application.query.OrganizationQuery;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 组织数据资源库
 *
 * @author legendjw
 */
public interface OrganizationDataRepository {
    /**
     * 根据查询获取分页组织
     *
     * @param query
     * @return
     */
    Page<OrganizationDO> findPageByQuery(OrganizationQuery query);

    /**
     * 获取所有的组织
     *
     * @return
     */
    List<OrganizationDO> findAllOrganizations();

    /**
     * 获取指定用户加入的组织
     *
     * @param userId
     * @return
     */
    List<OrganizationDO> findUserJoinOrganizations(Long userId);

    /**
     * 根据id集合获取组织
     *
     * @param ids
     * @return
     */
    List<OrganizationDO> findByIds(List<Long> ids);

    /**
     * 根据id集合以及关键字获取组织
     *
     * @param ids
     * @return
     */
    List<OrganizationDO> findByIdsAndKeywords(List<Long> ids, String keywords);

    /**
     * 根据id获取组织
     *
     * @param id
     * @return
     */
    OrganizationDO findById(Long id);

    /**
     * 根据来源获取组织
     *
     * @param source
     * @param sourceDataId
     * @return
     */
    OrganizationDO findBySource(String source, Long sourceDataId);

    /**
     * 根据父级id获取组织
     *
     * @param parentId
     * @return
     */
    List<OrganizationDO> findByParentId(Long parentId);

}
