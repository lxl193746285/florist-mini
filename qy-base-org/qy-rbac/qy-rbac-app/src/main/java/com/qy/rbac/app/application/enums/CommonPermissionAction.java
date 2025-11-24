package com.qy.rbac.app.application.enums;

import java.util.Arrays;

/**
 * 通用权限操作
 *
 * @author legendjw
 */
public enum CommonPermissionAction {
    MENU("menu", "菜单", 2, "页面权限"),
    LIST("index", "列表", 2, "页面权限"),
    CREATE("create", "新增", 2, "页面权限"),
    UPDATE("update", "编辑", 1, "数据权限"),
    VIEW("view", "查看", 1, "数据权限"),
    DELETE("delete", "删除", 1, "数据权限"),
    SORT("sort", "排序", 1, "数据权限"),
    EXPORT("export", "导出",2, "页面权限"),
    IMPORT("import", "导入", 2, "页面权限"),
    STATUS("status", "更改状态", 1, "数据权限"),
    AUDIT("audit", "审核", 1, "数据权限");

    private String id;
    private String name;

    private int type;
    private String typeName;

    public static CommonPermissionAction getById(String id) {
        return Arrays.stream(CommonPermissionAction.values()).filter(a -> a.id.equals(id)).findFirst().orElse(null);
    }

    CommonPermissionAction(String id, String name, int type, String typeName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
    public String getTypeName() {
        return typeName;
    }
}
