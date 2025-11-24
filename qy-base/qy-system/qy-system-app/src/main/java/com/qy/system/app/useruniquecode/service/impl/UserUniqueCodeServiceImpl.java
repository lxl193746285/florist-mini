package com.qy.system.app.useruniquecode.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.organization.api.dto.OrganizationDTO;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.loginlog.entity.LoginLogEntity;
import com.qy.system.app.loginlog.service.LoginLogService;
import com.qy.system.app.useruniquecode.entity.UserUniqueCodeEntity;
import com.qy.system.app.useruniquecode.enums.IsIgnore;
import com.qy.system.app.useruniquecode.mapper.UserUniqueCodeMapper;
import com.qy.system.app.useruniquecode.service.UserUniqueCodeService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.system.app.useruniquecode.dto.*;
import com.qy.security.permission.action.Action;

import java.util.List;
import java.util.ArrayList;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.system.app.util.StringUtils;
import com.qy.verification.api.client.VerificationCodeClient;
import com.qy.verification.api.dto.ValidateCodeResultDTO;
import com.qy.verification.api.query.ValidateVerificationCodeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


/**
 * 用户设备唯一码 服务实现类
 *
 * @author wwd
 * @since 2024-04-19
 */
@Service
public class UserUniqueCodeServiceImpl extends ServiceImpl<UserUniqueCodeMapper, UserUniqueCodeEntity> implements UserUniqueCodeService {
    @Autowired
    private UserUniqueCodeMapper userUniqueCodeMapper;
    @Autowired
    private OrganizationClient organizationClient;
    @Autowired
    private OrgDatasourceClient orgDatasourceClient;
    @Autowired
    private VerificationCodeClient verificationCodeClient;
    @Autowired
    private LoginLogService loginLogService;

    @Override
    public IPage<UserUniqueCodeDTO> getUserUniqueCodes(IPage iPage, UserUniqueCodeQueryDTO userUniqueCodeQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<UserUniqueCodeEntity> userUniqueCodeQueryWrapper = new LambdaQueryWrapper<>();
        userUniqueCodeQueryWrapper.eq(userUniqueCodeQueryDTO.getIsIgnore() != null, UserUniqueCodeEntity::getIsIgnore, userUniqueCodeQueryDTO.getIsIgnore());
        userUniqueCodeQueryWrapper.orderByDesc(UserUniqueCodeEntity::getCreateTime)
                .eq(UserUniqueCodeEntity::getOrganizationId, currentUser.getOrganizationId());
//        IPage<UserUniqueCodeDTO> dtoIPage = userUniqueCodeMapper.getDTOList(iPage, userUniqueCodeQueryWrapper);
        IPage<UserUniqueCodeDTO> dtoIPage = userUniqueCodeMapper.getDTOListCondition(iPage, userUniqueCodeQueryDTO);
        List<Action> actions = currentUser.getActions("ark_user_unique_code");
        dtoIPage.getRecords().stream().map(dto -> {
            OrganizationDTO organizationDTO = organizationClient.getOrganizationById(dto.getOrganizationId());
            if (organizationDTO != null) {
                dto.setOrganizationName(organizationDTO.getName());
            }
            dto.setActions(actions);
            return dto;
        }).collect(Collectors.toList());
        return dtoIPage;
    }

    @Override
    public List<UserUniqueCodeEntity> getUserUniqueCodes(UserUniqueCodeQueryDTO userUniqueCodeQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<UserUniqueCodeEntity> userUniqueCodeQueryWrapper = new LambdaQueryWrapper<>();
        return super.list(userUniqueCodeQueryWrapper);
    }

    @Override
    public UserUniqueCodeEntity getUserUniqueCode(Long id, EmployeeIdentity currentUser) {
        UserUniqueCodeEntity userUniqueCodeEntity = this.getById(id);

        if (userUniqueCodeEntity == null) {
            throw new RuntimeException("未找到 用户设备唯一码");
        }

        return userUniqueCodeEntity;
    }


    @Override
    public UserUniqueCodeDTO getUserUniqueCodeDTO(Long id, EmployeeIdentity currentUser) {

        return userUniqueCodeMapper.getDTOById(id);
    }

    @Override
    public Boolean createUserUniqueCode(UserUniqueCodeFormDTO userUniqueCodeFormDTO) {
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(userUniqueCodeFormDTO.getOrganizationId());
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }

        LambdaQueryWrapper<UserUniqueCodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserUniqueCodeEntity::getUserId, userUniqueCodeFormDTO.getUserId())
                .eq(UserUniqueCodeEntity::getOrganizationId, userUniqueCodeFormDTO.getOrganizationId());
        UserUniqueCodeEntity uniqueCode = getOne(wrapper, false);
        if (uniqueCode != null && uniqueCode.getIsIgnore() == IsIgnore.ENABLE.getId()) {
            if (!StringUtils.isNullOfEmpty(userUniqueCodeFormDTO.getRegistrationId())
                    && uniqueCode.getUniqueCode().equals(userUniqueCodeFormDTO.getUniqueCode())) {
                uniqueCode.setRegistrationId(userUniqueCodeFormDTO.getRegistrationId());
                updateById(uniqueCode);
            }
            return true;
        }
        if (uniqueCode == null) {
            UserUniqueCodeEntity userUniqueCodeEntity = new UserUniqueCodeEntity();
            BeanUtils.copyProperties(userUniqueCodeFormDTO, userUniqueCodeEntity);
            userUniqueCodeEntity.setCreateTime(LocalDateTime.now());
            this.save(userUniqueCodeEntity);
            return true;
        }
        wrapper.eq(UserUniqueCodeEntity::getUniqueCode, userUniqueCodeFormDTO.getUniqueCode());
        if (count(wrapper) > 0) {
            UserUniqueCodeEntity userUniqueCodeEntity = getOne(wrapper, false);
            if (!StringUtils.isNullOfEmpty(userUniqueCodeFormDTO.getRegistrationId())) {
                userUniqueCodeEntity.setRegistrationId(userUniqueCodeFormDTO.getRegistrationId());
                updateById(userUniqueCodeEntity);
            }
            return true;
        }

        if (!StringUtils.isNullOfEmpty(userUniqueCodeFormDTO.getCode())) {
            ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery();
            verificationCodeQuery.setCode(userUniqueCodeFormDTO.getCode());
            verificationCodeQuery.setScene("MEMBER_LOGIN");
            verificationCodeQuery.setAddress(userUniqueCodeFormDTO.getPhone());
            verificationCodeQuery.setMessageType("SMS");
            ValidateCodeResultDTO dto = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
            if (!dto.isValid()) {
                throw new ValidationException(dto.getErrorMessage());
            } else {
                return true;
            }
        } else {
            if (!uniqueCode.getUniqueCode().equals(userUniqueCodeFormDTO.getUniqueCode())) {
                LoginLogEntity entity = loginLogService.getById(userUniqueCodeFormDTO.getLogId());
                if (entity != null) {
                    entity.setIsException(1);
                    loginLogService.updateById(entity);
                }
                throw new ValidationException("登录设备非绑定设备，需短信验证登录");
            }
        }
        DynamicDataSourceContextHolder.clear();
        return false;
    }

    @Override
    public UserUniqueCodeEntity updateUserUniqueCode(Long id, UserUniqueCodeFormDTO userUniqueCodeFormDTO, EmployeeIdentity currentUser) {
        UserUniqueCodeEntity userUniqueCodeEntity = getById(id);
        BeanUtils.copyProperties(userUniqueCodeFormDTO, userUniqueCodeEntity);
        userUniqueCodeEntity.setUpdateTime(LocalDateTime.now());
        userUniqueCodeEntity.setUpdatorId(currentUser.getId());
        userUniqueCodeEntity.setId(id);

        this.updateById(userUniqueCodeEntity);
        return userUniqueCodeEntity;
    }

    @Override
    public UserUniqueCodeEntity deleteUserUniqueCode(Long id, EmployeeIdentity currentUser) {
        UserUniqueCodeEntity userUniqueCodeEntity = getUserUniqueCode(id, currentUser);

        this.removeById(id);
        return userUniqueCodeEntity;
    }

    @Override
    public UserUniqueCodeDTO mapperToDTO(UserUniqueCodeEntity userUniqueCodeEntity, EmployeeIdentity currentUser) {
        UserUniqueCodeDTO userUniqueCodeDTO = new UserUniqueCodeDTO();
        if (userUniqueCodeEntity != null){
            BeanUtils.copyProperties(userUniqueCodeEntity, userUniqueCodeDTO);
        }
        return userUniqueCodeDTO;
    }

    @Override
    public List<UserUniqueCodeDTO> mapperToDTO(List<UserUniqueCodeEntity> userUniqueCodeEntityList, EmployeeIdentity currentUser) {
        List<UserUniqueCodeDTO> userUniqueCodeDTOs = new ArrayList<>();
        for (UserUniqueCodeEntity userUniqueCodeEntity : userUniqueCodeEntityList) {
            UserUniqueCodeDTO userUniqueCodeDTO = mapperToDTO(userUniqueCodeEntity, currentUser);
            userUniqueCodeDTOs.add(userUniqueCodeDTO);
        }
        return userUniqueCodeDTOs;
    }

    @Override
    public UserUniqueCodeBasicDTO getUserUniqueCodeDTO(Long memberId, Long orgId) {
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(orgId);
        if (orgDatasourceDTO != null) {
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }
        LambdaQueryWrapper<UserUniqueCodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserUniqueCodeEntity::getUserId, memberId);
        UserUniqueCodeEntity entity = getOne(wrapper, false);
        if (entity == null){
            return null;
        }
        UserUniqueCodeBasicDTO dto = new UserUniqueCodeBasicDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
