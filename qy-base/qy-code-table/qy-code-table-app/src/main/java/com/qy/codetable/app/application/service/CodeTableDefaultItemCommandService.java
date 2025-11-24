package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.command.CreateCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableDefaultItemCommand;

/**
 * 代码表默认细项命令服务
 *
 * @author legendjw
 */
public interface CodeTableDefaultItemCommandService {
    /**
     * 创建代码表默认细项
     *
     * @param command
     * @return
     */
    Long createCodeTableItem(CreateCodeTableDefaultItemCommand command);

    /**
     * 编辑代码表默认细项
     *
     * @param command
     */
    void updateCodeTableItem(UpdateCodeTableDefaultItemCommand command);

    /**
     * 删除代码表默认细项
     *
     * @param command
     */
    void deleteCodeTableItem(DeleteCodeTableDefaultItemCommand command);
}
