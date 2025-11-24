package com.qy.security.session;

/**
 * 组织会话上下文
 *
 * @author legendjw
 */
public interface OrganizationSessionContext {
    /**
     * 上下文id
     */
    String contextId = "organization";

    /**
     * 获取当前会话中通过身份认证的用户
     *
     * @return
     */
    Identity getUser();

    /**
     * 获取当前认证用户默认组织下的员工
     *
     * @return
     */
    EmployeeIdentity getEmployee();

    /**
     * 获取指定组织下当前通过认证的员工
     *
     * @return
     */
    EmployeeIdentity getEmployee(Long organizationId);

    /**
     * 获取指定组织会员系统下当前通过认证的员工
     *
     * @return
     */
    EmployeeIdentity getEmployee(Long organizationId, Long systemId);

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