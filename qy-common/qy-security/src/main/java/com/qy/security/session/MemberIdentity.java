package com.qy.security.session;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.action.Action;
import com.qy.security.permission.rule.MemberHasPermission;

import java.util.List;

/**
 * 会员身份
 *
 * @author legendjw
 */
public interface MemberIdentity {
    /**
     * 获取会员ID
     *
     * @return
     */
    Long getId();

    /**
     * 获取会员名称
     *
     * @return
     */
    String getName();

    /**
     * 获取客户端ID
     *
     * @return
     */
    String getClientId();

    /**
     * 获取账号类型: 1:主账号 2: 子账号
     *
     * @return
     */
    Integer getAccountType();

    /**
     * 获取账号ID
     *
     * @return
     */
    Long getAccountId();

    /**
     * 获取账号名称
     *
     * @return
     */
    String getAccountName();

    /**
     * 获取组织ID
     *
     * @return
     */
    Long getOrganizationId();

    /**
     * 获取会员系统ID
     *
     * @return
     */
    Long getMemberSystemId();

    /**
     * 获取employeeId
     *
     * @return
     */
    Long getEmployeeId();

    /**
     * 获取会员类型(1公司员工2经销商3经销商员工)
     *
     * @return
     */
    Integer getMemberType();

    Long getStoreId();

    /**
     * 是否有指定的权限
     *
     * @param permission
     * @return
     */
    boolean hasPermission(String permission);

    /**
     * 是否有指定的权限
     *
     * @param permission
     * @return
     */
    boolean hasPermission(GetPermission permission);

    /**
     * 当前会员在指定权限规则下对指定实体是否拥有指定权限
     *
     * @param memberHasPermission
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(MemberHasPermission memberHasPermission, String permission, Object id);

    /**
     * 当前会员在指定权限规则下对指定实体是否拥有指定权限
     *
     * @param memberHasPermission
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(MemberHasPermission memberHasPermission, GetPermission permission, Object id);

    /**
     * 获取对应模块拥有的权限按钮
     *
     * @param code
     * @return
     */
    List<Action> getActions(String code);

    /**
     * 当前操作是否有权限
     *
     * @param code
     * @param permissionAction
     * @return
     */
    boolean hasPermission(String code, String permissionAction);
}