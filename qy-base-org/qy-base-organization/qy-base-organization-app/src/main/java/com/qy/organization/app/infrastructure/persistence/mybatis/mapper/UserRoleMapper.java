package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 组织用户权限组关联 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-09
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
    Integer getPersonNum(Long roleId);
}
