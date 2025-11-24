package com.qy.organization.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更改员工登录手机号命令
 *
 * @author legendjw
 */
@Data
public class ChangeEmployeeUserPhoneCommand {
    /**
     * 员工id
     */
    @JsonIgnore
    private Long id;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "请输入手机号")
    private String phone;
}
