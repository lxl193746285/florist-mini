package com.qy.codetable.app.application.service.impl;

import com.qy.codetable.app.application.assembler.CodeTableCategoryAssembler;
import com.qy.codetable.app.application.dto.CodeTableCategoryDTO;
import com.qy.codetable.app.application.query.CodeTableCategoryQuery;
import com.qy.codetable.app.application.service.CodeTableCategoryQueryService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableCategoryDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
import com.qy.security.session.Identity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码表查询服务实现
 *
 * @author legendjw
 */
@Service
public class CodeTableCategoryQueryServiceImpl implements CodeTableCategoryQueryService {
    private CodeTableCategoryDataRepository codeTableCategoryDataRepository;
    private CodeTableCategoryAssembler codeTableCategoryAssembler;

    public CodeTableCategoryQueryServiceImpl(CodeTableCategoryDataRepository codeTableCategoryDataRepository, CodeTableCategoryAssembler codeTableCategoryAssembler) {
        this.codeTableCategoryDataRepository = codeTableCategoryDataRepository;
        this.codeTableCategoryAssembler = codeTableCategoryAssembler;
    }

    @Override
    public List<CodeTableCategoryDTO> getCodeTableCategorys(CodeTableCategoryQuery query, Identity identity) {
        List<CodeTableCategoryDO> codeTableCategoryDOS = codeTableCategoryDataRepository.findByQuery(query);
        return codeTableCategoryDOS.stream().map(c -> codeTableCategoryAssembler.toDTO(c, null)).collect(Collectors.toList());
    }

    @Override
    public CodeTableCategoryDTO getCodeTableCategoryById(Long id) {
        CodeTableCategoryDO codeTableCategoryDO = codeTableCategoryDataRepository.findById(id);
        return codeTableCategoryAssembler.toDTO(codeTableCategoryDO, null);
    }
}