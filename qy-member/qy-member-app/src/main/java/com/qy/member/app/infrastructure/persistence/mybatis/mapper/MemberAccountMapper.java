package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员系统会员账号 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Mapper
public interface MemberAccountMapper extends BaseMapper<MemberAccountDO> {

}
