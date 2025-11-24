package com.qy.message.app.infrastructure.persistence;

import com.qy.message.app.application.query.PlatformQuery;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.PlatformDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;

import java.util.List;

/**
 * 消息平台资源库
 *
 * @author legendjw
 */
public interface PlatformRepository {
    /**
     * 获取所有的消息平台
     *
     * @return
     */
    List<PlatformDO> findAll();

    /**
     * 分页查询消息平台
     *
     * @param query
     * @return
     */
    Page<PlatformDO> findByQuery(PlatformQuery query);

    /**
     * 根据id集合查询消息平台
     *
     * @param ids
     * @return
     */
    List<PlatformDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询消息平台
     *
     * @param id
     * @return
     */
    PlatformDO findById(Long id);

    /**
     * 根据微信应用id获取消息平台
     *
     * @param weixinAppId
     * @return
     */
    PlatformDO findByWeixinAppId(String weixinAppId);

    /**
     * 保存一个消息平台
     *
     * @param platformDO
     * @return
     */
    Long save(PlatformDO platformDO);

    /**
     * 移除一个消息平台
     *
     * @param id
     * @param employee
     */
    void remove(Long id, EmployeeIdentity employee);

    /**
     * 查找指定名称的数量
     *
     * @param name
     * @param excludeId
     * @return
     */
    int countByName(String name, Long excludeId);
}