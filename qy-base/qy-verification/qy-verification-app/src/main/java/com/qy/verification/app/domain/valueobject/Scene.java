package com.qy.verification.app.domain.valueobject;

import java.io.Serializable;

/**
 * 场景
 *
 * @author legendjw
 */
public class Scene implements Serializable {
    private String id;

    public Scene(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
