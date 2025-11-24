package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.SystemPassportQrcodeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统二维码 Mapper 接口
 * </p>
 *
 * @author wwd
 * @since 2024-07-26
 */
@Mapper
public interface SystemPassportQrcodeMapper extends BaseMapper<SystemPassportQrcodeDO> {

}
