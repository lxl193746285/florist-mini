package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.application.query.RuleScopeQuery;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;

import java.util.List;

/**
 * 规则范围数据资源库
 *
 * @author legendjw
 */
public interface RuleScopeDataRepository {
    /**
     * 查询所有的规则范围
     *
     * @return
     */
    List<RuleScopeDO> findAll();

    /**
     * 分页查询规则范围
     *
     * @param query
     * @return
     */
    List<RuleScopeDO> findByQuery(RuleScopeQuery query);

    /**
     * 根据ID查询规则范围
     *
     * @param id
     * @return
     */
    RuleScopeDO findById(String id);

    /**
     * 插入一个规则范围
     *
     * @param ruleScopeDO
     * @return
     */
    String insert(RuleScopeDO ruleScopeDO);

    /**
     * 修改一个规则范围
     *
     * @param ruleScopeDO
     */
    void update(String id, RuleScopeDO ruleScopeDO);

    /**
     * 移除一个规则范围
     *
     * @param id
     */
    void remove(String id);

    /**
     * 查询指定规则范围id的数量
     *
     * @param id
     * @param excludeId
     * @return
     */
    int countById(String id, String excludeId);
}
