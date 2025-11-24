package com.qy.region.app.infrastructure.persistence.mybatis.mapper;

import com.qy.region.app.infrastructure.persistence.mybatis.dataobject.AreaDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 地区 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-26
 */
@Mapper
public interface AreaMapper extends BaseMapper<AreaDO> {

}
