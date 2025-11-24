package com.qy.system.app.config.service.impl;

import com.qy.system.app.config.entity.SystemConfigCategoryEntity;
import com.qy.system.app.config.entity.SystemConfigEntity;
import com.qy.system.app.config.mapper.SystemConfigCategoryMapper;
import com.qy.system.app.config.service.SystemConfigCategoryService;

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
 * 配置类别 服务实现类
 *
 * @author hh
 * @since 2024-07-09
 */
@Service
public class SystemConfigCategoryServiceImpl extends ServiceImpl<SystemConfigCategoryMapper, SystemConfigCategoryEntity> implements SystemConfigCategoryService {
    @Autowired
    private SystemConfigCategoryMapper systemConfigCategoryMapper;

    @Override
    public IPage<SystemConfigCategoryDTO> getSystemConfigCategorys(IPage iPage, SystemConfigCategoryQueryDTO queryDTO, MemberIdentity currentUser) {
        if (!StringUtils.isNullOfEmpty(queryDTO.getStartCreateDate())) {
            queryDTO.setStartCreateDate(queryDTO.getStartCreateDate() + " 00:00:00");
        }
        if (!StringUtils.isNullOfEmpty(queryDTO.getEndCreateDate())) {
            queryDTO.setEndCreateDate(queryDTO.getEndCreateDate() + " 23:59:59");
        }
        IPage<SystemConfigCategoryDTO> dtoIPage = systemConfigCategoryMapper.selectPageList(iPage,queryDTO);
        List<Action> actions = currentUser.getActions("st_system_config_category");
        dtoIPage.getRecords().stream().map(dto -> {

            dto.setActions(actions);
            return dto;
        }).collect(Collectors.toList());
        return dtoIPage;
    }

    @Override
    public List<SystemConfigCategoryEntity> getSystemConfigCategorys(SystemConfigCategoryQueryDTO systemConfigCategoryQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<SystemConfigCategoryEntity> systemConfigCategoryQueryWrapper = new LambdaQueryWrapper<>();
        return super.list(systemConfigCategoryQueryWrapper);
    }

    @Override
    public SystemConfigCategoryEntity getSystemConfigCategory(Long id, MemberIdentity currentUser) {
        SystemConfigCategoryEntity systemConfigCategoryEntity = this.getById(id);

        if (systemConfigCategoryEntity == null) {
            throw new RuntimeException("未找到 配置类别");
        }

        return systemConfigCategoryEntity;
    }



    @Override
    public SystemConfigCategoryDTO getSystemConfigCategoryDTO(Long id, MemberIdentity currentUser) {

        return systemConfigCategoryMapper.getDTOById(id);
    }

    @Override
    public SystemConfigCategoryEntity createSystemConfigCategory(SystemConfigCategoryFormDTO systemConfigCategoryFormDTO, MemberIdentity currentUser) {
        if (countByIdentifier(systemConfigCategoryFormDTO.getIdentifier())){
            throw new RuntimeException("标识符已存在");
        }
        if (countByName(systemConfigCategoryFormDTO.getName())){
            throw new RuntimeException("配置类别名称已存在");
        }
        SystemConfigCategoryEntity systemConfigCategoryEntity = new SystemConfigCategoryEntity();
        BeanUtils.copyProperties(systemConfigCategoryFormDTO, systemConfigCategoryEntity);
        systemConfigCategoryEntity.setCreateTime(LocalDateTime.now());
        systemConfigCategoryEntity.setCreatorId(currentUser.getId());
        systemConfigCategoryEntity.setUpdatorId(0L);

        this.save(systemConfigCategoryEntity);
        return systemConfigCategoryEntity;
    }

    @Override
    public SystemConfigCategoryEntity updateSystemConfigCategory(Long id, SystemConfigCategoryFormDTO systemConfigCategoryFormDTO, MemberIdentity currentUser) {
        if (countByIdentifier(systemConfigCategoryFormDTO.getIdentifier())){
            throw new RuntimeException("标识符已存在");
        }
        if (countByName(systemConfigCategoryFormDTO.getName())){
            throw new RuntimeException("配置类别名称已存在");
        }
        SystemConfigCategoryEntity systemConfigCategoryEntity = getById(id);
        BeanUtils.copyProperties(systemConfigCategoryFormDTO, systemConfigCategoryEntity);
        systemConfigCategoryEntity.setUpdateTime(LocalDateTime.now());
        systemConfigCategoryEntity.setUpdatorId(currentUser.getId());
        systemConfigCategoryEntity.setId(id);

        this.updateById(systemConfigCategoryEntity);
        return systemConfigCategoryEntity;
    }

    @Override
    public SystemConfigCategoryEntity deleteSystemConfigCategory(Long id, MemberIdentity currentUser) {
        SystemConfigCategoryEntity systemConfigCategoryEntity = getSystemConfigCategory(id, currentUser);
        systemConfigCategoryEntity.setDeleteTime(LocalDateTime.now());
        systemConfigCategoryEntity.setDeletorId(currentUser.getId());
        systemConfigCategoryEntity.setIsDeleted(1);

        this.updateById(systemConfigCategoryEntity);
        return systemConfigCategoryEntity;
    }

    @Override
    public SystemConfigCategoryDTO mapperToDTO(SystemConfigCategoryEntity systemConfigCategoryEntity, MemberIdentity currentUser) {
        SystemConfigCategoryDTO systemConfigCategoryDTO = new SystemConfigCategoryDTO();
        BeanUtils.copyProperties(systemConfigCategoryEntity, systemConfigCategoryDTO);
        return systemConfigCategoryDTO;
    }

    @Override
    public List<SystemConfigCategoryDTO> mapperToDTO(List<SystemConfigCategoryEntity> systemConfigCategoryEntityList, MemberIdentity currentUser) {
        List<SystemConfigCategoryDTO> systemConfigCategoryDTOs = new ArrayList<>();
        for (SystemConfigCategoryEntity systemConfigCategoryEntity : systemConfigCategoryEntityList) {
            SystemConfigCategoryDTO systemConfigCategoryDTO = mapperToDTO(systemConfigCategoryEntity, currentUser);
            systemConfigCategoryDTOs.add(systemConfigCategoryDTO);
        }


        return systemConfigCategoryDTOs;
    }

    private boolean countByIdentifier(String identifier) {
        LambdaQueryWrapper<SystemConfigCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConfigCategoryEntity::getIdentifier, identifier)
                .eq(SystemConfigCategoryEntity::getIsDeleted, 0);
        return count(queryWrapper) > 0;
    }

    private boolean countByName(String name) {
        LambdaQueryWrapper<SystemConfigCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConfigCategoryEntity::getName, name)
                .eq(SystemConfigCategoryEntity::getIsDeleted, 0);
        return count(queryWrapper) > 0;
    }
}
