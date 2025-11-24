package com.qy.organization.app.domain.enums;

public enum WechatUserStatus {

    UNBOUND("未绑定", 0),
    BIND("已绑定", 1),
    NOT_CONCERNED("未关注", 2),
    HAS_BEEN_CONCERNED("已关注", 3),
    NOT_FOUND_USER("未找到该用户",4),
    ;


    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    WechatUserStatus(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
