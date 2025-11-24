package com.qy.codetable.app.infrastructure.persistence.mybatis;

import com.qy.codetable.app.application.query.CodeTableCategoryQuery;
import com.qy.codetable.app.infrastructure.persistence.CodeTableCategoryDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.mapper.CodeTableCategoryMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码表分类资源库
 *
 * @author legendjw
 */
@Repository
public class CodeTableCategoryDataRepositoryImpl implements CodeTableCategoryDataRepository {
    private CodeTableCategoryMapper codeTableCategoryMapper;

    public CodeTableCategoryDataRepositoryImpl(CodeTableCategoryMapper codeTableCategoryMapper) {
        this.codeTableCategoryMapper = codeTableCategoryMapper;
    }

    @Override
    public List<CodeTableCategoryDO> findByQuery(CodeTableCategoryQuery query) {
        LambdaQueryWrapper<CodeTableCategoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableCategoryDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(CodeTableCategoryDO::getSort).orderByAsc(CodeTableCategoryDO::getCreateTime);
        if (query.getType() != null) {
            queryWrapper.eq(CodeTableCategoryDO::getType, query.getType());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(CodeTableCategoryDO::getStatusId, query.getStatus());
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.like(CodeTableCategoryDO::getName, query.getKeywords());
        }
        return codeTableCategoryMapper.selectList(queryWrapper);
    }

    @Override
    public CodeTableCategoryDO findById(Long id) {
        LambdaQueryWrapper<CodeTableCategoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableCategoryDO::getId, id)
                .eq(CodeTableCategoryDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return codeTableCategoryMapper.selectOne(queryWrapper);
    }

    @Override
    public CodeTableCategoryDO findByCode(String code) {
        LambdaQueryWrapper<CodeTableCategoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableCategoryDO::getCode, code)
                .eq(CodeTableCategoryDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return codeTableCategoryMapper.selectOne(queryWrapper);
    }

    @Override
    public int countByTypeAndCode(String type, String code, Long excludeId) {
        LambdaQueryWrapper<CodeTableCategoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableCategoryDO::getType, type)
                .eq(CodeTableCategoryDO::getCode, code)
                .eq(CodeTableCategoryDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(CodeTableCategoryDO::getId, excludeId);
        }
        return codeTableCategoryMapper.selectCount(queryWrapper);
    }

    @Override
    public Long save(CodeTableCategoryDO codeTableCategoryDO) {
        if (codeTableCategoryDO.getId() == null) {
            codeTableCategoryMapper.insert(codeTableCategoryDO);
        }
        else {
            codeTableCategoryMapper.updateById(codeTableCategoryDO);
        }
        return codeTableCategoryDO.getId();
    }

    @Override
    public void remove(Long id, Identity user) {
        CodeTableCategoryDO categoryDO = findById(id);
        categoryDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        categoryDO.setDeletorId(user.getId());
        categoryDO.setDeletorName(user.getName());
        categoryDO.setDeleteTime(LocalDateTime.now());
        codeTableCategoryMapper.updateById(categoryDO);
    }
}
