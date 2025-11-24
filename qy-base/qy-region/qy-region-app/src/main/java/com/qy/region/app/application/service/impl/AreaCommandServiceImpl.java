package com.qy.region.app.application.service.impl;

import com.qy.region.app.application.assembler.AreaAssembler;
import com.qy.region.app.application.command.CreateAreaCommand;
import com.qy.region.app.application.command.UpdateAreaCommand;
import com.qy.region.app.application.service.AreaCommandService;
import com.qy.region.app.infrastructure.persistence.AreaDataRepository;
import com.qy.region.app.infrastructure.persistence.mybatis.dataobject.AreaDO;
import com.qy.rest.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaCommandServiceImpl implements AreaCommandService {

    private AreaDataRepository areaDataRepository;

    public AreaCommandServiceImpl(AreaDataRepository areaDataRepository) {
        this.areaDataRepository = areaDataRepository;
    }

    @Override
    public void createArea(CreateAreaCommand command) {
        AreaDO aDo = areaDataRepository.findById(command.getId());
        if (aDo != null) {
            throw new ValidationException("编码重复");
        }
        AreaDO area = new AreaDO();
        area.setId(command.getId());
        area.setName(command.getName());
        area.setShortName(command.getName());
        area.setParentId(command.getParentId());
        area.setSort(command.getSort());
        area.setPopulation(command.getPopulation());
        area.setCode(command.getId().toString());
        if (command.getParentId() != null && command.getParentId() != 0) {
            AreaDO areaDO = areaDataRepository.findById(command.getParentId());
            if (areaDO != null) {
                if (areaDO.getLevel() == 1) {
                    area.setCity(command.getName());
                } else if (areaDO.getLevel() == 2) {
                    area.setCity(areaDO.getCity());
                    area.setCountry(command.getName());
                } else if (areaDO.getLevel() == 3) {
                    area.setCity(areaDO.getCity());
                    area.setCountry(areaDO.getCountry());
                    area.setStreet(command.getName());
                }
                area.setLevel(areaDO.getLevel() + 1);
                area.setProvince(areaDO.getProvince());

            }
        }
        areaDataRepository.createArea(area);
    }


    @Override
    public void updateArea(UpdateAreaCommand command) {
        AreaDO area = areaDataRepository.findById(command.getId());
        area.setName(command.getName());
        area.setShortName(command.getName());
        area.setParentId(command.getParentId());
        area.setSort(command.getSort());
        if (command.getParentId() != null && command.getParentId() != 0) {
            AreaDO areaDO = areaDataRepository.findById(command.getParentId());
            if (areaDO != null) {
                if (areaDO.getLevel() == 1) {
                    area.setCity(command.getName());
                } else if (areaDO.getLevel() == 2) {
                    area.setCity(areaDO.getCity());
                    area.setCountry(command.getName());
                } else if (areaDO.getLevel() == 3) {
                    area.setCity(areaDO.getCity());
                    area.setCountry(areaDO.getCountry());
                    area.setStreet(command.getName());
                }
                area.setLevel(areaDO.getLevel() + 1);
                area.setProvince(areaDO.getProvince());
            }
        }
        areaDataRepository.updateArea(area);
    }


    @Override
    public void deleteArea(Long id) {
        areaDataRepository.deleteArea(id);
    }
}
