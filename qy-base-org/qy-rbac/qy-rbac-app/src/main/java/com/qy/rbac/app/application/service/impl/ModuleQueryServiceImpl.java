package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.assembler.ModuleAssembler;
import com.qy.rbac.app.application.dto.AppBasicDTO;
import com.qy.rbac.app.application.dto.ModuleBasicDTO;
import com.qy.rbac.app.application.dto.ModuleDTO;
import com.qy.rbac.app.application.query.ModuleQuery;
import com.qy.rbac.app.application.service.AppQueryService;
import com.qy.rbac.app.application.service.ModuleQueryService;
import com.qy.rbac.app.infrastructure.persistence.ModuleDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ModuleDO;
import com.qy.security.session.Identity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模块查询服务实现
 *
 * @author legendjw
 */
@Service
public class ModuleQueryServiceImpl implements ModuleQueryService {
    private ModuleAssembler moduleAssembler;
    private ModuleDataRepository moduleDataRepository;
    private AppQueryService appQueryService;


    public ModuleQueryServiceImpl(ModuleAssembler moduleAssembler, ModuleDataRepository moduleDataRepository, AppQueryService appQueryService) {
        this.moduleAssembler = moduleAssembler;
        this.moduleDataRepository = moduleDataRepository;
        this.appQueryService = appQueryService;
    }

    @Override
    public List<ModuleDTO> getModules(ModuleQuery query, Identity identity) {
        List<ModuleDO> moduleDOPage = moduleDataRepository.findByQuery(query);
        List<AppBasicDTO> appBasicDTOS = moduleDOPage.isEmpty() ? new ArrayList<>() : appQueryService.getBasicAppsByIds(moduleDOPage.stream().map(ModuleDO::getAppId).collect(Collectors.toList()));
        List<ModuleDTO> moduleDTOPage = moduleDOPage.stream().map(module -> moduleAssembler.toDTO(module, appBasicDTOS, identity)).collect(Collectors.toList());
        return moduleDTOPage;
    }

    @Override
    public ModuleDTO getModuleById(Long id) {
        ModuleDO moduleDO = moduleDataRepository.findById(id);
        List<AppBasicDTO> appBasicDTOS = moduleDO == null ? new ArrayList<>() : appQueryService.getBasicAppsByIds(Arrays.asList(moduleDO.getAppId()));
        return moduleAssembler.toDTO(moduleDO, appBasicDTOS, null);
    }

    @Override
    public List<ModuleBasicDTO> getBasicModulesByIds(List<Long> ids) {
        List<ModuleDO> moduleDOS = moduleDataRepository.findByIds(ids);
        return moduleDOS.stream().map(module -> moduleAssembler.toBasicDTO(module)).collect(Collectors.toList());
    }

    @Override
    public ModuleBasicDTO getBasicModuleByAppAndCode(Long appId, String code) {
        ModuleDO moduleDO = moduleDataRepository.findByAppAndCode(appId, code);
        return moduleAssembler.toBasicDTO(moduleDO);
    }
}
