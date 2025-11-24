package com.qy.security.session;

/**
 * 访问令牌
 *
 * @author legendjw
 */
public interface AccessToken {
    /**
     * 获取访问令牌
     *
     * @return
     */
    String getAccessToken();
}