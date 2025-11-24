package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.dto.RuleScopeDTO;
import com.qy.rbac.app.application.query.RuleScopeQuery;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 规则范围查询服务
 *
 * @author legendjw
 */
public interface RuleScopeQueryService {
    /**
     * 查询规则范围
     *
     * @param query
     * @param identity
     * @return
     */
    List<RuleScopeDTO> getRuleScopes(RuleScopeQuery query, Identity identity);

    /**
     * 根据ID查询规则范围
     *
     * @param id
     * @return
     */
    RuleScopeDTO getRuleScopeById(String id);

    /**
     * 获取指定规则范围可选择的数据
     *
     * @param id
     * @param identity
     * @return
     */
    Object getRuleScopeSelectData(String id, Identity identity);
}