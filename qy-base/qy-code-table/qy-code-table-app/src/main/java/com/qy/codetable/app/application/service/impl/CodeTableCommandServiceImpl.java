package com.qy.codetable.app.application.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.codetable.app.application.command.CreateCodeTableCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableCommand;
import com.qy.codetable.app.application.service.CodeTableCommandService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDefaultItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 代码表命令服务实现
 *
 * @author legendjw
 */
@Service
public class CodeTableCommandServiceImpl implements CodeTableCommandService {
    private CodeTableDataRepository codeTableDataRepository;
    private CodeTableItemDataRepository codeTableItemDataRepository;
    private CodeTableDefaultItemDataRepository codeTableDefaultItemDataRepository;
    private OrgDatasourceClient orgDatasourceClient;

    public CodeTableCommandServiceImpl(CodeTableDataRepository codeTableDataRepository,
                                       CodeTableItemDataRepository codeTableItemDataRepository,
                                       CodeTableDefaultItemDataRepository codeTableDefaultItemDataRepository,
                                       OrgDatasourceClient orgDatasourceClient) {
        this.codeTableDataRepository = codeTableDataRepository;
        this.codeTableItemDataRepository = codeTableItemDataRepository;
        this.codeTableDefaultItemDataRepository = codeTableDefaultItemDataRepository;
        this.orgDatasourceClient = orgDatasourceClient;
    }

    @Override
    public Long createCodeTable(CreateCodeTableCommand command) {
        Identity user = command.getUser();
        if (codeTableDataRepository.countByTypeAndCode(command.getType(), command.getCode(), null) > 0) {
            throw new ValidationException("指定标示的代码表已经存在");
        }
        CodeTableDO tableDO = new CodeTableDO();
        BeanUtils.copyProperties(command, tableDO, "user");
        tableDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        tableDO.setCreateTime(LocalDateTime.now());
        tableDO.setCreatorId(user.getId());
        tableDO.setCreatorName(user.getName());
        codeTableDataRepository.save(tableDO);
        return tableDO.getId();
    }

    @Override
    @Transactional
    public void updateCodeTable(UpdateCodeTableCommand command) {
        Identity user = command.getUser();
        CodeTableDO tableDO = codeTableDataRepository.findById(command.getId());
        if (tableDO == null) {
            throw new NotFoundException("未找到指定的代码表");
        }
        String oldTableCode = tableDO.getCode();
        if (codeTableDataRepository.countByTypeAndCode(command.getType(), command.getCode(), tableDO.getId()) > 0) {
            throw new ValidationException("指定标示的代码表已经存在");
        }
        BeanUtils.copyProperties(command, tableDO, "user");
        tableDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        tableDO.setUpdateTime(LocalDateTime.now());
        tableDO.setUpdatorId(user.getId());
        tableDO.setUpdatorName(user.getName());
        codeTableDataRepository.save(tableDO);

        //代码表编码更新需要更改对应的代码表项
        if (!oldTableCode.equals(tableDO.getCode())) {
            codeTableItemDataRepository.batchUpdateCode(tableDO.getType(), oldTableCode, tableDO.getCode());
            codeTableDefaultItemDataRepository.batchUpdateCode(tableDO.getType(), oldTableCode, tableDO.getCode());
        }
    }

    @Override
    public void deleteCodeTable(DeleteCodeTableCommand command) {
        Identity user = command.getUser();
        codeTableDataRepository.remove(command.getId(), user);
    }
}
