package com.qy.message.app.application.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TemplateButton {

    VIEW("view", "查看", 1, ""),
    UPDATE("update", "编辑", 2, ""),
    PARAMS("params", "参数配置", 3, ""),
    RULES("rules", "规则配置", 4, ""),
    CUSTOMIZE_PARAMS("customize_params", "自定义参数", 5, ""),
    DELETE("delete", "删除", 7, "")
    ;

    // 成员变量
    private String id;
    private String name;
    private int sort;
    private String permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    TemplateButton(String id, String name, int sort, String permission) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.permission = permission;
    }
}
