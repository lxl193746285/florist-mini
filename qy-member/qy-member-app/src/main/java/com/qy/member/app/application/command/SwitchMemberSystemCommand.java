package com.qy.member.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 切换会员系统
 *
 * @author legendjw
 */
@Data
public class SwitchMemberSystemCommand {
    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    @JsonIgnore
    private String clientId;

    /**
     * 账号id
     */
    @ApiModelProperty("账号id")
    @JsonIgnore
    private Long accountId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 会员系统id
     */
    @ApiModelProperty("会员系统id")
    private Long systemId;

    /**
     * appid
     */
    @ApiModelProperty("appid")
    private String appId;

    /**
     * 手机型号
     */
    @ApiModelProperty(value = "手机型号")
    private String phoneModel;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;

    private String extraData;

    private Integer isException;
}