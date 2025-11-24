package com.qy.workflow.service.impl;

import com.qy.security.session.MemberIdentity;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfRunNodeEntity;
import com.qy.workflow.mapper.WfRunNodeMapper;
import com.qy.workflow.service.WfRunNodeService;
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
 * @since 2022-11-16
 */
@Service
public class WfRunNodeServiceImpl extends ServiceImpl<WfRunNodeMapper, WfRunNodeEntity> implements WfRunNodeService {

    @Override
    public IPage<WfRunNodeEntity> getWfRunNodes(IPage iPage, WfRunNodeQueryDTO wfRunNodeQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfRunNodeEntity> wfRunNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        wfRunNodeQueryWrapper.eq(wfRunNodeQueryDTO.getStatus()!=null,WfRunNodeEntity::getStatus,wfRunNodeQueryDTO.getStatus());
        wfRunNodeQueryWrapper.orderByDesc(WfRunNodeEntity::getCreateTime);
        return super.page(iPage, wfRunNodeQueryWrapper);
    }


    @Override
    public List<WfRunNodeEntity> getWfRunNodes(WfRunNodeQueryDTO wfRunNodeQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfRunNodeEntity> wfRunNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(wfRunNodeQueryWrapper);
    }


    @Override
    public WfRunNodeEntity getWfRunNode(Long id, MemberIdentity currentUser) {
        WfRunNodeEntity wfRunNodeEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (wfRunNodeEntity == null) {
            throw new RuntimeException("未找到 工作流_执行_节点");
        }

        return wfRunNodeEntity;
    }


    @Override
    public WfRunNodeEntity createWfRunNode(WfRunNodeFormDTO wfRunNodeFormDTO, MemberIdentity currentUser) {
        WfRunNodeEntity wfRunNodeEntity = new WfRunNodeEntity();
        BeanUtils.copyProperties(wfRunNodeFormDTO, wfRunNodeEntity);
        wfRunNodeEntity.setCreateTime(LocalDateTime.now());
        wfRunNodeEntity.setCreatorId(currentUser.getId());
        wfRunNodeEntity.setCreatorName(currentUser.getName());
        wfRunNodeEntity.setUpdatorId(Long.valueOf(0));
        wfRunNodeEntity.setIsDeleted((int)0);
        wfRunNodeEntity.setDeletorId(Long.valueOf(0));

        this.save(wfRunNodeEntity);
        return wfRunNodeEntity;
    }


    @Override
    public WfRunNodeEntity updateWfRunNode(Long id, WfRunNodeFormDTO wfRunNodeFormDTO, MemberIdentity currentUser) {
        WfRunNodeEntity wfRunNodeEntity = getById(id);
        BeanUtils.copyProperties(wfRunNodeFormDTO, wfRunNodeEntity);
        wfRunNodeEntity.setUpdateTime(LocalDateTime.now());
        wfRunNodeEntity.setUpdatorId(currentUser.getId());
        wfRunNodeEntity.setUpdatorName(currentUser.getName());

        this.updateById(wfRunNodeEntity);
        return wfRunNodeEntity;
    }


    @Override
    public WfRunNodeEntity deleteWfRunNode(Long id, MemberIdentity currentUser) {
        WfRunNodeEntity wfRunNodeEntity = getWfRunNode(id, currentUser);
        wfRunNodeEntity.setDeleteTime(LocalDateTime.now());
        wfRunNodeEntity.setDeletorId(currentUser.getId());
        wfRunNodeEntity.setDeletorName(currentUser.getName());

        wfRunNodeEntity.setIsDeleted((int)1);
        this.updateById(wfRunNodeEntity);
        return wfRunNodeEntity;
    }


    @Override
    public WfRunNodeDTO mapperToDTO(WfRunNodeEntity wfRunNodeEntity, MemberIdentity currentUser) {
        WfRunNodeDTO wfRunNodeDTO = new WfRunNodeDTO();
        BeanUtils.copyProperties(wfRunNodeEntity, wfRunNodeDTO);
        return wfRunNodeDTO;
    }


    @Override
    public List<WfRunNodeDTO> mapperToDTO(List<WfRunNodeEntity> wfRunNodeEntityList, MemberIdentity currentUser) {
        List<WfRunNodeDTO> wfRunNodeDTOs = new ArrayList<>();
        for (WfRunNodeEntity wfRunNodeEntity : wfRunNodeEntityList) {
            WfRunNodeDTO wfRunNodeDTO = mapperToDTO(wfRunNodeEntity, currentUser);
            wfRunNodeDTOs.add(wfRunNodeDTO);
        }


        return wfRunNodeDTOs;
    }
}
