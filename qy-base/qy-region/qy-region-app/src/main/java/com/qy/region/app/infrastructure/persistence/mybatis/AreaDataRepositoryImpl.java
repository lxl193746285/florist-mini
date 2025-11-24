package com.qy.region.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.region.app.application.query.AreaQuery;
import com.qy.region.app.infrastructure.persistence.AreaDataRepository;
import com.qy.region.app.infrastructure.persistence.mybatis.dataobject.AreaDO;
import com.qy.region.app.infrastructure.persistence.mybatis.mapper.AreaMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 地区数据资源库
 *
 * @author legendjw
 */
@Repository
public class AreaDataRepositoryImpl implements AreaDataRepository {
    private AreaMapper areaMapper;

    public AreaDataRepositoryImpl(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }

    @Override
    public List<AreaDO> findByQuery(AreaQuery query) {
        LambdaQueryWrapper<AreaDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AreaDO::getParentId, query.getParentId() == null ? 0 : query.getParentId())
                .like(StringUtils.isNotBlank(query.getKeywords()), AreaDO::getName, query.getKeywords())
                .eq(query.getLevel() != null, AreaDO::getLevel, query.getLevel())
                .orderByAsc(AreaDO::getSort)
                .orderByAsc(AreaDO::getId);
        return areaMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<AreaDO> findByQueryPage(AreaQuery query) {
        IPage<AreaDO> iPage = new Page<>(query.getPage(), query.getPerPage());
        LambdaQueryWrapper<AreaDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AreaDO::getParentId, query.getParentId() == null ? 0 : query.getParentId())
                .like(StringUtils.isNotBlank(query.getKeywords()), AreaDO::getName, query.getKeywords())
                .eq(query.getLevel() != null, AreaDO::getLevel, query.getLevel())
                .orderByAsc(AreaDO::getSort)
                .orderByAsc(AreaDO::getId);
        return areaMapper.selectPage(iPage, queryWrapper);
    }

    @Override
    public AreaDO findById(Long areaId) {
        LambdaQueryWrapper<AreaDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AreaDO::getId, areaId)
                .last("limit 1");
        return areaMapper.selectOne(queryWrapper);
    }

    @Override
    public AreaDO findByCode(String code) {
        LambdaQueryWrapper<AreaDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AreaDO::getCode, code)
                .last("limit 1");
        return areaMapper.selectOne(queryWrapper);
    }

    @Override
    public AreaDO findByCondition(AreaQuery query) {
        LambdaQueryWrapper<AreaDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AreaDO::getParentId, query.getParentId() == null ? 0 : query.getParentId())
                .eq(StringUtils.isNotBlank(query.getKeywords()), AreaDO::getName, query.getKeywords());
        return areaMapper.selectOne(queryWrapper);
    }

    @Override
    public void createArea(AreaDO areaDO) {
        areaMapper.insert(areaDO);
    }

    @Override
    public void updateArea(AreaDO areaDO) {
        areaMapper.updateById(areaDO);
    }

    @Override
    public void deleteArea(Long id) {
        areaMapper.deleteById(id);
    }

    @Override
    public boolean hasChildren(Long id) {
        LambdaQueryWrapper<AreaDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AreaDO::getParentId, id);
        return areaMapper.selectCount(queryWrapper) > 0;
    }
}
