package com.qy.wf.defNodeForm.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defNodeForm.dto.DefNodeFormDTO;
import com.qy.wf.defNodeForm.dto.DefNodeFormFormDTO;
import com.qy.wf.defNodeForm.dto.DefNodeFormQueryDTO;
import com.qy.wf.defNodeForm.entity.DefNodeFormEntity;
import com.qy.wf.defNodeForm.mapper.DefNodeFormMapper;
import com.qy.wf.defNodeForm.service.DefNodeFormService;
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
 * 工作流_设计_表单 服务实现类
 *
 * @author syf
 * @since 2022-11-14
 */
@Service
public class DefNodeFormServiceImpl extends ServiceImpl<DefNodeFormMapper, DefNodeFormEntity> implements DefNodeFormService {
    @Autowired
    private DefNodeFormMapper defNodeFormMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Override
    public IPage<DefNodeFormEntity> getDefNodeForms(IPage iPage, DefNodeFormQueryDTO defNodeFormQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeFormEntity> defNodeFormQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeFormQueryWrapper.eq(defNodeFormQueryDTO.getStatus()!=null,DefNodeFormEntity::getStatus,defNodeFormQueryDTO.getStatus());
        defNodeFormQueryWrapper.orderByDesc(DefNodeFormEntity::getCreateTime);
        return super.page(iPage, defNodeFormQueryWrapper);
    }

    @Override
    public List<DefNodeFormEntity> getDefNodeForms(DefNodeFormQueryDTO defNodeFormQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeFormEntity> defNodeFormQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(defNodeFormQueryWrapper);
    }

    @Override
    public DefNodeFormEntity getDefNodeForm(Long id, EmployeeIdentity currentUser) {
        DefNodeFormEntity defNodeFormEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defNodeFormEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_表单");
        }

        return defNodeFormEntity;
    }

    @Override
    public DefNodeFormEntity createDefNodeForm(DefNodeFormFormDTO defNodeFormFormDTO, EmployeeIdentity currentUser) {
        DefNodeFormEntity defNodeFormEntity = new DefNodeFormEntity();
        BeanUtils.copyProperties(defNodeFormFormDTO, defNodeFormEntity);
        defNodeFormEntity.setCreateTime(LocalDateTime.now());
        defNodeFormEntity.setCreatorId(currentUser.getId());
        defNodeFormEntity.setCreatorName(currentUser.getName());
        defNodeFormEntity.setIsDeleted(0);
        defNodeFormEntity.setCreatorName(currentUser.getName());
        this.save(defNodeFormEntity);
        return defNodeFormEntity;
    }

    @Override
    public DefNodeFormEntity updateDefNodeForm(Long id, DefNodeFormFormDTO defNodeFormFormDTO, EmployeeIdentity currentUser) {
        DefNodeFormEntity defNodeFormEntity = getById(id);
        BeanUtils.copyProperties(defNodeFormFormDTO, defNodeFormEntity);
        defNodeFormEntity.setUpdateTime(LocalDateTime.now());
        defNodeFormEntity.setUpdatorId(currentUser.getId());
        defNodeFormEntity.setUpdatorName(currentUser.getName());

        this.updateById(defNodeFormEntity);
        return defNodeFormEntity;
    }

    @Override
    public DefNodeFormEntity deleteDefNodeForm(Long id, EmployeeIdentity currentUser) {
        DefNodeFormEntity defNodeFormEntity = getDefNodeForm(id, currentUser);
        defNodeFormEntity.setDeleteTime(LocalDateTime.now());
        defNodeFormEntity.setDeletorId(currentUser.getId());
        defNodeFormEntity.setDeletorName(currentUser.getName());

        defNodeFormEntity.setIsDeleted(1);
        this.updateById(defNodeFormEntity);
        return defNodeFormEntity;
    }

    @Override
    public DefNodeFormDTO mapperToDTO(DefNodeFormEntity defNodeFormEntity, EmployeeIdentity currentUser) {
        DefNodeFormDTO defNodeFormDTO = new DefNodeFormDTO();
        BeanUtils.copyProperties(defNodeFormEntity, defNodeFormDTO);
        return defNodeFormDTO;
    }

    @Override
    public List<DefNodeFormDTO> mapperToDTO(List<DefNodeFormEntity> defNodeFormEntityList, EmployeeIdentity currentUser) {
        List<DefNodeFormDTO> defNodeFormDTOs = new ArrayList<>();
        for (DefNodeFormEntity defNodeFormEntity : defNodeFormEntityList) {
            DefNodeFormDTO defNodeFormDTO = mapperToDTO(defNodeFormEntity, currentUser);
            defNodeFormDTOs.add(defNodeFormDTO);
        }


        return defNodeFormDTOs;
    }
}
