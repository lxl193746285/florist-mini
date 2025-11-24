package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.InvitationCodeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 组织邀请码 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-09-16
 */
@Mapper
public interface InvitationCodeMapper extends BaseMapper<InvitationCodeDO> {

}
