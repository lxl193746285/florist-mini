package com.qy.organization.api.dto;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织权限组
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Data
public class RoleDTO implements Serializable {
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
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称拼音
     */
    private String namePinyin;

    /**
     * 授权项
     */
    private String authItem;

    /**
     * 岗位描述
     */
    private String description;

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
     * 默认权限组类型
     */
    private String defaultRole;

    /**
     * 默认权限组类型名称
     */
    private String defaultRoleName;

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
     * 人员
     */
    private List<RoleUserDTO> users = new ArrayList<>();

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
