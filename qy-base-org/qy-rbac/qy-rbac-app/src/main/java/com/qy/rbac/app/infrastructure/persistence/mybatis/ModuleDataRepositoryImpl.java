package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.rbac.app.application.query.ModuleQuery;
import com.qy.rbac.app.infrastructure.persistence.ModuleDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ModuleDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.ModuleMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 模块数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class ModuleDataRepositoryImpl implements ModuleDataRepository {
    private ModuleMapper moduleMapper;

    public ModuleDataRepositoryImpl(ModuleMapper moduleMapper) {
        this.moduleMapper = moduleMapper;
    }

    @Override
    public List<ModuleDO> findByQuery(ModuleQuery query) {
        LambdaQueryWrapper<ModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ModuleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(ModuleDO::getSort)
                .orderByAsc(ModuleDO::getCreateTime);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(ModuleDO::getName, query.getKeywords()));
        }
        if (query.getAppId() != null) {
            queryWrapper.eq(ModuleDO::getAppId, query.getAppId());
        }
        return moduleMapper.selectList(queryWrapper);
    }

    @Override
    public List<ModuleDO> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<ModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ModuleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(ModuleDO::getId, ids)
                .orderByAsc(ModuleDO::getSort)
                .orderByAsc(ModuleDO::getCreateTime);
        return moduleMapper.selectList(queryWrapper);
    }

    @Override
    public ModuleDO findById(Long id) {
        LambdaQueryWrapper<ModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ModuleDO::getId, id)
                .eq(ModuleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return moduleMapper.selectOne(queryWrapper);
    }

    @Override
    public ModuleDO findByAppAndCode(Long appId, String code) {
        LambdaQueryWrapper<ModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ModuleDO::getAppId, appId)
                .eq(ModuleDO::getCode, code)
                .eq(ModuleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return moduleMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(ModuleDO moduleDO) {
        if (moduleDO.getId() == null) {
            moduleMapper.insert(moduleDO);
        }
        else {
            moduleMapper.updateById(moduleDO);
        }
        return moduleDO.getId();
    }

    @Override
    public void remove(Long id, Identity identity) {
        ModuleDO moduleDO = findById(id);
        moduleDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        moduleDO.setDeletorId(identity.getId());
        moduleDO.setDeletorName(identity.getName());
        moduleDO.setDeleteTime(LocalDateTime.now());
        moduleMapper.updateById(moduleDO);
    }

    @Override
    public int countByName(String name, Long excludeId) {
        LambdaQueryWrapper<ModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ModuleDO::getName, name)
                .eq(ModuleDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(ModuleDO::getId, excludeId);
        }
        return moduleMapper.selectCount(queryWrapper);
    }
}