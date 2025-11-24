package com.qy.rbac.app.domain.auth;

import com.qy.ddd.interfaces.Entity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 权限节点
 *
 * @author legendjw
 */
@Getter
@Builder
public class Permission implements Entity {
    /**
     * id
     */
    private PermissionId id;

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
     * 修改id
     *
     * @param permissionId
     */
    public void modifyId(PermissionId permissionId) {
        this.id = permissionId;
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
}
