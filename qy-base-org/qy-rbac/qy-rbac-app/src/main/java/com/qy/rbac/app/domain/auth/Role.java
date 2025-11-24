package com.qy.rbac.app.domain.auth;

import com.qy.ddd.interfaces.Entity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色
 *
 * @author legendjw
 */
@Getter
@Builder
public class Role implements Entity {
    /**
     * id
     */
    private RoleId id;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 权限节点
     */
    private List<PermissionWithRule> permissions;

    /**
     * 修改id
     *
     * @param roleId
     */
    public void modifyId(RoleId roleId) {
        this.id = roleId;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 修改描述
     *
     * @param description
     */
    public void modifyDescription(String description) {
        this.description = description;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 授权
     *
     * @param permissions
     */
    public void authorize(List<PermissionWithRule> permissions) {
        this.permissions = permissions;
    }
}
