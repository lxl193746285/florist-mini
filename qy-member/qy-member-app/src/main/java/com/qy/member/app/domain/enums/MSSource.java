package com.qy.member.app.domain.enums;

/**
 * 模块
 *
 * @author lxl
 */
public enum MSSource {
    CREATE(1, "创建"),
    AUTH(2, "授权");

    private int id;
    private String name;

    MSSource(int id, String name) {
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
