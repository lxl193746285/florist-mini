package com.qy.rbac.app.application.service.impl;

import com.qy.authorization.api.client.AuthClientClient;
import com.qy.authorization.api.command.CreateAuthClientCommand;
import com.qy.authorization.api.command.UpdateAuthClientCommand;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.rbac.app.application.command.CreateClientCommand;
import com.qy.rbac.app.application.command.DeleteClientCommand;
import com.qy.rbac.app.application.command.UpdateClientCommand;
import com.qy.rbac.app.application.service.ClientCommandService;
import com.qy.rbac.app.infrastructure.persistence.ClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ClientDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 客户端命令实现
 *
 * @author legendjw
 */
@Service
public class ClientCommandServiceImpl implements ClientCommandService {
    private ClientDataRepository clientDataRepository;
    private CodeTableClient codeTableClient;
    private AuthClientClient authClientClient;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ClientCommandServiceImpl(ClientDataRepository clientDataRepository, CodeTableClient codeTableClient, AuthClientClient authClientClient) {
        this.clientDataRepository = clientDataRepository;
        this.codeTableClient = codeTableClient;
        this.authClientClient = authClientClient;
    }

    @Override
    @Transactional
    public Long createClient(CreateClientCommand command) {
        Identity identity = command.getIdentity();
        if (clientDataRepository.countByName(command.getName(), null) > 0) {
            throw new ValidationException("指定客户端名称已经存在，请更换新的名称");
        }
        if (clientDataRepository.countByClientId(command.getClientId(), null) > 0) {
            throw new ValidationException("指定客户端id已经存在，请更换新的id");
        }

        ClientDO clientDO = new ClientDO();
        BeanUtils.copyProperties(command, clientDO, "identity");
        if (StringUtils.isNotBlank(command.getClientSecret())) {
            clientDO.setClientSecret(passwordEncoder.encode(command.getClientId()));
        }
        clientDO.setCreatorId(identity.getId());
        clientDO.setCreatorName(identity.getName());
        clientDO.setCreateTime(LocalDateTime.now());
        fillFormRelatedData(clientDO);
        clientDataRepository.save(clientDO);

        //创建授权客户端
        CreateAuthClientCommand createAuthClientCommand = new CreateAuthClientCommand();
        createAuthClientCommand.setClientId(command.getClientId());
        createAuthClientCommand.setClientSecret(command.getClientSecret());
        authClientClient.createClient(createAuthClientCommand);

        return clientDO.getId();
    }

    @Override
    @Transactional
    public void updateClient(UpdateClientCommand command) {
        Identity identity = command.getIdentity();
        ClientDO clientDO = clientDataRepository.findById(command.getId());
        if (clientDO == null) {
            throw new NotFoundException("未找到指定的客户端");
        }
        String oldClientId = clientDO.getClientId();
        if (clientDataRepository.countByName(command.getName(), clientDO.getId()) > 0) {
            throw new ValidationException("指定客户端名称已经存在，请更换新的名称");
        }
        if (clientDataRepository.countByClientId(command.getClientId(), clientDO.getId()) > 0) {
            throw new ValidationException("指定客户端id已经存在，请更换新的id");
        }

        BeanUtils.copyProperties(command, clientDO, "identity");
        if (StringUtils.isNotBlank(command.getClientSecret())) {
            clientDO.setClientSecret(passwordEncoder.encode(command.getClientId()));
        }
        clientDO.setUpdatorId(identity.getId());
        clientDO.setUpdatorName(identity.getName());
        clientDO.setUpdateTime(LocalDateTime.now());
        fillFormRelatedData(clientDO);
        clientDataRepository.save(clientDO);

        //更新授权客户端
        UpdateAuthClientCommand updateAuthClientCommand = new UpdateAuthClientCommand();
        updateAuthClientCommand.setClientId(command.getClientId());
        updateAuthClientCommand.setClientSecret(command.getClientSecret());
        authClientClient.updateClient(oldClientId, updateAuthClientCommand);
    }

    @Override
    public void deleteClient(DeleteClientCommand command) {
        ClientDO clientDO = clientDataRepository.findById(command.getId());
        if (clientDO == null) {
            throw new NotFoundException("未找到指定的客户端");
        }
        Identity identity = command.getIdentity();
        clientDataRepository.remove(command.getId(), identity);

        //删除授权客户端
        authClientClient.deleteClient(clientDO.getClientId());
    }

    /**
     * 填充表单关联数据
     *
     * @param clientDO
     */
    private void fillFormRelatedData(ClientDO clientDO) {

    }
}