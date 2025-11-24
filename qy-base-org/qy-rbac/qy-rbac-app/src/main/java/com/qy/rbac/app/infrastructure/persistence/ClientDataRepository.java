package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.application.query.ClientQuery;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ClientDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 客户端数据资源库
 *
 * @author legendjw
 */
public interface ClientDataRepository {
    /**
     * 分页查询客户端
     *
     * @param query
     * @return
     */
    Page<ClientDO> findByQuery(ClientQuery query);

    /**
     * 根据id集合查询客户端
     *
     * @param ids
     * @return
     */
    List<ClientDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询客户端
     *
     * @param id
     * @return
     */
    ClientDO findById(Long id);

    /**
     * 根据客户端id查询客户端
     *
     * @param clientId
     * @return
     */
    ClientDO findByClientId(String clientId);

    /**
     * 保存一个客户端
     *
     * @param clientDO
     * @return
     */
    Long save(ClientDO clientDO);

    /**
     * 移除一个客户端
     *
     * @param id
     * @param identity
     */
    void remove(Long id, Identity identity);

    /**
     * 查询指定客户端名称的数量
     *
     * @param name
     * @param excludeId
     * @return
     */
    int countByName(String name, Long excludeId);

    /**
     * 查询指定客户端id的数量
     *
     * @param clientId
     * @param excludeId
     * @return
     */
    int countByClientId(String clientId, Long excludeId);
}
