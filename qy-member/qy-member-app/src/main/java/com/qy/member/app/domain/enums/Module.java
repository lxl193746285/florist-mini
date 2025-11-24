package com.qy.member.app.domain.enums;

/**
 * 模块
 *
 * @author legendjw
 */
public enum Module {
    MEMBER("member", "会员");

    private String id;
    private String name;

    Module(String  id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
