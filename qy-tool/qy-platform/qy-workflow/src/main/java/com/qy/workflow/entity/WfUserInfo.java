package com.qy.workflow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_用户基本信息
 *
 * @author iFeng
 * @since 2022-11-26
 */
@Data
public class WfUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long UserId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 部门ID
     */
    private  Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

}
