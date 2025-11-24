package com.qy.organization.app.domain.enums;

/**
 * 权限组使用的上下文
 *
 * @author legendjw
 */
public enum RoleContext {
    ORGANIZATION("organization", "组织内部使用"),
    SUBORDINATE("subordinate", "下级客户使用"),
    MEMBER_SYSTEM("member_system", "会员系统使用"),
    MEMBER("member", "会员系统使用");

    private String id;
    private String name;

    RoleContext(String id, String name) {
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
