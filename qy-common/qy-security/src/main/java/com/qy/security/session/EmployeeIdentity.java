package com.qy.security.session;

import com.qy.security.interfaces.GetPermission;
import com.qy.security.permission.action.Action;
import com.qy.security.permission.rule.EmployeeHasPermission;

import java.util.List;

/**
 * 员工身份
 *
 * @author legendjw
 */
public interface EmployeeIdentity {
    /**
     * 获取员工ID
     *
     * @return
     */
    Long getId();

    /**
     * 获取员工姓名
     *
     * @return
     */
    String getName();

    /**
     * 获取组织ID
     *
     * @return
     */
    Long getOrganizationId();

    /**
     * 部门id
     */
    Long getDepartmentId();


    /**
     * 会员系统id
     */
    Long getSystemId();

    /**
     * 会员系统id
     */
    Long getEmployeeId();

    /**
     * 当前员工是否拥有指定的权限
     *
     * @param permission
     * @return
     */
    boolean hasPermission(String permission);

    /**
     * 当前员工是否拥有指定的权限
     *
     * @param permission
     * @return
     */
    boolean hasPermission(GetPermission permission);

    /**
     * 当前员工在指定权限规则下对指定实体是否拥有指定权限
     *
     * @param employeeHasPermission
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(EmployeeHasPermission employeeHasPermission, String permission, Object id);

    /**
     * 当前员工在指定权限规则下对指定实体是否拥有指定权限
     *
     * @param employeeHasPermission
     * @param permission
     * @param id
     * @return
     */
    boolean hasPermission(EmployeeHasPermission employeeHasPermission, GetPermission permission, Object id);

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