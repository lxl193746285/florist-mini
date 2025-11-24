package com.qy.organization.api.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * 员工查询
 *
 * @author legendjw
 */
@Data
public class EmployeeQuery extends PageQuery {
    /**
     * 组织id
     */
    private Long organizationId;
    /**
     * 部门id
     */
    private Long departmentId;
    /**
     * 多个部门id集合
     */
    private List<Long> departmentIds;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 岗位id
     */
    private Long jobId;
    /**
     * 多个岗位id集合
     */
    private List<Long> jobIds;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 在职状态
     */
    private Integer jobStatus;
    /**
     * 多个员工id集合
     */
    private List<Long> ids;
    /**
     * 权限节点
     */
    private String permission;
}
