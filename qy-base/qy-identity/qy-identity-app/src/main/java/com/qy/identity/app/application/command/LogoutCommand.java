package com.qy.identity.app.application.command;

import lombok.Data;

/**
 * 账号退出登录命令
 *
 * @author legendjw
 */
@Data
public class LogoutCommand {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 客户端
     */
    private String clientId;
}