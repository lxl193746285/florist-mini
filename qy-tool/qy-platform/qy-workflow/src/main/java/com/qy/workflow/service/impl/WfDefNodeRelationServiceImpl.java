package com.qy.workflow.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.MemberIdentity;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfDefNodeRelationEntity;
import com.qy.workflow.mapper.WfDefNodeRelationMapper;
import com.qy.workflow.service.WfDefNodeRelationService;
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
 * 工作流_设计_节点关系 服务实现类
 *
 * @author iFeng
 * @since 2022-11-15
 */
@Service
public class WfDefNodeRelationServiceImpl extends ServiceImpl<WfDefNodeRelationMapper, WfDefNodeRelationEntity> implements WfDefNodeRelationService {
    @Autowired
    private WfDefNodeRelationMapper wfDefNodeRelationMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;


    @Override
    public IPage<WfDefNodeRelationEntity> getWfDefNodeRelations(IPage iPage, WfDefNodeRelationQueryDTO wfDefNodeRelationQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfDefNodeRelationEntity> wfDefNodeRelationQueryWrapper = RestUtils.getLambdaQueryWrapper();
        wfDefNodeRelationQueryWrapper.like(wfDefNodeRelationQueryDTO.getName()!=null,WfDefNodeRelationEntity::getName,wfDefNodeRelationQueryDTO.getName());
        wfDefNodeRelationQueryWrapper.eq(wfDefNodeRelationQueryDTO.getStatus()!=null,WfDefNodeRelationEntity::getStatus,wfDefNodeRelationQueryDTO.getStatus());
        wfDefNodeRelationQueryWrapper.orderByDesc(WfDefNodeRelationEntity::getCreateTime);
        return super.page(iPage, wfDefNodeRelationQueryWrapper);
    }


    @Override
    public List<WfDefNodeRelationEntity> getWfDefNodeRelations(WfDefNodeRelationQueryDTO wfDefNodeRelationQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfDefNodeRelationEntity> wfDefNodeRelationQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(wfDefNodeRelationQueryWrapper);
    }


    @Override
    public WfDefNodeRelationEntity getWfDefNodeRelation(Long id, MemberIdentity currentUser) {
        WfDefNodeRelationEntity wfDefNodeRelationEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (wfDefNodeRelationEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_节点关系");
        }

        return wfDefNodeRelationEntity;
    }


    @Override
    public WfDefNodeRelationEntity createWfDefNodeRelation(WfDefNodeRelationFormDTO wfDefNodeRelationFormDTO, MemberIdentity currentUser) {
        WfDefNodeRelationEntity wfDefNodeRelationEntity = new WfDefNodeRelationEntity();
        BeanUtils.copyProperties(wfDefNodeRelationFormDTO, wfDefNodeRelationEntity);
        wfDefNodeRelationEntity.setCreateTime(LocalDateTime.now());
        wfDefNodeRelationEntity.setCreatorId(currentUser.getId());
        wfDefNodeRelationEntity.setCreatorName(currentUser.getName());
        wfDefNodeRelationEntity.setUpdatorId(Long.valueOf(0));
        wfDefNodeRelationEntity.setIsDeleted((int)0);
        wfDefNodeRelationEntity.setDeletorId(Long.valueOf(0));

        this.save(wfDefNodeRelationEntity);
        return wfDefNodeRelationEntity;
    }


    @Override
    public WfDefNodeRelationEntity updateWfDefNodeRelation(Long id, WfDefNodeRelationFormDTO wfDefNodeRelationFormDTO, MemberIdentity currentUser) {
        WfDefNodeRelationEntity wfDefNodeRelationEntity = getById(id);
        BeanUtils.copyProperties(wfDefNodeRelationFormDTO, wfDefNodeRelationEntity);
        wfDefNodeRelationEntity.setUpdateTime(LocalDateTime.now());
        wfDefNodeRelationEntity.setUpdatorId(currentUser.getId());
        wfDefNodeRelationEntity.setUpdatorName(currentUser.getName());

        this.updateById(wfDefNodeRelationEntity);
        return wfDefNodeRelationEntity;
    }


    @Override
    public WfDefNodeRelationEntity deleteWfDefNodeRelation(Long id, MemberIdentity currentUser) {
        WfDefNodeRelationEntity wfDefNodeRelationEntity = getWfDefNodeRelation(id, currentUser);
        wfDefNodeRelationEntity.setDeleteTime(LocalDateTime.now());
        wfDefNodeRelationEntity.setDeletorId(currentUser.getId());
        wfDefNodeRelationEntity.setDeletorName(currentUser.getName());

        wfDefNodeRelationEntity.setIsDeleted((int)1);
        this.updateById(wfDefNodeRelationEntity);
        return wfDefNodeRelationEntity;
    }


    @Override
    public WfDefNodeRelationDTO mapperToDTO(WfDefNodeRelationEntity wfDefNodeRelationEntity, MemberIdentity currentUser) {
        WfDefNodeRelationDTO wfDefNodeRelationDTO = new WfDefNodeRelationDTO();
        BeanUtils.copyProperties(wfDefNodeRelationEntity, wfDefNodeRelationDTO);
        return wfDefNodeRelationDTO;
    }


    @Override
    public List<WfDefNodeRelationDTO> mapperToDTO(List<WfDefNodeRelationEntity> wfDefNodeRelationEntityList, MemberIdentity currentUser) {
        List<WfDefNodeRelationDTO> wfDefNodeRelationDTOs = new ArrayList<>();
        for (WfDefNodeRelationEntity wfDefNodeRelationEntity : wfDefNodeRelationEntityList) {
            WfDefNodeRelationDTO wfDefNodeRelationDTO = mapperToDTO(wfDefNodeRelationEntity, currentUser);
            wfDefNodeRelationDTOs.add(wfDefNodeRelationDTO);
        }


        return wfDefNodeRelationDTOs;
    }

    @Override
    public List<WfDefNodeRelationEntity> getNodeRelations(Long wfId, Long nodeId) {
        LambdaQueryWrapper<WfDefNodeRelationEntity> wfDefNodeRelationQueryWrapper = new LambdaQueryWrapper<>();
        wfDefNodeRelationQueryWrapper.eq(WfDefNodeRelationEntity::getWfId, wfId)
                .eq(WfDefNodeRelationEntity::getFromNodeId, nodeId)
                .eq(WfDefNodeRelationEntity::getIsDeleted, 0)
                .eq(WfDefNodeRelationEntity::getStatus,1 );
        return list(wfDefNodeRelationQueryWrapper);
    }
}
