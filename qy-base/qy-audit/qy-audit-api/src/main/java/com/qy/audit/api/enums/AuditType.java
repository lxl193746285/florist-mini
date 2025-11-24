package com.qy.audit.api.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 审核类型
 *
 * @author legendjw
 */
public enum AuditType {
    SUBMIT(0, "提交审核"),
    PASS(1, "通过"),
    REFUSE(2, "不通过");

    private int id;
    private String name;

    AuditType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AuditType getById(int id) {
        return Arrays.stream(AuditType.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取对应的审核后状态
     *
     * @return
     */
    public AuditStatus getAuditStatus() {
        Map<AuditType, AuditStatus> map = new HashMap<>();
        map.put(SUBMIT, AuditStatus.WAITING_AUDIT);
        map.put(PASS, AuditStatus.PASSED);
        map.put(REFUSE, AuditStatus.REFUSED);

        return map.get(this);
    }
}
