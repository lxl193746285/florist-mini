package com.qy.codetable.app.application.service.impl;

import com.qy.codetable.app.application.assembler.CodeTableAssembler;
import com.qy.codetable.app.application.assembler.CodeTableCategoryAssembler;
import com.qy.codetable.app.application.dto.CodeTableCategoryDTO;
import com.qy.codetable.app.application.dto.CodeTableDTO;
import com.qy.codetable.app.application.query.CodeTableCategoryQuery;
import com.qy.codetable.app.application.query.CodeTableQuery;
import com.qy.codetable.app.application.service.CodeTableQueryService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableCategoryDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
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
public class CodeTableQueryServiceImpl implements CodeTableQueryService {
    private CodeTableDataRepository codeTableDataRepository;
    private CodeTableAssembler codeTableAssembler;
    private CodeTableCategoryDataRepository codeTableCategoryDataRepository;
    private CodeTableCategoryAssembler codeTableCategoryAssembler;

    public CodeTableQueryServiceImpl(CodeTableDataRepository codeTableDataRepository, CodeTableAssembler codeTableAssembler, CodeTableCategoryDataRepository codeTableCategoryDataRepository, CodeTableCategoryAssembler codeTableCategoryAssembler) {
        this.codeTableDataRepository = codeTableDataRepository;
        this.codeTableAssembler = codeTableAssembler;
        this.codeTableCategoryDataRepository = codeTableCategoryDataRepository;
        this.codeTableCategoryAssembler = codeTableCategoryAssembler;
    }

    @Override
    public List<CodeTableDTO> getCodeTables(CodeTableQuery query) {
        List<CodeTableDO> codeTableDOS = codeTableDataRepository.findByQuery(query);
        return codeTableDOS.stream().map(c -> codeTableAssembler.toDTO(c, null)).collect(Collectors.toList());
    }

    @Override
    public List<CodeTableCategoryDTO> getCodeTablesGroupByCategory(String type, Identity identity) {
        //查找分类
        CodeTableCategoryQuery categoryQuery = new CodeTableCategoryQuery();
        categoryQuery.setType(type);
        List<CodeTableCategoryDO> categoryDOS = codeTableCategoryDataRepository.findByQuery(categoryQuery);
        List<CodeTableCategoryDTO> codeTableCategoryDTOS = codeTableCategoryAssembler.toDTOs(categoryDOS, identity);

        //查找代码表
        CodeTableQuery codeTableQuery = new CodeTableQuery();
        codeTableQuery.setType(type);
        List<CodeTableDO> codeTableDOS = codeTableDataRepository.findByQuery(codeTableQuery);

        //组装
        for (CodeTableDO codeTableDO : codeTableDOS) {
            CodeTableCategoryDTO categoryDTO = codeTableCategoryDTOS.stream().filter(c -> c.getId().equals(codeTableDO.getCategoryId())).findFirst().orElse(null);
            if (categoryDTO != null) {
                categoryDTO.getCodeTables().add(codeTableAssembler.toDTO(codeTableDO, identity));
            }
        }

        return codeTableCategoryDTOS;
    }

    @Override
    public CodeTableDTO getCodeTableById(Long id) {
        CodeTableDO codeTableDO = codeTableDataRepository.findById(id);
        return codeTableAssembler.toDTO(codeTableDO, null);
    }
}
