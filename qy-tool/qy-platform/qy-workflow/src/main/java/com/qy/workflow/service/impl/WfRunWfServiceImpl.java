package com.qy.workflow.service.impl;

import com.qy.security.session.MemberIdentity;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfRunWfEntity;
import com.qy.workflow.mapper.WfRunWfMapper;
import com.qy.workflow.service.WfRunWfService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 工作流_执行_工作流 服务实现类
 *
 * @author iFeng
 * @since 2022-11-16
 */
@Service
public class WfRunWfServiceImpl extends ServiceImpl<WfRunWfMapper, WfRunWfEntity> implements WfRunWfService {

    @Override
    public IPage<WfRunWfEntity> getWfRunWfs(IPage iPage, WfRunWfQueryDTO wfRunWfQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfRunWfEntity> wfRunWfQueryWrapper = RestUtils.getLambdaQueryWrapper();
        wfRunWfQueryWrapper.eq(wfRunWfQueryDTO.getStatus()!=null,WfRunWfEntity::getStatus,wfRunWfQueryDTO.getStatus());
        wfRunWfQueryWrapper.orderByDesc(WfRunWfEntity::getCreateTime);
        return super.page(iPage, wfRunWfQueryWrapper);
    }


    @Override
    public List<WfRunWfEntity> getWfRunWfs(WfRunWfQueryDTO wfRunWfQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfRunWfEntity> wfRunWfQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(wfRunWfQueryWrapper);
    }


    @Override
    public WfRunWfEntity getWfRunWf(Long id, MemberIdentity currentUser) {
        WfRunWfEntity wfRunWfEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (wfRunWfEntity == null) {
            throw new RuntimeException("未找到 工作流_执行_工作流");
        }

        return wfRunWfEntity;
    }


    @Override
    public WfRunWfEntity createWfRunWf(WfRunWfFormDTO wfRunWfFormDTO, MemberIdentity currentUser) {
        WfRunWfEntity wfRunWfEntity = new WfRunWfEntity();
        BeanUtils.copyProperties(wfRunWfFormDTO, wfRunWfEntity);
        wfRunWfEntity.setCreateTime(LocalDateTime.now());
        wfRunWfEntity.setCreatorId(currentUser.getId());
        wfRunWfEntity.setCreatorName(currentUser.getName());
        wfRunWfEntity.setUpdatorId(Long.valueOf(0));
        wfRunWfEntity.setIsDeleted(0);
        wfRunWfEntity.setDeletorId(Long.valueOf(0));

        this.save(wfRunWfEntity);
        return wfRunWfEntity;
    }


    @Override
    public WfRunWfEntity updateWfRunWf(Long id, WfRunWfFormDTO wfRunWfFormDTO, MemberIdentity currentUser) {
        WfRunWfEntity wfRunWfEntity = getById(id);
        BeanUtils.copyProperties(wfRunWfFormDTO, wfRunWfEntity);
        wfRunWfEntity.setUpdateTime(LocalDateTime.now());
        wfRunWfEntity.setUpdatorId(currentUser.getId());
        wfRunWfEntity.setUpdatorName(currentUser.getName());

        this.updateById(wfRunWfEntity);
        return wfRunWfEntity;
    }



    @Override
    public WfRunWfEntity deleteWfRunWf(Long id, MemberIdentity currentUser) {
        WfRunWfEntity wfRunWfEntity = getWfRunWf(id, currentUser);
        wfRunWfEntity.setDeleteTime(LocalDateTime.now());
        wfRunWfEntity.setDeletorId(currentUser.getId());
        wfRunWfEntity.setDeletorName(currentUser.getName());

        wfRunWfEntity.setIsDeleted(1);
        this.updateById(wfRunWfEntity);
        return wfRunWfEntity;
    }


    @Override
    public WfRunWfDTO mapperToDTO(WfRunWfEntity wfRunWfEntity, MemberIdentity currentUser) {
        WfRunWfDTO wfRunWfDTO = new WfRunWfDTO();
        BeanUtils.copyProperties(wfRunWfEntity, wfRunWfDTO);
        return wfRunWfDTO;
    }

    @Override
    public List<WfRunWfDTO> mapperToDTO(List<WfRunWfEntity> wfRunWfEntityList, MemberIdentity currentUser) {
        List<WfRunWfDTO> wfRunWfDTOs = new ArrayList<>();
        for (WfRunWfEntity wfRunWfEntity : wfRunWfEntityList) {
            WfRunWfDTO wfRunWfDTO = mapperToDTO(wfRunWfEntity, currentUser);
            wfRunWfDTOs.add(wfRunWfDTO);
        }
        return wfRunWfDTOs;
    }
}
