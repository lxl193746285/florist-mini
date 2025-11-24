package com.qy.security.session;

/**
 * 访问令牌默认实现
 *
 * @author legendjw
 */
public class DefaultAccessToken implements AccessToken {
    private String accessToken;

    public DefaultAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }
}