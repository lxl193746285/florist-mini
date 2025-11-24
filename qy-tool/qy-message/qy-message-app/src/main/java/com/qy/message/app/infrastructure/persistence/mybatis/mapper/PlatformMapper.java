package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.PlatformDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 消息系统平台 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-10-07
 */
@Mapper
public interface PlatformMapper extends BaseMapper<PlatformDO> {

}
