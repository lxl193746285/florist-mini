package com.qy.uims.security.action;

import com.qy.rbac.api.client.MenuClient;
import com.qy.rest.context.ApplicationContextHolder;
import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.action.Action;
import org.apache.commons.lang3.StringUtils;

/**
 * 权限操作
 *
 * @author legendjw
 */
public class PermissionAction implements GetPermission {
    /**
     * 操作id
     */
    private String id;
    /**
     * 操作名称
     */
    private String name;
    /**
     * 权限节点
     */
    private String permission;
    /**
     * 是否是列表操作权限
     */
    private Boolean isListAction;
    /**
     * 菜单客户端
     */
    private MenuClient menuClient;

    public PermissionAction(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public PermissionAction(String id, String name, String permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
    }

    public PermissionAction(String id, String name, String permission, Boolean isListAction) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.isListAction = isListAction;
    }

    public static PermissionAction create(String id, String name, String permission, Boolean isListAction) {
        return new PermissionAction(id, name, permission, isListAction);
    }

    public static PermissionAction create(String id, String name, String permission) {
        return new PermissionAction(id, name, permission, true);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        initMenuClient();
        if (name == null){
            String menuName = menuClient.getMenuNameByAuthItem(permission);
            return StringUtils.isNotBlank(menuName) ? menuName : name;
        }
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public Boolean getListAction() {
        return isListAction;
    }

    /**
     * 转换为操作动作
     *
     * @return
     */
    public Action toAction() {
        return new Action(getId(), getName());
    }

    /**
     * 初始化菜单客户端
     */
    private void initMenuClient() {
        if (menuClient == null) {
            MenuClient bean = (MenuClient) ApplicationContextHolder.getBeanByClass(MenuClient.class);
            menuClient = bean;
        }
    }
}