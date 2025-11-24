package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemOrganizationDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员系统 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Mapper
public interface MemberSystemOrganizationMapper extends BaseMapper<MemberSystemOrganizationDO> {

}
