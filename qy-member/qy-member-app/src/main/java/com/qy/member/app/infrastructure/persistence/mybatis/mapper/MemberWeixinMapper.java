package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberWeixinDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员系统会员微信绑定 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Mapper
public interface MemberWeixinMapper extends BaseMapper<MemberWeixinDO> {

}
