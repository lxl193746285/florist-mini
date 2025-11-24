package com.qy.codetable.app.infrastructure.persistence.mybatis;

import com.qy.codetable.app.application.query.CodeTableDefaultItemQuery;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDefaultItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDefaultItemDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.mapper.CodeTableDefaultItemMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class CodeTableDefaultItemDataRepositoryImpl implements CodeTableDefaultItemDataRepository {
    private CodeTableDefaultItemMapper codeTableDefaultItemMapper;

    public CodeTableDefaultItemDataRepositoryImpl(CodeTableDefaultItemMapper codeTableDefaultItemMapper) {
        this.codeTableDefaultItemMapper = codeTableDefaultItemMapper;
    }

    @Override
    public List<CodeTableDefaultItemDO> findByQuery(CodeTableDefaultItemQuery query) {
        LambdaQueryWrapper<CodeTableDefaultItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDefaultItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(CodeTableDefaultItemDO::getSort).orderByAsc(CodeTableDefaultItemDO::getCreateTime);
        if (query.getType() != null) {
            queryWrapper.eq(CodeTableDefaultItemDO::getType, query.getType());
        }
        if (query.getCode() != null) {
            queryWrapper.eq(CodeTableDefaultItemDO::getCode, query.getCode());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(CodeTableDefaultItemDO::getStatusId, query.getStatus());
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(CodeTableDefaultItemDO::getName, query.getKeywords()));
        }
        return codeTableDefaultItemMapper.selectList(queryWrapper);
    }

    @Override
    public CodeTableDefaultItemDO findById(Long id) {
        LambdaQueryWrapper<CodeTableDefaultItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDefaultItemDO::getId, id)
                .eq(CodeTableDefaultItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return codeTableDefaultItemMapper.selectOne(queryWrapper);
    }

    @Override
    public int countByTypeAndRelatedAndCodeAndValue(String type, String code, String value, Long excludeId) {
        LambdaQueryWrapper<CodeTableDefaultItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDefaultItemDO::getType, type)
                .eq(CodeTableDefaultItemDO::getCode, code)
                .eq(CodeTableDefaultItemDO::getValue, value)
                .eq(CodeTableDefaultItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(CodeTableDefaultItemDO::getId, excludeId);
        }
        return codeTableDefaultItemMapper.selectCount(queryWrapper);
    }

    @Override
    public int countByTypeAndRelatedAndCodeAndName(String type, String code, String name, Long excludeId) {
        LambdaQueryWrapper<CodeTableDefaultItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDefaultItemDO::getType, type)
                .eq(CodeTableDefaultItemDO::getCode, code)
                .eq(CodeTableDefaultItemDO::getName, name)
                .eq(CodeTableDefaultItemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(CodeTableDefaultItemDO::getId, excludeId);
        }
        return codeTableDefaultItemMapper.selectCount(queryWrapper);
    }

    @Override
    public int getMaxValue(String type, String code) {
        return codeTableDefaultItemMapper.selectMaxValue(type, code);
    }

    @Override
    public Long save(CodeTableDefaultItemDO codeTableItemDO) {
        if (codeTableItemDO.getId() == null) {
            codeTableDefaultItemMapper.insert(codeTableItemDO);
        }
        else {
            codeTableDefaultItemMapper.updateById(codeTableItemDO);
        }
        return codeTableItemDO.getId();
    }

    @Override
    public void remove(Long id, Identity user) {
        CodeTableDefaultItemDO itemDO = findById(id);
        itemDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        itemDO.setDeletorId(user.getId());
        itemDO.setDeletorName(user.getName());
        itemDO.setDeleteTime(LocalDateTime.now());
        codeTableDefaultItemMapper.updateById(itemDO);
    }

    @Override
    public void batchUpdateCode(String type, String oldCode, String newCode) {
        LambdaQueryWrapper<CodeTableDefaultItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDefaultItemDO::getType, type)
                .eq(CodeTableDefaultItemDO::getCode, oldCode);

        CodeTableDefaultItemDO itemDO = new CodeTableDefaultItemDO();
        itemDO.setCode(newCode);
        codeTableDefaultItemMapper.update(itemDO, queryWrapper);
    }
}
