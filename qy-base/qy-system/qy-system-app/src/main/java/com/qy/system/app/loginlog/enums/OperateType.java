package com.qy.system.app.loginlog.enums;

import java.util.Arrays;

/**
 * 用户状态
 *
 * @author legendjw
 */
public enum OperateType {
    LOGIN(1, "登录"),
    LOGOUT(2, "登出");

    private int id;
    private String name;

    public static OperateType getById(int id) {
        return Arrays.stream(OperateType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    OperateType(int id, String name) {
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
