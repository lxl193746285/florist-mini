package com.qy.identity.app.application.service;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.identity.app.application.command.*;
import com.qy.identity.app.application.command.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 账号命令服务
 *
 * @author legendjw
 */
public interface AccountCommandService {
    /**
     * 注册账号
     *
     * @param command
     * @return
     */
    AccessTokenDTO registerAccount(RegisterAccountCommand command);

    /**
     * 密码登录
     *
     * @param command
     * @return
     */
    AccessTokenDTO loginByPassword(LoginByPasswordCommand command, HttpServletRequest request);

    /**
     * 手机验证码登录
     *
     * @param command
     * @param request
     * @return
     */
    AccessTokenDTO loginByPhone(LoginByPhoneCommand command, HttpServletRequest request);

    /**
     * 刷新令牌
     *
     * @param command
     * @return
     */
    AccessTokenDTO refreshToken(RefreshTokenCommand command);

    /**
     * 通过手机号找回密码
     *
     * @param command
     */
    void retrievePasswordByPhone(RetrievePasswordByPhoneCommand command);

    /**
     * 退出登录
     *
     * @param command
     * @param request
     */
    void logout(LogoutCommand command, HttpServletRequest request);

    AccessTokenDTO generateAccessToken (String clientId, Long userId);
}
