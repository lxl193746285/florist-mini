package com.qy.codetable.app.application.service.impl;

import com.qy.codetable.app.application.assembler.CodeTableItemAssembler;
import com.qy.codetable.app.application.dto.CodeTableItemBasicDTO;
import com.qy.codetable.app.application.dto.CodeTableItemDTO;
import com.qy.codetable.app.application.enums.CodeTableType;
import com.qy.codetable.app.application.enums.StructureType;
import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.codetable.app.application.service.CodeTableItemQueryService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableItemDO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码表查询服务实现
 *
 * @author legendjw
 */
@Service
public class CodeTableItemQueryServiceImpl implements CodeTableItemQueryService {
    private CodeTableItemDataRepository codeTableItemDataRepository;
    private CodeTableItemAssembler codeTableItemAssembler;
    private CodeTableDataRepository codeTableDataRepository;

    public CodeTableItemQueryServiceImpl(CodeTableItemDataRepository codeTableItemDataRepository, CodeTableItemAssembler codeTableItemAssembler, CodeTableDataRepository codeTableDataRepository) {
        this.codeTableItemDataRepository = codeTableItemDataRepository;
        this.codeTableItemAssembler = codeTableItemAssembler;
        this.codeTableDataRepository = codeTableDataRepository;
    }

    @Override
    public Page<CodeTableItemDTO> getCodeTableItems(CodeTableItemQuery query) {
        Page<CodeTableItemDO> codeTableItemDOPage = codeTableItemDataRepository.findByQuery(query);
        Page<CodeTableItemDTO> customerDTOPage = codeTableItemDOPage.map(c -> codeTableItemAssembler.toDTO(c, null));
        return customerDTOPage;
    }

    @Override
    public List<CodeTableItemDTO> getAllCodeTableItems(CodeTableItemQuery query) {
        List<CodeTableItemDO> codeTableItemDOS = codeTableItemDataRepository.findAllByQuery(query);
        return codeTableItemDOS.stream().map(c -> codeTableItemAssembler.toDTO(c, null)).collect(Collectors.toList());
    }

    @Override
    public List<CodeTableItemBasicDTO> getBasicCodeTableItems(CodeTableItemQuery query) {
        CodeTableDO codeTableDO = codeTableDataRepository.findByTypeAndCode(query.getType(), query.getCode());
        if (codeTableDO == null) {
            return new ArrayList<>();
//            throw new ValidationException("指定的代码表未找到");
        }
        query.setStatus(EnableDisableStatus.ENABLE.getId());
        List<CodeTableItemDO> codeTableItemDOS = codeTableItemDataRepository.findAllByQuery(query);
        //列表
        if (codeTableDO.getStructureType().equals(StructureType.LIST.name())) {
            return codeTableItemDOS.stream().map(c -> codeTableItemAssembler.toClientDTO(c)).collect(Collectors.toList());
        }
        //树形
        else {
            return codeTableItemDOS.stream().map(c -> {
                //父级项
                CodeTableItemDO parentItem = codeTableItemDOS.stream().filter(ci -> ci.getId().equals(c.getParentId())).findFirst().orElse(null);

                CodeTableItemBasicDTO codeTableItemBasicDTO = codeTableItemAssembler.toClientDTO(c);
                codeTableItemBasicDTO.setParentId(parentItem != null ? parentItem.getValue() : "0");
                return codeTableItemBasicDTO;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public CodeTableItemDTO getCodeTableItemById(Long id) {
        CodeTableItemDO codeTableDO = codeTableItemDataRepository.findById(id);
        return codeTableItemAssembler.toDTO(codeTableDO, null);
    }

    @Override
    public CodeTableItemBasicDTO getSystemCodeTableItem(String code, String value) {
        CodeTableItemDO codeTableItemDO = codeTableItemDataRepository.findByTypeAndCodeAndValue(CodeTableType.SYSTEM.name(), 0L, code, value);
        if (codeTableItemDO == null) {
            return null;
        }
        return codeTableItemAssembler.toClientDTO(codeTableItemDO);
    }

    @Override
    public CodeTableItemBasicDTO getOrganizationCodeTableItem(Long organizationId, String code, String value) {
        CodeTableItemDO codeTableItemDO = codeTableItemDataRepository.findByTypeAndCodeAndValue(CodeTableType.ORGANIZATION.name(), organizationId, code, value);
        if (codeTableItemDO == null) {
            return null;
        }
        return codeTableItemAssembler.toClientDTO(codeTableItemDO);
    }

    @Override
    public CodeTableItemBasicDTO getPersonalCodeTableItem(Long userId, String code, String value) {
        CodeTableItemDO codeTableItemDO = codeTableItemDataRepository.findByTypeAndCodeAndValue(CodeTableType.PERSONAL.name(), userId, code, value);
        if (codeTableItemDO == null) {
            return null;
        }
        return codeTableItemAssembler.toClientDTO(codeTableItemDO);
    }
}
