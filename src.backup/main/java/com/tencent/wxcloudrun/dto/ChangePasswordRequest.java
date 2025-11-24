package com.tencent.wxcloudrun.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 修改密码请求 DTO
 */
@Data
public class ChangePasswordRequest {
    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    /**
     * 确认新密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
