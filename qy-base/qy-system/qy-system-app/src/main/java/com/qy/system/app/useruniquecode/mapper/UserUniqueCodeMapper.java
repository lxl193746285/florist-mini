package com.qy.system.app.useruniquecode.mapper;

import com.qy.system.app.useruniquecode.entity.UserUniqueCodeEntity;
import com.qy.system.app.useruniquecode.dto.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 用户设备唯一码 Mapper 接口
 * </p>
 *
 * @author wwd
 * @since 2024-04-19
 */
public interface UserUniqueCodeMapper extends BaseMapper<UserUniqueCodeEntity> {

   IPage<UserUniqueCodeDTO> getDTOList(IPage iPage,@Param("ew")LambdaQueryWrapper<UserUniqueCodeEntity> wrapper);

   IPage<UserUniqueCodeDTO> getDTOListCondition(@Param("iPage") IPage iPage, @Param("query") UserUniqueCodeQueryDTO userUniqueCodeQueryDTO);

   List<UserUniqueCodeDTO> getDTOList(@Param("ew")LambdaQueryWrapper<UserUniqueCodeEntity> wrapper);

   UserUniqueCodeDTO getDTOById(Long id);


}
