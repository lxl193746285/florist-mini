package com.qy.wf.defMap.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkCodeTableService;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defMap.dto.DefMapDTO;
import com.qy.wf.defMap.dto.DefMapFormDTO;
import com.qy.wf.defMap.dto.DefMapQueryDTO;
import com.qy.wf.defMap.entity.DefMapEntity;
import com.qy.wf.defMap.mapper.DefMapMapper;
import com.qy.wf.defMap.service.DefMapService;
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
 * 工作流_设计_映射表 记录业务表与工作流id关联 服务实现类
 *
 * @author syf
 * @since 2022-11-21
 */
@Service
public class DefMapServiceImpl extends ServiceImpl<DefMapMapper, DefMapEntity> implements DefMapService {
    @Autowired
    private DefMapMapper defMapMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Autowired
    private ArkCodeTableService arkCodeTableService;

    @Override
    public IPage<DefMapEntity> getDefMaps(IPage iPage, DefMapQueryDTO defMapQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefMapEntity> defMapQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defMapQueryWrapper.eq(defMapQueryDTO.getStatus()!=null,DefMapEntity::getStatus,defMapQueryDTO.getStatus());
        defMapQueryWrapper.orderByDesc(DefMapEntity::getCreateTime);
        return super.page(iPage, defMapQueryWrapper);
    }

    @Override
    public List<DefMapEntity> getDefMaps(DefMapQueryDTO defMapQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefMapEntity> defMapQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(defMapQueryWrapper);
    }

    @Override
    public DefMapEntity getDefMap(Long id, EmployeeIdentity currentUser) {
        DefMapEntity defMapEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defMapEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_映射表 记录业务表与工作流id关联");
        }

        return defMapEntity;
    }

    @Override
    public DefMapEntity getDefMapByWfId(Long wfId, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefMapEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(DefMapEntity::getWfId, wfId);
        return this.getOne(queryWrapper);
    }

    @Override
    public DefMapEntity createDefMap(DefMapFormDTO defMapFormDTO, EmployeeIdentity currentUser) {
        DefMapEntity defMapEntity = new DefMapEntity();
        BeanUtils.copyProperties(defMapFormDTO, defMapEntity);
        defMapEntity.setCompanyId(currentUser.getOrganizationId());
        defMapEntity.setCreateTime(LocalDateTime.now());
        defMapEntity.setCreatorId(currentUser.getId());
        defMapEntity.setCreatorName(currentUser.getName());
        defMapEntity.setIsDeleted(0);

        //判断同一工作流是否存在，存在则修改，不存在则新增
        LambdaQueryWrapper<DefMapEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(DefMapEntity::getWfId, defMapFormDTO.getWfId());
        DefMapEntity defMap = this.getOne(queryWrapper);
        if (defMap != null) {
            BeanUtils.copyProperties(defMapFormDTO, defMap);
            defMap.setUpdateTime(LocalDateTime.now());
            defMap.setUpdatorId(currentUser.getId());
            defMap.setUpdatorName(currentUser.getName());
            this.updateById(defMap);
        } else {
            this.save(defMapEntity);
        }

        return defMapEntity;
    }

    @Override
    public DefMapEntity updateDefMap(Long id, DefMapFormDTO defMapFormDTO, EmployeeIdentity currentUser) {
        DefMapEntity defMapEntity = getById(id);
        BeanUtils.copyProperties(defMapFormDTO, defMapEntity);
        defMapEntity.setUpdateTime(LocalDateTime.now());
        defMapEntity.setUpdatorId(currentUser.getId());
        defMapEntity.setUpdatorName(currentUser.getName());

        this.updateById(defMapEntity);
        return defMapEntity;
    }

    @Override
    public DefMapEntity deleteDefMap(Long id, EmployeeIdentity currentUser) {
        defMapMapper.deleteById(id);

        return null;
    }

    @Override
    public DefMapDTO mapperToDTO(DefMapEntity defMapEntity, EmployeeIdentity currentUser) {
        DefMapDTO defMapDTO = new DefMapDTO();
        if (defMapEntity != null) {
            BeanUtils.copyProperties(defMapEntity, defMapDTO);
        }
        //码表翻译
        if (defMapDTO.getStatus() != null) {
            defMapDTO.setStatusName(arkCodeTableService.getNameBySystem("common_status", defMapDTO.getStatus()));
        }

        return defMapDTO;
    }

    @Override
    public List<DefMapDTO> mapperToDTO(List<DefMapEntity> defMapEntityList, EmployeeIdentity currentUser) {
        List<DefMapDTO> defMapDTOs = new ArrayList<>();
        for (DefMapEntity defMapEntity : defMapEntityList) {
            DefMapDTO defMapDTO = mapperToDTO(defMapEntity, currentUser);
            defMapDTOs.add(defMapDTO);
        }


        return defMapDTOs;
    }
}
