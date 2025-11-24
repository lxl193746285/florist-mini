package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageDO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageWithUserDO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 消息系统消息 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-10-07
 */
@Mapper
public interface MessageMapper extends BaseMapper<MessageDO> {
    IPage<MessageWithUserDO> selectUserMessages(IPage<MessageWithUserDO> page, @Param("ew") Wrapper<MessageWithUserDO> wrapper);
    Integer selectUserMessageCount(@Param("ew") Wrapper<MessageWithUserDO> wrapper);
}
