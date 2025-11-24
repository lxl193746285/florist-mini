package com.qy.system.app.config.mapper;

import com.qy.system.app.config.entity.SystemConfigCategoryEntity;
import com.qy.system.app.config.dto.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 配置类别 Mapper 接口
 * </p>
 *
 * @author hh
 * @since 2024-07-09
 */
@Mapper
public interface SystemConfigCategoryMapper extends BaseMapper<SystemConfigCategoryEntity> {

   IPage<SystemConfigCategoryDTO> getDTOList(IPage iPage,@Param("ew")LambdaQueryWrapper<SystemConfigCategoryEntity> wrapper);

   List<SystemConfigCategoryDTO> getDTOList(@Param("ew")LambdaQueryWrapper<SystemConfigCategoryEntity> wrapper);

   SystemConfigCategoryDTO getDTOById(Long id);

   IPage<SystemConfigCategoryDTO> selectPageList(@Param("iPage") IPage iPage, @Param("queryDTO") SystemConfigCategoryQueryDTO queryDTO);
}
