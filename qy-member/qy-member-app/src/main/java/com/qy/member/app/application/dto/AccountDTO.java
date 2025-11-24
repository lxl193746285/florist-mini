package com.qy.member.app.application.dto;

import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员详情
 *
 * @author legendjw
 */
@Data
public class AccountDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 会员系统id
     */
    private Long systemId;
    /**
     * 名称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

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
     * 权限组
     */
    private List<RoleBasicDTO> roles = new ArrayList<>();

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
