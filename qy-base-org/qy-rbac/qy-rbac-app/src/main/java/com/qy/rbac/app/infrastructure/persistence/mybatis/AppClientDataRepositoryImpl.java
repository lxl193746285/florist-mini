package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.rbac.app.infrastructure.persistence.AppClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppClientDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AppClientMapper;
import com.qy.rest.constant.LogicDeleteConstant;
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
public class AppClientDataRepositoryImpl implements AppClientDataRepository {
    private AppClientMapper appClientMapper;

    public AppClientDataRepositoryImpl(AppClientMapper appClientMapper) {
        this.appClientMapper = appClientMapper;
    }

    @Override
    public void save(AppClientDO appClientDO) {
        appClientDO.setCreateTime(LocalDateTime.now());
        appClientMapper.insert(appClientDO);
    }

    @Override
    public List<AppClientDO> findByClientId(Long clientId) {
        LambdaQueryWrapper<AppClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppClientDO::getClientId, clientId);
        return appClientMapper.selectList(queryWrapper);
    }

    @Override
    public List<AppClientDO> findByAppId(Long appId) {
        LambdaQueryWrapper<AppClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppClientDO::getAppId, appId);
        return appClientMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByAppId(Long appId) {
        LambdaQueryWrapper<AppClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppClientDO::getAppId, appId);
        appClientMapper.delete(queryWrapper);
    }

    @Override
    public List<AppClientDO> findByClientIds(List<Long> clientIds) {
        if (clientIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AppClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AppClientDO::getClientId, clientIds);
        return appClientMapper.selectList(queryWrapper);
    }

    @Override
    public int countByClientIds(List<Long> clientIds, Long excludeId) {
        LambdaQueryWrapper<AppClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AppClientDO::getClientId, clientIds);
        if (excludeId != null) {
            queryWrapper.ne(AppClientDO::getAppId, excludeId);
        }
        return appClientMapper.selectCount(queryWrapper);
    }
}