package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.command.CreateAppCommand;
import com.qy.rbac.app.application.command.DeleteAppCommand;
import com.qy.rbac.app.application.command.UpdateAppCommand;
import com.qy.rbac.app.application.dto.ClientBasicDTO;
import com.qy.rbac.app.application.service.AppCommandService;
import com.qy.rbac.app.infrastructure.persistence.AppClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.AppDataRepository;
import com.qy.rbac.app.infrastructure.persistence.AppMemberSystemRepository;
import com.qy.rbac.app.infrastructure.persistence.ClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppClientDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppMemberSystemDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ClientDO;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用命令实现
 *
 * @author legendjw
 */
@Service
public class AppCommandServiceImpl implements AppCommandService {
    private AppDataRepository appDataRepository;
    private AppMemberSystemRepository appMemberSystemRepository;
    private AppClientDataRepository appClientDataRepository;
    private ClientDataRepository clientDataRepository;

    public AppCommandServiceImpl(AppDataRepository appDataRepository, AppMemberSystemRepository appMemberSystemRepository,
                                 AppClientDataRepository appClientDataRepository, ClientDataRepository clientDataRepository) {
        this.appDataRepository = appDataRepository;
        this.appMemberSystemRepository = appMemberSystemRepository;
        this.appClientDataRepository = appClientDataRepository;
        this.clientDataRepository = clientDataRepository;
    }

    @Override
    @Transactional
    public Long createApp(CreateAppCommand command) {
        Identity identity = command.getIdentity();
        if (appDataRepository.countByName(command.getName(), null) > 0) {
            throw new ValidationException("指定应用名称已经存在，请更换新的名称");
        }
        if (appClientDataRepository.countByClientIds(command.getClientIds(), null) > 0) {
            throw new ValidationException("同一个客户端不能被多个应用使用");
        }

        AppDO appDO = new AppDO();
        BeanUtils.copyProperties(command, appDO, "identity");
        appDO.setStatusName(EnableDisableStatus.getNameById(appDO.getStatusId()));
        appDO.setCreatorId(identity.getId());
        appDO.setCreatorName(identity.getName());
        appDO.setCreateTime(LocalDateTime.now());
        fillFormRelatedData(appDO);
        appDataRepository.save(appDO);

        if (command.getClientIds() != null && !command.getClientIds().isEmpty()) {
            // 创建应用和会员系统的关系
            List<ClientDO> clientDOList = clientDataRepository.findByIds(command.getClientIds());
            // 过滤重复的会员系统id
            List<Long> distinctSystemIds = clientDOList.stream().map(ClientDO::getSystemId).distinct().collect(Collectors.toList());
            for (Long systemId : distinctSystemIds) {
                AppMemberSystemDO appMemberSystemDO = new AppMemberSystemDO();
                appMemberSystemDO.setAppId(appDO.getId());
                appMemberSystemDO.setOrganizationId(command.getOrganizationId());
                appMemberSystemDO.setMbrSysId(systemId);
                appMemberSystemDO.setCreatorId(identity.getId());
                appMemberSystemDO.setCreateTime(LocalDateTime.now());
                appMemberSystemRepository.save(appMemberSystemDO);
            }
            // 创建应用和客户端的关系
            for (Long clientId : command.getClientIds()) {
                AppClientDO appClientDO = new AppClientDO();
                appClientDO.setAppId(appDO.getId());
                appClientDO.setClientId(clientId);
                appClientDataRepository.save(appClientDO);
            }
        }
        return appDO.getId();
    }

    @Override
    @Transactional
    public void updateApp(UpdateAppCommand command) {
        Identity identity = command.getIdentity();
        AppDO appDO = appDataRepository.findById(command.getId());
        if (appDO == null) {
            throw new NotFoundException("未找到指定的应用");
        }
        if (appDataRepository.countByName(command.getName(), appDO.getId()) > 0) {
            throw new ValidationException("指定应用名称已经存在，请更换新的名称");
        }
        if (appClientDataRepository.countByClientIds(command.getClientIds(), command.getId()) > 0) {
            throw new ValidationException("同一个客户端不能被多个应用使用");
        }

        BeanUtils.copyProperties(command, appDO, "identity");
        appDO.setStatusName(EnableDisableStatus.getNameById(appDO.getStatusId()));
        appDO.setUpdatorId(identity.getId());
        appDO.setUpdatorName(identity.getName());
        appDO.setUpdateTime(LocalDateTime.now());
        fillFormRelatedData(appDO);
        appDataRepository.save(appDO);

        // 删除应用和客户端的关系
        appClientDataRepository.deleteByAppId(appDO.getId());
        // 删除应用和会员系统的关系
        appMemberSystemRepository.removeByAppId(appDO.getId(), identity);
        if (command.getClientIds() != null && !command.getClientIds().isEmpty()) {
            // 创建应用和会员系统的关系
            List<ClientDO> clientDOList = clientDataRepository.findByIds(command.getClientIds());
            // 过滤重复的会员系统id
            List<Long> distinctSystemIds = clientDOList.stream().map(ClientDO::getSystemId).distinct().collect(Collectors.toList());
            for (Long systemId : distinctSystemIds) {
                AppMemberSystemDO appMemberSystemDO = new AppMemberSystemDO();
                appMemberSystemDO.setAppId(appDO.getId());
                appMemberSystemDO.setOrganizationId(command.getOrganizationId());
                appMemberSystemDO.setMbrSysId(systemId);
                appMemberSystemDO.setCreatorId(identity.getId());
                appMemberSystemDO.setCreateTime(LocalDateTime.now());
                appMemberSystemRepository.save(appMemberSystemDO);
            }
            // 创建应用和客户端的关系
            for (Long clientId : command.getClientIds()) {
                AppClientDO appClientDO = new AppClientDO();
                appClientDO.setAppId(appDO.getId());
                appClientDO.setClientId(clientId);
                appClientDataRepository.save(appClientDO);
            }
        }
    }

    @Override
    public void deleteApp(DeleteAppCommand command) {
        Identity identity = command.getIdentity();

        appDataRepository.remove(command.getId(), identity);
        // 删除应用和客户端的关系
        appClientDataRepository.deleteByAppId(command.getId());
        // 删除应用和会员系统的关系
        appMemberSystemRepository.removeByAppId(command.getId(), identity);
    }

    /**
     * 填充表单关联数据
     *
     * @param appDO
     */
    private void fillFormRelatedData(AppDO appDO) {

    }
}