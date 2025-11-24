package com.qy.identity.app.domain.repository;

import com.qy.identity.app.application.dto.EmployeeDO;
import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.domain.valueobject.UserId;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserLogDO;

/**
 * 用户资源库
 *
 * @author legendjw
 */
public interface UserRepository {
    /**
     * 通过Id获取用户
     *
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据账号名获取用户，账号名可以为用户名以及手机号以及邮箱
     *
     * @param account
     * @return
     */
    User findByAccount(String account);

    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 通过手机号获取用户
     *
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 通过邮箱获取用户
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 保存一个用户
     *
     * @param user
     * @return
     */
    UserId saveUser(User user);

    /**
     * 保存用户登录日志
     *
     * @param userLogDO
     * @return
     */
    UserLogDO saveUserLog(UserLogDO userLogDO);
    /**
     * 保存用户登录日志
     *
     * @param username
     * @return
     */
    EmployeeDO getEmployeeDO(String username);


}