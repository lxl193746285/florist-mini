package com.qy.common.enums;

public enum CommonClientAuditStatus {

    /**
     * 通过
     */
    PASS("通过", 0),

    /**
     * 拒绝
     */
    NO_PASS("拒绝", 1)
    ;

    // 成员变量
    private String name;
    private int index;

    /**
     * 构造方法
     * @param name
     * @param index
     */
    private CommonClientAuditStatus(String name, int index) {
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
     * @param index
     * @return
     */
    public static String getName(Integer index) {
        if(index==null){
            return "";
        }
        for (CommonClientAuditStatus m : CommonClientAuditStatus.values()) {
            if (m.getIndex() == index) {
                return m.name;
            }
        }
        return "";
    }

}
