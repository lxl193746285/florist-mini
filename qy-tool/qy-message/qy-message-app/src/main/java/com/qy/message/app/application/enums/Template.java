package com.qy.message.app.application.enums;

public enum Template {

    MODEL_TXT(1,"固定文本"),
    MODEL_PARAMS(2,"参数匹配"),
    MODEL_SQL(3,"SQL取值"),
        ;

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Template(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 根据值获取名称
     * @param id
     * @return
     */
    public static String getName(int id) {
        for (Template m : Template.values()) {
            if (m.getId() == id) {
                return m.name;
            }
        }
        return null;
    }
}
