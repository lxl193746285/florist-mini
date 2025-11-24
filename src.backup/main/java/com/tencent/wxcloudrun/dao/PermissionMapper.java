package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限/菜单Mapper
 */
@Mapper
public interface PermissionMapper {

    /**
     * 根据用户ID查询用户的所有权限
     */
    @Select("SELECT DISTINCT p.* FROM permission p " +
            "INNER JOIN role_permission rp ON p.id = rp.permission_id " +
            "INNER JOIN user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND p.status = 1 " +
            "ORDER BY p.sort ASC, p.id ASC")
    List<Permission> selectByUserId(Long userId);

    /**
     * 查询所有权限
     */
    @Select("SELECT * FROM permission WHERE status = 1 ORDER BY sort ASC, id ASC")
    List<Permission> selectAll();

    /**
     * 根据ID查询权限
     */
    @Select("SELECT * FROM permission WHERE id = #{id}")
    Permission selectById(Long id);

    /**
     * 根据权限编码查询
     */
    @Select("SELECT * FROM permission WHERE permission_code = #{permissionCode}")
    Permission selectByCode(String permissionCode);
}
