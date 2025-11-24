package com.qy.wf.defNodeRelation.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationDTO;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationFormDTO;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationQueryDTO;
import com.qy.wf.defNodeRelation.entity.DefNodeRelationEntity;
import com.qy.wf.defNodeRelation.mapper.DefNodeRelationMapper;
import com.qy.wf.defNodeRelation.service.DefNodeRelationService;
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
import java.util.stream.Collectors;


/**
 * 工作流_设计_节点关系 服务实现类
 *
 * @author syf
 * @since 2022-11-14
 */
@Service
public class DefNodeRelationServiceImpl extends ServiceImpl<DefNodeRelationMapper, DefNodeRelationEntity> implements DefNodeRelationService {
    @Autowired
    private DefNodeRelationMapper defNodeRelationMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Override
    public IPage<DefNodeRelationEntity> getDefNodeRelations(IPage iPage, DefNodeRelationQueryDTO defNodeRelationQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeRelationEntity> defNodeRelationQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeRelationQueryWrapper.like(defNodeRelationQueryDTO.getName()!=null,DefNodeRelationEntity::getName,defNodeRelationQueryDTO.getName());
        defNodeRelationQueryWrapper.eq(defNodeRelationQueryDTO.getStatus()!=null,DefNodeRelationEntity::getStatus,defNodeRelationQueryDTO.getStatus());
        defNodeRelationQueryWrapper.orderByDesc(DefNodeRelationEntity::getCreateTime);
        return super.page(iPage, defNodeRelationQueryWrapper);
    }

    @Override
    public List<DefNodeRelationEntity> getDefNodeRelations(DefNodeRelationQueryDTO defNodeRelationQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeRelationEntity> defNodeRelationQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(defNodeRelationQueryWrapper);
    }

    @Override
    public DefNodeRelationEntity getDefNodeRelation(Long id, EmployeeIdentity currentUser) {
        DefNodeRelationEntity defNodeRelationEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defNodeRelationEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_节点关系");
        }

        return defNodeRelationEntity;
    }

    @Override
    public DefNodeRelationEntity createDefNodeRelation(DefNodeRelationFormDTO defNodeRelationFormDTO, EmployeeIdentity currentUser) {
        DefNodeRelationEntity defNodeRelationEntity = new DefNodeRelationEntity();
        BeanUtils.copyProperties(defNodeRelationFormDTO, defNodeRelationEntity);
        defNodeRelationEntity.setCreateTime(LocalDateTime.now());
        defNodeRelationEntity.setCreatorId(currentUser.getId());
        defNodeRelationEntity.setCreatorName(currentUser.getName());
        defNodeRelationEntity.setIsDeleted(0);
        this.save(defNodeRelationEntity);
        return defNodeRelationEntity;
    }

    @Override
    public DefNodeRelationEntity updateDefNodeRelation(Long id, DefNodeRelationFormDTO defNodeRelationFormDTO, EmployeeIdentity currentUser) {
        DefNodeRelationEntity defNodeRelationEntity = getById(id);
        BeanUtils.copyProperties(defNodeRelationFormDTO, defNodeRelationEntity);
        defNodeRelationEntity.setUpdateTime(LocalDateTime.now());
        defNodeRelationEntity.setUpdatorId(currentUser.getId());
        defNodeRelationEntity.setUpdatorName(currentUser.getName());

        this.updateById(defNodeRelationEntity);
        return defNodeRelationEntity;
    }

    @Override
    public DefNodeRelationEntity deleteDefNodeRelation(Long id, EmployeeIdentity currentUser) {
        DefNodeRelationEntity defNodeRelationEntity = getDefNodeRelation(id, currentUser);
        defNodeRelationEntity.setDeleteTime(LocalDateTime.now());
        defNodeRelationEntity.setDeletorId(currentUser.getId());
        defNodeRelationEntity.setDeletorName(currentUser.getName());

        defNodeRelationEntity.setIsDeleted(1);
        this.updateById(defNodeRelationEntity);
        return defNodeRelationEntity;
    }

    @Override
    public DefNodeRelationDTO mapperToDTO(DefNodeRelationEntity defNodeRelationEntity, EmployeeIdentity currentUser) {
        DefNodeRelationDTO defNodeRelationDTO = new DefNodeRelationDTO();
        BeanUtils.copyProperties(defNodeRelationEntity, defNodeRelationDTO);
        return defNodeRelationDTO;
    }

    @Override
    public List<DefNodeRelationDTO> mapperToDTO(List<DefNodeRelationEntity> defNodeRelationEntityList, EmployeeIdentity currentUser) {
        List<DefNodeRelationDTO> defNodeRelationDTOs = new ArrayList<>();
        for (DefNodeRelationEntity defNodeRelationEntity : defNodeRelationEntityList) {
            DefNodeRelationDTO defNodeRelationDTO = mapperToDTO(defNodeRelationEntity, currentUser);
            defNodeRelationDTOs.add(defNodeRelationDTO);
        }


        return defNodeRelationDTOs;
    }

    @Override
    public void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, EmployeeIdentity currentUser) {
        //根据工作流id查询已存在的数据
        LambdaQueryWrapper<DefNodeRelationEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(DefNodeRelationEntity::getWfId, defNodeBacthDTO.getWfId());
        List<DefNodeRelationEntity> oldDefNodeRelationEntityList = this.list(queryWrapper);

        //判断传入的值和已存在的值是否存在
        List<String> oldNodeRelationCollect = oldDefNodeRelationEntityList.stream().map(DefNodeRelationEntity::getNodeCode).collect(Collectors.toList());
        List<String> newNodeRelationCollect = defNodeBacthDTO.getDefNodeRelationFormDTOList().stream().map(DefNodeRelationFormDTO::getNodeCode).collect(Collectors.toList());

        //取交集（需修改）
        List<String> intersection = oldNodeRelationCollect.stream().filter(item -> newNodeRelationCollect.contains(item)).collect(Collectors.toList());
        //取差集处理（需新增）
        List<String> strNewDifList = newNodeRelationCollect.stream().filter(num -> !oldNodeRelationCollect.contains(num)).collect(Collectors.toList());//取差集(需新增)
        //取差集处理（需删除）
        List<String> strOldDifList = oldNodeRelationCollect.stream().filter(num -> !newNodeRelationCollect.contains(num)).collect(Collectors.toList());//取差集(需删除)
        //新增处理
        List<DefNodeRelationEntity> needAddEntityList = new ArrayList<>();
        for (String nodeCode : strNewDifList) {
            for (DefNodeRelationFormDTO defNodeRelationFormDTO : defNodeBacthDTO.getDefNodeRelationFormDTOList()) {
                if (nodeCode.equals(defNodeRelationFormDTO.getNodeCode())) {
                    DefNodeRelationEntity defNodeRelationEntity = new DefNodeRelationEntity();
                    BeanUtils.copyProperties(defNodeRelationFormDTO, defNodeRelationEntity);
                    defNodeRelationEntity.setCompanyId(currentUser.getOrganizationId());
                    defNodeRelationEntity.setCreateTime(LocalDateTime.now());
                    defNodeRelationEntity.setCreatorId(currentUser.getId());
                    defNodeRelationEntity.setCreatorName(currentUser.getName());
                    defNodeRelationEntity.setIsDeleted(0);
                    needAddEntityList.add(defNodeRelationEntity);
                }
            }
        }

        //修改处理
        List<DefNodeRelationFormDTO> needUpdateFormList = new ArrayList<>();
        List<DefNodeRelationEntity> needUpdateEntityList = new ArrayList<>();
        for (String nodeCode : intersection) {
            for (DefNodeRelationFormDTO defNodeRelationFormDTO : defNodeBacthDTO.getDefNodeRelationFormDTOList()) {
                if (nodeCode.equals(defNodeRelationFormDTO.getNodeCode())) {
                    needUpdateFormList.add(defNodeRelationFormDTO);
                }
            }
            for (DefNodeRelationEntity defNodeRelationEntity : oldDefNodeRelationEntityList) {
                if (nodeCode.equals(defNodeRelationEntity.getNodeCode())) {
                    needUpdateEntityList.add(defNodeRelationEntity);
                }
            }
        }
        //修改赋值操作
        for (DefNodeRelationFormDTO defNodeRelationFormDTO : needUpdateFormList) {
            for (DefNodeRelationEntity defNodeRelationEntity : needUpdateEntityList) {
                if (defNodeRelationFormDTO.getNodeCode().equals(defNodeRelationEntity.getNodeCode())) {
                    BeanUtils.copyProperties(defNodeRelationFormDTO,defNodeRelationEntity);
                    defNodeRelationEntity.setUpdateTime(LocalDateTime.now());
                    defNodeRelationEntity.setUpdatorId(currentUser.getId());
                    defNodeRelationEntity.setUpdatorName(currentUser.getName());
                }
            }
        }

        //新增节点id赋值
        for (DefNodeRelationEntity defNodeRelationEntity : needAddEntityList) {
            for (DefNodeEntity defNodeEntity : needDefNodeAddEntityList) {
                if (defNodeRelationEntity.getFromNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setFromNodeId(defNodeEntity.getId());
                }
                if (defNodeRelationEntity.getToNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setToNodeId(defNodeEntity.getId());
                }
            }
            for (DefNodeEntity defNodeEntity : needDefNodeUpdateEntityList) {
                if (defNodeRelationEntity.getFromNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setFromNodeId(defNodeEntity.getId());
                }
                if (defNodeRelationEntity.getToNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setToNodeId(defNodeEntity.getId());
                }
            }
        }

        //修改节点赋值
        for (DefNodeRelationEntity defNodeRelationEntity : needUpdateEntityList) {
            for (DefNodeEntity defNodeEntity : needDefNodeAddEntityList) {
                if (defNodeRelationEntity.getFromNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setFromNodeId(defNodeEntity.getId());
                }
                if (defNodeRelationEntity.getToNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setToNodeId(defNodeEntity.getId());
                }
            }
            for (DefNodeEntity defNodeEntity : needDefNodeUpdateEntityList) {
                if (defNodeRelationEntity.getFromNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setFromNodeId(defNodeEntity.getId());
                }
                if (defNodeRelationEntity.getToNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeRelationEntity.setToNodeId(defNodeEntity.getId());
                }
            }
        }


        //新增处理
        if (!CollectionUtils.isEmpty(needAddEntityList)) {
            this.saveBatch(needAddEntityList);
        }
        //修改处理
        if (!CollectionUtils.isEmpty(needUpdateEntityList)) {
            this.updateBatchById(needUpdateEntityList);
        }
        //删除处理
        if (!CollectionUtils.isEmpty(strOldDifList)) {
            defNodeRelationMapper.deleteByNodeCode(strOldDifList);
        }



    }
}
