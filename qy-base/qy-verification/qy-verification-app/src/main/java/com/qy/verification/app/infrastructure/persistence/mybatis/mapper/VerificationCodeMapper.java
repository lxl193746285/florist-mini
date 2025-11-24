package com.qy.verification.app.infrastructure.persistence.mybatis.mapper;

import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 验证码 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-05
 */
@Mapper
public interface VerificationCodeMapper extends BaseMapper<VerificationCodeDO> {

}
