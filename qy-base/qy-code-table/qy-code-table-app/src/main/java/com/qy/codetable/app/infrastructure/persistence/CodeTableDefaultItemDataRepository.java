package com.qy.codetable.app.infrastructure.persistence;

import com.qy.codetable.app.application.query.CodeTableDefaultItemQuery;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDefaultItemDO;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 代码表默认细项资源库
 *
 * @author legendjw
 */
public interface CodeTableDefaultItemDataRepository {
    /**
     * 根据查询条件查询代码表细项
     *
     * @param query
     * @return
     */
    List<CodeTableDefaultItemDO> findByQuery(CodeTableDefaultItemQuery query);

    /**
     * 根据ID获取代码表细项
     *
     * @param id
     * @return
     */
    CodeTableDefaultItemDO findById(Long id);

    /**
     * 根据类型、标示码、值查询数量
     *
     * @param type
     * @param code
     * @param value
     * @param excludeId
     * @return
     */
    int countByTypeAndRelatedAndCodeAndValue(String type, String code, String value, Long excludeId);

    /**
     * 根据类型、标示码、名称查询数量
     *
     * @param type
     * @param code
     * @param name
     * @param excludeId
     * @return
     */
    int countByTypeAndRelatedAndCodeAndName(String type, String code, String name, Long excludeId);

    /**
     * 获取指定类型、关联id、标示码下最大的值
     *
     * @param type
     * @param code
     * @return
     */
    int getMaxValue(String type, String code);

    /**
     * 保存一个代码表细项
     *
     * @return
     */
    Long save(CodeTableDefaultItemDO codeTableItemDO);

    /**
     * 删除一个代码表细项
     *
     * @param id
     * @param user
     */
    void remove(Long id, Identity user);

    /**
     * 批量更新代码表code
     *
     * @param type
     * @param oldCode
     * @param newCode
     */
    void batchUpdateCode(String type, String oldCode, String newCode);
}
