package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppClientDO;

import java.util.List;

/**
 * 应用数据资源库
 *
 * @author legendjw
 */
public interface AppClientDataRepository {

    /**
     * 保存一个应用客户端关系
     *
     * @param appClientDO
     */
    void save(AppClientDO appClientDO);

    /**
     * 通过客户端id找到应用客户端关系
     *
     * @param clientId
     */
    List<AppClientDO> findByClientId(Long clientId);

    /**
     * 通过应用id找到应用客户端关系
     *
     * @param appId
     */
    List<AppClientDO> findByAppId(Long appId);

    /**
     * 根据应用id删除应用客户端关系
     *
     * @param appId
     */
    void deleteByAppId(Long appId);

    /**
     * 根据多个客户端id找到应用客户端关系
     *
     * @param clientIds
     */
    List<AppClientDO> findByClientIds(List<Long> clientIds);

    /**
     * 查询指定客户端对应的关系数量
     *
     * @param clientIds
     * @return
     */
    int countByClientIds(List<Long> clientIds, Long excludeId);
}
