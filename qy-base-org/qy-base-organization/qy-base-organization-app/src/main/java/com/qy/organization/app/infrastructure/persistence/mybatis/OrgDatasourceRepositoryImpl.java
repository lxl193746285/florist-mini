package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.qy.organization.app.application.query.OrgDatasourceQuery;
import com.qy.organization.app.infrastructure.persistence.OrgDatasourceDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrgDatasourceDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.OrgDatasourceMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class OrgDatasourceRepositoryImpl implements OrgDatasourceDataRepository {
    private OrgDatasourceMapper orgDatasourceMapper;

    public OrgDatasourceRepositoryImpl(OrgDatasourceMapper orgDatasourceMapper) {
        this.orgDatasourceMapper = orgDatasourceMapper;
    }

    @Override
    public Page<OrgDatasourceDO> findPageByQuery(OrgDatasourceQuery query) {
        LambdaQueryWrapper<OrgDatasourceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrgDatasourceDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(query.getOrgId() != null, OrgDatasourceDO::getOrgId, query.getOrgId())
                .like(!StringUtils.isEmpty(query.getDatasourceName()), OrgDatasourceDO::getDatasourceName, query.getDatasourceName())
                .orderByDesc(OrgDatasourceDO::getCreateTime);
        IPage<OrgDatasourceDO> iPage = orgDatasourceMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public OrgDatasourceDO findById(Long id) {
        LambdaQueryWrapper<OrgDatasourceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrgDatasourceDO::getId, id)
                .eq(OrgDatasourceDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return orgDatasourceMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(OrgDatasourceDO orgDatasourceDO) {
        LambdaQueryWrapper<OrgDatasourceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrgDatasourceDO::getDatasourceName, orgDatasourceDO.getDatasourceName())
                .eq(OrgDatasourceDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .ne(orgDatasourceDO.getId() != null, OrgDatasourceDO::getId, orgDatasourceDO.getId());
        if (orgDatasourceMapper.selectCount(queryWrapper) > 0) {
            throw new ValidationException("数据源名称重复");
        }
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrgDatasourceDO::getOrgId, orgDatasourceDO.getOrgId())
                .eq(OrgDatasourceDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .ne(orgDatasourceDO.getId() != null, OrgDatasourceDO::getId, orgDatasourceDO.getId());
        if (orgDatasourceMapper.selectCount(queryWrapper) > 0) {
            throw new ValidationException("该组织下已存在数据源");
        }
        if (orgDatasourceDO.getId() == null) {
            orgDatasourceMapper.insert(orgDatasourceDO);
        } else {
            orgDatasourceMapper.updateById(orgDatasourceDO);
        }
    }

    @Override
    public void remove(Long id, Long userId) {
        OrgDatasourceDO orgDatasourceDO = findById(id);
        if (orgDatasourceDO == null) {
            throw new ValidationException("数据源不存在");
        }
        orgDatasourceDO.setIsDeleted(LogicDeleteConstant.DELETED);
        orgDatasourceDO.setDeleteTime(LocalDateTime.now());
        orgDatasourceDO.setDeletorId(userId);
        orgDatasourceMapper.updateById(orgDatasourceDO);
    }

    @Override
    public OrgDatasourceDO findByOrgId(Long orgId) {
        LambdaQueryWrapper<OrgDatasourceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrgDatasourceDO::getOrgId, orgId)
                .eq(OrgDatasourceDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return orgDatasourceMapper.selectOne(queryWrapper);
    }

    @Override
    public List<OrgDatasourceDO> findByOrgIds(List<Long> orgIds) {
        LambdaQueryWrapper<OrgDatasourceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(orgIds != null && !orgIds.isEmpty(), OrgDatasourceDO::getOrgId, orgIds)
                .eq(OrgDatasourceDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return orgDatasourceMapper.selectList(queryWrapper);
    }


}
