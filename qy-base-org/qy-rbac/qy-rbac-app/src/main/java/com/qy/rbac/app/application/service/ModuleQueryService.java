package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.dto.ModuleBasicDTO;
import com.qy.rbac.app.application.dto.ModuleDTO;
import com.qy.rbac.app.application.query.ModuleQuery;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 模块查询服务
 *
 * @author legendjw
 */
public interface ModuleQueryService {
    /**
     * 查询模块
     *
     * @param query
     * @param identity
     * @return
     */
    List<ModuleDTO> getModules(ModuleQuery query, Identity identity);

    /**
     * 根据ID查询模块
     *
     * @param id
     * @return
     */
    ModuleDTO getModuleById(Long id);

    /**
     * 获取基本模块数据
     *
     * @param ids
     * @return
     */
    List<ModuleBasicDTO> getBasicModulesByIds(List<Long> ids);

    /**
     * 根据应用id以及模块标示获取模块
     *
     * @param appId
     * @param code
     * @return
     */
    ModuleBasicDTO getBasicModuleByAppAndCode(Long appId, String code);
}