package com.qy.region.app.application.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.region.app.application.assembler.AreaAssembler;
import com.qy.region.app.application.dto.AdderssIdDTO;
import com.qy.region.app.application.dto.AreaDTO;
import com.qy.region.app.application.query.AreaQuery;
import com.qy.region.app.application.service.AreaQueryService;
import com.qy.region.app.infrastructure.persistence.AreaDataRepository;
import com.qy.region.app.infrastructure.persistence.mybatis.dataobject.AreaDO;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.permission.action.Action;
import com.qy.security.session.MemberIdentity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 地区查询服务实现
 *
 * @author legendjw
 */
@Service
public class AreaQueryServiceImpl implements AreaQueryService {
    private AreaAssembler areaAssembler;
    private AreaDataRepository areaDataRepository;

    public AreaQueryServiceImpl(AreaAssembler areaAssembler, AreaDataRepository areaDataRepository) {
        this.areaAssembler = areaAssembler;
        this.areaDataRepository = areaDataRepository;
    }

    @Override
    public List<AreaDTO> getAreas(AreaQuery query, MemberIdentity identity) {
        List<AreaDO> areaDOS = areaDataRepository.findByQuery(query);
        List<AreaDTO> dtos = areaAssembler.toDTOs(areaDOS);
        List<Action> actions = identity.getActions("ark_sys_region_area");
        dtos.stream().map(areaDTO -> {
            areaDTO.setActions(actions);
            areaDTO.setHasChildren(areaDataRepository.hasChildren(areaDTO.getId()));
            return areaDTO;
        }).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<AreaDTO> getAreas(AreaQuery query) {
        List<AreaDO> areaDOS = areaDataRepository.findByQuery(query);
        List<AreaDTO> dtos = areaAssembler.toDTOs(areaDOS);
        dtos.stream().map(areaDTO -> {
            areaDTO.setHasChildren(areaDataRepository.hasChildren(areaDTO.getId()));
            return areaDTO;
        }).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public com.qy.rest.pagination.Page<AreaDTO> getAreasPage(AreaQuery query, MemberIdentity identity) {
        IPage<AreaDO> iPage = areaDataRepository.findByQueryPage(query);
        com.qy.rest.pagination.Page<AreaDTO> dtoPage =new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), areaAssembler.toDTOs(iPage.getRecords()));
        List<Action> actions = identity.getActions("ark_sys_region_area");
        dtoPage.getRecords().stream().map(areaDTO -> {
            areaDTO.setActions(actions);
            areaDTO.setHasChildren(areaDataRepository.hasChildren(areaDTO.getId()));
            return areaDTO;
        }).collect(Collectors.toList());
        return dtoPage;
    }

    @Override
    public AreaDTO getAreaById(Long areaId) {
        AreaDO areaDO = areaDataRepository.findById(areaId);
        return areaAssembler.toDTO(areaDO);
    }

    @Override
    public AdderssIdDTO getAreaIdByCondition(Map<String, String> map) {
        AdderssIdDTO adderssIdDTO = new AdderssIdDTO();
        String province = map.get("province");
        String city = map.get("city");
        String county = map.get("county");
        String town = map.get("town");
        AreaQuery query = new AreaQuery();
        if (!StringUtils.isEmpty(province)) {
            query.setKeywords(province);
            AreaDO provinceAreaDO = areaDataRepository.findByCondition(query);
            if (provinceAreaDO != null) {
                adderssIdDTO.setProvinceId(provinceAreaDO.getId());//省
                if (!StringUtils.isEmpty(city)) {
                    query.setKeywords(city);
                    query.setParentId(provinceAreaDO.getId());
                    AreaDO cityAreaDO = areaDataRepository.findByCondition(query);
                    if (cityAreaDO != null) {
                        adderssIdDTO.setCityId(cityAreaDO.getId());//市
                        if (!StringUtils.isEmpty(county)) {
                            query.setParentId(cityAreaDO.getId());
                            query.setKeywords(county);
                            AreaDO countyAreaDO = areaDataRepository.findByCondition(query);
                            if (countyAreaDO != null) {
                                adderssIdDTO.setCountyId(countyAreaDO.getId());//区/县
                                if (!StringUtils.isEmpty(town)) {
                                    query.setParentId(countyAreaDO.getParentId());
                                    query.setKeywords(countyAreaDO.getName());
                                    AreaDO townAreaDO = areaDataRepository.findByCondition(query);
                                    if (townAreaDO != null) {
                                        adderssIdDTO.setTownId(townAreaDO.getId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return adderssIdDTO;
    }
}
