package com.qy.codetable.app.application.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.codetable.app.application.command.BatchDeleteCodeTableItemCommand;
import com.qy.codetable.app.application.command.CreateCodeTableItemCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableItemCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableItemCommand;
import com.qy.codetable.app.application.enums.ValueType;
import com.qy.codetable.app.application.service.CodeTableItemCommandService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableItemDO;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class CodeTableItemCommandServiceImpl implements CodeTableItemCommandService {
    private CodeTableItemDataRepository codeTableItemDataRepository;
    private CodeTableDataRepository codeTableDataRepository;

    public CodeTableItemCommandServiceImpl(CodeTableItemDataRepository codeTableItemDataRepository,
                                           CodeTableDataRepository codeTableDataRepository,
                                           OrgDatasourceClient orgDatasourceClient) {
        this.codeTableItemDataRepository = codeTableItemDataRepository;
        this.codeTableDataRepository = codeTableDataRepository;
    }

    @Override
    public Long createCodeTableItem(CreateCodeTableItemCommand command) {
        Identity user = command.getUser();
        CodeTableDO codeTableDO = codeTableDataRepository.findByTypeAndCode(command.getType(), command.getCode());
        if (codeTableDO == null) {
            throw new ValidationException("未找到指定的代码表");
        }
        //如果是数字类型的值，且值为空则自动计算下一个值
        if (command.getValue() == null && codeTableDO.getValueType().equals(ValueType.NUMBER.name())) {
            int maxValue = codeTableItemDataRepository.getMaxValue(command.getType(), command.getRelatedId(), command.getCode());
            command.setValue(String.valueOf(maxValue + 1));
        }
        if (codeTableItemDataRepository.countByTypeAndRelatedAndCodeAndName(command.getType(), command.getRelatedId(), command.getCode(), command.getName(), null) > 0) {
            throw new ValidationException("指定代码名称已经存在");
        }
        if (codeTableItemDataRepository.countByTypeAndRelatedAndCodeAndValue(command.getType(), command.getRelatedId(), command.getCode(), command.getValue(), null) > 0) {
            throw new ValidationException("指定代码值已经存在");
        }
        CodeTableItemDO itemDO = new CodeTableItemDO();
        BeanUtils.copyProperties(command, itemDO, "user");
        itemDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        itemDO.setCreateTime(LocalDateTime.now());
        itemDO.setCreatorId(user.getId());
        itemDO.setCreatorName(user.getName());
        codeTableItemDataRepository.save(itemDO);
        return itemDO.getId();
    }

    @Override
    public void updateCodeTableItem(UpdateCodeTableItemCommand command) {
        Identity user = command.getUser();

        CodeTableItemDO itemDO = codeTableItemDataRepository.findById(command.getId());
        if (itemDO == null) {
            throw new NotFoundException("未找到指定的代码表细项");
        }
        if (codeTableItemDataRepository.countByTypeAndRelatedAndCodeAndName(command.getType(), command.getRelatedId(), command.getCode(), command.getName(), itemDO.getId()) > 0) {
            throw new ValidationException("指定的代码名称已经存在");
        }
        if (codeTableItemDataRepository.countByTypeAndRelatedAndCodeAndValue(command.getType(), command.getRelatedId(), command.getCode(), command.getValue(), itemDO.getId()) > 0) {
            throw new ValidationException("指定的代码值已经存在");
        }
        if (command.getParentId() != null && command.getParentId().equals(itemDO.getId())) {
            throw new ValidationException("不能选择自己作为父级");
        }
        BeanUtils.copyProperties(command, itemDO, "user");
        itemDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        itemDO.setUpdateTime(LocalDateTime.now());
        itemDO.setUpdatorId(user.getId());
        itemDO.setUpdatorName(user.getName());
        codeTableItemDataRepository.save(itemDO);
    }

    @Override
    public void deleteCodeTableItem(DeleteCodeTableItemCommand command) {
        Identity user = command.getUser();
        if (codeTableItemDataRepository.countByParentId(command.getId()) > 0) {
            throw new ValidationException("指定删除的代码项下含有子项目无法删除");
        }
        codeTableItemDataRepository.remove(command.getId(), user);
    }

    @Override
    public void deleteCodeTableItem(BatchDeleteCodeTableItemCommand command) {
        Identity user = command.getUser();
        for (Long id : command.getIds()) {
            if (codeTableItemDataRepository.countByParentId(id) > 0) {
                throw new ValidationException("指定删除的代码项下含有子项目无法删除");
            }
        }
        codeTableItemDataRepository.remove(command.getIds(), user);
    }
}
