package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.dto.CodeTableDefaultItemDTO;
import com.qy.codetable.app.application.query.CodeTableDefaultItemQuery;

import java.util.List;

/**
 * 代码表默认项查询服务
 *
 * @author legendjw
 */
public interface CodeTableDefaultItemQueryService {
    /**
     * 根据查询条件查询代码表细项
     *
     * @param query
     * @return
     */
    List<CodeTableDefaultItemDTO> getCodeTableItems(CodeTableDefaultItemQuery query);

    /**
     * 根据ID查询一个代码表细项
     *
     * @param id
     * @return
     */
    CodeTableDefaultItemDTO getCodeTableItemById(Long id);
}
