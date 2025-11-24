package com.qy.common.enums;

public enum CommonAuditStatus {

    /**
     * 草稿
     */
    SAVE("草稿", -1),

    /**
     * 待审核
     */
    INIT("待审核", 0),

    /**
     * 通过
     */
    PASS("通过", 1),

    /**
     * 不通过
     */
    NO_PASS("不通过", 2),
    ;

    // 成员变量
    private String name;
    private int index;

    /**
     * 构造方法
     *
     * @param name
     * @param index
     */
    private CommonAuditStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 根据值获取名称
     *
     * @param index
     * @return
     */
    public static String getName(Integer index) {
        if (index == null) {
            return "";
        }
        for (CommonAuditStatus m : CommonAuditStatus.values()) {
            if (m.getIndex() == index) {
                return m.name;
            }
        }
        return "";
    }

    public static CommonAuditStatus getCommonAuditStatus(Integer index) {
        if (index == null) {
            return null;
        }
        for (CommonAuditStatus m : CommonAuditStatus.values()) {
            if (m.getIndex() == index) {
                return m;
            }
        }
        return null;
    }

}
