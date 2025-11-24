package com.qy.wf.var.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkCodeTableService;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.var.dto.VarDTO;
import com.qy.wf.var.dto.VarFormDTO;
import com.qy.wf.var.dto.VarQueryDTO;
import com.qy.wf.var.entity.VarEntity;
import com.qy.wf.var.mapper.VarMapper;
import com.qy.wf.var.service.VarService;
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
 * 工作流_变量 服务实现类
 *
 * @author syf
 * @since 2022-11-21
 */
@Service
public class VarServiceImpl extends ServiceImpl<VarMapper, VarEntity> implements VarService {
    @Autowired
    private VarMapper varMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Autowired
    private ArkCodeTableService arkCodeTableService;


    @Override
    public IPage<VarEntity> getVars(IPage iPage, VarQueryDTO varQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<VarEntity> varQueryWrapper = RestUtils.getLambdaQueryWrapper();
        varQueryWrapper.eq(varQueryDTO.getStatus()!=null,VarEntity::getStatus,varQueryDTO.getStatus());
        varQueryWrapper.orderByAsc(VarEntity::getSort);
        return super.page(iPage, varQueryWrapper);
    }

    @Override
    public List<VarEntity> getVars(VarQueryDTO varQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<VarEntity> varQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(varQueryWrapper);
    }

    @Override
    public VarEntity getVar(Long id, EmployeeIdentity currentUser) {
        VarEntity varEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (varEntity == null) {
            throw new RuntimeException("未找到 工作流_变量");
        }

        return varEntity;
    }

    @Override
    public VarEntity createVar(VarFormDTO varFormDTO, EmployeeIdentity currentUser) {
        VarEntity varEntity = new VarEntity();
        BeanUtils.copyProperties(varFormDTO, varEntity);
        varEntity.setCreateTime(LocalDateTime.now());
        varEntity.setCreatorId(currentUser.getId());
        varEntity.setCreatorName(currentUser.getName());
        varEntity.setIsDeleted(0);
        this.save(varEntity);
        return varEntity;
    }

    @Override
    public VarEntity updateVar(Long id, VarFormDTO varFormDTO, EmployeeIdentity currentUser) {
        VarEntity varEntity = getById(id);
        BeanUtils.copyProperties(varFormDTO, varEntity);
        varEntity.setUpdateTime(LocalDateTime.now());
        varEntity.setUpdatorId(currentUser.getId());
        varEntity.setUpdatorName(currentUser.getName());

        this.updateById(varEntity);
        return varEntity;
    }

    @Override
    public VarEntity deleteVar(Long id, EmployeeIdentity currentUser) {
        VarEntity varEntity = getVar(id, currentUser);
        varEntity.setDeleteTime(LocalDateTime.now());
        varEntity.setDeletorId(currentUser.getId());
        varEntity.setDeletorName(currentUser.getName());

        varEntity.setIsDeleted(1);
        this.updateById(varEntity);
        return varEntity;
    }

    @Override
    public VarDTO mapperToDTO(VarEntity varEntity, EmployeeIdentity currentUser) {
        VarDTO varDTO = new VarDTO();
        BeanUtils.copyProperties(varEntity, varDTO);
        //码表翻译
        varDTO.setStatusName(arkCodeTableService.getNameBySystem("common_status", varDTO.getStatus()));
        varDTO.setVarTypeName(arkCodeTableService.getNameBySystem("wf_var_type", varDTO.getVarType()));

        return varDTO;
    }

    @Override
    public List<VarDTO> mapperToDTO(List<VarEntity> varEntityList, EmployeeIdentity currentUser) {
        List<VarDTO> varDTOs = new ArrayList<>();
        for (VarEntity varEntity : varEntityList) {
            VarDTO varDTO = mapperToDTO(varEntity, currentUser);
            varDTOs.add(varDTO);
        }


        return varDTOs;
    }
}
