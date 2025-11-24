package com.qy.member.api.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信登录命令
 *
 * @author legendjw
 */
@Data
public class LoginByMiniCommand {
    /**
     * 会员系统id
     */
    private String systemId;

    /**
     * 会员系统id
     */
    private String appId;

    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 组织id
     */
    private Long organizationId;
    /**
     * 授权手机号
     */
    private String phone;

    /**
     * 授权code
     */
    @NotBlank(message = "请输入授权code")
    private String code;

    private Integer memberType;
}