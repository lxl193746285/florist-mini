package com.qy.organization.app.application.command;

import lombok.Data;

import java.util.List;

/**
 * 会员系统及权限组ID集合
 *
 * @author lxl
 */
@Data
public class SystemRoleCommand {
    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 权限组ID集合
     */
    private List<Long> roleIds;

}
