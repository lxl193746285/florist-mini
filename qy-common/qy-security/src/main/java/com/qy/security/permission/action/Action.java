package com.qy.security.permission.action;

/**
 * 操作动作
 *
 * @author legendjw
 */
public class Action {
    private String id;
    private String name;

    public Action(String id, String name) {
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