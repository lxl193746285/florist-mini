package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.command.CreateClientCommand;
import com.qy.rbac.app.application.command.DeleteClientCommand;
import com.qy.rbac.app.application.command.UpdateClientCommand;

/**
 * 客户端命令服务
 *
 * @author legendjw
 */
public interface ClientCommandService {
    /**
     * 创建客户端命令
     *
     * @param command
     * @return
     */
    Long createClient(CreateClientCommand command);

    /**
     * 编辑客户端命令
     *
     * @param command
     */
    void updateClient(UpdateClientCommand command);

    /**
     * 删除客户端命令
     *
     * @param command
     */
    void deleteClient(DeleteClientCommand command);
}
