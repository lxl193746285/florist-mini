package com.qy.codetable.app.application.enums;

/**
 * 数据结构类型
 *
 * @author legendjw
 */
public enum StructureType {
    LIST("列表"),
    TREE("树形");

    private String name;

    StructureType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}