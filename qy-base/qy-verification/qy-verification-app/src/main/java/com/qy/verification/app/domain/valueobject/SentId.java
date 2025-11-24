package com.qy.verification.app.domain.valueobject;

import java.io.Serializable;

/**
 * 发送验证码的唯一标示ID
 *
 * @author legendjw
 */
public class SentId implements Serializable {
    private Long id;

    public SentId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
