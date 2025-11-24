package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.command.CreateAppCommand;
import com.qy.rbac.app.application.command.DeleteAppCommand;
import com.qy.rbac.app.application.command.UpdateAppCommand;

import java.util.List;

/**
 * 应用命令服务
 *
 * @author legendjw
 */
public interface AppCommandService {
    /**
     * 创建应用命令
     *
     * @param command
     * @return
     */
    Long createApp(CreateAppCommand command);

    /**
     * 编辑应用命令
     *
     * @param command
     */
    void updateApp(UpdateAppCommand command);

    /**
     * 删除应用命令
     *
     * @param command
     */
    void deleteApp(DeleteAppCommand command);
}
