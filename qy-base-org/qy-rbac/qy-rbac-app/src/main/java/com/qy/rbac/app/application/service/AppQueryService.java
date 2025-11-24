package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.dto.AppBasicDTO;
import com.qy.rbac.app.application.dto.AppDTO;
import com.qy.rbac.app.application.query.AppQuery;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 应用查询服务
 *
 * @author legendjw
 */
public interface AppQueryService {
    /**
     * 查询应用
     *
     * @param query
     * @param identity
     * @return
     */
    List<AppDTO> getApps(AppQuery query, Identity identity);

    /**
     * 根据ID查询应用
     *
     * @param id
     * @return
     */
    AppDTO getAppById(Long id);

    /**
     * 获取基本应用数据
     *
     * @param ids
     * @return
     */
    List<AppBasicDTO> getBasicAppsByIds(List<Long> ids);

    /**
     * 根据客户端id获取基本应用数据
     *
     * @param clientId
     * @return
     */
    AppBasicDTO getBasicAppByClientId(String clientId);

    /**
     * 根据应用标示获取应用
     *
     * @param appCode
     * @return
     */
    AppBasicDTO getBasicAppByCode(String appCode);

    List<AppBasicDTO> getBasicAppsBySystemId(List<Long> ids, String systemId);

    /**
     * 根据客户端的ids获取应用
     *
     * @param clientIds
     * @return
     */
    List<AppBasicDTO> getBasicAppsByClentIds(List<Long> clientIds);

}