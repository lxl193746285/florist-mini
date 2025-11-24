package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper 接口
 */
@Mapper
public interface UserMapper {
    /**
     * 根据ID查询用户
     */
    User selectById(@Param("id") Long id);

    /**
     * 根据用户名查询用户
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 根据手机号查询用户
     */
    User selectByPhone(@Param("phone") String phone);

    /**
     * 根据邮箱查询用户
     */
    User selectByEmail(@Param("email") String email);

    /**
     * 根据账号查询用户（用户名/手机号/邮箱）
     */
    User selectByAccount(@Param("account") String account);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户
     */
    int update(User user);

    /**
     * 更新最后登录信息
     */
    int updateLastLogin(@Param("id") Long id, @Param("loginIp") String loginIp);

    /**
     * 检查用户名是否存在
     */
    int countByUsername(@Param("username") String username);

    /**
     * 检查手机号是否存在
     */
    int countByPhone(@Param("phone") String phone);

    /**
     * 检查邮箱是否存在
     */
    int countByEmail(@Param("email") String email);
}
