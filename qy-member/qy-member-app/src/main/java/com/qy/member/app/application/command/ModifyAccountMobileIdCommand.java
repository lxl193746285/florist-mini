package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改账号手机id命令
 *
 * @author legendjw
 */
@Data
public class ModifyAccountMobileIdCommand {
    /**
     * 手机id
     */
    @ApiModelProperty("手机id")
    private String mobileId;
}