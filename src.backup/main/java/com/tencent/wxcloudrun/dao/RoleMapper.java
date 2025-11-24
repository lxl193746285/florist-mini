package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色 Mapper 接口
 */
@Mapper
public interface RoleMapper {
    /**
     * 根据用户ID查询角色列表
     */
    List<Role> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据角色编码查询
     */
    Role selectByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 批量插入用户角色关联
     */
    int insertUserRoles(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds);
}
