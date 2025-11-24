package com.qy.security.session;

import java.io.Serializable;

/**
 * 客户端和JWTid
 *
 * @author legendjw
 */
public class ClientAndJti implements Serializable {
    /**
     * 客户端id
     */
    private String clientId;

    /**
     * jwt令牌id
     */
    private String jti;

    public ClientAndJti(String clientId, String jti) {
        this.clientId = clientId;
        this.jti = jti;
    }

    public String getClientId() {
        return clientId;
    }

    public String getJti() {
        return jti;
    }
}
