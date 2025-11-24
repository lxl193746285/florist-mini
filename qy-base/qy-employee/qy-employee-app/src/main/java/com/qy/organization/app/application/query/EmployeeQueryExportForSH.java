package com.qy.organization.app.application.query;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeQueryExportForSH {
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


    //盛昊查询字段
    //关键字
    private String shKeywords;

    /**
     * 在职状态
     */
    private Integer shOnJobStatus;

    /**
     * 岗位编码
     */
    private String shPostCode;

    /**
     * 入职日期开始
     */
    private String shHiredateStart;

    /**
     * 入职日期结束
     */
    private String shHiredateEnd;

    /**
     * 公司id
     */
    private Long shCompanyId;

    /**
     * 部门id
     */
    private Long shDepartmentId;

    /**
     * 岗位id
     */
    private Long shJobId;

    /**
     * 职位id
     */
    private Long shPositionId;

    /**
     * 保险id
     */
    private Long shInsuranceId;



}
