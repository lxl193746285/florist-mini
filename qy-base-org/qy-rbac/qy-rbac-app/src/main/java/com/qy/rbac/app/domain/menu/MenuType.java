package com.qy.rbac.app.domain.menu;

import java.util.Arrays;

/**
 * 菜单类型
 *
 * @author legendjw
 */
public enum MenuType {
    GENERAL_MENU(0, "普通菜单"),
    PERMISSION_MENU(1, "权限菜单"),
    PATH_MENU(2, "路径菜单"),
    ;

    private int id;
    private String name;

    public static MenuType getById(int id) {
        return Arrays.stream(MenuType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    MenuType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
