package com.qy.codetable.app.infrastructure.persistence.mybatis;

import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.codetable.app.infrastructure.persistence.CodeTableItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableItemDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.mapper.CodeTableItemMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码表资源库
 *
 * @author legendjw
 */
@Repository
public class CodeTableItemDataRepositoryImpl implements CodeTableItemDataRepository {
    private CodeTableItemMapper codeTableItemMapper;

    public CodeTableItemDataRepositoryImpl(CodeTableItemMapper codeTableItemMapper) {
        this.codeTableItemMapper = codeTableItemMapper;
    }

    @Override
    public Page<CodeTableItemDO> findByQuery(CodeTableItemQuery query) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(CodeTableItemDO::getSort).orderByAsc(CodeTableItemDO::getCreateTime);
        if (query.getType() != null) {
            queryWrapper.eq(CodeTableItemDO::getType, query.getType());
        }
        if (query.getRelatedId() != null) {
            queryWrapper.eq(CodeTableItemDO::getRelatedId, query.getRelatedId());
        }
        if (query.getCode() != null) {
            queryWrapper.eq(CodeTableItemDO::getCode, query.getCode());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(CodeTableItemDO::getStatusId, query.getStatus());
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(CodeTableItemDO::getName, query.getKeywords()));
        }
        IPage<CodeTableItemDO> iPage = codeTableItemMapper.selectPage(
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(query.getPage(), query.getPerPage()),
                queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<CodeTableItemDO> findAllByQuery(CodeTableItemQuery query) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(CodeTableItemDO::getSort).orderByAsc(CodeTableItemDO::getCreateTime);
        if (query.getType() != null) {
            queryWrapper.eq(CodeTableItemDO::getType, query.getType());
        }
        if (query.getRelatedId() != null) {
            queryWrapper.eq(CodeTableItemDO::getRelatedId, query.getRelatedId());
        }
        if (query.getCode() != null) {
            queryWrapper.eq(CodeTableItemDO::getCode, query.getCode());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(CodeTableItemDO::getStatusId, query.getStatus());
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(CodeTableItemDO::getName, query.getKeywords()));
        }
        if (query.getExcludes() != null && !query.getExcludes().isEmpty()) {
            queryWrapper.notIn(CodeTableItemDO::getValue, query.getExcludes());
        }
        return codeTableItemMapper.selectList(queryWrapper);
    }

    @Override
    public CodeTableItemDO findById(Long id) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getId, id)
                .eq(CodeTableItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return codeTableItemMapper.selectOne(queryWrapper);
    }

    @Override
    public CodeTableItemDO findByTypeAndCodeAndValue(String type, Long relatedId, String code, String value) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getType, type)
                .eq(CodeTableItemDO::getRelatedId, relatedId)
                .eq(CodeTableItemDO::getCode, code)
                .eq(CodeTableItemDO::getValue, value)
                .eq(CodeTableItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return codeTableItemMapper.selectOne(queryWrapper);
    }

    @Override
    public int countByTypeAndRelatedAndCodeAndValue(String type, Long relatedId, String code, String value, Long excludeId) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getType, type)
                .eq(CodeTableItemDO::getRelatedId, relatedId)
                .eq(CodeTableItemDO::getCode, code)
                .eq(CodeTableItemDO::getValue, value)
                .eq(CodeTableItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(CodeTableItemDO::getId, excludeId);
        }
        return codeTableItemMapper.selectCount(queryWrapper);
    }

    @Override
    public int countByTypeAndRelatedAndCodeAndName(String type, Long relatedId, String code, String name, Long excludeId) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getType, type)
                .eq(CodeTableItemDO::getRelatedId, relatedId)
                .eq(CodeTableItemDO::getCode, code)
                .eq(CodeTableItemDO::getName, name)
                .eq(CodeTableItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(CodeTableItemDO::getId, excludeId);
        }
        return codeTableItemMapper.selectCount(queryWrapper);
    }

    @Override
    public int countByParentId(Long parentId) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getParentId, parentId)
                .eq(CodeTableItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return codeTableItemMapper.selectCount(queryWrapper);
    }

    @Override
    public int getMaxValue(String type, Long relatedId, String code) {
        return codeTableItemMapper.selectMaxValue(type, relatedId, code);
    }

    @Override
    public Long save(CodeTableItemDO codeTableItemDO) {
        if (codeTableItemDO.getId() == null) {
            codeTableItemMapper.insert(codeTableItemDO);
        }
        else {
            codeTableItemMapper.updateById(codeTableItemDO);
        }
        return codeTableItemDO.getId();
    }

    @Override
    public void remove(Long id, Identity user) {
        CodeTableItemDO itemDO = findById(id);
        itemDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        itemDO.setDeletorId(user.getId());
        itemDO.setDeletorName(user.getName());
        itemDO.setDeleteTime(LocalDateTime.now());
        codeTableItemMapper.updateById(itemDO);
    }

    @Override
    public void remove(List<Long> ids, Identity user) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CodeTableItemDO::getId, ids);

        CodeTableItemDO itemDO = new CodeTableItemDO();
        itemDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        itemDO.setDeletorId(user.getId());
        itemDO.setDeletorName(user.getName());
        itemDO.setDeleteTime(LocalDateTime.now());
        codeTableItemMapper.update(itemDO, queryWrapper);
    }

    @Override
    public void batchUpdateCode(String type, String oldCode, String newCode) {
        LambdaQueryWrapper<CodeTableItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableItemDO::getType, type)
                .eq(CodeTableItemDO::getCode, oldCode);

        CodeTableItemDO itemDO = new CodeTableItemDO();
        itemDO.setCode(newCode);
        codeTableItemMapper.update(itemDO, queryWrapper);
    }
}
