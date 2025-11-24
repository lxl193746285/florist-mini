package com.qy.authorization.app.application.service;

import com.qy.authorization.app.application.command.CreateAuthClientCommand;
import com.qy.authorization.app.application.command.UpdateAuthClientCommand;

import java.util.List;

/**
 * 授权客户端服务
 *
 * @author legendjw
 */
public interface AuthClientService {
    /**
     * 创建授权客户端
     *
     * @param command
     */
    void createAuthClient(CreateAuthClientCommand command);

    /**
     * 更新授权客户端
     *
     * @param command
     */
    void updateAuthClient(UpdateAuthClientCommand command);

    /**
     * 删除授权客户端
     *
     * @param clientId
     */
    void deleteAuthClient(String clientId);

    List<String> getAllClientId();
}
