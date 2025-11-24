package com.qy.base.interfaces.region.api;

import com.qy.region.app.application.dto.AdderssIdDTO;
import com.qy.region.app.application.dto.AreaDTO;
import com.qy.region.app.application.service.AreaQueryService;
import com.qy.rest.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 地区内部服务
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/region/areas")
public class AreaApiController {
    private AreaQueryService areaQueryService;

    public AreaApiController(AreaQueryService areaQueryService) {
        this.areaQueryService = areaQueryService;
    }

    /**
     * 根据地区id获取地区
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> getArea(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(areaQueryService.getAreaById(id));
    }

    /**
     * 根据地区id获取地区
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/name")
    public ResponseEntity<String> getAreaName(
            @PathVariable(value = "id") Long id
    ) {
        AreaDTO areaDTO = areaQueryService.getAreaById(id);
        return ResponseUtils.ok().body(areaDTO != null ? areaDTO.getName() : "");
    }

    /**
     * 根据省、市、区/县、街道名称查询id
     * @param province
     * @param city
     * @param county
     * @param town
     * @return
     */
    @GetMapping("/getAreaIdByname")
    public ResponseEntity<AdderssIdDTO> getAreaIdByName(
            @RequestParam(value = "province",required = false) String province,
            @RequestParam(value = "city",required = false) String city,
            @RequestParam(value = "county",required = false) String county,
            @RequestParam(value = "town",required = false) String town
    ) {
        Map<String,String> map = new HashMap<>();
        if (!"".equals(province) && province != null) {
            map.put("province",province);
        }
        if (!"".equals(city) && city != null) {
            map.put("city",city);
        }
        if (!"".equals(county) && county != null) {
            map.put("county",county);
        }
        if (!"".equals(town) && town != null) {
            map.put("town",town);
        }
        AdderssIdDTO adderssIdDTO = areaQueryService.getAreaIdByCondition(map);
        return ResponseUtils.ok().body(adderssIdDTO);
    }
}