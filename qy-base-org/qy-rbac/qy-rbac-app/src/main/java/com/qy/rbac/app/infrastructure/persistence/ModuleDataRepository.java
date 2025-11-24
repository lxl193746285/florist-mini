package com.qy.rbac.app.infrastructure.persistence;

import com.qy.rbac.app.application.query.ModuleQuery;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ModuleDO;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 模块数据资源库
 *
 * @author legendjw
 */
public interface ModuleDataRepository {
    /**
     * 分页查询模块
     *
     * @param query
     * @return
     */
    List<ModuleDO> findByQuery(ModuleQuery query);

    /**
     * 根据id集合查询模块
     *
     * @param ids
     * @return
     */
    List<ModuleDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询模块
     *
     * @param id
     * @return
     */
    ModuleDO findById(Long id);

    /**
     * 根据应用id以及模块标示获取模块
     *
     * @param appId
     * @param code
     * @return
     */
    ModuleDO findByAppAndCode(Long appId, String code);

    /**
     * 保存一个模块
     *
     * @param moduleDO
     * @return
     */
    Long save(ModuleDO moduleDO);

    /**
     * 移除一个模块
     *
     * @param id
     * @param identity
     */
    void remove(Long id, Identity identity);

    /**
     * 查询指定模块名称的数量
     *
     * @param name
     * @param excludeId
     * @return
     */
    int countByName(String name, Long excludeId);
}
