package com.qy.wf.defNodeEvent.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNodeEvent.dto.DefNodeEventDTO;
import com.qy.wf.defNodeEvent.dto.DefNodeEventFormDTO;
import com.qy.wf.defNodeEvent.dto.DefNodeEventQueryDTO;
import com.qy.wf.defNodeEvent.entity.DefNodeEventEntity;
import com.qy.wf.defNodeEvent.mapper.DefNodeEventMapper;
import com.qy.wf.defNodeEvent.service.DefNodeEventService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 工作流_设计_节点事件 服务实现类
 *
 * @author syf
 * @since 2022-11-21
 */
@Service
public class DefNodeEventServiceImpl extends ServiceImpl<DefNodeEventMapper, DefNodeEventEntity> implements DefNodeEventService {
    @Autowired
    private DefNodeEventMapper defNodeEventMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Override
    public IPage<DefNodeEventEntity> getDefNodeEvents(IPage iPage, DefNodeEventQueryDTO defNodeEventQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeEventEntity> defNodeEventQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeEventQueryWrapper.eq(defNodeEventQueryDTO.getStatus()!=null,DefNodeEventEntity::getStatus,defNodeEventQueryDTO.getStatus());
        defNodeEventQueryWrapper.orderByDesc(DefNodeEventEntity::getCreateTime);
        return super.page(iPage, defNodeEventQueryWrapper);
    }

    @Override
    public List<DefNodeEventEntity> getDefNodeEvents(DefNodeEventQueryDTO defNodeEventQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeEventEntity> defNodeEventQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(defNodeEventQueryWrapper);
    }

    @Override
    public DefNodeEventEntity getDefNodeEvent(Long id, EmployeeIdentity currentUser) {
        DefNodeEventEntity defNodeEventEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defNodeEventEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_节点事件");
        }

        return defNodeEventEntity;
    }

    @Override
    public DefNodeEventEntity createDefNodeEvent(DefNodeEventFormDTO defNodeEventFormDTO, EmployeeIdentity currentUser) {
        DefNodeEventEntity defNodeEventEntity = new DefNodeEventEntity();
        BeanUtils.copyProperties(defNodeEventFormDTO, defNodeEventEntity);
        defNodeEventEntity.setCreateTime(LocalDateTime.now());
        defNodeEventEntity.setCreatorId(currentUser.getId());
        defNodeEventEntity.setCreatorName(currentUser.getName());
        defNodeEventEntity.setIsDeleted(0);
        this.save(defNodeEventEntity);
        return defNodeEventEntity;
    }

    @Override
    public DefNodeEventEntity updateDefNodeEvent(Long id, DefNodeEventFormDTO defNodeEventFormDTO, EmployeeIdentity currentUser) {
        DefNodeEventEntity defNodeEventEntity = getById(id);
        BeanUtils.copyProperties(defNodeEventFormDTO, defNodeEventEntity);
        defNodeEventEntity.setUpdateTime(LocalDateTime.now());
        defNodeEventEntity.setUpdatorId(currentUser.getId());
        defNodeEventEntity.setUpdatorName(currentUser.getName());

        this.updateById(defNodeEventEntity);
        return defNodeEventEntity;
    }

    @Override
    public DefNodeEventEntity deleteDefNodeEvent(Long id, EmployeeIdentity currentUser) {
        DefNodeEventEntity defNodeEventEntity = getDefNodeEvent(id, currentUser);
        defNodeEventEntity.setDeleteTime(LocalDateTime.now());
        defNodeEventEntity.setDeletorId(currentUser.getId());
        defNodeEventEntity.setDeletorName(currentUser.getName());

        defNodeEventEntity.setIsDeleted(1);
        this.updateById(defNodeEventEntity);
        return defNodeEventEntity;
    }

    @Override
    public DefNodeEventDTO mapperToDTO(DefNodeEventEntity defNodeEventEntity, EmployeeIdentity currentUser) {
        DefNodeEventDTO defNodeEventDTO = new DefNodeEventDTO();
        BeanUtils.copyProperties(defNodeEventEntity, defNodeEventDTO);
        return defNodeEventDTO;
    }

    @Override
    public List<DefNodeEventDTO> mapperToDTO(List<DefNodeEventEntity> defNodeEventEntityList, EmployeeIdentity currentUser) {
        List<DefNodeEventDTO> defNodeEventDTOs = new ArrayList<>();
        for (DefNodeEventEntity defNodeEventEntity : defNodeEventEntityList) {
            DefNodeEventDTO defNodeEventDTO = mapperToDTO(defNodeEventEntity, currentUser);
            defNodeEventDTOs.add(defNodeEventDTO);
        }


        return defNodeEventDTOs;
    }

    @Override
    public void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> list, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, List<String> needDeleteNodeList, EmployeeIdentity currentUser) {
        //节点事件，先删除再新增
        List<DefNodeEventFormDTO> nodeEventFormDTOList = defNodeBacthDTO.getNodeEventFormDTOList();
//        List<String> collect = nodeEventFormDTOList.stream().map(DefNodeEventFormDTO::getNodeCode).collect(Collectors.toList());
//        List<String> collect1 = collect.stream().distinct().collect(Collectors.toList());
//        collect1.addAll(needDeleteNodeList);//添加需要删除节点的code
//        //删除处理
//        if (!CollectionUtils.isEmpty(collect1)) {
//            defNodeEventMapper.deleteByNodeCode(collect1);
//        }
        defNodeEventMapper.deleteByWfId(defNodeBacthDTO.getWfId());

        //新增处理
        List<DefNodeEventEntity> needAddEntityList = new ArrayList<>();
        for (DefNodeEventFormDTO defNodeEventFormDTO : nodeEventFormDTOList) {
            DefNodeEventEntity defNodeEventEntity = new DefNodeEventEntity();
            BeanUtils.copyProperties(defNodeEventFormDTO, defNodeEventEntity);
            defNodeEventEntity.setCreateTime(LocalDateTime.now());
            defNodeEventEntity.setCreatorId(currentUser.getId());
            defNodeEventEntity.setCreatorName(currentUser.getName());
            defNodeEventEntity.setIsDeleted(0);
            Optional<DefNodeEntity> first = list.stream().filter(o -> o.getNodeCode().equals(defNodeEventEntity.getNodeCode())).findFirst();
            if (first.isPresent()) {
                defNodeEventEntity.setNodeId(first.get().getId());
            }
            needAddEntityList.add(defNodeEventEntity);
        }
//        //新增节点id赋值
//        for (DefNodeEventEntity defNodeEventEntity : needAddEntityList) {
//            for (DefNodeEntity defNodeEntity : needDefNodeAddEntityList) {
//                if (defNodeEventEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
//                    defNodeEventEntity.setNodeId(defNodeEntity.getId());
//                }
//            }
//            for (DefNodeEntity defNodeEntity : needDefNodeUpdateEntityList) {
//                if (defNodeEventEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
//                    defNodeEventEntity.setNodeId(defNodeEntity.getId());
//                }
//            }
//        }
        //新增处理
        if (!CollectionUtils.isEmpty(needAddEntityList)) {
            this.saveBatch(needAddEntityList);
        }

    }
}
