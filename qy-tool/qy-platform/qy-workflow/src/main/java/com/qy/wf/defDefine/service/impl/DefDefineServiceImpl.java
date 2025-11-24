package com.qy.wf.defDefine.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.enums.ArkOperation;
import com.qy.common.service.ArkCodeTableService;
import com.qy.common.service.ArkOperationService;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.security.permission.action.Action;
import com.qy.security.session.MemberIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.defDefine.dto.DefDefineDTO;
import com.qy.wf.defDefine.dto.DefDefineFormDTO;
import com.qy.wf.defDefine.dto.DefDefineQueryDTO;
import com.qy.wf.defDefine.dto.DefStartDTO;
import com.qy.wf.defDefine.entity.DefDefineEntity;
import com.qy.wf.defDefine.enums.DefDefineAction;
import com.qy.wf.defDefine.mapper.DefDefineMapper;
import com.qy.wf.defDefine.service.DefDefineService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 工作流_设计_定义
 * @author wch
 * @since 2022-11-12
 */
@Service
public class DefDefineServiceImpl extends ServiceImpl<DefDefineMapper, DefDefineEntity> implements DefDefineService {
    @Autowired
    private DefDefineMapper defDefineMapper;
    @Autowired
    private ArkOperationService operationService;
    @Autowired
    private CodeTableClient codeTableClient;
    @Autowired
    private ArkCodeTableService arkCodeTableService;
    @Autowired
    private OrganizationClient organizationClient;


    @Override
    public IPage<DefDefineEntity> getDefDefines(IPage iPage, DefDefineQueryDTO defDefineQueryDTO, MemberIdentity currentUser) {
            LambdaQueryWrapper<DefDefineEntity> defDefineQueryWrapper = RestUtils.getLambdaQueryWrapper();
            LocalDate startDate;
            LocalDate endDate;
            if (!com.google.common.base.Strings.isNullOrEmpty(defDefineQueryDTO.getStartCreateDate())) {
                startDate = LocalDate.parse(defDefineQueryDTO.getStartCreateDate());
                defDefineQueryWrapper.ge(null != startDate, DefDefineEntity::getCreateTime, startDate);
            }
            if (!com.google.common.base.Strings.isNullOrEmpty(defDefineQueryDTO.getEndCreateDate())) {
                endDate = LocalDate.parse(defDefineQueryDTO.getEndCreateDate());
                if (null != endDate) {
                    defDefineQueryWrapper.le(DefDefineEntity::getCreateTime, endDate.atTime(23, 59, 59));
                }
            }
            defDefineQueryWrapper.eq(defDefineQueryDTO.getWfType()!=null,DefDefineEntity::getWfType,defDefineQueryDTO.getWfType());
            defDefineQueryWrapper.eq(defDefineQueryDTO.getGroupId()!=null,DefDefineEntity::getGroupId,defDefineQueryDTO.getGroupId());
            defDefineQueryWrapper.like(defDefineQueryDTO.getName()!=null,DefDefineEntity::getName,defDefineQueryDTO.getName());
            defDefineQueryWrapper.eq(defDefineQueryDTO.getCompanyId()!=null,DefDefineEntity::getCompanyId,defDefineQueryDTO.getCompanyId());
            defDefineQueryWrapper.eq(defDefineQueryDTO.getStatus()!=null,DefDefineEntity::getStatus,defDefineQueryDTO.getStatus());
            defDefineQueryWrapper.eq(defDefineQueryDTO.getCanStart()!=null,DefDefineEntity::getCanStart,defDefineQueryDTO.getCanStart());
            defDefineQueryWrapper.orderByAsc(DefDefineEntity::getSort);
            defDefineQueryWrapper.orderByDesc(DefDefineEntity::getCreateTime);
            return super.page(iPage, defDefineQueryWrapper);
    }

    @Override
    public List<DefDefineEntity> getDefDefines(DefDefineQueryDTO defDefineQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<DefDefineEntity> defDefineQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(defDefineQueryWrapper);
    }

    @Override
    public DefDefineEntity getDefDefine(Long id, MemberIdentity currentUser) {
        DefDefineEntity defDefineEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (defDefineEntity == null) {
            throw new RuntimeException("未找到工作流");
        }

        return defDefineEntity;
    }

    @Override
    public DefDefineEntity createDefDefine(DefDefineFormDTO defDefineFormDTO, MemberIdentity currentUser) {
        DefDefineEntity defDefineEntity = new DefDefineEntity();
        BeanUtils.copyProperties(defDefineFormDTO, defDefineEntity);
        defDefineEntity.setCompanyId(currentUser.getOrganizationId());
        defDefineEntity.setCreateTime(LocalDateTime.now());
        defDefineEntity.setCreatorId(currentUser.getId());
        defDefineEntity.setCreatorName(currentUser.getName());
        defDefineEntity.setUpdatorId(Long.valueOf(0));
        defDefineEntity.setIsDeleted((int) 0);
        defDefineEntity.setDeletorId(Long.valueOf(0));

        this.save(defDefineEntity);
        return defDefineEntity;
    }

    @Override
    public DefDefineEntity updateDefDefine(Long id, DefDefineFormDTO defDefineFormDTO, MemberIdentity currentUser) {
        DefDefineEntity defDefineEntity = getById(id);
        BeanUtils.copyProperties(defDefineFormDTO, defDefineEntity);
        defDefineEntity.setUpdateTime(LocalDateTime.now());
        defDefineEntity.setUpdatorId(currentUser.getId());
        defDefineEntity.setUpdatorName(currentUser.getName());

        this.updateById(defDefineEntity);
        return defDefineEntity;
    }

    @Override
    public DefDefineEntity deleteDefDefine(Long id, MemberIdentity currentUser) {
        DefDefineEntity defDefineEntity = getDefDefine(id, currentUser);
        defDefineEntity.setDeleteTime(LocalDateTime.now());
        defDefineEntity.setDeletorId(currentUser.getId());
        defDefineEntity.setDeletorName(currentUser.getName());
        defDefineEntity.setIsDeleted((int) 1);
        this.updateById(defDefineEntity);
        return defDefineEntity;
    }

    @Override
    public DefDefineDTO mapperToDTO(DefDefineEntity defDefineEntity, MemberIdentity currentUser) {
        DefDefineDTO defDefineDTO = new DefDefineDTO();
        BeanUtils.copyProperties(defDefineEntity, defDefineDTO);
        defDefineDTO.setCompanyName(organizationClient.getOrganizationById(defDefineDTO.getCompanyId()).getName());
        defDefineDTO.setWfTitleConfigName(arkCodeTableService.getNameBySystem("wf_def_define_title_config", defDefineDTO.getWfTitleConfig()));
        defDefineDTO.setWfNoteConfigName(arkCodeTableService.getNameBySystem("wf_def_define_note_config", defDefineDTO.getWfNoteConfig()));
        defDefineDTO.setStatusName(arkCodeTableService.getNameBySystem("common_status", defDefineDTO.getStatus()));
        defDefineDTO.setGroupName(arkCodeTableService.getNameBySystem("user_wf_group_id", defDefineDTO.getGroupId()));
        defDefineDTO.setWfTypeName(arkCodeTableService.getNameBySystem("wf_def_define_wf_type", defDefineDTO.getWfType()));
        defDefineDTO.setCanStartName(arkCodeTableService.getNameBySystem("common_status", defDefineDTO.getCanStart()));

        return defDefineDTO;
    }

    @Override
    public List<DefDefineDTO> mapperToDTO(List<DefDefineEntity> defDefineEntityList, MemberIdentity currentUser) {
        List<DefDefineDTO> defDefineDTOs = new ArrayList<>();
        List<Action> actions = currentUser.getActions("wf_def_define");
        for (DefDefineEntity defDefineEntity : defDefineEntityList) {
            DefDefineDTO defDefineDTO = mapperToDTO(defDefineEntity, currentUser);
            defDefineDTO.setActions(actions);
            defDefineDTOs.add(defDefineDTO);
        }
        return defDefineDTOs;
    }

    @Override
    public String getWfStr(Long id, MemberIdentity currentUser) {
        DefDefineEntity defDefineEntity = getById(id);
        return defDefineEntity.getWfStr();
    }

    @Override
    public List<DefStartDTO> getCanStartWFDef() {
        return defDefineMapper.getCanStartWFDef();
    }

    @Override
    public void callbackUpdate(Long wfId, DefDefineFormDTO defDefineFormDTO, String wfStr) {
        DefDefineEntity defDefineEntity = getDefDefine(wfId, null);
        if (defDefineEntity != null) {
            if (defDefineFormDTO != null) {
                BeanUtils.copyProperties(defDefineFormDTO, defDefineEntity);
            }
            if (wfStr == null) {
                defDefineEntity.setWfStr("");
            } else {
                defDefineEntity.setWfStr(wfStr);
            }
            this.updateById(defDefineEntity);
        }
    }
}
