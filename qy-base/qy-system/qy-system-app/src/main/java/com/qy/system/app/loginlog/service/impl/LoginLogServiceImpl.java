package com.qy.system.app.loginlog.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.qy.organization.api.client.OrgDatasourceClient;
import com.qy.organization.api.dto.OrgDatasourceDTO;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.system.app.loginlog.entity.LoginLogEntity;
import com.qy.system.app.loginlog.mapper.LoginLogMapper;
import com.qy.system.app.loginlog.service.LoginLogService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.system.app.loginlog.dto.*;
import com.qy.security.permission.action.Action;
import java.util.List;
import java.util.ArrayList;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.system.app.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


/**
 * 系统登录日志 服务实现类
 *
 * @author wwd
 * @since 2024-04-18
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogEntity> implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private OrgDatasourceClient orgDatasourceClient;

    @Override
    public IPage<LoginLogDTO> getLoginLogs(IPage iPage, LoginLogQueryDTO loginLogQueryDTO, EmployeeIdentity currentUser) {
        if (!StringUtils.isNullOfEmpty(loginLogQueryDTO.getStartCreateDate())){
            loginLogQueryDTO.setStartCreateDate(loginLogQueryDTO.getStartCreateDate() + " 00:00:00");
            loginLogQueryDTO.setEndCreateDate(loginLogQueryDTO.getEndCreateDate() + " 23:59:59");
        }
        IPage<LoginLogDTO> dtoIPage = loginLogMapper.getDTOList(iPage, loginLogQueryDTO);
        List<Action> actions = currentUser.getActions("ark_sys_login_log");
        dtoIPage.getRecords().stream().map(dto -> {

            dto.setActions(actions);
            return dto;
        }).collect(Collectors.toList());
        return dtoIPage;
    }

    @Override
    public List<LoginLogEntity> getLoginLogs(LoginLogQueryDTO loginLogQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<LoginLogEntity> loginLogQueryWrapper = new LambdaQueryWrapper<>();
        return super.list(loginLogQueryWrapper);
    }

    @Override
    public LoginLogEntity getLoginLog(Long id, EmployeeIdentity currentUser) {
        LoginLogEntity loginLogEntity = this.getById(id);

        if (loginLogEntity == null) {
            throw new RuntimeException("未找到 系统登录日志");
        }

        return loginLogEntity;
    }



    @Override
    public LoginLogDTO getLoginLogDTO(Long id, EmployeeIdentity currentUser) {

        return loginLogMapper.getDTOById(id);
    }

    @Override
    public LoginLogEntity createLoginLog(LoginLogFormDTO loginLogFormDTO, EmployeeIdentity currentUser) {
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        BeanUtils.copyProperties(loginLogFormDTO, loginLogEntity);
        OrgDatasourceDTO orgDatasourceDTO = orgDatasourceClient.getBasicOrganizationByOrgId(loginLogFormDTO.getOrganizationId());
        if (orgDatasourceDTO != null){
            DynamicDataSourceContextHolder.push(orgDatasourceDTO.getDatasourceName());
        }
        this.save(loginLogEntity);
        DynamicDataSourceContextHolder.clear();
        return loginLogEntity;
    }

    @Override
    public LoginLogEntity updateLoginLog(Long id, LoginLogFormDTO loginLogFormDTO, EmployeeIdentity currentUser) {
        LoginLogEntity loginLogEntity = getById(id);
        BeanUtils.copyProperties(loginLogFormDTO, loginLogEntity);
        loginLogEntity.setId(id);

        this.updateById(loginLogEntity);
        return loginLogEntity;
    }

    @Override
    public LoginLogEntity deleteLoginLog(Long id, EmployeeIdentity currentUser) {
        LoginLogEntity loginLogEntity = getLoginLog(id, currentUser);

        this.removeById(id);
        return loginLogEntity;
    }

    @Override
    public LoginLogDTO mapperToDTO(LoginLogEntity loginLogEntity, EmployeeIdentity currentUser) {
        LoginLogDTO loginLogDTO = new LoginLogDTO();
        BeanUtils.copyProperties(loginLogEntity, loginLogDTO);
        return loginLogDTO;
    }

    @Override
    public List<LoginLogDTO> mapperToDTO(List<LoginLogEntity> loginLogEntityList, EmployeeIdentity currentUser) {
        List<LoginLogDTO> loginLogDTOs = new ArrayList<>();
        for (LoginLogEntity loginLogEntity : loginLogEntityList) {
            LoginLogDTO loginLogDTO = mapperToDTO(loginLogEntity, currentUser);
            loginLogDTOs.add(loginLogDTO);
        }


        return loginLogDTOs;
    }

    @Override
    public LoginLogDTO getLastLog(MemberIdentity member) {
        LambdaQueryWrapper<LoginLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginLogEntity::getUserId, member.getId())
                .orderByDesc(LoginLogEntity::getOperateTime)
                .last("limit 1");
        LoginLogEntity loginLogEntity = this.getOne(queryWrapper);
        LoginLogDTO loginLogDTO = new LoginLogDTO();
        BeanUtils.copyProperties(loginLogEntity, loginLogDTO);
        return loginLogDTO;
    }
}
