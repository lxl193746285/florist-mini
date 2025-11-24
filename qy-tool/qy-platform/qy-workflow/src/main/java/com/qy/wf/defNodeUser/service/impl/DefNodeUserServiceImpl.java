package com.qy.wf.defNodeUser.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNodeUser.dto.DefNodeUserDTO;
import com.qy.wf.defNodeUser.dto.DefNodeUserFormDTO;
import com.qy.wf.defNodeUser.dto.DefNodeUserQueryDTO;
import com.qy.wf.defNodeUser.entity.DefNodeUserEntity;
import com.qy.wf.defNodeUser.mapper.DefNodeUserMapper;
import com.qy.wf.defNodeUser.service.DefNodeUserService;
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
 * 工作流_设计_节点人员 服务实现类
 *
 * @author syf
 * @since 2022-11-14
 */
@Service
public class DefNodeUserServiceImpl extends ServiceImpl<DefNodeUserMapper, DefNodeUserEntity> implements DefNodeUserService {
    @Autowired
    private DefNodeUserMapper defNodeUserMapper;
    @Autowired
    private ArkOperationService operationService;
    @Autowired
    private CodeTableClient codeTableClient;

    @Override
    public IPage<DefNodeUserEntity> getDefNodeUsers(IPage iPage, DefNodeUserQueryDTO defNodeUserQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeUserEntity> defNodeUserQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeUserQueryWrapper.eq(defNodeUserQueryDTO.getWfId()!=null, DefNodeUserEntity::getWfId, defNodeUserQueryDTO.getWfId());
        defNodeUserQueryWrapper.eq(defNodeUserQueryDTO.getNodeId()!=null, DefNodeUserEntity::getNodeId, defNodeUserQueryDTO.getNodeId());
        defNodeUserQueryWrapper.eq(defNodeUserQueryDTO.getType()!=null,DefNodeUserEntity::getType,defNodeUserQueryDTO.getType());
        defNodeUserQueryWrapper.orderByDesc(DefNodeUserEntity::getCreateTime);
        return super.page(iPage, defNodeUserQueryWrapper);
    }

    @Override
    public List<DefNodeUserEntity> getDefNodeUsers(DefNodeUserQueryDTO defNodeUserQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<DefNodeUserEntity> defNodeUserQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeUserQueryWrapper.eq(defNodeUserQueryDTO.getWfId()!=null, DefNodeUserEntity::getWfId, defNodeUserQueryDTO.getWfId());
        defNodeUserQueryWrapper.eq(defNodeUserQueryDTO.getNodeId()!=null, DefNodeUserEntity::getNodeId, defNodeUserQueryDTO.getNodeId());
        defNodeUserQueryWrapper.eq(defNodeUserQueryDTO.getType()!=null,DefNodeUserEntity::getType,defNodeUserQueryDTO.getType());
        return super.list(defNodeUserQueryWrapper);
    }

    @Override
    public DefNodeUserEntity getDefNodeUser(Long id, EmployeeIdentity currentUser) {
        DefNodeUserEntity defNodeUserEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defNodeUserEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_节点人员");
        }

        return defNodeUserEntity;
    }

    @Override
    public DefNodeUserEntity createDefNodeUser(DefNodeUserFormDTO defNodeUserFormDTO, EmployeeIdentity currentUser) {
        DefNodeUserEntity defNodeUserEntity = new DefNodeUserEntity();
        BeanUtils.copyProperties(defNodeUserFormDTO, defNodeUserEntity);
        defNodeUserEntity.setCreateTime(LocalDateTime.now());
        defNodeUserEntity.setCreatorId(currentUser.getId());
        defNodeUserEntity.setCreatorName(currentUser.getName());
        defNodeUserEntity.setIsDeleted(0);
        this.save(defNodeUserEntity);
        return defNodeUserEntity;
    }

    @Override
    public DefNodeUserEntity updateDefNodeUser(Long id, DefNodeUserFormDTO defNodeUserFormDTO, EmployeeIdentity currentUser) {
        DefNodeUserEntity defNodeUserEntity = getById(id);
        BeanUtils.copyProperties(defNodeUserFormDTO, defNodeUserEntity);
        defNodeUserEntity.setUpdateTime(LocalDateTime.now());
        defNodeUserEntity.setUpdatorId(currentUser.getId());
        defNodeUserEntity.setUpdatorName(currentUser.getName());

        this.updateById(defNodeUserEntity);
        return defNodeUserEntity;
    }

    @Override
    public DefNodeUserEntity deleteDefNodeUser(Long id, EmployeeIdentity currentUser) {
        DefNodeUserEntity defNodeUserEntity = getDefNodeUser(id, currentUser);
        defNodeUserEntity.setDeleteTime(LocalDateTime.now());
        defNodeUserEntity.setDeletorId(currentUser.getId());
        defNodeUserEntity.setDeletorName(currentUser.getName());

        defNodeUserEntity.setIsDeleted(1);
        this.updateById(defNodeUserEntity);
        return defNodeUserEntity;
    }

    @Override
    public DefNodeUserDTO mapperToDTO(DefNodeUserEntity defNodeUserEntity, EmployeeIdentity currentUser) {
        DefNodeUserDTO defNodeUserDTO = new DefNodeUserDTO();
        BeanUtils.copyProperties(defNodeUserEntity, defNodeUserDTO);
        return defNodeUserDTO;
    }

    @Override
    public List<DefNodeUserDTO> mapperToDTO(List<DefNodeUserEntity> defNodeUserEntityList, EmployeeIdentity currentUser) {
        List<DefNodeUserDTO> defNodeUserDTOs = new ArrayList<>();
        for (DefNodeUserEntity defNodeUserEntity : defNodeUserEntityList) {
            DefNodeUserDTO defNodeUserDTO = mapperToDTO(defNodeUserEntity, currentUser);
            defNodeUserDTOs.add(defNodeUserDTO);
        }


        return defNodeUserDTOs;
    }

    @Override
    public void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, EmployeeIdentity currentUser) {
        /**判断是否存在，存在则修改，不存在则新增；旧数据需要删除得，删除处理*/
        /*设计节点处理*/
        //根据工作流id查询已存在的数据
        LambdaQueryWrapper<DefNodeUserEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper.eq(DefNodeUserEntity::getWfId, defNodeBacthDTO.getWfId());
        List<DefNodeUserEntity> oldDefNodeUserEntityList = this.list(queryWrapper);
        //判断传入的值和已存在的值是否存在
        List<String> oldNodeUserCollect = oldDefNodeUserEntityList.stream().map(DefNodeUserEntity::getNodeCode).collect(Collectors.toList());
        List<String> newNodeUserCollect = defNodeBacthDTO.getDefNodeUserFormDTOList().stream().map(DefNodeUserFormDTO::getNodeCode).collect(Collectors.toList());

        //取交集（需修改）
        List<String> intersection = oldNodeUserCollect.stream().filter(item -> newNodeUserCollect.contains(item)).collect(Collectors.toList());
        //取差集处理（需新增）
        List<String> strNewDifList = newNodeUserCollect.stream().filter(num -> !oldNodeUserCollect.contains(num)).collect(Collectors.toList());//取差集(需新增)
        //取差集处理（需删除）
        List<String> strOldDifList = oldNodeUserCollect.stream().filter(num -> !newNodeUserCollect.contains(num)).collect(Collectors.toList());//取差集(需删除)
        //新增处理
        List<DefNodeUserEntity> needAddEntityList = new ArrayList<>();
        for (String nodeCode : strNewDifList) {
            for (DefNodeUserFormDTO defNodeUserFormDTO : defNodeBacthDTO.getDefNodeUserFormDTOList()) {
                if (nodeCode.equals(defNodeUserFormDTO.getNodeCode())) {
                    DefNodeUserEntity defNodeUserEntity = new DefNodeUserEntity();
                    BeanUtils.copyProperties(defNodeUserFormDTO, defNodeUserEntity);
                    defNodeUserEntity.setCreateTime(LocalDateTime.now());
                    defNodeUserEntity.setCreatorId(currentUser.getId());
                    defNodeUserEntity.setCreatorName(currentUser.getName());
                    defNodeUserEntity.setIsDeleted(0);
                    needAddEntityList.add(defNodeUserEntity);
                }
            }
        }
        //修改处理
        List<DefNodeUserFormDTO> needUpdateFormList = new ArrayList<>();
        List<DefNodeUserEntity> needUpdateEntityList = new ArrayList<>();
        for (String nodeCode : intersection) {
            for (DefNodeUserFormDTO defNodeUserFormDTO : defNodeBacthDTO.getDefNodeUserFormDTOList()) {
                if (nodeCode.equals(defNodeUserFormDTO.getNodeCode())) {
                    needUpdateFormList.add(defNodeUserFormDTO);
                }
            }
            for (DefNodeUserEntity defNodeUserEntity : oldDefNodeUserEntityList) {
                if (nodeCode.equals(defNodeUserEntity.getNodeCode())) {
                    needUpdateEntityList.add(defNodeUserEntity);
                }
            }
        }
        //修改赋值操作
        for (DefNodeUserFormDTO defNodeUserFormDTO : needUpdateFormList) {
            for (DefNodeUserEntity defNodeUserEntity : needUpdateEntityList) {
                if (defNodeUserFormDTO.getNodeCode().equals(defNodeUserEntity.getNodeCode())) {
                    BeanUtils.copyProperties(defNodeUserFormDTO,defNodeUserEntity);
                    defNodeUserEntity.setUpdateTime(LocalDateTime.now());
                    defNodeUserEntity.setUpdatorId(currentUser.getId());
                    defNodeUserEntity.setUpdatorName(currentUser.getName());
                }
            }
        }

        //新增节点id赋值
        for (DefNodeUserEntity defNodeUserEntity : needAddEntityList) {
            for (DefNodeEntity defNodeEntity : needDefNodeAddEntityList) {
                if (defNodeUserEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeUserEntity.setNodeId(defNodeEntity.getId());
                }
            }
            for (DefNodeEntity defNodeEntity : needDefNodeUpdateEntityList) {
                if (defNodeUserEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeUserEntity.setNodeId(defNodeEntity.getId());
                }
            }
        }
        //修改节点id赋值
        for (DefNodeUserEntity defNodeUserEntity : needUpdateEntityList) {
            for (DefNodeEntity defNodeEntity : needDefNodeAddEntityList) {
                if (defNodeUserEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeUserEntity.setNodeId(defNodeEntity.getId());
                }
            }
            for (DefNodeEntity defNodeEntity : needDefNodeUpdateEntityList) {
                if (defNodeUserEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
                    defNodeUserEntity.setNodeId(defNodeEntity.getId());
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
            defNodeUserMapper.deleteByNodeCode(strOldDifList);
        }


    }

    @Override
    public void batchSaveV2(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> list, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, List<String> needDeleteNodeList, EmployeeIdentity currentUser) {
        //人员节点，先删除再新增
        List<DefNodeUserFormDTO> defNodeUserFormDTOList = defNodeBacthDTO.getDefNodeUserFormDTOList();
//        List<String> collect = defNodeUserFormDTOList.stream().map(DefNodeUserFormDTO::getNodeCode).collect(Collectors.toList());
//        List<String> collect1 = collect.stream().distinct().collect(Collectors.toList());
//        collect1.addAll(needDeleteNodeList);//添加需要删除节点的code
//        //删除处理
//        if (!CollectionUtils.isEmpty(collect1)) {
//            defNodeUserMapper.deleteByNodeCode(collect1);
//        }
        defNodeUserMapper.deleteByWfId(defNodeBacthDTO.getWfId());


        //新增处理
        List<DefNodeUserEntity> needAddEntityList = new ArrayList<>();
        for (DefNodeUserFormDTO defNodeUserFormDTO : defNodeUserFormDTOList) {
            DefNodeUserEntity defNodeUserEntity = new DefNodeUserEntity();
            BeanUtils.copyProperties(defNodeUserFormDTO, defNodeUserEntity);
            defNodeUserEntity.setCompanyId(currentUser.getOrganizationId());
            defNodeUserEntity.setCreateTime(LocalDateTime.now());
            defNodeUserEntity.setCreatorId(currentUser.getId());
            defNodeUserEntity.setCreatorName(currentUser.getName());
            defNodeUserEntity.setIsDeleted(0);

            Optional<DefNodeEntity> first = list.stream().filter(o -> o.getNodeCode().equals(defNodeUserEntity.getNodeCode())).findFirst();
            if (first.isPresent()) {
                defNodeUserEntity.setNodeId(first.get().getId());
            }
            needAddEntityList.add(defNodeUserEntity);
        }
//        //新增节点id赋值
//        for (DefNodeUserEntity defNodeUserEntity : needAddEntityList) {
//            for (DefNodeEntity defNodeEntity : needDefNodeAddEntityList) {
//                if (defNodeUserEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
//                    defNodeUserEntity.setNodeId(defNodeEntity.getId());
//                }
//            }
//            for (DefNodeEntity defNodeEntity : needDefNodeUpdateEntityList) {
//                if (defNodeUserEntity.getNodeCode().equals(defNodeEntity.getNodeCode())) {
//                    defNodeUserEntity.setNodeId(defNodeEntity.getId());
//                }
//            }
//        }
        //新增处理
        if (!CollectionUtils.isEmpty(needAddEntityList)) {
            this.saveBatch(needAddEntityList);
        }


    }
}
