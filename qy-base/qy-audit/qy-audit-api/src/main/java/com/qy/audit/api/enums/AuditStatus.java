package com.qy.audit.api.enums;

import java.util.Arrays;

/**
 * 审核类型
 *
 * @author legendjw
 */
public enum AuditStatus {
    DRAFT(-1, "草稿"),
    WAITING_AUDIT(0, "待审核"),
    PASSED(1, "通过"),
    REFUSED(2, "不通过");

    private int id;
    private String name;

    public static AuditStatus getById(int id) {
        return Arrays.stream(AuditStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    AuditStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
