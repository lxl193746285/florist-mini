package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.application.query.AppMemberSystemQuery;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppMemberSystemDO;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 应用数据资源库
 *
 * @author legendjw
 */
public interface AppMemberSystemRepository {
    /**
     * 分页查询应用
     *
     * @param query
     * @return
     */
    List<AppMemberSystemDO> findByQuery(AppMemberSystemQuery query);

    /**
     * 根据id集合查询应用
     *
     * @param ids
     * @return
     */
    List<AppMemberSystemDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询应用
     *
     * @param id
     * @return
     */
    AppMemberSystemDO findById(Long id);

    /**
     * 保存一个应用
     *
     * @param appMemberSystemDO
     * @return
     */
    Long save(AppMemberSystemDO appMemberSystemDO);

    /**
     * 移除一个应用
     *
     * @param id
     * @param identity
     */
    void remove(Long id, Identity identity);


    /**
     * 根据应用id和组织id查询应用
     *
     * @param appId
     * @param organizationId
     * @param systemId
     * @return
     */
    AppMemberSystemDO findByAppIdAndOrganizationId(Long appId, Long organizationId, Long systemId);

    /**
     * 移除一个应用
     *
     * @param appId
     * @param identity
     */
    void removeByAppId(Long appId, Identity identity);

    List<AppMemberSystemDO> findBySystemId(Long systemId);

    AppMemberSystemDO findByAppId(Long appId);
}
