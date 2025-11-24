package com.qy.security.session;

/**
 * 会话上下文
 *
 * @author legendjw
 */
public interface SessionContext {
    /**
     * 上下文id
     */
    String contextId = "identity";

    /**
     * 获取当前会话中通过身份认证的用户
     *
     * @return
     */
    Identity getUser();

    /**
     * 获取当前会话中的客户端
     *
     * @return
     */
    Client getClient();

    /**
     * 获取当前会话中的访问令牌
     *
     * @return
     */
    AccessToken getAccessToken();

    /**
     * 获取当前会话是否登录
     *
     * @return
     */
    boolean isGuest();

    /**
     * 获取当前会话用户ID
     *
     * @return
     */
    String getUserId();
}