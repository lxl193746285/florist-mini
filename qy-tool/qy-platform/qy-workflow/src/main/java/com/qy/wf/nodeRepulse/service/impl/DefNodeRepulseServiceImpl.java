package com.qy.wf.nodeRepulse.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkOperationService;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNode.mapper.DefNodeMapper;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseDTO;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseFormDTO;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseQueryDTO;
import com.qy.wf.nodeRepulse.entity.DefNodeRepulseEntity;
import com.qy.wf.nodeRepulse.mapper.DefNodeRepulseMapper;
import com.qy.wf.nodeRepulse.service.DefNodeRepulseService;
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
 * 工作流_设计_打回节点 服务实现类
 *
 * @author syf
 * @since 2023-08-16
 */
@Service
public class DefNodeRepulseServiceImpl extends ServiceImpl<DefNodeRepulseMapper, DefNodeRepulseEntity> implements DefNodeRepulseService {
    @Autowired
    private DefNodeRepulseMapper defNodeRepulseMapper;

    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Autowired
    private DefNodeMapper defNodeMapper;

    @Override
    public IPage<DefNodeRepulseEntity> getDefNodeRepulses(IPage iPage, DefNodeRepulseQueryDTO defNodeRepulseQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeRepulseEntity> defNodeRepulseQueryWrapper = RestUtils.getLambdaQueryWrapper();
        defNodeRepulseQueryWrapper.eq(defNodeRepulseQueryDTO.getStatus()!=null,DefNodeRepulseEntity::getStatus,defNodeRepulseQueryDTO.getStatus());
        defNodeRepulseQueryWrapper.orderByDesc(DefNodeRepulseEntity::getCreateTime);
        return super.page(iPage, defNodeRepulseQueryWrapper);
    }

    @Override
    public List<DefNodeRepulseEntity> getDefNodeRepulses(DefNodeRepulseQueryDTO defNodeRepulseQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<DefNodeRepulseEntity> defNodeRepulseQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(defNodeRepulseQueryWrapper);
    }

    @Override
    public DefNodeRepulseEntity getDefNodeRepulse(Long id, EmployeeIdentity currentUser) {
        DefNodeRepulseEntity defNodeRepulseEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defNodeRepulseEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_打回节点");
        }

        return defNodeRepulseEntity;
    }

    @Override
    public DefNodeRepulseEntity createDefNodeRepulse(DefNodeRepulseFormDTO defNodeRepulseFormDTO, EmployeeIdentity currentUser) {
        DefNodeRepulseEntity defNodeRepulseEntity = new DefNodeRepulseEntity();
        BeanUtils.copyProperties(defNodeRepulseFormDTO, defNodeRepulseEntity);
        defNodeRepulseEntity.setCreateTime(LocalDateTime.now());
        defNodeRepulseEntity.setCreatorId(currentUser.getId());
        defNodeRepulseEntity.setIsDeleted(0);
        this.save(defNodeRepulseEntity);
        return defNodeRepulseEntity;
    }

    @Override
    public DefNodeRepulseEntity updateDefNodeRepulse(Long id, DefNodeRepulseFormDTO defNodeRepulseFormDTO, EmployeeIdentity currentUser) {
        DefNodeRepulseEntity defNodeRepulseEntity = getById(id);
        BeanUtils.copyProperties(defNodeRepulseFormDTO, defNodeRepulseEntity);
        defNodeRepulseEntity.setUpdateTime(LocalDateTime.now());
        defNodeRepulseEntity.setUpdatorId(currentUser.getId());

        this.updateById(defNodeRepulseEntity);
        return defNodeRepulseEntity;
    }

    @Override
    public DefNodeRepulseEntity deleteDefNodeRepulse(Long id, EmployeeIdentity currentUser) {
        DefNodeRepulseEntity defNodeRepulseEntity = getDefNodeRepulse(id, currentUser);
        defNodeRepulseEntity.setDeleteTime(LocalDateTime.now());
        defNodeRepulseEntity.setDeletorId(currentUser.getId());

        defNodeRepulseEntity.setIsDeleted(1);
        this.updateById(defNodeRepulseEntity);
        return defNodeRepulseEntity;
    }

    @Override
    public DefNodeRepulseDTO mapperToDTO(DefNodeRepulseEntity defNodeRepulseEntity, EmployeeIdentity currentUser) {
        DefNodeRepulseDTO defNodeRepulseDTO = new DefNodeRepulseDTO();
        BeanUtils.copyProperties(defNodeRepulseEntity, defNodeRepulseDTO);
        return defNodeRepulseDTO;
    }

    @Override
    public List<DefNodeRepulseDTO> mapperToDTO(List<DefNodeRepulseEntity> defNodeRepulseEntityList, EmployeeIdentity currentUser) {
        List<DefNodeRepulseDTO> defNodeRepulseDTOs = new ArrayList<>();
        for (DefNodeRepulseEntity defNodeRepulseEntity : defNodeRepulseEntityList) {
            DefNodeRepulseDTO defNodeRepulseDTO = mapperToDTO(defNodeRepulseEntity, currentUser);
            defNodeRepulseDTOs.add(defNodeRepulseDTO);
        }


        return defNodeRepulseDTOs;
    }

    @Override
    public void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeRepulseFormDTO> defNodeRepulseFormDTOS, EmployeeIdentity currentUser) {
        //先删除在新增
        defNodeRepulseMapper.deleteByWfId(defNodeBacthDTO.getWfId());
        //新增操作
        List<DefNodeRepulseEntity> nodeRepulseEntityList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(defNodeRepulseFormDTOS)) {
            List<String> codeList = defNodeRepulseFormDTOS.stream().map(DefNodeRepulseFormDTO::getCurNodeCode).collect(Collectors.toList());
            List<DefNodeEntity> nodeEntityList = defNodeMapper.getIdByNodeCode(codeList);

            for (DefNodeRepulseFormDTO defNodeRepulseFormDTO : defNodeRepulseFormDTOS) {
                DefNodeRepulseEntity defNodeRepulseEntity = new DefNodeRepulseEntity();
                BeanUtils.copyProperties(defNodeRepulseFormDTO, defNodeRepulseEntity);
                List<DefNodeEntity> collect = nodeEntityList.stream().filter(o -> o.getNodeCode().equals(defNodeRepulseFormDTO.getCurNodeCode())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    defNodeRepulseEntity.setCurNodeId(collect.get(0).getId());
                }
                defNodeRepulseEntity.setCreatorId(currentUser.getId());
                defNodeRepulseEntity.setCreateTime(LocalDateTime.now());
                defNodeRepulseEntity.setCompanyId(currentUser.getOrganizationId());
                defNodeRepulseEntity.setWfId(defNodeBacthDTO.getWfId());
                nodeRepulseEntityList.add(defNodeRepulseEntity);
            }
        }
        this.saveBatch(nodeRepulseEntityList);
    }
}
