package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.rbac.app.application.query.RuleScopeQuery;
import com.qy.rbac.app.infrastructure.persistence.RuleScopeDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.RuleScopeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规则范围数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class RuleScopeDataRepositoryImpl implements RuleScopeDataRepository {
    private RuleScopeMapper ruleScopeMapper;

    public RuleScopeDataRepositoryImpl(RuleScopeMapper ruleScopeMapper) {
        this.ruleScopeMapper = ruleScopeMapper;
    }

    @Override
    public List<RuleScopeDO> findAll() {
        return ruleScopeMapper.selectList(null);
    }

    @Override
    public List<RuleScopeDO> findByQuery(RuleScopeQuery query) {
        LambdaQueryWrapper<RuleScopeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderByAsc(RuleScopeDO::getSort)
                .orderByAsc(RuleScopeDO::getCreateTime);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(RuleScopeDO::getName, query.getKeywords()));
        }
        return ruleScopeMapper.selectList(queryWrapper);
    }

    @Override
    public RuleScopeDO findById(String id) {
        LambdaQueryWrapper<RuleScopeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RuleScopeDO::getId, id)
                .last("limit 1");
        return ruleScopeMapper.selectOne(queryWrapper);
    }

    @Override
    public String insert(RuleScopeDO ruleScopeDO) {
        ruleScopeMapper.insert(ruleScopeDO);
        return ruleScopeDO.getId();
    }

    @Override
    public void update(String id, RuleScopeDO ruleScopeDO) {
        ruleScopeMapper.update(id, ruleScopeDO);
    }

    @Override
    public void remove(String id) {
        LambdaQueryWrapper<RuleScopeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RuleScopeDO::getId, id);
        ruleScopeMapper.delete(queryWrapper);
    }

    @Override
    public int countById(String id, String excludeId) {
        LambdaQueryWrapper<RuleScopeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(RuleScopeDO::getId, id);
        if (excludeId != null) {
            queryWrapper.ne(RuleScopeDO::getId, excludeId);
        }
        return ruleScopeMapper.selectCount(queryWrapper);
    }
}