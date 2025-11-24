package com.qy.codetable.app.infrastructure.persistence;

import com.qy.codetable.app.application.query.CodeTableQuery;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 代码表资源库
 *
 * @author legendjw
 */
public interface CodeTableDataRepository {
    /**
     * 根据查询条件查询代码表
     *
     * @param query
     * @return
     */
    List<CodeTableDO> findByQuery(CodeTableQuery query);

    /**
     * 根据ID获取代码表
     *
     * @param id
     * @return
     */
    CodeTableDO findById(Long id);

    /**
     * 根据类型和唯一标示获取代码表
     *
     * @param type
     * @param code
     * @return
     */
    CodeTableDO findByTypeAndCode(String type, String code);

    /**
     * 根据类型和标示码查询数量
     *
     * @param type
     * @param code
     * @param excludeId
     * @return
     */
    int countByTypeAndCode(String type, String code, Long excludeId);

    /**
     * 保存一个代码表
     *
     * @return
     */
    Long save(CodeTableDO codeTableDO);

    /**
     * 删除一个代码表
     *
     * @param id
     * @param user
     */
    void remove(Long id, Identity user);
}
