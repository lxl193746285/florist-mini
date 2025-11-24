package com.qy.identity.app.infrastructure.persistence.mybatis.mapper;

import com.qy.identity.app.application.dto.EmployeeDO;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-13
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    EmployeeDO getEmployeeDO(String username);
}
