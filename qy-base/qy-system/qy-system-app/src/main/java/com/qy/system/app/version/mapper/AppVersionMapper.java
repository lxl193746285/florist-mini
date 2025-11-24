package com.qy.system.app.version.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.system.app.version.dto.AppVersionDTO;
import com.qy.system.app.version.entity.AppVersionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * APP版本 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2024-05-21
 */
@Mapper
public interface AppVersionMapper extends BaseMapper<AppVersionEntity> {

   IPage<AppVersionDTO> getDTOList(IPage iPage, @Param("ew")LambdaQueryWrapper<AppVersionEntity> wrapper);

   List<AppVersionDTO> getDTOList(@Param("ew")LambdaQueryWrapper<AppVersionEntity> wrapper);

   AppVersionDTO getDTOById(Long id);
}
