package com.qy.system.app.dismodel.service.impl;

import com.qy.rest.exception.ValidationException;
import com.qy.security.permission.action.Action;
import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.comment.ArkOperation;
import com.qy.system.app.dismodel.dto.QrcodeModelDTO;
import com.qy.system.app.dismodel.dto.QrcodeModelFormDTO;
import com.qy.system.app.dismodel.dto.QrcodeModelQueryDTO;
import com.qy.system.app.dismodel.entity.QrcodeModelEntity;
import com.qy.system.app.dismodel.enums.QrcodeModelAction;
import com.qy.system.app.dismodel.mapper.QrcodeModelMapper;
import com.qy.system.app.dismodel.service.QrcodeModelService;
import com.qy.system.app.util.ObjectUtils;
import com.qy.system.app.util.RestUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 二维码配置模板 服务实现类
 *
 * @author sxj
 * @since 2022-03-17
 */
@Service
public class QrcodeModelServiceImpl extends ServiceImpl<QrcodeModelMapper, QrcodeModelEntity> implements QrcodeModelService {
    @Autowired
    private QrcodeModelMapper qrcodeModelMapper;


    @Override
    public IPage<QrcodeModelEntity> getDistributionQrcodeModels(IPage iPage, QrcodeModelQueryDTO distributionQrcodeModelQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<QrcodeModelEntity> distributionQrcodeModelQueryWrapper = new LambdaQueryWrapper<>();
        distributionQrcodeModelQueryWrapper.eq(!ObjectUtils.isNullOfEmpty(distributionQrcodeModelQueryDTO.getModelId()), QrcodeModelEntity::getModelId,distributionQrcodeModelQueryDTO.getModelId())
                .eq(null!=distributionQrcodeModelQueryDTO.getStatus(), QrcodeModelEntity::getStatus,distributionQrcodeModelQueryDTO.getStatus())
                .like(!ObjectUtils.isNullOfEmpty(distributionQrcodeModelQueryDTO.getName()), QrcodeModelEntity::getName,distributionQrcodeModelQueryDTO.getName())
                .gt(!ObjectUtils.isNullOfEmpty(distributionQrcodeModelQueryDTO.getStartCreateDate()), QrcodeModelEntity::getCreateTime,distributionQrcodeModelQueryDTO.getStartCreateDate())
                .lt(!ObjectUtils.isNullOfEmpty(distributionQrcodeModelQueryDTO.getEndCreateDate()), QrcodeModelEntity::getCreateTime,distributionQrcodeModelQueryDTO.getEndCreateDate())
                .orderByAsc(QrcodeModelEntity::getSort)
                .orderByDesc(QrcodeModelEntity::getCreateTime);
        return super.page(iPage, distributionQrcodeModelQueryWrapper);
    }

    @Override
    public List<QrcodeModelEntity> getDistributionQrcodeModels(QrcodeModelQueryDTO distributionQrcodeModelQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<QrcodeModelEntity> distributionQrcodeModelQueryWrapper = new LambdaQueryWrapper<>();
        return super.list(distributionQrcodeModelQueryWrapper);
    }

    @Override
    public QrcodeModelEntity getDistributionQrcodeModel(Long id, EmployeeIdentity currentUser) {
        QrcodeModelEntity distributionQrcodeModelEntity = this.getById(id);

        if (distributionQrcodeModelEntity == null) {
            throw new RuntimeException("未找到 二维码配置模板");
        }

        return distributionQrcodeModelEntity;
    }

    @Override
    public QrcodeModelEntity createDistributionQrcodeModel(QrcodeModelFormDTO distributionQrcodeModelFormDTO, EmployeeIdentity currentUser) {
        QrcodeModelEntity distributionQrcodeModelEntity = new QrcodeModelEntity();
        BeanUtils.copyProperties(distributionQrcodeModelFormDTO, distributionQrcodeModelEntity);
        LambdaQueryWrapper<QrcodeModelEntity> queryWrapper= RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(QrcodeModelEntity::getScene,distributionQrcodeModelFormDTO.getScene())
                .eq(QrcodeModelEntity::getIsDeleted,0);
        QrcodeModelEntity distributionQrcodeModel=this.getOne(queryWrapper);
        if(distributionQrcodeModel!=null){
            throw new ValidationException("当前场景已存在！");
        }
        distributionQrcodeModelEntity.setCreateTime(LocalDateTime.now());
        distributionQrcodeModelEntity.setCreatorName(currentUser.getName());
        distributionQrcodeModelEntity.setCreatorId(currentUser.getOrganizationId());
        this.save(distributionQrcodeModelEntity);
        return distributionQrcodeModelEntity;
    }

    @Override
    public QrcodeModelEntity updateDistributionQrcodeModel(Long id, QrcodeModelFormDTO distributionQrcodeModelFormDTO, EmployeeIdentity currentUser) {
        QrcodeModelEntity distributionQrcodeModelEntity = getById(id);
        BeanUtils.copyProperties(distributionQrcodeModelFormDTO, distributionQrcodeModelEntity);
        distributionQrcodeModelEntity.setUpdateTime(LocalDateTime.now());
        distributionQrcodeModelEntity.setUpdatorName(currentUser.getName());
        distributionQrcodeModelEntity.setUpdatorId(currentUser.getOrganizationId());
        this.updateById(distributionQrcodeModelEntity);
        return distributionQrcodeModelEntity;
    }

    @Override
    public QrcodeModelEntity deleteDistributionQrcodeModel(Long id, EmployeeIdentity currentUser) {
        QrcodeModelEntity distributionQrcodeModelEntity = getDistributionQrcodeModel(id, currentUser);
        distributionQrcodeModelEntity.setDeleteTime(LocalDateTime.now());
        distributionQrcodeModelEntity.setDeletorName(currentUser.getName());
        distributionQrcodeModelEntity.setDeletorId(currentUser.getOrganizationId());
        this.removeById(id);
        return distributionQrcodeModelEntity;
    }

    @Override
    public QrcodeModelDTO mapperToDTO(QrcodeModelEntity distributionQrcodeModelEntity, EmployeeIdentity currentUser) {
        QrcodeModelDTO distributionQrcodeModelDTO = new QrcodeModelDTO();
        BeanUtils.copyProperties(distributionQrcodeModelEntity, distributionQrcodeModelDTO);
        distributionQrcodeModelDTO.setCreateTimeName(distributionQrcodeModelDTO.getCreateTime());
        distributionQrcodeModelDTO.setUpdateTimeName(distributionQrcodeModelDTO.getUpdateTime());
        distributionQrcodeModelDTO.setStatusName(distributionQrcodeModelDTO.getStatus());
        return distributionQrcodeModelDTO;
    }

    @Override
    public List<QrcodeModelDTO> mapperToDTO(List<QrcodeModelEntity> distributionQrcodeModelEntityList, EmployeeIdentity currentUser) {
        List<QrcodeModelDTO> distributionQrcodeModelDTOs = new ArrayList<>();
        for (QrcodeModelEntity distributionQrcodeModelEntity : distributionQrcodeModelEntityList) {
            QrcodeModelDTO distributionQrcodeModelDTO = mapperToDTO(distributionQrcodeModelEntity, currentUser);
            distributionQrcodeModelDTO.setCreateTimeName(distributionQrcodeModelDTO.getCreateTime());
            distributionQrcodeModelDTO.setUpdateTimeName(distributionQrcodeModelDTO.getUpdateTime());
            distributionQrcodeModelDTO.setStatusName(distributionQrcodeModelDTO.getStatus());
            List<Action> actions = new ArrayList<>();

            if(currentUser.hasPermission(QrcodeModelAction.VIEW.getPermission())){
                actions.add(ArkOperation.fromIEnumAction(QrcodeModelAction.VIEW));
            }
            if(currentUser.hasPermission(QrcodeModelAction.EDIT.getPermission())){
                actions.add(ArkOperation.fromIEnumAction(QrcodeModelAction.EDIT));
            }
            if(currentUser.hasPermission(QrcodeModelAction.DELETE.getPermission())){
                actions.add(ArkOperation.fromIEnumAction(QrcodeModelAction.DELETE));
            }
            distributionQrcodeModelDTO.setActions(actions);
            distributionQrcodeModelDTOs.add(distributionQrcodeModelDTO);
        }

        return distributionQrcodeModelDTOs;
    }
}
