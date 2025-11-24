package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.command.CreateCodeTableCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableCommand;

/**
 * 代码表命令服务
 *
 * @author legendjw
 */
public interface CodeTableCommandService {
    /**
     * 创建代码表
     *
     * @param command
     * @return
     */
    Long createCodeTable(CreateCodeTableCommand command);

    /**
     * 编辑代码表
     *
     * @param command
     */
    void updateCodeTable(UpdateCodeTableCommand command);

    /**
     * 删除代码表
     *
     * @param command
     */
    void deleteCodeTable(DeleteCodeTableCommand command);
}
