package com.qy.codetable.app.infrastructure.persistence.mybatis;

import com.qy.codetable.app.application.query.CodeTableQuery;
import com.qy.codetable.app.infrastructure.persistence.CodeTableCategoryDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.mapper.CodeTableMapper;
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
public class CodeTableDataRepositoryImpl implements CodeTableDataRepository {
    private CodeTableMapper codeTableMapper;
    private CodeTableCategoryDataRepository codeTableCategoryDataRepository;

    public CodeTableDataRepositoryImpl(CodeTableMapper codeTableMapper, CodeTableCategoryDataRepository codeTableCategoryDataRepository) {
        this.codeTableMapper = codeTableMapper;
        this.codeTableCategoryDataRepository = codeTableCategoryDataRepository;
    }

    @Override
    public List<CodeTableDO> findByQuery(CodeTableQuery query) {
        LambdaQueryWrapper<CodeTableDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(CodeTableDO::getSort).orderByAsc(CodeTableDO::getCreateTime);
        if (query.getType() != null) {
            queryWrapper.eq(CodeTableDO::getType, query.getType());
        }
        if (query.getCategoryId() != null) {
            queryWrapper.eq(CodeTableDO::getCategoryId, query.getCategoryId());
        }
        if (StringUtils.isNotBlank(query.getCategoryCode())) {
            CodeTableCategoryDO categoryDO = codeTableCategoryDataRepository.findByCode(query.getCategoryCode());
            if (categoryDO != null) {
                queryWrapper.eq(CodeTableDO::getCategoryId, categoryDO.getId());
            }
            else {
                queryWrapper.eq(CodeTableDO::getCategoryId, 0);
            }
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(CodeTableDO::getName, query.getKeywords()).or().like(CodeTableDO::getCode, query.getKeywords()));
        }
        return codeTableMapper.selectList(queryWrapper);
    }

    @Override
    public CodeTableDO findById(Long id) {
        LambdaQueryWrapper<CodeTableDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDO::getId, id)
                .eq(CodeTableDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return codeTableMapper.selectOne(queryWrapper);
    }

    @Override
    public CodeTableDO findByTypeAndCode(String type, String code) {
        LambdaQueryWrapper<CodeTableDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDO::getType, type)
                .eq(CodeTableDO::getCode, code)
                .eq(CodeTableDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return codeTableMapper.selectOne(queryWrapper);
    }

    @Override
    public int countByTypeAndCode(String type, String code, Long excludeId) {
        LambdaQueryWrapper<CodeTableDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CodeTableDO::getType, type)
                .eq(CodeTableDO::getCode, code)
                .eq(CodeTableDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(CodeTableDO::getId, excludeId);
        }
        return codeTableMapper.selectCount(queryWrapper);
    }

    @Override
    public Long save(CodeTableDO codeTableCategoryDO) {
        if (codeTableCategoryDO.getId() == null) {
            codeTableMapper.insert(codeTableCategoryDO);
        }
        else {
            codeTableMapper.updateById(codeTableCategoryDO);
        }
        return codeTableCategoryDO.getId();
    }

    @Override
    public void remove(Long id, Identity user) {
        CodeTableDO categoryDO = findById(id);
        categoryDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        categoryDO.setDeletorId(user.getId());
        categoryDO.setDeletorName(user.getName());
        categoryDO.setDeleteTime(LocalDateTime.now());
        codeTableMapper.updateById(categoryDO);
    }
}
