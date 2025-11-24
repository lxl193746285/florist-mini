package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.rbac.app.application.query.AppQuery;
import com.qy.rbac.app.infrastructure.persistence.AppDataRepository;
import com.qy.rbac.app.infrastructure.persistence.AppMemberSystemRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppMemberSystemDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AppMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class AppDataRepositoryImpl implements AppDataRepository {
    private AppMapper appMapper;
    private AppMemberSystemRepository appMemberSystemRepository;

    public AppDataRepositoryImpl(AppMapper appMapper, AppMemberSystemRepository appMemberSystemRepository) {
        this.appMapper = appMapper;
        this.appMemberSystemRepository = appMemberSystemRepository;
    }

    @Override
    public List<AppDO> findByQuery(AppQuery query) {
        LambdaQueryWrapper<AppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(AppDO::getSort)
                .orderByAsc(AppDO::getCreateTime);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(AppDO::getName, query.getKeywords()));
        }
        return appMapper.selectList(queryWrapper);
    }

    @Override
    public List<AppDO> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(AppDO::getId, ids)
                .orderByAsc(AppDO::getSort)
                .orderByAsc(AppDO::getCreateTime);
        return appMapper.selectList(queryWrapper);
    }

    @Override
    public AppDO findById(Long id) {
        LambdaQueryWrapper<AppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppDO::getId, id)
                .eq(AppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return appMapper.selectOne(queryWrapper);
    }

    @Override
    public AppDO findByCode(String code) {
        LambdaQueryWrapper<AppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppDO::getCode, code)
                .eq(AppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return appMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(AppDO appDO) {
        if (appDO.getId() == null) {
            appMapper.insert(appDO);
        } else {
            appMapper.updateById(appDO);
        }
        return appDO.getId();
    }

    @Override
    public void remove(Long id, Identity identity) {
        AppDO appDO = findById(id);
        appDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        appDO.setDeletorId(identity.getId());
        appDO.setDeletorName(identity.getName());
        appDO.setDeleteTime(LocalDateTime.now());
        appMapper.updateById(appDO);
    }

    @Override
    public int countByName(String name, Long excludeId) {
        LambdaQueryWrapper<AppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppDO::getName, name)
                .eq(AppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(AppDO::getId, excludeId);
        }
        return appMapper.selectCount(queryWrapper);
    }

    @Override
    public List<AppDO> findBySystemId(List<Long> ids, Long systemId) {
        List<AppMemberSystemDO> systemDOS = appMemberSystemRepository.findBySystemId(systemId);
        if (systemDOS == null || systemDOS.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<AppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AppDO::getId, systemDOS.stream().map(AppMemberSystemDO::getAppId).collect(Collectors.toList()))
                .in(ids != null && !ids.isEmpty(), AppDO::getId, ids)
                .eq(AppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return appMapper.selectList(queryWrapper);
    }
}