package com.qy.system.app.loginlog.mapper;

import com.qy.system.app.loginlog.entity.LoginLogEntity;
import com.qy.system.app.loginlog.dto.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 系统登录日志 Mapper 接口
 * </p>
 *
 * @author wwd
 * @since 2024-04-18
 */
public interface LoginLogMapper extends BaseMapper<LoginLogEntity> {

   IPage<LoginLogDTO> getDTOList(IPage iPage,@Param("queryDTO")LoginLogQueryDTO queryDTO);

   List<LoginLogDTO> getDTOList(@Param("queryDTO")LoginLogQueryDTO queryDTO);

   LoginLogDTO getDTOById(Long id);
}
