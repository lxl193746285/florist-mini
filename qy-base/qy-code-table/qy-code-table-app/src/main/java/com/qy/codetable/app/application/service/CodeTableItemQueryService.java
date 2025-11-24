package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.dto.CodeTableItemBasicDTO;
import com.qy.codetable.app.application.dto.CodeTableItemDTO;
import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 代码表项命令服务
 *
 * @author legendjw
 */
public interface CodeTableItemQueryService {
    /**
     * 根据查询条件查询分页代码表细项
     *
     * @param query
     * @return
     */
    Page<CodeTableItemDTO> getCodeTableItems(CodeTableItemQuery query);

    /**
     * 根据查询条件查询代码表细项
     *
     * @param query
     * @return
     */
    List<CodeTableItemDTO> getAllCodeTableItems(CodeTableItemQuery query);

    /**
     * 根据查询条件获取客户端使用的代码表细项
     *
     * @param query
     * @return
     */
    List<CodeTableItemBasicDTO> getBasicCodeTableItems(CodeTableItemQuery query);

    /**
     * 根据ID查询一个代码表细项
     *
     * @param id
     * @return
     */
    CodeTableItemDTO getCodeTableItemById(Long id);

    /**
     * 获取系统代码表细项信息
     *
     * @param code
     * @param value
     * @return
     */
    CodeTableItemBasicDTO getSystemCodeTableItem(String code, String value);

    /**
     * 获取组织代码表细项信息
     *
     * @param organizationId
     * @param code
     * @param value
     * @return
     */
    CodeTableItemBasicDTO getOrganizationCodeTableItem(Long organizationId, String code, String value);

    /**
     * 获取个人代码表细项信息
     *
     * @param userId
     * @param code
     * @param value
     * @return
     */
    CodeTableItemBasicDTO getPersonalCodeTableItem(Long userId, String code, String value);
}
