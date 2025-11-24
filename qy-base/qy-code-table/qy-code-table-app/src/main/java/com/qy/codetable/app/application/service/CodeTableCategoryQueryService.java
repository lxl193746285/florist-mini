package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.dto.CodeTableCategoryDTO;
import com.qy.codetable.app.application.query.CodeTableCategoryQuery;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 代码表分类命令服务
 *
 * @author legendjw
 */
public interface CodeTableCategoryQueryService {
    /**
     * 根据查询条件查询代码表分类
     *
     * @param query
     * @param identity
     * @return
     */
    List<CodeTableCategoryDTO> getCodeTableCategorys(CodeTableCategoryQuery query, Identity identity);

    /**
     * 根据ID查询一个代码表分类
     *
     * @param id
     * @return
     */
    CodeTableCategoryDTO getCodeTableCategoryById(Long id);
}
