package com.qy.identity.app.domain.service;

import com.qy.identity.app.domain.enums.UserSource;
import com.qy.identity.app.domain.valueobject.*;
import com.qy.identity.app.domain.valueobject.*;

/**
 * 用户领域服务
 *
 * @author legendjw
 */
public interface UserDomainService {
    /**
     * 创建一个用户
     *
     * @param nickname
     * @param username
     * @param phone
     * @param email
     * @param password
     * @param source
     * @return
     */
    UserId create(String nickname, Username username, PhoneNumber phone, Email email, Password password, UserSource source);

    /**
     * 用户更换手机号
     *
     * @param userId
     * @param phoneNumber
     */
    void changePhone(UserId userId, PhoneNumber phoneNumber);

    /**
     * 用户更换邮箱
     *
     * @param userId
     * @param email
     */
    void changeEmail(UserId userId, Email email);

    /**
     * 用户更换用户名
     *
     * @param userId
     * @param username
     */
    void changeUsername(UserId userId, Username username);

    /**
     * 用户修改密码
     *
     * @param userId
     * @param password
     */
    void modifyPassword(UserId userId, Password password);

    /**
     * 用户登录
     *
     * @param userId
     * @param clientId
     * @param userAgent
     * @param ip
     */
    void login(UserId userId, String clientId, String userAgent, String ip);

    /**
     * 用户退出
     *
     * @param userId
     * @param clientId
     * @param userAgent
     * @param ip
     */
    void logout(UserId userId, String clientId, String userAgent, String ip);
}
