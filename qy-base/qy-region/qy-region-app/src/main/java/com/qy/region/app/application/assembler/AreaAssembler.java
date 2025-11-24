package com.qy.region.app.application.assembler;

import com.qy.region.app.application.dto.AreaDTO;
import com.qy.region.app.infrastructure.persistence.mybatis.dataobject.AreaDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 地区汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class AreaAssembler {
    abstract public AreaDTO toDTO(AreaDO areaDO);
    abstract public List<AreaDTO> toDTOs(List<AreaDO> areaDOs);
}
