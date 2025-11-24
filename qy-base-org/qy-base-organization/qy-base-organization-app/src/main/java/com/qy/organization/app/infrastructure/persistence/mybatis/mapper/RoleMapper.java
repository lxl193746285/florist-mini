package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.qy.organization.app.application.dto.AuthRoleUserDTO;
import com.qy.organization.app.application.dto.AuthRoleUserQueryDTO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.RoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 组织权限组 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-09
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

    IPage<AuthRoleUserDTO> getAuthUsers(@Param("ipage") IPage ipage, @Param("query") AuthRoleUserQueryDTO query);
}
