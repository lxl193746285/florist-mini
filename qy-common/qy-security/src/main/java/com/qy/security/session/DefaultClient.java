package com.qy.security.session;

/**
 * 客户端默认实现
 *
 * @author legendjw
 */
public class DefaultClient implements Client {
    private String clientId;

    public DefaultClient(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }
}
