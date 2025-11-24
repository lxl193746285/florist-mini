package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.application.query.AppQuery;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppDO;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 应用数据资源库
 *
 * @author legendjw
 */
public interface AppDataRepository {
    /**
     * 分页查询应用
     *
     * @param query
     * @return
     */
    List<AppDO> findByQuery(AppQuery query);

    /**
     * 根据id集合查询应用
     *
     * @param ids
     * @return
     */
    List<AppDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询应用
     *
     * @param id
     * @return
     */
    AppDO findById(Long id);


    /**
     * 根据应用标示查询应用
     *
     * @param code
     * @return
     */
    AppDO findByCode(String code);

    /**
     * 保存一个应用
     *
     * @param appDO
     * @return
     */
    Long save(AppDO appDO);

    /**
     * 移除一个应用
     *
     * @param id
     * @param identity
     */
    void remove(Long id, Identity identity);

    /**
     * 查询指定应用名称的数量
     *
     * @param name
     * @param excludeId
     * @return
     */
    int countByName(String name, Long excludeId);

    List<AppDO> findBySystemId(List<Long> ids, Long systemId);
}
