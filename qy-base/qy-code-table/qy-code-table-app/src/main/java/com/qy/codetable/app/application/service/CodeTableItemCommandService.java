package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.command.BatchDeleteCodeTableItemCommand;
import com.qy.codetable.app.application.command.CreateCodeTableItemCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableItemCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableItemCommand;

/**
 * 代码表细项命令服务
 *
 * @author legendjw
 */
public interface CodeTableItemCommandService {
    /**
     * 创建代码表细项
     *
     * @param command
     * @return
     */
    Long createCodeTableItem(CreateCodeTableItemCommand command);

    /**
     * 编辑代码表细项
     *
     * @param command
     */
    void updateCodeTableItem(UpdateCodeTableItemCommand command);

    /**
     * 删除代码表细项
     *
     * @param command
     */
    void deleteCodeTableItem(DeleteCodeTableItemCommand command);

    /**
     * 批量删除代码表细项
     *
     * @param command
     */
    void deleteCodeTableItem(BatchDeleteCodeTableItemCommand command);
}
