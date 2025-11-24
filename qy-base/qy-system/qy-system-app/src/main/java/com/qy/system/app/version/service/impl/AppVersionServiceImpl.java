package com.qy.system.app.version.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.system.app.util.RestUtils;
import com.qy.system.app.version.dto.AppVersionDTO;
import com.qy.system.app.version.dto.AppVersionFormDTO;
import com.qy.system.app.version.dto.AppVersionQueryDTO;
import com.qy.system.app.version.entity.AppVersionEntity;
import com.qy.system.app.version.mapper.AppVersionMapper;
import com.qy.system.app.version.service.AppVersionService;
import com.qy.security.permission.action.Action;
import com.qy.security.session.MemberIdentity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * APP版本 服务实现类
 *
 * @author syf
 * @since 2024-05-21
 */
@Service
@DS("arkcsd")
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersionEntity> implements AppVersionService {
    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public IPage<AppVersionDTO> getAppVersions(IPage iPage, AppVersionQueryDTO appVersionQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<AppVersionEntity> appVersionQueryWrapper = RestUtils.getLambdaQueryWrapper();
        appVersionQueryWrapper.like(appVersionQueryDTO.getName()!=null,AppVersionEntity::getName,appVersionQueryDTO.getName());
        appVersionQueryWrapper.eq(appVersionQueryDTO.getItemTypeId()!=null,AppVersionEntity::getItemTypeId,appVersionQueryDTO.getItemTypeId());
        appVersionQueryWrapper.eq(appVersionQueryDTO.getTypeId()!=null,AppVersionEntity::getTypeId,appVersionQueryDTO.getTypeId());
        appVersionQueryWrapper.orderByDesc(AppVersionEntity::getCreateTime);
        IPage<AppVersionDTO> dtoIPage = appVersionMapper.getDTOList(iPage,appVersionQueryWrapper);
        List<Action> actions = currentUser.getActions("st_system_app_version");
        dtoIPage.getRecords().stream().map(dto -> {

            dto.setActions(actions);
            return dto;
        }).collect(Collectors.toList());
        return dtoIPage;
    }

    @Override
    public List<AppVersionEntity> getAppVersions(AppVersionQueryDTO appVersionQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<AppVersionEntity> appVersionQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(appVersionQueryWrapper);
    }

    @Override
    public AppVersionEntity getAppVersion(Long id, MemberIdentity currentUser) {
        AppVersionEntity appVersionEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (appVersionEntity == null) {
            throw new RuntimeException("未找到 APP版本");
        }

        return appVersionEntity;
    }

    @Override
    public AppVersionDTO getAppVersionDTO(Long id, MemberIdentity currentUser) {

        return appVersionMapper.getDTOById(id);
    }

    @Override
    public AppVersionEntity createAppVersion(AppVersionFormDTO appVersionFormDTO, MemberIdentity currentUser) {
        AppVersionEntity appVersionEntity = new AppVersionEntity();
        BeanUtils.copyProperties(appVersionFormDTO, appVersionEntity);
        appVersionEntity.setCreatorId(currentUser.getId());
        appVersionEntity.setCreateTime(LocalDateTime.now());
        appVersionEntity.setUpdatorId(0L);
        appVersionEntity.setIsDeleted(0);
        appVersionEntity.setDeletorId(0L);

        this.save(appVersionEntity);
        return appVersionEntity;
    }

    @Override
    public AppVersionEntity updateAppVersion(Long id, AppVersionFormDTO appVersionFormDTO, MemberIdentity currentUser) {
        AppVersionEntity appVersionEntity = getById(id);
        BeanUtils.copyProperties(appVersionFormDTO, appVersionEntity);
        appVersionEntity.setUpdateTime(LocalDateTime.now());
        appVersionEntity.setUpdatorId(currentUser.getId());
        appVersionEntity.setId(id);

        this.updateById(appVersionEntity);
        return appVersionEntity;
    }

    @Override
    public AppVersionEntity deleteAppVersion(Long id, MemberIdentity currentUser) {
        AppVersionEntity appVersionEntity = getAppVersion(id, currentUser);
        appVersionEntity.setDeleteTime(LocalDateTime.now());
        appVersionEntity.setDeletorId(currentUser.getId());

        appVersionEntity.setIsDeleted(1);
        this.updateById(appVersionEntity);
        return appVersionEntity;
    }

    @Override
    public AppVersionDTO mapperToDTO(AppVersionEntity appVersionEntity, MemberIdentity currentUser) {
        AppVersionDTO appVersionDTO = new AppVersionDTO();
        BeanUtils.copyProperties(appVersionEntity, appVersionDTO);
        return appVersionDTO;
    }

    @Override
    public List<AppVersionDTO> mapperToDTO(List<AppVersionEntity> appVersionEntityList, MemberIdentity currentUser) {
        List<AppVersionDTO> appVersionDTOs = new ArrayList<>();
        for (AppVersionEntity appVersionEntity : appVersionEntityList) {
            AppVersionDTO appVersionDTO = mapperToDTO(appVersionEntity, currentUser);
            appVersionDTOs.add(appVersionDTO);
        }

        return appVersionDTOs;
    }

    @Override
    public AppVersionDTO getNewVersion(String type, String itemType) {
        LambdaQueryWrapper<AppVersionEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(AppVersionEntity::getType, type)
                .eq(AppVersionEntity::getItemType, itemType)
                .orderByDesc(AppVersionEntity::getVersion)
                .orderByDesc(AppVersionEntity::getCreateTime)
                .last("limit 1");
        AppVersionEntity appVersionEntity = getOne(queryWrapper);
        if (appVersionEntity == null) {
            return new AppVersionDTO();
        }
        return mapperToDTO(appVersionEntity, null);
    }

}
