package com.qy.organization.app.infrastructure.persistence;

import com.qy.organization.app.domain.entity.Organization;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.security.session.Identity;

/**
 * 组织领域资源库
 *
 * @author legendjw
 */
public interface OrganizationDomainRepository {
    /**
     * 根据id查找组织
     *
     * @param id
     * @return
     */
    Organization findById(OrganizationId id);

    /**
     * 保存一个组织
     *
     * @param organization
     * @return
     */
    OrganizationId save(Organization organization);

    /**
     * 删除一个组织
     *
     * @param id
     * @param identity
     */
    void remove(OrganizationId id, Identity identity);

    /**
     * 查询指定创建人指定名称的组织数量
     *
     * @param creatorId
     * @param name
     * @param excludeId
     * @return
     */
    int countByCreatorAndName(Long creatorId, String name, Long excludeId);
}
