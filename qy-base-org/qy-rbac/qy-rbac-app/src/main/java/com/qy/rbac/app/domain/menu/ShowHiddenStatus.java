package com.qy.rbac.app.domain.menu;

import java.util.Arrays;

/**
 * 显示隐藏状态
 *
 * @author legendjw
 */
public enum ShowHiddenStatus {
    SHOW(1, "显示"),
    HIDDEN(0, "隐藏");

    private int id;
    private String name;

    public static ShowHiddenStatus getById(int id) {
        return Arrays.stream(ShowHiddenStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    public static String getNameById(int id) {
        ShowHiddenStatus status = getById(id);
        return status == null ? "" : status.getName();
    }

    ShowHiddenStatus(int id, String name) {
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
