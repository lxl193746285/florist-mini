package com.qy.authorization.app.application.service;

import com.qy.authorization.app.application.command.GenerateAccessTokenCommand;
import com.qy.authorization.app.application.command.RefreshAccessTokenCommand;
import com.qy.authorization.app.application.dto.AccessTokenDTO;
import com.qy.authorization.app.application.dto.ValidateClientResultDTO;

/**
 * 授权服务
 *
 * @author legendjw
 */
public interface AuthorizationService {
    /**
     * 验证客户端
     *
     * @param clientId
     * @param clientSecret
     */
    ValidateClientResultDTO validateClient(String clientId, String clientSecret);

    /**
     * 生成一个用户访问令牌
     *
     * @param command
     * @return
     */
    AccessTokenDTO generateAccessToken(GenerateAccessTokenCommand command);

    /**
     * 刷新token
     *
     * @param command
     * @return
     */
    AccessTokenDTO refreshAccessToken(RefreshAccessTokenCommand command);

    void deleteUserAccessToken(Long userId);
}
