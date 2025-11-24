package com.qy.codetable.app.application.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.codetable.app.application.command.CreateCodeTableCategoryCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableCategoryCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableCategoryCommand;
import com.qy.codetable.app.application.service.CodeTableCategoryCommandService;
import com.qy.codetable.app.infrastructure.persistence.CodeTableCategoryDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
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
 * 代码表分类命令服务实现
 *
 * @author legendjw
 */
@Service
public class CodeTableCategoryCommandServiceImpl implements CodeTableCategoryCommandService {
    private CodeTableCategoryDataRepository codeTableCategoryDataRepository;
    private OrgDatasourceClient orgDatasourceClient;

    public CodeTableCategoryCommandServiceImpl(CodeTableCategoryDataRepository codeTableCategoryDataRepository,
                                               OrgDatasourceClient orgDatasourceClient) {
        this.codeTableCategoryDataRepository = codeTableCategoryDataRepository;
        this.orgDatasourceClient = orgDatasourceClient;
    }

    @Override
    public Long createCodeTableCategory(CreateCodeTableCategoryCommand command) {
        Identity user = command.getUser();
        if (codeTableCategoryDataRepository.countByTypeAndCode(command.getType(), command.getCode(), null) > 0) {
            throw new ValidationException("指定标示的分类已经存在");
        }

        CodeTableCategoryDO categoryDO = new CodeTableCategoryDO();
        BeanUtils.copyProperties(command, categoryDO, "user");
        categoryDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        categoryDO.setCreateTime(LocalDateTime.now());
        categoryDO.setCreatorId(user.getId());
        categoryDO.setCreatorName(user.getName());
        List<String> dataSources = getDataSources();
        for (String dataSource : dataSources){
            DynamicDataSourceContextHolder.push(dataSource);
            codeTableCategoryDataRepository.save(categoryDO);
            DynamicDataSourceContextHolder.clear();
        }

        return categoryDO.getId();
    }

    @Override
    public void updateCodeTableCategory(UpdateCodeTableCategoryCommand command) {
        Identity user = command.getUser();
        CodeTableCategoryDO categoryDO = codeTableCategoryDataRepository.findById(command.getId());
        if (categoryDO == null) {
            throw new NotFoundException("未找到指定的代码表分类");
        }
        if (codeTableCategoryDataRepository.countByTypeAndCode(command.getType(), command.getCode(), categoryDO.getId()) > 0) {
            throw new ValidationException("指定标示的分类已经存在");
        }
        BeanUtils.copyProperties(command, categoryDO, "user");
        categoryDO.setStatusName(EnableDisableStatus.getNameById(command.getStatusId()));
        categoryDO.setUpdateTime(LocalDateTime.now());
        categoryDO.setUpdatorId(user.getId());
        categoryDO.setUpdatorName(user.getName());
        List<String> dataSources = getDataSources();
        for (String dataSource : dataSources){
            DynamicDataSourceContextHolder.push(dataSource);
            codeTableCategoryDataRepository.save(categoryDO);
            DynamicDataSourceContextHolder.clear();
        }
    }

    @Override
    public void deleteCodeTableCategory(DeleteCodeTableCategoryCommand command) {
        Identity user = command.getUser();
        List<String> dataSources = getDataSources();
        for (String dataSource : dataSources){
            DynamicDataSourceContextHolder.push(dataSource);
            codeTableCategoryDataRepository.remove(command.getId(), user);
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
