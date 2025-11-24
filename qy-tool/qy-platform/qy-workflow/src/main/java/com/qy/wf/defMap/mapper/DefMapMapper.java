package com.qy.wf.defMap.mapper;

import com.qy.wf.defMap.entity.DefMapEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 工作流_设计_映射表 记录业务表与工作流id关联 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2022-11-21
 */
@Mapper
public interface DefMapMapper extends BaseMapper<DefMapEntity> {

}
