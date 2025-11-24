package com.qy.member.app.application.dto;

import com.qy.rbac.api.dto.AppBasicDTO;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员系统DTO
 *
 * @author legendjw
 */
@Data
public class MemberSystemDTO {
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
     * 系统类型id
     */
    private Integer typeId;

    /**
     * 系统类型名称
     */
    private String typeName;

    /**
     * 会员类型id
     */
    private Integer memberTypeId;

    /**
     * 会员类型名称
     */
    private String memberTypeName;

    /**
     * 名称
     */
    private String name;

    /**
     * 会员注册是否需要审核
     */
    private Byte isMemberAudit;

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

    private List<AppBasicDTO> apps;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
