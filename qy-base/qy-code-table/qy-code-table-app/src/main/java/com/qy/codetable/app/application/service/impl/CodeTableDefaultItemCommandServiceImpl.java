package com.qy.codetable.app.application.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.codetable.app.application.command.CreateCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.enums.ValueType;
import com.qy.codetable.app.application.service.CodeTableDefaultItemCommandService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDataRepository;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDefaultItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDefaultItemDO;
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
 * 代码表默认项命令服务实现
 *
 * @author legendjw
 */
@Service
public class CodeTableDefaultItemCommandServiceImpl implements CodeTableDefaultItemCommandService {
    private CodeTableDefaultItemDataRepository codeTableDefaultItemDataRepository;
    private CodeTableDataRepository codeTableDataRepository;
    private OrgDatasourceClient orgDatasourceClient;

    public CodeTableDefaultItemCommandServiceImpl(CodeTableDefaultItemDataRepository codeTableDefaultItemDataRepository,
                                                  CodeTableDataRepository codeTableDataRepository,
                                                  OrgDatasourceClient orgDatasourceClient) {
        this.codeTableDefaultItemDataRepository = codeTableDefaultItemDataRepository;
        this.codeTableDataRepository = codeTableDataRepository;
        this.orgDatasourceClient = orgDatasourceClient;
    }

    @Override
    public Long createCodeTableItem(CreateCodeTableDefaultItemCommand command) {
        Identity user = command.getUser();
        CodeTableDO codeTableDO = codeTableDataRepository.findByTypeAndCode(command.getType(), command.getCode());
        if (codeTableDO == null) {
            throw new ValidationException("未找到指定的代码表");
        }
        //如果是数字类型的值，且值为空则自动计算下一个值
        if (command.getValue() == null && codeTableDO.getValueType().equals(ValueType.NUMBER.name())) {
            int maxValue = codeTableDefaultItemDataRepository.getMaxValue(command.getType(), command.getCode());
            command.setValue(String.valueOf(maxValue + 1));
        }
        if (codeTableDefaultItemDataRepository.countByTypeAndRelatedAndCodeAndName(command.getType(), command.getCode(), command.getName(), null) > 0) {
            throw new ValidationException("指定代码名称已经存在");
        }
        if (codeTableDefaultItemDataRepository.countByTypeAndRelatedAndCodeAndValue(command.getType(), command.getCode(), command.getValue(), null) > 0) {
            throw new ValidationException("指定代码值已经存在");
        }
        CodeTableDefaultItemDO itemDO = new CodeTableDefaultItemDO();
        BeanUtils.copyProperties(command, itemDO, "user");
        itemDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        itemDO.setCreateTime(LocalDateTime.now());
        itemDO.setCreatorId(user.getId());
        itemDO.setCreatorName(user.getName());
        List<String> dataSources = getDataSources();
        for (String dataSource : dataSources){
            DynamicDataSourceContextHolder.push(dataSource);
            codeTableDefaultItemDataRepository.save(itemDO);
            DynamicDataSourceContextHolder.clear();
        }
        return itemDO.getId();
    }

    @Override
    public void updateCodeTableItem(UpdateCodeTableDefaultItemCommand command) {
        Identity user = command.getUser();
        CodeTableDefaultItemDO itemDO = codeTableDefaultItemDataRepository.findById(command.getId());
        if (itemDO == null) {
            throw new NotFoundException("未找到指定的代码表细项");
        }
        if (codeTableDefaultItemDataRepository.countByTypeAndRelatedAndCodeAndName(command.getType(),  command.getCode(), command.getName(), itemDO.getId()) > 0) {
            throw new ValidationException("指定的代码名称已经存在");
        }
        if (codeTableDefaultItemDataRepository.countByTypeAndRelatedAndCodeAndValue(command.getType(),  command.getCode(), command.getValue(), itemDO.getId()) > 0) {
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
        List<String> dataSources = getDataSources();
        for (String dataSource : dataSources){
            DynamicDataSourceContextHolder.push(dataSource);
            codeTableDefaultItemDataRepository.save(itemDO);
            DynamicDataSourceContextHolder.clear();
        }
    }

    @Override
    public void deleteCodeTableItem(DeleteCodeTableDefaultItemCommand command) {
        Identity user = command.getUser();
        List<String> dataSources = getDataSources();
        for (String dataSource : dataSources){
            DynamicDataSourceContextHolder.push(dataSource);
            codeTableDefaultItemDataRepository.remove(command.getId(), user);
            DynamicDataSourceContextHolder.clear();
        }
    }

    private List<String> getDataSources() {
        String datasource = DynamicDataSourceContextHolder.peek();
        Set<String> dataSources = new HashSet<>();
        List<OrgDatasourceDTO> datasources = orgDatasourceClient.getBasicOrganizationByOrgIds(new ArrayList<>());
        if (datasources != null && !datasources.isEmpty()) {
            for (OrgDatasourceDTO datasourceDTO : datasources) {
                dataSources.add(datasourceDTO.getDatasourceName());
            }
        }
        dataSources.add("arkcsd");
        dataSources.add(datasource);
        return new ArrayList<>(dataSources);
    }
}
