package com.qy.organization.api.command;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织部门
 * </p>
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Data
public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 父级名称
     */
    private String parentName;

    /**
     * 名称
     */
    private String name;

    /**
     * 部门负责人
     */
    private Long leaderId;

    /**
     * 部门负责人名称
     */
    private String leaderName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private String createTimeName;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private String updateTimeName;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
