package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.assembler.RuleScopeAssembler;
import com.qy.rbac.app.application.dto.RuleScopeDTO;
import com.qy.rbac.app.application.query.RuleScopeQuery;
import com.qy.rbac.app.application.service.RuleScopeQueryService;
import com.qy.rbac.app.infrastructure.persistence.RuleScopeDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 规则范围查询服务实现
 *
 * @author legendjw
 */
@Service
public class RuleScopeQueryServiceImpl implements RuleScopeQueryService {
    private RuleScopeAssembler ruleScopeAssembler;
    private RuleScopeDataRepository ruleScopeDataRepository;
    private JdbcTemplate jdbcTemplate;

    public RuleScopeQueryServiceImpl(RuleScopeAssembler ruleScopeAssembler, RuleScopeDataRepository ruleScopeDataRepository, JdbcTemplate jdbcTemplate) {
        this.ruleScopeAssembler = ruleScopeAssembler;
        this.ruleScopeDataRepository = ruleScopeDataRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RuleScopeDTO> getRuleScopes(RuleScopeQuery query, Identity identity) {
        List<RuleScopeDO> ruleScopeDOPage = ruleScopeDataRepository.findByQuery(query);
        List<RuleScopeDTO> ruleScopeDTOPage = ruleScopeDOPage.stream().map(ruleScope -> ruleScopeAssembler.toDTO(ruleScope, identity)).collect(Collectors.toList());
        return ruleScopeDTOPage;
    }

    @Override
    public RuleScopeDTO getRuleScopeById(String id) {
        RuleScopeDO ruleScopeDO = ruleScopeDataRepository.findById(id);
        return ruleScopeAssembler.toDTO(ruleScopeDO, null);
    }

    @Override
    public Object getRuleScopeSelectData(String id, Identity identity) {
        RuleScopeDO ruleScopeDO = ruleScopeDataRepository.findById(id);
        if (ruleScopeDO == null) {
            throw new NotFoundException("未找到指定的规则范围");
        }
        if (ruleScopeDO.getDataSource().intValue() != 2) {
            throw new ValidationException("指定规则范围不是sql查询无法查询数据");
        }
        if (StringUtils.isBlank(ruleScopeDO.getDataSourceSql())) {
            throw new ValidationException("指定规则范围sql语句为空无法查询数据");
        }
        //ExpressionParser parser = new SpelExpressionParser();
        //StandardEvaluationContext context = new StandardEvaluationContext();
        //context.setVariable("identity_id", identity.getId());
        //Expression expression = parser.parseExpression(String.format("'%s'", ruleScopeDO.getDataSourceSql()));
        //String sql = expression.getExpressionString();

        return jdbcTemplate.queryForList(ruleScopeDO.getDataSourceSql());
    }
}
