package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.assembler.PlatformAssembler;
import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.query.PlatformQuery;
import com.qy.message.app.application.security.PlatformPermission;
import com.qy.message.app.application.service.PlatformQueryService;
import com.qy.message.app.infrastructure.persistence.PlatformRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.PlatformDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息平台查询服务实现
 *
 * @author legendjw
 */
@Service
public class PlatformQueryServiceImpl implements PlatformQueryService {
    private PlatformAssembler platformAssembler;
    private PlatformRepository platformDataRepository;
    private PlatformPermission platformPermission;

    public PlatformQueryServiceImpl(PlatformAssembler platformAssembler, PlatformRepository platformDataRepository, PlatformPermission platformPermission) {
        this.platformAssembler = platformAssembler;
        this.platformDataRepository = platformDataRepository;
        this.platformPermission = platformPermission;
    }

    @Override
    public Page<PlatformDTO> getPlatforms(PlatformQuery query, Identity identity) {
        Page<PlatformDO> platformDOPage = platformDataRepository.findByQuery(query);
        return platformDOPage.map(platform -> platformAssembler.toDTO(platform, identity));
    }

    @Override
    public PlatformDTO getPlatformById(Long id, Identity identity) {
        PlatformDO platformDO = platformDataRepository.findById(id);
        return platformAssembler.toDTO(platformDO, identity);
    }

    @Override
    public PlatformDTO getPlatformById(Long id) {
        PlatformDO platformDO = platformDataRepository.findById(id);
        return platformAssembler.toDTO(platformDO, null);
    }

    @Override
    public PlatformDTO getPlatformByWeixinAppId(String weixinAppId) {
        List<PlatformDO> platformDOList = platformDataRepository.findAll();
        List<PlatformDTO> platformDTOS = platformDOList.stream().map(p -> platformAssembler.toDTO(p, null)).collect(Collectors.toList());
        return platformDTOS.stream().filter(p -> p.getConfig() != null && p.getConfig().getWeixinAppId() != null && p.getConfig().getWeixinAppId().equals(weixinAppId)).findFirst().orElse(null);
    }
}
