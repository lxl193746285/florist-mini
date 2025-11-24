package com.qy.codetable.app.infrastructure.persistence;

import com.qy.codetable.app.application.query.CodeTableCategoryQuery;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 代码表分类资源库
 *
 * @author legendjw
 */
public interface CodeTableCategoryDataRepository {
    /**
     * 根据查询条件查询代码表分类
     *
     * @param query
     * @return
     */
    List<CodeTableCategoryDO> findByQuery(CodeTableCategoryQuery query);

    /**
     * 根据ID获取代码表分类
     *
     * @param id
     * @return
     */
    CodeTableCategoryDO findById(Long id);

    /**
     * 根据标示查询代码表分类
     *
     * @param code
     * @return
     */
    CodeTableCategoryDO findByCode(String code);

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
     * 保存一个代码表分类
     *
     * @return
     */
    Long save(CodeTableCategoryDO codeTableCategoryDO);

    /**
     * 删除一个代码表分类
     *
     * @param id
     * @param user
     */
    void remove(Long id, Identity user);
}
