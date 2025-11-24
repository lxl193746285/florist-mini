package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 消息系统消息用户 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-10-07
 */
@Mapper
public interface MessageUserMapper extends BaseMapper<MessageUserDO> {

}
