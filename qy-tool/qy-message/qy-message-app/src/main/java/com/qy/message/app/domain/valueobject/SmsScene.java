package com.qy.message.app.domain.valueobject;

import java.io.Serializable;

/**
 * 场景
 *
 * @author lxl
 */
public class SmsScene implements Serializable {
    private String id;

    public SmsScene(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
