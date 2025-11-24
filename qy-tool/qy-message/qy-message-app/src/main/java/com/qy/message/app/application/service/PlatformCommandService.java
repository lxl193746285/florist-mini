package com.qy.message.app.application.service;

import com.qy.message.app.application.command.CreatePlatformCommand;
import com.qy.message.app.application.command.UpdatePlatformCommand;
import com.qy.security.session.EmployeeIdentity;

/**
 * 消息平台命令服务
 *
 * @author legendjw
 */
public interface PlatformCommandService {
    /**
     * 创建消息平台命令
     *
     * @param command
     * @param identity
     * @return
     */
    Long createPlatform(CreatePlatformCommand command, EmployeeIdentity identity);

    /**
     * 编辑消息平台命令
     *
     * @param command
     * @param identity
     */
    void updatePlatform(UpdatePlatformCommand command, EmployeeIdentity identity);

    /**
     * 删除消息平台命令
     *
     * @param id
     * @param identity
     */
    void deletePlatform(Long id, EmployeeIdentity identity);
}
