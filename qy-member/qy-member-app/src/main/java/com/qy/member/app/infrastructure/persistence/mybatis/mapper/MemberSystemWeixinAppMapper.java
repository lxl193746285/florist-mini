package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员系统微信应用绑定 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Mapper
public interface MemberSystemWeixinAppMapper extends BaseMapper<MemberSystemWeixinAppDO> {

}
