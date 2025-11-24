package com.qy.system.app.scopedata.service.impl;

import com.qy.rbac.api.client.RuleScopeClient;
import com.qy.rbac.api.dto.RuleScopeDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.system.app.scopedata.service.ScopeDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScopeDataServiceImpl implements ScopeDataService {

    @Autowired
    private RuleScopeClient ruleScopeClient;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object getScopeData(String scopeId) {
        RuleScopeDTO ruleScopeDTO = ruleScopeClient.getRuleScope(scopeId);
        if (ruleScopeDTO == null) {
            throw new NotFoundException("未找到指定的规则范围");
        }
        if (ruleScopeDTO.getDataSource().intValue() != 2) {
            throw new ValidationException("指定规则范围不是sql查询无法查询数据");
        }
        if (StringUtils.isBlank(ruleScopeDTO.getDataSourceSql())) {
            throw new ValidationException("指定规则范围sql语句为空无法查询数据");
        }
        return jdbcTemplate.queryForList(ruleScopeDTO.getDataSourceSql());
    }
}
