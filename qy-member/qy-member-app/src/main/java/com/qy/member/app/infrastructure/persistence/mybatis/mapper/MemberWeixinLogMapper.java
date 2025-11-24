package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberWeixinLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员系统微信绑定日志 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-09-03
 */
@Mapper
public interface MemberWeixinLogMapper extends BaseMapper<MemberWeixinLogDO> {

}
