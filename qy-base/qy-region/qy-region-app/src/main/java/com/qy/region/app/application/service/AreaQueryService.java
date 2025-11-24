package com.qy.region.app.application.service;

import com.qy.region.app.application.dto.AdderssIdDTO;
import com.qy.region.app.application.dto.AreaDTO;
import com.qy.region.app.application.query.AreaQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.MemberIdentity;

import java.util.List;
import java.util.Map;

/**
 * 地区查询服务
 *
 * @author legendjw
 */
public interface AreaQueryService {
    /**
     * 查询地区
     *
     * @param query
     * @param identity
     * @return
     */
    List<AreaDTO> getAreas(AreaQuery query, MemberIdentity identity);
    List<AreaDTO> getAreas(AreaQuery query);
    /**
     * 查询地区
     *
     * @param query
     * @param identity
     * @return
     */
    Page<AreaDTO> getAreasPage(AreaQuery query, MemberIdentity identity);

    /**
     * 根据id获取地区
     *
     * @param areaId
     * @return
     */
    AreaDTO getAreaById(Long areaId);

    /**
     * 根据省、市、区/县、街道名称查询id
     * @param map
     * @return
     */
    AdderssIdDTO getAreaIdByCondition(Map<String, String> map);
}