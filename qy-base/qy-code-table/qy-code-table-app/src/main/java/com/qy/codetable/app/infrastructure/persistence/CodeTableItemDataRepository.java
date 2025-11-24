package com.qy.codetable.app.infrastructure.persistence;

import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableItemDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 代码表细项资源库
 *
 * @author legendjw
 */
public interface CodeTableItemDataRepository {
    /**
     * 根据查询条件查询代码表
     *
     * @param query
     * @return
     */
    Page<CodeTableItemDO> findByQuery(CodeTableItemQuery query);

    /**
     * 根据查询条件查询代码表
     *
     * @param query
     * @return
     */
    List<CodeTableItemDO> findAllByQuery(CodeTableItemQuery query);

    /**
     * 根据ID获取代码表
     *
     * @param id
     * @return
     */
    CodeTableItemDO findById(Long id);

    /**
     * 根据指定类型以及指定关联id以及值获取代码表细项
     *
     * @param type
     * @param relatedId
     * @param code
     * @param value
     * @return
     */
    CodeTableItemDO findByTypeAndCodeAndValue(String type, Long relatedId, String code, String value);

    /**
     * 根据类型、关联id、标示码、值查询数量
     *
     * @param type
     * @param relatedId
     * @param code
     * @param value
     * @param excludeId
     * @return
     */
    int countByTypeAndRelatedAndCodeAndValue(String type, Long relatedId, String code, String value, Long excludeId);

    /**
     * 根据类型、关联id、标示码、名称查询数量
     *
     * @param type
     * @param relatedId
     * @param code
     * @param name
     * @param excludeId
     * @return
     */
    int countByTypeAndRelatedAndCodeAndName(String type, Long relatedId, String code, String name, Long excludeId);

    /**
     * 根据父级id查询数量
     *
     * @param parentId
     * @return
     */
    int countByParentId(Long parentId);

    /**
     * 获取指定类型、关联id、标示码下最大的值
     *
     * @param type
     * @param relatedId
     * @param code
     * @return
     */
    int getMaxValue(String type, Long relatedId, String code);

    /**
     * 保存一个代码表
     *
     * @return
     */
    Long save(CodeTableItemDO codeTableItemDO);

    /**
     * 删除一个代码表
     *
     * @param id
     * @param user
     */
    void remove(Long id, Identity user);

    /**
     * 批量删除一个代码表
     *
     * @param ids
     * @param user
     */
    void remove(List<Long> ids, Identity user);

    /**
     * 批量更新代码表code
     *
     * @param type
     * @param oldCode
     * @param newCode
     */
    void batchUpdateCode(String type, String oldCode, String newCode);
}
