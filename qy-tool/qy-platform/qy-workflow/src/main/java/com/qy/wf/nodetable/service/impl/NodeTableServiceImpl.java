package com.qy.wf.nodetable.service.impl;

import com.google.common.base.Strings;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.enums.ArkOperation;
import com.qy.common.service.ArkCodeTableService;
import com.qy.common.service.ArkOperationService;
import com.qy.security.permission.action.Action;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.nodetable.dto.*;
import com.qy.wf.nodetable.entity.NodeTableEntity;
import com.qy.wf.nodetable.enums.NodeTableAction;
import com.qy.wf.nodetable.mapper.NodeTableMapper;
import com.qy.wf.nodetable.service.NodeTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 工作流_设计_节点表单 服务实现类
 *
 * @author hh
 * @since 2022-11-19
 */
@Service
public class NodeTableServiceImpl extends ServiceImpl<NodeTableMapper, NodeTableEntity> implements NodeTableService {
    @Autowired
    private NodeTableMapper nodeTableMapper;
    @Autowired
    private ArkCodeTableService arkCodeTableService;
    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Override
    public IPage<NodeTableEntity> getNodeTables(IPage iPage, NodeTableQueryDTO nodeTableQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<NodeTableEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        LocalDate startDate;
        LocalDate endDate;
        if (!Strings.isNullOrEmpty(nodeTableQueryDTO.getStartCreateDate())) {
            startDate = LocalDate.parse(nodeTableQueryDTO.getStartCreateDate());
            queryWrapper.ge(null != startDate, NodeTableEntity::getCreateTime, startDate);
        }
        if (!Strings.isNullOrEmpty(nodeTableQueryDTO.getEndCreateDate())) {
            endDate = LocalDate.parse(nodeTableQueryDTO.getEndCreateDate());
            if (null != endDate) {
                queryWrapper.le(NodeTableEntity::getCreateTime, endDate.atTime(23, 59, 59));
            }
        }
        queryWrapper.eq(nodeTableQueryDTO.getCompanyId()!=null,NodeTableEntity::getCompanyId,nodeTableQueryDTO.getCompanyId());
        queryWrapper.eq(nodeTableQueryDTO.getWfId()!=null,NodeTableEntity::getWfId,nodeTableQueryDTO.getWfId());
        queryWrapper.eq(nodeTableQueryDTO.getNodeId()!=null,NodeTableEntity::getNodeId,nodeTableQueryDTO.getNodeId());
        queryWrapper.eq(nodeTableQueryDTO.getTableType()!=null,NodeTableEntity::getTableType,nodeTableQueryDTO.getTableType());
        queryWrapper.eq(nodeTableQueryDTO.getTableId()!=null,NodeTableEntity::getTableId,nodeTableQueryDTO.getTableId());
        queryWrapper.eq(nodeTableQueryDTO.getCompanyId()!=null,NodeTableEntity::getCompanyId,nodeTableQueryDTO.getCompanyId());
        queryWrapper.eq(nodeTableQueryDTO.getStatus()!=null,NodeTableEntity::getStatus,nodeTableQueryDTO.getStatus());
        queryWrapper.orderByDesc(NodeTableEntity::getCreateTime);
        return super.page(iPage, queryWrapper);
    }

    @Override
    public List<NodeTableEntity> getNodeTables(NodeTableQueryDTO nodeTableQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<NodeTableEntity> nodeTableQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(nodeTableQueryWrapper);
    }

    @Override
    public NodeTableEntity getNodeTable(Long id, EmployeeIdentity currentUser) {
        NodeTableEntity nodeTableEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (nodeTableEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_节点表单");
        }

        return nodeTableEntity;
    }

    @Override
    public NodeTableEntity createNodeTable(NodeTableFormDTO nodeTableFormDTO, EmployeeIdentity currentUser) {
        NodeTableEntity nodeTableEntity = new NodeTableEntity();
        BeanUtils.copyProperties(nodeTableFormDTO, nodeTableEntity);
        nodeTableEntity.setCreateTime(LocalDateTime.now());
        nodeTableEntity.setCreatorId(currentUser.getId());
        nodeTableEntity.setCreatorName(currentUser.getName());
        nodeTableEntity.setUpdatorId(Long.valueOf(0));
        nodeTableEntity.setIsDeleted(0);
        nodeTableEntity.setDeletorId(Long.valueOf(0));

        this.save(nodeTableEntity);
        return nodeTableEntity;
    }

    @Override
    public NodeTableEntity updateNodeTable(Long id, NodeTableFormDTO nodeTableFormDTO, EmployeeIdentity currentUser) {
        NodeTableEntity nodeTableEntity = getById(id);
        BeanUtils.copyProperties(nodeTableFormDTO, nodeTableEntity);
        nodeTableEntity.setUpdateTime(LocalDateTime.now());
        nodeTableEntity.setUpdatorId(currentUser.getId());
        nodeTableEntity.setUpdatorName(currentUser.getName());

        this.updateById(nodeTableEntity);
        return nodeTableEntity;
    }

    @Override
    public NodeTableEntity deleteNodeTable(Long id, EmployeeIdentity currentUser) {
        NodeTableEntity nodeTableEntity = getNodeTable(id, currentUser);
        nodeTableEntity.setDeleteTime(LocalDateTime.now());
        nodeTableEntity.setDeletorId(currentUser.getId());

        nodeTableEntity.setIsDeleted(1);
        this.updateById(nodeTableEntity);
        return nodeTableEntity;
    }

    @Override
    public NodeTableDTO mapperToDTO(NodeTableEntity nodeTableEntity, EmployeeIdentity currentUser) {
        NodeTableDTO nodeTableDTO = new NodeTableDTO();
        BeanUtils.copyProperties(nodeTableEntity, nodeTableDTO);
        nodeTableDTO.setStatusName(arkCodeTableService.getNameBySystem("common_status", nodeTableDTO.getStatus()));
        nodeTableDTO.setTableTypeName(arkCodeTableService.getNameBySystem("table_type", nodeTableDTO.getStatus()));
        if (currentUser != null) {
            //权限按钮
            List<Action> actions = new ArrayList<>();
            if (currentUser.hasPermission(NodeTableAction.VIEW.getPermission())) {
                actions.add(ArkOperation.fromIEnumAction(NodeTableAction.VIEW));
            }
            if (currentUser.hasPermission(NodeTableAction.EDIT.getPermission())) {
                actions.add(ArkOperation.fromIEnumAction(NodeTableAction.EDIT));
            }
            if (currentUser.hasPermission(NodeTableAction.DELETE.getPermissionAction())) {
                actions.add(ArkOperation.fromIEnumAction(NodeTableAction.DELETE));
            }
            nodeTableDTO.setActions(actions);
        }
        return nodeTableDTO;
    }

    @Override
    public List<NodeTableDTO> mapperToDTO(List<NodeTableEntity> nodeTableEntityList, EmployeeIdentity currentUser) {
        List<NodeTableDTO> nodeTableDTOs = new ArrayList<>();
        for (NodeTableEntity nodeTableEntity : nodeTableEntityList) {
            NodeTableDTO nodeTableDTO = mapperToDTO(nodeTableEntity, currentUser);
            nodeTableDTOs.add(nodeTableDTO);
        }


        return nodeTableDTOs;
    }

    @Override
    public void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> list, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, List<String> needDeleteNodeList, EmployeeIdentity currentUser) {
        //节点表单，先删除再新增
        List<NodeTableFormDTO> nodeTableFormDTOList = defNodeBacthDTO.getNodeTableFormDTOList();
//        List<String> collect = nodeTableFormDTOList.stream().map(NodeTableFormDTO::getNodeCode).collect(Collectors.toList());
//        List<String> collect1 = collect.stream().distinct().collect(Collectors.toList());
//        collect1.addAll(needDeleteNodeList);//添加需要删除节点的code
//        //删除处理
//        if (!CollectionUtils.isEmpty(collect1)) {
//            nodeTableMapper.deleteByNodeCode(collect1);
//        }
        nodeTableMapper.deleteByWfId(defNodeBacthDTO.getWfId());
        List<NodeTableEntity> needAddEntityList = new ArrayList<>();
        for (NodeTableFormDTO nodeTableFormDTO : nodeTableFormDTOList) {
            NodeTableEntity nodeTableEntity = new NodeTableEntity();
            BeanUtils.copyProperties(nodeTableFormDTO, nodeTableEntity);
            nodeTableEntity.setCreateTime(LocalDateTime.now());
            nodeTableEntity.setCreatorId(currentUser.getId());
            nodeTableEntity.setCreatorName(currentUser.getName());
            nodeTableEntity.setIsDeleted(0);

            Optional<DefNodeEntity> first = list.stream().filter(o -> o.getNodeCode().equals(nodeTableEntity.getNodeCode())).findFirst();
            if (first.isPresent()) {
                nodeTableEntity.setNodeId(first.get().getId());
            }

            needAddEntityList.add(nodeTableEntity);
        }
//        //新增节点id赋值
//        for (NodeTableEntity nodeTableEntity : needAddEntityList) {
//            for (DefNodeEntity defNodeEntity : needDefNodeAddEntityList) {
//                if (nodeTableEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
//                    nodeTableEntity.setNodeId(defNodeEntity.getId());
//                }
//            }
//            for (DefNodeEntity defNodeEntity : needDefNodeUpdateEntityList) {
//                if (nodeTableEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
//                    nodeTableEntity.setNodeId(defNodeEntity.getId());
//                }
//            }
//        }
        //新增处理
        if (!CollectionUtils.isEmpty(needAddEntityList)) {
            this.saveBatch(needAddEntityList);
        }

    }
}
