package com.qy.organization.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更改员工登录用户名命令
 *
 * @author legendjw
 */
@Data
public class ChangeEmployeeUsernameCommand {
    /**
     * 员工id
     */
    @JsonIgnore
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "请输入用户名")
    private String username;
}
