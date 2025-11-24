package com.qy.message.app.application.enums;

public enum UserReceiveMode {

    SYSTEM(1,"系统定义"),
    USER(2,"用户定义"),
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

    UserReceiveMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 根据值获取名称
     * @param id
     * @return
     */
    public static String getName(int id) {
        for (UserReceiveMode m : UserReceiveMode.values()) {
            if (m.getId() == id) {
                return m.name;
            }
        }
        return null;
    }
}
