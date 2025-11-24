package com.qy.system.app.config.mapper;

import com.qy.system.app.config.entity.SystemConfigEntity;
import com.qy.system.app.config.dto.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 配置 Mapper 接口
 * </p>
 *
 * @author hh
 * @since 2024-07-09
 */
@Mapper
public interface SystemConfigMapper extends BaseMapper<SystemConfigEntity> {

    SystemConfigSearchDTO getDataByAttribute(String attribute,String categoryIdentifier);

    IPage<SystemConfigDTO> getDTOList(IPage iPage, @Param("ew")LambdaQueryWrapper<SystemConfigEntity> wrapper);

    List<SystemConfigDTO> getDTOList(@Param("ew")LambdaQueryWrapper<SystemConfigEntity> wrapper);

    SystemConfigDTO getDTOById(Long id);

    IPage<SystemConfigDTO> selectPageList(@Param("iPage") IPage iPage, @Param("queryDTO") SystemConfigQueryDTO queryDTO);
}
