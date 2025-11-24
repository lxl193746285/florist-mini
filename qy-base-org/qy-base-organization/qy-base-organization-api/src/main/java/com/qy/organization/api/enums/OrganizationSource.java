package com.qy.organization.api.enums;

/**
 * 组织注册来源
 *
 * @author legendjw
 */
public enum OrganizationSource {
    REGISTER("register", "系统注册");

    private String id;
    private String name;

    OrganizationSource(String id, String name) {
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