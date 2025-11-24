package com.qy.member.api.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更换手机号命令
 *
 * @author cxp
 */
@Data
public class UpdatePhoneCommand {
    /**
     * 新的手机号
     */
    @NotBlank(message = "请输入新的手机号")
    private String phone;
}