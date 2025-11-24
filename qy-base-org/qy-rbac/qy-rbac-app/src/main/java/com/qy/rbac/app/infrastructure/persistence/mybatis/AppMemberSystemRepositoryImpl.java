package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.rbac.app.application.query.AppMemberSystemQuery;
import com.qy.rbac.app.infrastructure.persistence.AppMemberSystemRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppMemberSystemDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AppMemberSystemMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.Identity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class AppMemberSystemRepositoryImpl implements AppMemberSystemRepository {
    private AppMemberSystemMapper appMemberSystemMapper;

    public AppMemberSystemRepositoryImpl(AppMemberSystemMapper appMemberSystemMapper) {
        this.appMemberSystemMapper = appMemberSystemMapper;
    }

    @Override
    public List<AppMemberSystemDO> findByQuery(AppMemberSystemQuery query) {
        LambdaQueryWrapper<AppMemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppMemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(query.getAppId() != null, AppMemberSystemDO::getAppId, query.getAppId())
                .eq(query.getSystemId() != null, AppMemberSystemDO::getMbrSysId, query.getSystemId())
                .eq(query.getOrganizationId() != null, AppMemberSystemDO::getOrganizationId, query.getOrganizationId())
                .orderByAsc(AppMemberSystemDO::getCreateTime);
        return appMemberSystemMapper.selectList(queryWrapper);
    }

    @Override
    public List<AppMemberSystemDO> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AppMemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppMemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(AppMemberSystemDO::getId, ids)
                .orderByAsc(AppMemberSystemDO::getCreateTime);
        return appMemberSystemMapper.selectList(queryWrapper);
    }

    @Override
    public AppMemberSystemDO findById(Long id) {
        LambdaQueryWrapper<AppMemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppMemberSystemDO::getId, id)
                .eq(AppMemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return appMemberSystemMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(AppMemberSystemDO appMemberSystemDO) {
        if (appMemberSystemDO.getId() == null) {
            appMemberSystemMapper.insert(appMemberSystemDO);
        } else {
            appMemberSystemMapper.updateById(appMemberSystemDO);
        }
        return appMemberSystemDO.getId();
    }

    @Override
    public void remove(Long id, Identity identity) {
        AppMemberSystemDO appMemberSystemDO = findById(id);
        appMemberSystemDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        appMemberSystemDO.setDeletorId(identity.getId());
        appMemberSystemDO.setDeleteTime(LocalDateTime.now());
        appMemberSystemMapper.updateById(appMemberSystemDO);
    }

    @Override
    public AppMemberSystemDO findByAppIdAndOrganizationId(Long appId, Long organizationId, Long systemId) {
        LambdaQueryWrapper<AppMemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppMemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(AppMemberSystemDO::getAppId, appId)
                .eq(AppMemberSystemDO::getMbrSysId, systemId)
                .eq(AppMemberSystemDO::getOrganizationId, organizationId);
        return appMemberSystemMapper.selectOne(queryWrapper);
    }

    @Override
    public void removeByAppId(Long appId, Identity identity) {
        LambdaQueryWrapper<AppMemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppMemberSystemDO::getAppId, appId);
        AppMemberSystemDO appMemberSystemDO = new AppMemberSystemDO();
        appMemberSystemDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        appMemberSystemDO.setDeletorId(identity.getId());
        appMemberSystemDO.setDeleteTime(LocalDateTime.now());
        appMemberSystemMapper.update(appMemberSystemDO, queryWrapper);
    }

    @Override
    public List<AppMemberSystemDO> findBySystemId(Long systemId) {
        LambdaQueryWrapper<AppMemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppMemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(systemId != null, AppMemberSystemDO::getMbrSysId, systemId);
        return appMemberSystemMapper.selectList(queryWrapper);
    }

    @Override
    public AppMemberSystemDO findByAppId(Long appId) {
        LambdaQueryWrapper<AppMemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AppMemberSystemDO::getAppId, appId)
                .eq(AppMemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return appMemberSystemMapper.selectOne(queryWrapper);
    }
}