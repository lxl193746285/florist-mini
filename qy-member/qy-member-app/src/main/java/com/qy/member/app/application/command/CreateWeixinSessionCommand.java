package com.qy.member.app.application.command;

import lombok.Data;

/**
 * 创建微信会话命令
 *
 * @author legendjw
 */
@Data
public class CreateWeixinSessionCommand {
    /**
     * 会员系统id
     */
    private String systemId;

    /**
     * 微信应用id
     */
    private String appId;

    /**
     * 授权code
     */
    private String code;
}
