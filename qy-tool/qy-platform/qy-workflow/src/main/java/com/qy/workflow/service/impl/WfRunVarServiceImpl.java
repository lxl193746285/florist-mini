package com.qy.workflow.service.impl;

import com.qy.security.session.MemberIdentity;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfRunVarEntity;
import com.qy.workflow.mapper.WfRunVarMapper;
import com.qy.workflow.service.WfRunVarService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 工作流_执行_节点 服务实现类
 *
 * @author iFeng
 * @since 2022-11-23
 */
@Service
public class WfRunVarServiceImpl extends ServiceImpl<WfRunVarMapper, WfRunVarEntity> implements WfRunVarService {

    @Override
    public IPage<WfRunVarEntity> getWfRunVars(IPage iPage, WfRunVarQueryDTO wfRunVarQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfRunVarEntity> wfRunVarQueryWrapper = RestUtils.getLambdaQueryWrapper();
        wfRunVarQueryWrapper.orderByDesc(WfRunVarEntity::getCreateTime);
        return super.page(iPage, wfRunVarQueryWrapper);
    }


    @Override
    public List<WfRunVarEntity> getWfRunVars(WfRunVarQueryDTO wfRunVarQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfRunVarEntity> wfRunVarQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(wfRunVarQueryWrapper);
    }

    @Override
    public WfRunVarEntity getWfRunVar(Long id, MemberIdentity currentUser) {
        WfRunVarEntity wfRunVarEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (wfRunVarEntity == null) {
            throw new RuntimeException("未找到 工作流_执行_节点");
        }

        return wfRunVarEntity;
    }


    @Override
    public WfRunVarEntity createWfRunVar(WfRunVarFormDTO wfRunVarFormDTO, MemberIdentity currentUser) {
        WfRunVarEntity wfRunVarEntity = new WfRunVarEntity();
        BeanUtils.copyProperties(wfRunVarFormDTO, wfRunVarEntity);
        wfRunVarEntity.setCreateTime(LocalDateTime.now());
        wfRunVarEntity.setCreatorId(currentUser.getId());
        wfRunVarEntity.setCreatorName(currentUser.getName());
        wfRunVarEntity.setUpdatorId(Long.valueOf(0));
        wfRunVarEntity.setIsDeleted((Integer) 0);
        wfRunVarEntity.setDeletorId(Long.valueOf(0));

        this.save(wfRunVarEntity);
        return wfRunVarEntity;
    }


    @Override
    public WfRunVarEntity updateWfRunVar(Long id, WfRunVarFormDTO wfRunVarFormDTO, MemberIdentity currentUser) {
        WfRunVarEntity wfRunVarEntity = getById(id);
        BeanUtils.copyProperties(wfRunVarFormDTO, wfRunVarEntity);
        wfRunVarEntity.setUpdateTime(LocalDateTime.now());
        wfRunVarEntity.setUpdatorId(currentUser.getId());
        wfRunVarEntity.setUpdatorName(currentUser.getName());

        this.updateById(wfRunVarEntity);
        return wfRunVarEntity;
    }


    @Override
    public WfRunVarEntity deleteWfRunVar(Long id, MemberIdentity currentUser) {
        WfRunVarEntity wfRunVarEntity = getWfRunVar(id, currentUser);
        wfRunVarEntity.setDeleteTime(LocalDateTime.now());
        wfRunVarEntity.setDeletorId(currentUser.getId());
        wfRunVarEntity.setDeletorName(currentUser.getName());

        wfRunVarEntity.setIsDeleted((Integer)1);
        this.updateById(wfRunVarEntity);
        return wfRunVarEntity;
    }


    @Override
    public WfRunVarDTO mapperToDTO(WfRunVarEntity wfRunVarEntity, MemberIdentity currentUser) {
        WfRunVarDTO wfRunVarDTO = new WfRunVarDTO();
        BeanUtils.copyProperties(wfRunVarEntity, wfRunVarDTO);
        return wfRunVarDTO;
    }


    @Override
    public List<WfRunVarDTO> mapperToDTO(List<WfRunVarEntity> wfRunVarEntityList, MemberIdentity currentUser) {
        List<WfRunVarDTO> wfRunVarDTOs = new ArrayList<>();
        for (WfRunVarEntity wfRunVarEntity : wfRunVarEntityList) {
            WfRunVarDTO wfRunVarDTO = mapperToDTO(wfRunVarEntity, currentUser);
            wfRunVarDTOs.add(wfRunVarDTO);
        }
        return wfRunVarDTOs;
    }
}
