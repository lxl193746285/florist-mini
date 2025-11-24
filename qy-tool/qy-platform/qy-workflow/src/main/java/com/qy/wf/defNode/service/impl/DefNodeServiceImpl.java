package com.qy.wf.defNode.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkOperationService;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defDefine.service.DefDefineService;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.dto.DefNodeDTO;
import com.qy.wf.defNode.dto.DefNodeFormNodeDTO;
import com.qy.wf.defNode.dto.DefNodeQueryDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNode.mapper.DefNodeMapper;
import com.qy.wf.defNode.service.DefNodeService;
import com.qy.wf.defNodeEvent.service.DefNodeEventService;
import com.qy.wf.defNodeRelation.entity.DefNodeRelationEntity;
import com.qy.wf.defNodeRelation.service.DefNodeRelationService;
import com.qy.wf.defNodeUser.service.DefNodeUserService;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseFormDTO;
import com.qy.wf.nodeRepulse.service.DefNodeRepulseService;
import com.qy.wf.nodetable.service.NodeTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 工作流_设计_节点 服务实现类
 *
 * @author syf
 * @since 2022-11-14
 */
@Service
public class DefNodeServiceImpl extends ServiceImpl<DefNodeMapper, DefNodeEntity> implements DefNodeService {
    @Autowired
    private DefNodeMapper defNodeMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Autowired
    private DefNodeRelationService defNodeRelationService;

    @Autowired
    private DefNodeUserService defNodeUserService;

    @Autowired
    private DefDefineService defDefineService;

    @Autowired
    private NodeTableService nodeTableService;

    @Autowired
    private DefNodeEventService defNodeEventService;

    @Autowired
    private DefNodeRepulseService defNodeRepulseService;

    @Override
    public IPage<DefNodeEntity> getDefNodes(IPage iPage, DefNodeQueryDTO defNodeQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeEntity> defNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeQueryWrapper.like(defNodeQueryDTO.getName()!=null,DefNodeEntity::getName,defNodeQueryDTO.getName());
        defNodeQueryWrapper.eq(defNodeQueryDTO.getStatus()!=null,DefNodeEntity::getStatus,defNodeQueryDTO.getStatus());
        defNodeQueryWrapper.orderByDesc(DefNodeEntity::getCreateTime);
        return super.page(iPage, defNodeQueryWrapper);
    }

    @Override
    public IPage<DefNodeEntity> getDefNodesSelect(IPage iPage, DefNodeQueryDTO defNodeQueryDTO, EmployeeIdentity currentUser) {
        if (defNodeQueryDTO.getWfId() == null) {
            throw new ValidationException("请选择工作流！");
        }
        LambdaQueryWrapper<DefNodeEntity> defNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeQueryWrapper.like(defNodeQueryDTO.getName()!=null,DefNodeEntity::getName,defNodeQueryDTO.getName());
        defNodeQueryWrapper.eq(DefNodeEntity::getStatus,1);
        defNodeQueryWrapper.eq(DefNodeEntity::getWfId, defNodeQueryDTO.getWfId());
//        defNodeQueryWrapper.orderByAsc(DefNodeEntity::getNodeType);
        defNodeQueryWrapper.orderByAsc(DefNodeEntity::getSort);
        return super.page(iPage, defNodeQueryWrapper);
    }

    @Override
    public List<DefNodeDTO> getNodesByWfId(Long wfId, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(DefNodeEntity::getWfId, wfId);
        queryWrapper.orderByDesc(DefNodeEntity::getCreateTime);
        List<DefNodeEntity> list = this.list(queryWrapper);
        return mapperToDTO(list, currentUser);
    }

    @Override
    public List<DefNodeEntity> getDefNodes(DefNodeQueryDTO defNodeQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeEntity> defNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(defNodeQueryWrapper);
    }

    @Override
    public DefNodeEntity getDefNode(Long id, EmployeeIdentity currentUser) {
        DefNodeEntity defNodeEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defNodeEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_节点");
        }

        return defNodeEntity;
    }

    @Override
    public DefNodeEntity createDefNode(DefNodeFormNodeDTO defNodeFormNodeDTO, EmployeeIdentity currentUser) {
        DefNodeEntity defNodeEntity = new DefNodeEntity();
        BeanUtils.copyProperties(defNodeFormNodeDTO, defNodeEntity);
        defNodeEntity.setCreateTime(LocalDateTime.now());
        defNodeEntity.setCreatorId(currentUser.getId());
        defNodeEntity.setCreatorName(currentUser.getName());
        defNodeEntity.setIsDeleted(0);
        this.save(defNodeEntity);
        return defNodeEntity;
    }

    @Override
    public DefNodeEntity updateDefNode(Long id, DefNodeFormNodeDTO defNodeFormNodeDTO, EmployeeIdentity currentUser) {
        DefNodeEntity defNodeEntity = getById(id);
        BeanUtils.copyProperties(defNodeFormNodeDTO, defNodeEntity);
        defNodeEntity.setUpdateTime(LocalDateTime.now());
        defNodeEntity.setUpdatorId(currentUser.getId());
        defNodeEntity.setUpdatorName(currentUser.getName());

        this.updateById(defNodeEntity);
        return defNodeEntity;
    }

    @Override
    public DefNodeEntity deleteDefNode(Long id, EmployeeIdentity currentUser) {
        DefNodeEntity defNodeEntity = getDefNode(id, currentUser);
        defNodeEntity.setDeleteTime(LocalDateTime.now());
        defNodeEntity.setDeletorId(currentUser.getId());
        defNodeEntity.setDeletorName(currentUser.getName());

        defNodeEntity.setIsDeleted(1);
        this.updateById(defNodeEntity);
        return defNodeEntity;
    }

    @Override
    public DefNodeDTO getByNodeCode(String nodeCode, Integer type, EmployeeIdentity currentUser) {
        DefNodeDTO defNodeDTO = new DefNodeDTO();
        if (type == 1) {
            LambdaQueryWrapper<DefNodeEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
            queryWrapper.eq(DefNodeEntity::getNodeCode, nodeCode);
            DefNodeEntity defNodeEntity = this.getOne(queryWrapper);
            if (defNodeEntity == null) {
                return defNodeDTO;
            } else {
                BeanUtils.copyProperties(defNodeEntity, defNodeDTO);
            }
        } else if (type == 2) {
            LambdaQueryWrapper<DefNodeRelationEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
            queryWrapper.eq(DefNodeRelationEntity::getNodeCode, nodeCode);
            DefNodeRelationEntity defNodeRelationEntity = defNodeRelationService.getOne(queryWrapper);
            if (defNodeRelationEntity == null) {
                return defNodeDTO;
            } else {
                BeanUtils.copyProperties(defNodeRelationEntity, defNodeDTO);
            }
        }

        return defNodeDTO;
    }

    @Override
    public DefNodeDTO mapperToDTO(DefNodeEntity defNodeEntity, EmployeeIdentity currentUser) {
        DefNodeDTO defNodeDTO = new DefNodeDTO();
        BeanUtils.copyProperties(defNodeEntity, defNodeDTO);
        return defNodeDTO;
    }

    @Override
    public List<DefNodeDTO> mapperToDTO(List<DefNodeEntity> defNodeEntityList, EmployeeIdentity currentUser) {
        List<DefNodeDTO> defNodeDTOs = new ArrayList<>();
        for (DefNodeEntity defNodeEntity : defNodeEntityList) {
            DefNodeDTO defNodeDTO = mapperToDTO(defNodeEntity, currentUser);
            defNodeDTOs.add(defNodeDTO);
        }

        return defNodeDTOs;
    }

    @Override
    public DefNodeEntity getDefNodeById(Long curNodeId, Long wfId) {
        LambdaQueryWrapper<DefNodeEntity> defNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeQueryWrapper.eq(curNodeId!=null, DefNodeEntity::getId, curNodeId);
        defNodeQueryWrapper.eq(wfId!=null, DefNodeEntity::getWfId, wfId);
        return super.getOne(defNodeQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCreateDefNode(DefNodeBacthDTO defNodeBacthDTO, EmployeeIdentity currentUser) {
        List<Integer> collect = defNodeBacthDTO.getDefNodeFormNodeDTOList().stream().map(DefNodeFormNodeDTO::getNodeType).collect(Collectors.toList());
        int startNum = 0;
        int endNum = 0;
        for (Integer type : collect) {
            if (type == 0) {//开始
                startNum = startNum + 1;
            }
            if (type == 1) {//结束
                endNum = endNum + 1;
            }
        }
        if (startNum > 1) {
            throw new ValidationException("开始节点只能为一个");
        }
        if (endNum > 1) {
            throw new ValidationException("结束节点只能为一个");
        }


        Long companyId = currentUser.getOrganizationId();
        //保存前先批量删除再新增
//        defNodeRelationService.batchDeleteByWfId(defNodeBacthDTO.getWfId());
//        defNodeUserService.batchDeleteByWfId(defNodeBacthDTO.getWfId());


        /**判断是否存在，存在则修改，不存在则新增；旧数据需要删除得，删除处理*/
        /*设计节点处理*/
        //根据工作流id查询已存在的数据
        LambdaQueryWrapper<DefNodeEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(DefNodeEntity::getWfId, defNodeBacthDTO.getWfId());
        List<DefNodeEntity> oldDefNodeEntityList = this.list(queryWrapper);

        //判断传入的值和已存在的值是否存在
        List<String> oldNodeCollect = oldDefNodeEntityList.stream().map(DefNodeEntity::getNodeCode).collect(Collectors.toList());
        List<String> newNodeCollect = defNodeBacthDTO.getDefNodeFormNodeDTOList().stream().map(DefNodeFormNodeDTO::getNodeCode).collect(Collectors.toList());

        //取交集（需修改）
        List<String> intersection = oldNodeCollect.stream().filter(item -> newNodeCollect.contains(item)).collect(Collectors.toList());
        //取差集处理（需新增）
        List<String> strNewDifList = newNodeCollect.stream().filter(num -> !oldNodeCollect.contains(num)).collect(Collectors.toList());//取差集(需新增)
        //取差集处理（需删除）
        List<String> strOldDifList = oldNodeCollect.stream().filter(num -> !newNodeCollect.contains(num)).collect(Collectors.toList());//取差集(需删除)
        //新增处理
        List<DefNodeEntity> needAddEntityList = new ArrayList<>();
        for (String nodeCode : strNewDifList) {
            for (DefNodeFormNodeDTO defNodeFormNodeDTO : defNodeBacthDTO.getDefNodeFormNodeDTOList()) {
                if (nodeCode.equals(defNodeFormNodeDTO.getNodeCode())) {
                    DefNodeEntity defNodeEntity = new DefNodeEntity();
                    BeanUtils.copyProperties(defNodeFormNodeDTO, defNodeEntity);
                    defNodeEntity.setCompanyId(companyId);
                    defNodeEntity.setCreateTime(LocalDateTime.now());
                    defNodeEntity.setCreatorId(currentUser.getId());
                    defNodeEntity.setCreatorName(currentUser.getName());
                    defNodeEntity.setIsDeleted(0);
                    needAddEntityList.add(defNodeEntity);
                }
            }
        }
        
        //修改处理
        List<DefNodeFormNodeDTO> needUpdateFormList = new ArrayList<>();
        List<DefNodeEntity> needUpdateEntityList = new ArrayList<>();
        for (String nodeCode : intersection) {
            for (DefNodeFormNodeDTO defNodeFormNodeDTO : defNodeBacthDTO.getDefNodeFormNodeDTOList()) {
                if (nodeCode.equals(defNodeFormNodeDTO.getNodeCode())) {
                    needUpdateFormList.add(defNodeFormNodeDTO);
                }
            }
            for (DefNodeEntity defNodeEntity : oldDefNodeEntityList) {
                if (nodeCode.equals(defNodeEntity.getNodeCode())) {
                    needUpdateEntityList.add(defNodeEntity);
                }
            }
        }
        //修改赋值操作
        for (DefNodeFormNodeDTO defNodeFormNodeDTO : needUpdateFormList) {
            for (DefNodeEntity defNodeEntity : needUpdateEntityList) {
                if (defNodeFormNodeDTO.getNodeCode().equals(defNodeEntity.getNodeCode())) {
                    BeanUtils.copyProperties(defNodeFormNodeDTO,defNodeEntity);
                    defNodeEntity.setUpdateTime(LocalDateTime.now());
                    defNodeEntity.setUpdatorId(currentUser.getId());
                    defNodeEntity.setUpdatorName(currentUser.getName());
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
            defNodeMapper.deleteByNodeCode(strOldDifList);
        }

        List<String> needDeleteNodeList = strOldDifList;


        //节点打回处理
        if (!CollectionUtils.isEmpty(defNodeBacthDTO.getDefNodeFormNodeDTOList())) {
            List<DefNodeRepulseFormDTO> defNodeRepulseFormDTOS = new ArrayList<>();
            for (DefNodeFormNodeDTO defNodeFormNodeDTO : defNodeBacthDTO.getDefNodeFormNodeDTOList()) {
                if (!CollectionUtils.isEmpty(defNodeFormNodeDTO.getNodeRepulseFormDTOList())) {
                    for (DefNodeRepulseFormDTO defNodeRepulseFormDTO : defNodeFormNodeDTO.getNodeRepulseFormDTOList()) {
                        defNodeRepulseFormDTO.setCurNodeCode(defNodeFormNodeDTO.getNodeCode());
                        defNodeRepulseFormDTOS.add(defNodeRepulseFormDTO);
                    }
                }
            }
            /**打回节点配置*/
            defNodeRepulseService.batchSave(defNodeBacthDTO, defNodeRepulseFormDTOS, currentUser);
        }



        /**节点关系批量处理处理*/
        //节点关系批量保存
        defNodeRelationService.batchSave(defNodeBacthDTO, needAddEntityList, needUpdateEntityList, currentUser);

        //获取工作流节点数据
        LambdaQueryWrapper<DefNodeEntity> queryWrapper1 = RestUtils.getLambdaQueryWrapper();
        queryWrapper1.eq(DefNodeEntity::getWfId, defNodeBacthDTO.getWfId());
        List<DefNodeEntity> list = this.list(queryWrapper1);


        /**节点人员批量保存*/
        //节点人员批量保存
//        defNodeUserService.batchSave(defNodeBacthDTO, needAddEntityList, needUpdateEntityList, currentUser);
        defNodeUserService.batchSaveV2(defNodeBacthDTO,list, needAddEntityList, needUpdateEntityList, needDeleteNodeList, currentUser);

        /**节点表单批量保存*/
        nodeTableService.batchSave(defNodeBacthDTO,list, needAddEntityList, needUpdateEntityList, needDeleteNodeList, currentUser);

        /**节点事件批量保存*/
        defNodeEventService.batchSave(defNodeBacthDTO,list, needAddEntityList, needUpdateEntityList, needDeleteNodeList, currentUser);

        /**回调保存工作流定义修改数据*/
        defDefineService.callbackUpdate(defNodeBacthDTO.getWfId(),defNodeBacthDTO.getDefDefineFormDTO(), defNodeBacthDTO.getWfStr());

    }
}
