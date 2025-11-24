package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.command.BatchDeleteMenuCommand;
import com.qy.rbac.app.application.command.CreateMenuCommand;
import com.qy.rbac.app.application.command.DeleteMenuCommand;
import com.qy.rbac.app.application.command.UpdateMenuCommand;
import com.qy.rbac.app.domain.menu.MenuId;

/**
 * 菜单命令服务
 *
 * @author legendjw
 */
public interface MenuCommandService {
    /**
     * 创建普通菜单
     *
     * @param command
     * @return
     */
    MenuId createGeneralMenu(CreateMenuCommand command);

    MenuId createPathMenu(CreateMenuCommand command);

    /**
     * 创建权限菜单
     *
     * @param command
     * @return
     */
    MenuId createPermissionMenu(CreateMenuCommand command);

    /**
     * 更新普通菜单
     *
     * @param command
     */
    void updateGeneralMenu(UpdateMenuCommand command);

    void updatePathMenu(UpdateMenuCommand command);

    /**
     * 更新权限菜单
     *
     * @param command
     */
    void updatePermissionMenu(UpdateMenuCommand command);

    /**
     * 删除菜单
     *
     * @param command
     */
    void deleteMenu(DeleteMenuCommand command);

    /**
     * 批量删除菜单
     *
     * @param command
     */
    void batchDeleteMenu(BatchDeleteMenuCommand command);

    /**
     * 批量修复菜单权限
     */
    void batchRepairMenuPermission();
}
