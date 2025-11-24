package com.qy.rbac.app.application.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.rbac.app.application.command.CreateModuleCommand;
import com.qy.rbac.app.application.command.DeleteModuleCommand;
import com.qy.rbac.app.application.command.UpdateModuleCommand;
import com.qy.rbac.app.application.service.ModuleCommandService;
import com.qy.rbac.app.infrastructure.persistence.ModuleDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ModuleDO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 模块命令实现
 *
 * @author legendjw
 */
@Service
public class ModuleCommandServiceImpl implements ModuleCommandService {
    private ModuleDataRepository moduleDataRepository;
    private CodeTableClient codeTableClient;

    public ModuleCommandServiceImpl(ModuleDataRepository moduleDataRepository, CodeTableClient codeTableClient) {
        this.moduleDataRepository = moduleDataRepository;
        this.codeTableClient = codeTableClient;
    }

    @Override
    @Transactional
    public Long createModule(CreateModuleCommand command) {
        Identity identity = command.getIdentity();
        if (moduleDataRepository.countByName(command.getName(), null) > 0) {
            throw new ValidationException("指定模块名称已经存在，请更换新的名称");
        }

        ModuleDO moduleDO = new ModuleDO();
        BeanUtils.copyProperties(command, moduleDO, "identity");
        moduleDO.setStatusName(EnableDisableStatus.getNameById(moduleDO.getStatusId()));
        moduleDO.setCreatorId(identity.getId());
        moduleDO.setCreatorName(identity.getName());
        moduleDO.setCreateTime(LocalDateTime.now());
        fillFormRelatedData(moduleDO);
        moduleDataRepository.save(moduleDO);

        return moduleDO.getId();
    }

    @Override
    @Transactional
    public void updateModule(UpdateModuleCommand command) {
        Identity identity = command.getIdentity();
        ModuleDO moduleDO = moduleDataRepository.findById(command.getId());
        if (moduleDO == null) {
            throw new NotFoundException("未找到指定的模块");
        }
        if (moduleDataRepository.countByName(command.getName(), moduleDO.getId()) > 0) {
            throw new ValidationException("指定模块名称已经存在，请更换新的名称");
        }

        BeanUtils.copyProperties(command, moduleDO, "identity");
        moduleDO.setStatusName(EnableDisableStatus.getNameById(moduleDO.getStatusId()));
        moduleDO.setUpdatorId(identity.getId());
        moduleDO.setUpdatorName(identity.getName());
        moduleDO.setUpdateTime(LocalDateTime.now());
        fillFormRelatedData(moduleDO);
        moduleDataRepository.save(moduleDO);
    }

    @Override
    public void deleteModule(DeleteModuleCommand command) {
        Identity identity = command.getIdentity();

        moduleDataRepository.remove(command.getId(), identity);
    }

    /**
     * 填充表单关联数据
     *
     * @param moduleDO
     */
    private void fillFormRelatedData(ModuleDO moduleDO) {

    }
}