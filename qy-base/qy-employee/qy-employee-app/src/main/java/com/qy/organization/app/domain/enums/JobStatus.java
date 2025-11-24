package com.qy.organization.app.domain.enums;

import java.util.Arrays;

/**
 * 在职状态
 *
 * @author legendjw
 */
public enum JobStatus {
    ON_JOB(1, "在职"),
    LEAVE_JOB(0, "离职");

    private int id;
    private String name;

    public static JobStatus getById(int id) {
        return Arrays.stream(JobStatus.values()).filter(a -> a.id == id).findFirst().orElse(null);
    }

    JobStatus(int id, String name) {
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
