package com.qy.comment.app.enums;

public enum SystemTypeStatus {
    OS("办公系统", 1),
    MEMBER_SYSTEM("会员系统", 2);

    // 成员变量
    private String name;
    private int index;

    /**
     * 构造方法
     * @param name
     * @param index
     */
    private SystemTypeStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 根据值获取名称
     * @param index
     * @return
     */
    public static String getName(int index) {
        for (SystemTypeStatus m : SystemTypeStatus.values()) {
            if (m.getIndex() == index) {
                return m.name;
            }
        }
        return null;
    }



}