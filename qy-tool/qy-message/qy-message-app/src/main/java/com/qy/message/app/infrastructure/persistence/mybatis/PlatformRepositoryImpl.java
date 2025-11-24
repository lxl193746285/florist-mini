package com.qy.message.app.infrastructure.persistence.mybatis;

import com.qy.message.app.application.query.PlatformQuery;
import com.qy.message.app.infrastructure.persistence.PlatformRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.PlatformDO;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.PlatformMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.EmployeeIdentity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息平台资源库实现
 *
 * @author legendjw
 */
@Repository
public class PlatformRepositoryImpl implements PlatformRepository {
    private PlatformMapper platformMapper;

    public PlatformRepositoryImpl(PlatformMapper platformMapper) {
        this.platformMapper = platformMapper;
    }

    @Override
    public List<PlatformDO> findAll() {
        return platformMapper.selectList(null);
    }

    @Override
    public Page<PlatformDO> findByQuery(PlatformQuery query) {
        LambdaQueryWrapper<PlatformDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PlatformDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(PlatformDO::getCreateTime);

        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(PlatformDO::getName, query.getKeywords()));
        }

        IPage<PlatformDO> iPage = platformMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<PlatformDO> findByIds(List<Long> ids) {
        return platformMapper.selectBatchIds(ids);
    }

    @Override
    public PlatformDO findById(Long id) {
        LambdaQueryWrapper<PlatformDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PlatformDO::getId, id)
                .eq(PlatformDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return platformMapper.selectOne(queryWrapper);
    }

    @Override
    public PlatformDO findByWeixinAppId(String weixinAppId) {
        return null;
    }

    @Override
    public Long save(PlatformDO platformDO) {
        if (platformDO.getId() == null) {
            platformMapper.insert(platformDO);
        }
        else {
            platformMapper.updateById(platformDO);
        }
        return platformDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity employee) {
        PlatformDO platformDO = findById(id);
        if (platformDO == null) { return; }
        platformDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        platformDO.setDeletorId(employee.getId());
        platformDO.setDeletorName(employee.getName());
        platformDO.setDeleteTime(LocalDateTime.now());
        platformMapper.updateById(platformDO);
    }

    @Override
    public int countByName(String name, Long excludeId) {
        LambdaQueryWrapper<PlatformDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PlatformDO::getName, name)
                .eq(PlatformDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(PlatformDO::getId, excludeId);
        }
        return platformMapper.selectCount(queryWrapper);
    }
}
