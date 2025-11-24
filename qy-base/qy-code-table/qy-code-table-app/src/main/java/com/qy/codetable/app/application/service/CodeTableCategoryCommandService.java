package com.qy.codetable.app.application.service;

import com.qy.codetable.app.application.command.CreateCodeTableCategoryCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableCategoryCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableCategoryCommand;

/**
 * 代码表分类命令服务
 *
 * @author legendjw
 */
public interface CodeTableCategoryCommandService {
    /**
     * 创建代码表分类
     *
     * @param command
     * @return
     */
    Long createCodeTableCategory(CreateCodeTableCategoryCommand command);

    /**
     * 编辑代码表分类
     *
     * @param command
     */
    void updateCodeTableCategory(UpdateCodeTableCategoryCommand command);

    /**
     * 删除代码表分类
     *
     * @param command
     */
    void deleteCodeTableCategory(DeleteCodeTableCategoryCommand command);
}
