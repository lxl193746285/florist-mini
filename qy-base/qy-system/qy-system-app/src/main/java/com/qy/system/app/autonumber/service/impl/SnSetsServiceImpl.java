package com.qy.system.app.autonumber.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.rest.exception.ValidationException;
import com.qy.security.permission.action.Action;
import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.autonumber.dto.*;
import com.qy.system.app.autonumber.entity.SnSetsEntity;
import com.qy.system.app.autonumber.enums.SnsetsAction;
import com.qy.system.app.autonumber.mapper.SnSetsDetailMapper;
import com.qy.system.app.autonumber.mapper.SnSetsMapper;
import com.qy.system.app.autonumber.service.SnSetsDetailService;
import com.qy.system.app.autonumber.service.SnSetsService;
import com.qy.system.app.comment.enums.CommonEnumCode;
import com.qy.system.app.util.RestUtils;
import com.qy.system.app.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.system.app.autonumber.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 编号规则设置 服务实现类
 *
 * @author ln
 * @since 2022-04-29
 */
@Service
public class SnSetsServiceImpl extends ServiceImpl<SnSetsMapper, SnSetsEntity> implements SnSetsService {
    @Autowired
    private SnSetsDetailService snSetsDetailService;
    @Autowired
    private SnSetsDetailMapper snSetsDetailMapper;
    @Autowired
    private CodeTableClient codeTableClient;

    @Override
    public IPage<SnSetsEntity> getSnSets(IPage iPage, SnSetsQueryDTO snSetsQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<SnSetsEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        snSetsQueryDTO.setCompanyId(currentUser.getOrganizationId());
        queryWrapper
                .eq(null != snSetsQueryDTO.getCompanyId(), SnSetsEntity::getCompanyId, snSetsQueryDTO.getCompanyId())
                .eq(null != snSetsQueryDTO.getStatus(), SnSetsEntity::getStatus, snSetsQueryDTO.getStatus())
                .like(null != snSetsQueryDTO.getNoid() && !"".equals(snSetsQueryDTO.getNoid()), SnSetsEntity::getNoid, snSetsQueryDTO.getNoid())
                .like(null != snSetsQueryDTO.getNoname() && !"".equals(snSetsQueryDTO.getNoname()), SnSetsEntity::getNoname, snSetsQueryDTO.getNoname())
                .orderByDesc(SnSetsEntity::getCreateTime);
        IPage<SnSetsEntity> page = super.page(iPage, queryWrapper);
        return page;
    }

    @Override
    public List<SnSetsDTO> mapperToDTO(List<SnSetsEntity> snSetsEntityList, EmployeeIdentity currentUser) {
        List<SnSetsDTO> snSetsDTOS = new ArrayList<>();
        for (SnSetsEntity snSetsEntity : snSetsEntityList) {
            SnSetsDTO snSetsDTO = mapperToDTO(snSetsEntity, currentUser);
            snSetsDTOS.add(snSetsDTO);
            //按钮权限
            snSetsDTO.setActions(getPermission(snSetsDTO, currentUser));
        }
        return snSetsDTOS;
    }

    @Override
    public SnSetsDTO mapperToDTO(SnSetsEntity snsetsEntity, EmployeeIdentity currentUser) {
        SnSetsDTO snSetsDTO = new SnSetsDTO();
        BeanUtils.copyProperties(snsetsEntity, snSetsDTO);
        snSetsDTO.setIsPlatformName(snSetsDTO.getIsPlatform());
        snSetsDTO.setCreateTimeName(snsetsEntity.getCreateTime());
        if (snsetsEntity.getUpdateTime() != null) {
            snSetsDTO.setUpdateTimeName(snsetsEntity.getUpdateTime());
        }
        snSetsDTO.setStatusName(codeTableClient.getSystemCodeTableItemName("common_status", snSetsDTO.getStatus()+""));
        final Map<Integer, String> snCircle = getSystemListByCode
                (CommonEnumCode.ARK_MALL_SNSET_CIRCLE.getCode());
        snSetsDTO.setCycleName(snCircle.get(snSetsDTO.getCycle()));
        SnSetsDetailQueryDTO snSetsDetailQueryDTO = new SnSetsDetailQueryDTO();
        snSetsDetailQueryDTO.setNoid(snSetsDTO.getNoid());
        snSetsDetailQueryDTO.setCompanyId(currentUser.getOrganizationId());
        List<SnSetsDetailDTO> snSetsDetailDTOList = snSetsDetailService.getSnSetsDetails(snSetsDetailQueryDTO);
        if (CollectionUtils.isEmpty(snSetsDetailDTOList)) {
            snSetsDTO.setNextseq(null);
            snSetsDTO.setNowyear(null);
            snSetsDTO.setNowmonth(null);
            snSetsDTO.setNowday(null);
            snSetsDTO.setNowvalue(null);
        }
        snSetsDTO.setItems(snSetsDetailDTOList);

        return snSetsDTO;
    }

    @Override
    public SnSetsEntity getSnSet(String noid, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<SnSetsEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        queryWrapper
                .eq(SnSetsEntity::getCompanyId, currentUser.getOrganizationId())
                .eq(SnSetsEntity::getNoid, noid);
        List<SnSetsEntity> list = super.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new ValidationException("未找到编号设置");
        }
        return list.get(0);
    }

    @Override
    @Transactional
    public SnSetsEntity createSnSet(SnSetsFormDTO snSetsFormDTO, EmployeeIdentity currentUser) {
        savevalidate(snSetsFormDTO, currentUser, true);
        SnSetsEntity snsetsEntity = new SnSetsEntity();
        BeanUtils.copyProperties(snSetsFormDTO, snsetsEntity);
        snsetsEntity.setCreatorId(currentUser.getId());
        snsetsEntity.setCreatorName(currentUser.getName());
        snsetsEntity.setCreateTime(LocalDateTime.now());
        snsetsEntity.setCompanyId(currentUser.getOrganizationId());
        snsetsEntity.setIsDeleted(0);
        this.save(snsetsEntity);
        if (!CollectionUtils.isEmpty(snSetsFormDTO.getItems())) {
            for (SnSetsDetailFormDTO snSetsDetailFormDTO : snSetsFormDTO.getItems()) {
                snSetsDetailFormDTO.setNoid(snsetsEntity.getNoid());
                snSetsDetailService.createSnSetsDetail(snSetsDetailFormDTO, currentUser);
            }
        }
        return snsetsEntity;
    }

    public void savevalidate(SnSetsFormDTO snSetsFormDTO, EmployeeIdentity currentUser,Boolean isNew) {
        //编码新增重复校验,编码不让修改
        if (isNew) {
            String code = snSetsFormDTO.getNoid();
            if (StringUtils.isNullOfEmpty(code)) {
                throw new ValidationException("编号不能为空，请检查！");
            }
            SnSetsQueryDTO snSetsQueryDTOCode = new SnSetsQueryDTO();
            snSetsQueryDTOCode.setCompanyId(currentUser.getOrganizationId());
            snSetsQueryDTOCode.setNoid(code);
            List<SnSetsEntity> listCode = getSnSets(snSetsQueryDTOCode);
            if (!CollectionUtils.isEmpty(listCode)) {
                throw new ValidationException("编号设置编码已存在，请检查！");
            }
        }
        int yearcnt = 0, monthcnt = 0, daycnt = 0, seqcnt = 0, randomcnt = 0, fixcnt = 0;
        if (!CollectionUtils.isEmpty(snSetsFormDTO.getItems())) {
            for (SnSetsDetailFormDTO snSetsDetailFormDTO : snSetsFormDTO.getItems()) {
                if (snSetsDetailFormDTO.getUseyear() == 1) {
                    yearcnt++;
                }
                if (snSetsDetailFormDTO.getUsemonth() == 1) {
                    monthcnt++;
                }
                if (snSetsDetailFormDTO.getUseday() == 1) {
                    daycnt++;
                }
                if (snSetsDetailFormDTO.getUseseq() == 1) {
                    seqcnt++;
                     if (snSetsDetailFormDTO.getDigit() == 0) {
                        throw new ValidationException("使用序号,序号位数必填，请检查！");
                    }
                }
                if (snSetsDetailFormDTO.getUserandom() == 1) {
                    randomcnt++;
                    if (snSetsDetailFormDTO.getRandomdigit() == 0) {
                        throw new ValidationException("使用随机数，请设置随机数长度！");
                    }
                }
                if (snSetsDetailFormDTO.getUsefix() == 1) {
                    fixcnt++;
                    if ("".equals(snSetsDetailFormDTO.getFixval())) {
                        throw new ValidationException("使用固定字符，请设置固定字符！");
                    }
                }
            }
            if (yearcnt > 1) {
                throw new ValidationException("使用年只能选择一次，请检查！");
            }
            if (monthcnt > 1) {
                throw new ValidationException("使用月只能选择一次，请检查！");
            }
            if (daycnt > 1) {
                throw new ValidationException("使用日只能选择一次，请检查！");
            }
            if (seqcnt > 1) {
                throw new ValidationException("使用序号只能选择一次，请检查！");
            }
            if (randomcnt > 1) {
                throw new ValidationException("使用随机数只能选择一次，请检查！");
            }
            if (fixcnt > 1) {
                throw new ValidationException("使用固定字符只能选择一次，请检查！");
            }
        }
    }

    @Override
    public List<SnSetsEntity> getSnSets(SnSetsQueryDTO snSetsQueryDTO) {
        LambdaQueryWrapper<SnSetsEntity> snSetsQueryWrapper = new LambdaQueryWrapper();
        snSetsQueryWrapper
                .eq(null != snSetsQueryDTO.getNoname() && !"".equals(snSetsQueryDTO.getNoname()), SnSetsEntity::getNoname, snSetsQueryDTO.getNoname())
                .eq(null != snSetsQueryDTO.getNoid() && !"".equals(snSetsQueryDTO.getNoid()), SnSetsEntity::getNoid, snSetsQueryDTO.getNoid())
                .eq(null != snSetsQueryDTO.getCompanyId(), SnSetsEntity::getCompanyId, snSetsQueryDTO.getCompanyId())
                .eq(SnSetsEntity::getIsDeleted, 0);
        return super.list(snSetsQueryWrapper);
    }

    @Override
    @Transactional
    public SnSetsEntity updateSnSet(SnSetsFormDTO snSetsFormDTO, EmployeeIdentity currentUser) {
        savevalidate(snSetsFormDTO, currentUser, false);
        SnSetsEntity snSetsEntity = getSnSet(snSetsFormDTO.getNoid(), currentUser);
        BeanUtils.copyProperties(snSetsFormDTO, snSetsEntity);
        snSetsEntity.setUpdatorId(currentUser.getId());
        snSetsEntity.setUpdatorName(currentUser.getName());
        snSetsEntity.setUpdateTime(LocalDateTime.now());

        //先清空关联的子表的数据，然后在插入
        snSetsDetailService.deleteSnSetsDetailByNoId(snSetsEntity.getNoid(), currentUser);
        //往子表里添加数据
        if (!CollectionUtils.isEmpty(snSetsFormDTO.getItems())) {
            for (SnSetsDetailFormDTO detail : snSetsFormDTO.getItems()) {
                detail.setNoid(snSetsEntity.getNoid());
                snSetsDetailService.createSnSetsDetail(detail, currentUser);
            }
        }
        LambdaQueryWrapper<SnSetsEntity> lambdaQueryWrapper = RestUtils.getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(SnSetsEntity::getCompanyId, currentUser.getOrganizationId());
        lambdaQueryWrapper.eq(SnSetsEntity::getNoid, snSetsFormDTO.getNoid());
        //更新主表
        this.update(snSetsEntity,lambdaQueryWrapper);

        return snSetsEntity;
    }

    private List<Action> getPermission(SnSetsDTO snSetsDTO, EmployeeIdentity user) {
        List<Action> actions = new ArrayList<>();
        int status = snSetsDTO.getStatus();

        actions.add(SnsetsAction.VIEW.getPermissionAction().toAction());
        Boolean hasStatusOnOnPermission = user.hasPermission(SnsetsAction.ENABLE.getPermission());
        Boolean hasStatusOffPermission = user.hasPermission(SnsetsAction.DISABLE.getPermission());
        Boolean hasEditPermission = user.hasPermission(SnsetsAction.UPDATE.getPermission());
        Boolean hasDeletePermission = user.hasPermission(SnsetsAction.DELETE.getPermission());

        if (status == 1 && hasStatusOffPermission) {
            /* 启用状态 */
            if (hasStatusOffPermission) {
                actions.add(SnsetsAction.DISABLE.getPermissionAction().toAction());
            }
        } else if (status == 0) {
            /* 停用状态 */
            if (hasStatusOnOnPermission) {
                actions.add(SnsetsAction.ENABLE.getPermissionAction().toAction());
            }
            if (hasEditPermission) {
                actions.add(SnsetsAction.UPDATE.getPermissionAction().toAction());
            }
            if (hasDeletePermission) {
                actions.add(SnsetsAction.DELETE.getPermissionAction().toAction());
            }
        }

        return actions;
    }

    @Override
    public SnSetsEntity updateSnSetsStatus(String noid, SnSetsFormDTO snSetsFormDTO, EmployeeIdentity currentUser) {
        SnSetsEntity snSetsEntity = getSnSet(noid, currentUser);
        snSetsEntity.setStatus(snSetsFormDTO.getStatus());
        LambdaQueryWrapper<SnSetsEntity> lambdaQueryWrapper = RestUtils.getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(SnSetsEntity::getCompanyId, currentUser.getOrganizationId());
        lambdaQueryWrapper.eq(SnSetsEntity::getNoid, noid);
        //更新主表
        this.update(snSetsEntity,lambdaQueryWrapper);
        return snSetsEntity;
    }

    @Override
    public SnSetsEntity deleteSnSet(String noid, EmployeeIdentity currentUser) {
        SnSetsEntity snSetsEntity = getSnSet(noid, currentUser);
        snSetsEntity.setDeletorId(currentUser.getId());
        snSetsEntity.setDeletorName(currentUser.getName());
        snSetsEntity.setDeleteTime(LocalDateTime.now());
        snSetsEntity.setIsDeleted(1);

        LambdaQueryWrapper<SnSetsEntity> lambdaQueryWrapper = RestUtils.getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(SnSetsEntity::getCompanyId, currentUser.getOrganizationId());
        lambdaQueryWrapper.eq(SnSetsEntity::getNoid, noid);
        //更新主表
        this.update(snSetsEntity, lambdaQueryWrapper);

        // 删除子表
        snSetsDetailService.deleteSnSetsDetailByNoId(noid, currentUser);

//        SnSetsDetailQueryDTO snSetsDetailQueryDTO = new SnSetsDetailQueryDTO();
//        snSetsDetailQueryDTO.setNoid(snSetsEntity.getNoid());
//        snSetsDetailQueryDTO.setCompanyId(currentUser.getOrganizationId());
//        List<SnSetsDetailDTO> snSetsDetailDTOList = snSetsDetailService.getSnSetsDetails(snSetsDetailQueryDTO);
//        for (SnSetsDetailDTO snSetsDetailDTO: snSetsDetailDTOList) {
//        }
        return snSetsEntity;
    }

    @Override
    public String getAutoNumber(String noId, Long companyId, Long userId) {
        String autoNumber = snSetsDetailMapper.getAutoNumber(noId,companyId,userId);
        return autoNumber;
    }

    public LinkedHashMap<Integer, String> getSystemListByCode(String code) {
        final List<CodeTableItemBasicDTO> allCodeTableItems = codeTableClient.getSystemBasicCodeTableItems(code);
        final LinkedHashMap<Integer, String> valueToName = new LinkedHashMap<>();
        for (CodeTableItemBasicDTO dto : allCodeTableItems) {
            valueToName.put(Integer.parseInt(dto.getId()), dto.getName());
        }

        return valueToName;
    }


}
