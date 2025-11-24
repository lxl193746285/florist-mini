package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.assembler.ClientAssembler;
import com.qy.rbac.app.application.dto.ClientBasicDTO;
import com.qy.rbac.app.application.dto.ClientDTO;
import com.qy.rbac.app.application.query.ClientQuery;
import com.qy.rbac.app.application.service.ClientQueryService;
import com.qy.rbac.app.infrastructure.persistence.ClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ClientDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户端查询服务实现
 *
 * @author legendjw
 */
@Service
public class ClientQueryServiceImpl implements ClientQueryService {
    private ClientAssembler clientAssembler;
    private ClientDataRepository clientDataRepository;

    public ClientQueryServiceImpl(ClientAssembler clientAssembler, ClientDataRepository clientDataRepository) {
        this.clientAssembler = clientAssembler;
        this.clientDataRepository = clientDataRepository;
    }

    @Override
    public Page<ClientDTO> getClients(ClientQuery query, Identity identity) {
        Page<ClientDO> clientDOPage = clientDataRepository.findByQuery(query);
        return clientDOPage.map(client -> clientAssembler.toDTO(client, identity));
    }

    @Override
    public ClientDTO getClientById(Long id) {
        ClientDO clientDO = clientDataRepository.findById(id);
        return clientAssembler.toDTO(clientDO, null);
    }

    @Override
    public ClientDTO getClientByClientId(String clientId) {
        ClientDO clientDO = clientDataRepository.findByClientId(clientId);
        return clientAssembler.toDTO(clientDO, null);
    }

    @Override
    public List<ClientBasicDTO> getBasicClientsByIds(List<Long> ids) {
        List<ClientDO> clientDOS = clientDataRepository.findByIds(ids);
        return clientDOS.stream().map(client -> clientAssembler.toBasicDTO(client)).collect(Collectors.toList());
    }
}
