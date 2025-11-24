package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.command.CreateModuleCommand;
import com.qy.rbac.app.application.command.DeleteModuleCommand;
import com.qy.rbac.app.application.command.UpdateModuleCommand;

/**
 * 模块命令服务
 *
 * @author legendjw
 */
public interface ModuleCommandService {
    /**
     * 创建模块命令
     *
     * @param command
     * @return
     */
    Long createModule(CreateModuleCommand command);

    /**
     * 编辑模块命令
     *
     * @param command
     */
    void updateModule(UpdateModuleCommand command);

    /**
     * 删除模块命令
     *
     * @param command
     */
    void deleteModule(DeleteModuleCommand command);
}
