package com.qy.verification.app.infrastructure.persistence.mybatis.mapper;

import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 验证码发送日志 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-05
 */
@Mapper
public interface VerificationCodeLogMapper extends BaseMapper<VerificationCodeLogDO> {

}
