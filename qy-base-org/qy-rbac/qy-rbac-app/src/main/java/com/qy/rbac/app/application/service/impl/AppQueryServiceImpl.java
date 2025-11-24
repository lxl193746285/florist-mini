package com.qy.rbac.app.application.service.impl;

import com.qy.member.api.client.MemberSystemClient;
import com.qy.member.api.dto.MemberSystemBasicDTO;
import com.qy.rbac.app.application.assembler.AppAssembler;
import com.qy.rbac.app.application.dto.AppBasicDTO;
import com.qy.rbac.app.application.dto.AppDTO;
import com.qy.rbac.app.application.dto.ClientDTO;
import com.qy.rbac.app.application.query.AppQuery;
import com.qy.rbac.app.application.service.AppQueryService;
import com.qy.rbac.app.application.service.ClientQueryService;
import com.qy.rbac.app.infrastructure.persistence.AppClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.AppDataRepository;
import com.qy.rbac.app.infrastructure.persistence.AppMemberSystemRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppClientDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppMemberSystemDO;
import com.qy.security.session.Identity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用查询服务实现
 *
 * @author legendjw
 */
@Service
public class AppQueryServiceImpl implements AppQueryService {
    private AppAssembler appAssembler;
    private AppDataRepository appDataRepository;
    private ClientQueryService clientQueryService;
    private AppClientDataRepository appClientDataRepository;

    public AppQueryServiceImpl(AppAssembler appAssembler, AppDataRepository appDataRepository, ClientQueryService clientQueryService,
                               AppClientDataRepository appClientDataRepository) {
        this.appAssembler = appAssembler;
        this.appDataRepository = appDataRepository;
        this.clientQueryService = clientQueryService;
        this.appClientDataRepository = appClientDataRepository;
    }

    @Override
    public List<AppDTO> getApps(AppQuery query, Identity identity) {
        List<AppDO> appDOPage = appDataRepository.findByQuery(query);
        List<AppDTO> dtos = appDOPage.stream().map(app -> appAssembler.toDTO(app, identity)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public AppDTO getAppById(Long id) {
        AppDO appDO = appDataRepository.findById(id);
        AppDTO dto = appAssembler.toDTO(appDO, null);
        return dto;
    }

    @Override
    public List<AppBasicDTO> getBasicAppsByIds(List<Long> ids) {
        List<AppDO> appDOS = appDataRepository.findByIds(ids);
        return appDOS.stream().map(app -> appAssembler.toBasicDTO(app)).collect(Collectors.toList());
    }

    @Override
    public AppBasicDTO getBasicAppByClientId(String clientId) {
        ClientDTO clientDTO = clientQueryService.getClientByClientId(clientId);
        if (clientDTO == null) {
            return null;
        }
        List<AppClientDO> appClientDOS = appClientDataRepository.findByClientId(clientDTO.getId());
        if (appClientDOS == null || appClientDOS.isEmpty()) {
            return null;
        }
        AppDO appDO = appDataRepository.findById(appClientDOS.get(0).getAppId());
        return appAssembler.toBasicDTO(appDO);
    }

    @Override
    public AppBasicDTO getBasicAppByCode(String appCode) {
        AppDO appDO = appDataRepository.findByCode(appCode);
        return appAssembler.toBasicDTO(appDO);
    }

    @Override
    public List<AppBasicDTO> getBasicAppsBySystemId(List<Long> ids, String systemId) {
        Long memberSystemId = null;
        if (systemId != null) {
            memberSystemId = Long.valueOf(systemId);
        }
        List<AppDO> appDOS = appDataRepository.findBySystemId(ids, memberSystemId);
        return appDOS.stream().map(app -> appAssembler.toBasicDTO(app)).collect(Collectors.toList());
    }

    @Override
    public List<AppBasicDTO> getBasicAppsByClentIds(List<Long> clientIds) {
        List<AppClientDO> appClientDOS = appClientDataRepository.findByClientIds(clientIds);
        List<Long> appIds = appClientDOS.stream().map(appClientDO->appClientDO.getAppId()).collect(Collectors.toList());
        List<AppDO> appDOS = appDataRepository.findByIds(appIds);
        return appDOS.stream().map(app -> appAssembler.toBasicDTO(app)).collect(Collectors.toList());
    }
}
