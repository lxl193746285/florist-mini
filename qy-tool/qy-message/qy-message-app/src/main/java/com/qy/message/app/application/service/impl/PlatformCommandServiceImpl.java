package com.qy.message.app.application.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.message.app.application.command.CreatePlatformCommand;
import com.qy.message.app.application.command.UpdatePlatformCommand;
import com.qy.message.app.application.service.PlatformCommandService;
import com.qy.message.app.infrastructure.persistence.PlatformRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.PlatformDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 消息平台命令实现
 *
 * @author legendjw
 */
@Service
public class PlatformCommandServiceImpl implements PlatformCommandService {
    private PlatformRepository platformDataRepository;
    private CodeTableClient codeTableClient;
    private ObjectMapper objectMapper;

    public PlatformCommandServiceImpl(PlatformRepository platformDataRepository, CodeTableClient codeTableClient, ObjectMapper objectMapper) {
        this.platformDataRepository = platformDataRepository;
        this.codeTableClient = codeTableClient;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public Long createPlatform(CreatePlatformCommand command, EmployeeIdentity identity) {
        if (platformDataRepository.countByName(command.getName(), null) > 0) {
            throw new ValidationException("已经存在同名的消息平台，请更换新的消息平台名称");
        }

        PlatformDO platformDO = new PlatformDO();
        BeanUtils.copyProperties(command, platformDO);
        try {
            platformDO.setConfig(objectMapper.writeValueAsString(command.getConfig()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        platformDO.setCreatorId(identity.getId());
        platformDO.setCreatorName(identity.getName());
        platformDO.setCreateTime(LocalDateTime.now());
        platformDataRepository.save(platformDO);

        return platformDO.getId();
    }

    @Override
    @Transactional
    public void updatePlatform(UpdatePlatformCommand command, EmployeeIdentity identity) {
        PlatformDO platformDO = platformDataRepository.findById(command.getId());
        if (platformDO == null) {
            throw new NotFoundException("未找到指定的消息平台");
        }
        if (platformDataRepository.countByName(command.getName(), platformDO.getId()) > 0) {
            throw new ValidationException("组织下已经存在同名的消息平台，请更换新的消息平台名称");
        }

        BeanUtils.copyProperties(command, platformDO, "config");
        try {
            platformDO.setConfig(objectMapper.writeValueAsString(command.getConfig()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        platformDO.setUpdatorId(identity.getId());
        platformDO.setUpdatorName(identity.getName());
        platformDO.setUpdateTime(LocalDateTime.now());
        platformDataRepository.save(platformDO);
    }

    @Override
    @Transactional
    public void deletePlatform(Long id, EmployeeIdentity identity) {
        PlatformDO platformDO = platformDataRepository.findById(id);
        if (platformDO == null) {
            throw new NotFoundException("未找到指定的消息平台");
        }
        platformDataRepository.remove(id, identity);
    }
}