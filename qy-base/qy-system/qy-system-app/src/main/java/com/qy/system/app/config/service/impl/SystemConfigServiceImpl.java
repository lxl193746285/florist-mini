package com.qy.system.app.config.service.impl;

import com.qy.system.app.config.entity.SystemConfigEntity;
import com.qy.system.app.config.mapper.SystemConfigMapper;
import com.qy.system.app.config.service.SystemConfigService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.system.app.config.dto.*;
import com.qy.security.permission.action.Action;
import java.util.List;
import java.util.ArrayList;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.security.session.MemberIdentity;
import com.qy.system.app.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


/**
 * 配置 服务实现类
 *
 * @author hh
 * @since 2024-07-09
 */
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfigEntity> implements SystemConfigService {
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public IPage<SystemConfigDTO> getSystemConfigs(IPage iPage, SystemConfigQueryDTO queryDTO, MemberIdentity currentUser) {
        if (!StringUtils.isNullOfEmpty(queryDTO.getStartCreateDate())) {
            queryDTO.setStartCreateDate(queryDTO.getStartCreateDate() + " 00:00:00");
        }
        if (!StringUtils.isNullOfEmpty(queryDTO.getEndCreateDate())) {
            queryDTO.setEndCreateDate(queryDTO.getEndCreateDate() + " 23:59:59");
        }
        IPage<SystemConfigDTO> dtoIPage = systemConfigMapper.selectPageList(iPage, queryDTO);
        List<Action> actions = currentUser.getActions("st_system_config");
        dtoIPage.getRecords().stream().map(dto -> {

            dto.setActions(actions);
            return dto;
        }).collect(Collectors.toList());
        return dtoIPage;
    }

    @Override
    public List<SystemConfigEntity> getSystemConfigs(SystemConfigQueryDTO systemConfigQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<SystemConfigEntity> systemConfigQueryWrapper = new LambdaQueryWrapper<>();
        return super.list(systemConfigQueryWrapper);
    }

    @Override
    public SystemConfigEntity getSystemConfig(Long id, MemberIdentity currentUser) {
        SystemConfigEntity systemConfigEntity = this.getById(id);

        if (systemConfigEntity == null) {
            throw new RuntimeException("未找到 配置");
        }

        return systemConfigEntity;
    }



    @Override
    public SystemConfigDTO getSystemConfigDTO(Long id, MemberIdentity currentUser) {

        return systemConfigMapper.getDTOById(id);
    }

    @Override
    public SystemConfigEntity createSystemConfig(SystemConfigFormDTO systemConfigFormDTO, MemberIdentity currentUser) {
        if (countByAttribute(systemConfigFormDTO.getAttribute(),systemConfigFormDTO.getCategoryId(),systemConfigFormDTO.getAttributeType())){
            throw new RuntimeException("属性值已存在");
        }
        SystemConfigEntity systemConfigEntity = new SystemConfigEntity();
        BeanUtils.copyProperties(systemConfigFormDTO, systemConfigEntity);
        systemConfigEntity.setCreateTime(LocalDateTime.now());
        systemConfigEntity.setCreatorId(currentUser.getId());
        systemConfigEntity.setUpdatorId(0L);

        this.save(systemConfigEntity);
        return systemConfigEntity;
    }

    @Override
    public SystemConfigEntity updateSystemConfig(Long id, SystemConfigFormDTO systemConfigFormDTO, MemberIdentity currentUser) {
        SystemConfigEntity systemConfigEntity = getById(id);
        if (!systemConfigFormDTO.getAttribute().equals(systemConfigEntity.getAttribute())
                && countByAttribute(systemConfigFormDTO.getAttribute(),systemConfigFormDTO.getCategoryId(),systemConfigFormDTO.getAttributeType())){
            throw new RuntimeException("属性值已存在");
        }
        BeanUtils.copyProperties(systemConfigFormDTO, systemConfigEntity);
        systemConfigEntity.setUpdateTime(LocalDateTime.now());
        systemConfigEntity.setUpdatorId(currentUser.getId());
        systemConfigEntity.setId(id);

        this.updateById(systemConfigEntity);
        return systemConfigEntity;
    }

    @Override
    public SystemConfigEntity deleteSystemConfig(Long id, MemberIdentity currentUser) {
        SystemConfigEntity systemConfigEntity = getSystemConfig(id, currentUser);
        systemConfigEntity.setDeleteTime(LocalDateTime.now());
        systemConfigEntity.setDeletorId(currentUser.getId());
        systemConfigEntity.setIsDeleted(1);

        this.updateById(systemConfigEntity);
        return systemConfigEntity;
    }

    @Override
    public SystemConfigDTO mapperToDTO(SystemConfigEntity systemConfigEntity, MemberIdentity currentUser) {
        SystemConfigDTO systemConfigDTO = new SystemConfigDTO();
        BeanUtils.copyProperties(systemConfigEntity, systemConfigDTO);
        return systemConfigDTO;
    }

    @Override
    public List<SystemConfigDTO> mapperToDTO(List<SystemConfigEntity> systemConfigEntityList, MemberIdentity currentUser) {
        List<SystemConfigDTO> systemConfigDTOs = new ArrayList<>();
        for (SystemConfigEntity systemConfigEntity : systemConfigEntityList) {
            SystemConfigDTO systemConfigDTO = mapperToDTO(systemConfigEntity, currentUser);
            systemConfigDTOs.add(systemConfigDTO);
        }


        return systemConfigDTOs;
    }

    @Override
    public SystemConfigSearchDTO getSystemConfigSearchDTO(String attribute, String categoryIdentifier, MemberIdentity currentUser) {
        if (StringUtils.isNullOfEmpty(attribute)){
            throw new RuntimeException("属性值不可为空");
        }
        if (StringUtils.isNullOfEmpty(categoryIdentifier)){
            throw new RuntimeException("分类标识不可为空");
        }
        return systemConfigMapper.getDataByAttribute(attribute, categoryIdentifier);
    }

    private boolean countByAttribute(String attribute,Long id,String attributeType) {
        LambdaQueryWrapper<SystemConfigEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConfigEntity::getAttribute, attribute)
                .eq(SystemConfigEntity::getCategoryId, id)
                .eq(SystemConfigEntity::getAttributeType, attributeType)
                .eq(SystemConfigEntity::getIsDeleted, 0);
        return count(queryWrapper) > 0;
    }


}
