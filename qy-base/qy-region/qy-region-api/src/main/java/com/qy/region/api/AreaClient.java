package com.qy.region.api;

import com.qy.region.dto.AdderssIdDTO;
import com.qy.region.dto.AreaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 地区客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-area")
public interface AreaClient {
    /**
     * 根据地区id获取地区
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/region/areas/{id}")
    AreaDTO getAreaById(
            @PathVariable(value = "id") Integer id
    );

    /**
     * 根据地区id获取地区名称
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/region/areas/{id}/name")
    String getAreaNameById(
            @PathVariable(value = "id") Integer id
    );

    /**
     * 根据省、市、区/县、街道名称查询list
     * @param province
     * @param city
     * @param county
     * @param town
     * @return
     */
    @GetMapping("/v4/api/region/areas/getAreaIdByname")
    AdderssIdDTO getAreaIdByName(
            @RequestParam(value = "province",required = false) String province,
            @RequestParam(value = "city",required = false) String city,
            @RequestParam(value = "county",required = false) String county,
            @RequestParam(value = "town",required = false) String town
    );
}
