package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 绑定微信命令
 *
 * @author legendjw
 */
@Data
public class BindWeixinBySessionCommand {
    /**
     * 微信token
     */
    @NotBlank(message = "请输入微信token")
    private String weixinToken;

    /**
     * 账号id
     */
    private Long accountId;

    private Long organizationId;

    private Long systemId;
}