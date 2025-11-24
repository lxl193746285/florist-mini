package com.qy.security.session;

/**
 * 会员系统会话上下文
 *
 * @author legendjw
 */
public interface MemberSystemSessionContext {
    /**
     * 上下文id
     */
    String contextId = "member_system";

    /**
     * 获取当前会话中通过身份认证的会员
     *
     * @return
     */
    MemberIdentity getMember();

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
}