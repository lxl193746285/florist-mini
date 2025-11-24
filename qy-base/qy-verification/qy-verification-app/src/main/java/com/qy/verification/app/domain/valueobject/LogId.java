package com.qy.verification.app.domain.valueobject;

import java.io.Serializable;

/**
 * 验证码日志唯一标示ID
 */
public class LogId implements Serializable {
    private Long id;

    public LogId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
