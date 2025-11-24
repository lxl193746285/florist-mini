package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.dto.ClientBasicDTO;
import com.qy.rbac.app.application.dto.ClientDTO;
import com.qy.rbac.app.application.query.ClientQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 客户端查询服务
 *
 * @author legendjw
 */
public interface ClientQueryService {
    /**
     * 查询客户端
     *
     * @param query
     * @param identity
     * @return
     */
    Page<ClientDTO> getClients(ClientQuery query, Identity identity);

    /**
     * 根据ID查询客户端
     *
     * @param id
     * @return
     */
    ClientDTO getClientById(Long id);

    /**
     * 根据客户端id查询客户端
     *
     * @param clientId
     * @return
     */
    ClientDTO getClientByClientId(String clientId);

    /**
     * 获取基本客户端数据
     *
     * @param ids
     * @return
     */
    List<ClientBasicDTO> getBasicClientsByIds(List<Long> ids);
}