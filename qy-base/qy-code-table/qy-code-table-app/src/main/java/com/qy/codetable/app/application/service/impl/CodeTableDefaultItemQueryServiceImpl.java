package com.qy.codetable.app.application.service.impl;

import com.qy.codetable.app.application.assembler.CodeTableDefaultItemAssembler;
import com.qy.codetable.app.application.dto.CodeTableDefaultItemDTO;
import com.qy.codetable.app.application.query.CodeTableDefaultItemQuery;
import com.qy.codetable.app.application.service.CodeTableDefaultItemQueryService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDefaultItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDefaultItemDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码表默认项查询服务实现
 *
 * @author legendjw
 */
@Service
public class CodeTableDefaultItemQueryServiceImpl implements CodeTableDefaultItemQueryService {
    private CodeTableDefaultItemDataRepository codeTableDefaultItemDataRepository;
    private CodeTableDefaultItemAssembler codeTableDefaultItemAssembler;

    public CodeTableDefaultItemQueryServiceImpl(CodeTableDefaultItemDataRepository codeTableDefaultItemDataRepository,
                                                CodeTableDefaultItemAssembler codeTableDefaultItemAssembler) {
        this.codeTableDefaultItemDataRepository = codeTableDefaultItemDataRepository;
        this.codeTableDefaultItemAssembler = codeTableDefaultItemAssembler;
    }

    @Override
    public List<CodeTableDefaultItemDTO> getCodeTableItems(CodeTableDefaultItemQuery query) {
        List<CodeTableDefaultItemDO> codeTableItemDOS = codeTableDefaultItemDataRepository.findByQuery(query);
        return codeTableItemDOS.stream().map(c -> codeTableDefaultItemAssembler.toDTO(c, null)).collect(Collectors.toList());
    }

    @Override
    public CodeTableDefaultItemDTO getCodeTableItemById(Long id) {
        CodeTableDefaultItemDO codeTableDO = codeTableDefaultItemDataRepository.findById(id);
        return codeTableDefaultItemAssembler.toDTO(codeTableDO, null);
    }
}