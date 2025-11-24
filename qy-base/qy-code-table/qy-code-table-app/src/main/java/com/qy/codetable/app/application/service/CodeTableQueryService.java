package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.dto.CodeTableCategoryDTO;
import com.qy.codetable.app.application.dto.CodeTableDTO;
import com.qy.codetable.app.application.query.CodeTableQuery;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 代码表分类命令服务
 *
 * @author legendjw
 */
public interface CodeTableQueryService {
    /**
     * 根据查询条件查询代码表分类
     *
     * @param query
     * @return
     */
    List<CodeTableDTO> getCodeTables(CodeTableQuery query);

    /**
     * 获取根据分类分组的代码表
     *
     * @param type
     * @param identity
     * @return
     */
    List<CodeTableCategoryDTO> getCodeTablesGroupByCategory(String type, Identity identity);

    /**
     * 根据ID查询一个代码表
     *
     * @param id
     * @return
     */
    CodeTableDTO getCodeTableById(Long id);
}
